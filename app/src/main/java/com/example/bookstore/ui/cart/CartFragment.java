package com.example.bookstore.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentCartBinding;
import com.example.bookstore.event.Bus;
import com.example.bookstore.event.EHideToolBar;
import com.example.bookstore.event.ELogin;
import com.example.bookstore.sqlhelper.SQLHelper;
import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.ui.book.BookItemInfo;
import com.example.bookstore.ui.book.IlongClickBook;
import com.example.bookstore.ui.book.IonClickBook;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bookstore.AccountAttribute.ACCOUNT_STATUS;
import static com.example.bookstore.AccountAttribute.SHARE_PRE_NAME;

public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    SQLHelper sqlHelper;
    List<Book> listCart, allBook;
    int pos=0;

    public static CartFragment newInstance() {

        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        listCart = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());

        allBook = sqlHelper.getAllBook();
        setLayout();

        return binding.getRoot();
    }
    public void setLayout(){
        listCart = sqlHelper.getAllBookInCart();
        CartAdapter adapter = new CartAdapter(listCart, getContext());
        adapter.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Bus.getInstance().post(new EHideToolBar());
                Fragment fragment = BookItemInfo.newInstance(allBook, book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        adapter.setIonClickDelete(new IonClickDelete() {
            @Override
            public void onClickItem(Book book) {
                onDialogShow(book);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        binding.listBookInCart.setAdapter(adapter);
        binding.listBookInCart.setLayoutManager(gridLayoutManager);
        Locale local = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(TotalMoney());
        if (TotalMoney() != 0) {
//            Toast.makeText(getContext(), getString(R.string.delete_item_cart), Toast.LENGTH_SHORT).show();
            binding.btnBuy.setText(getString(R.string.buy) + " " + money + " vnđ");
        } else {
            binding.btnBuy.setText(getString(R.string.nothing));
        }
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStatus()) {
                    if (listCart.size() > 0) {
                        onDialogOptionBuyShow();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.nothing), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    onDialogLoginShow();
                }
            }
        });
    }
    private double TotalMoney() {
        double price = 0;
        for (Book x : listCart
        ) {
            price += x.getPrice();
        }
        return price;
    }

    private void onDialogShow(final Book book) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.confirm))
                .setMessage(getString(R.string.delete_item_request))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),getString(R.string.delete_success),Toast.LENGTH_SHORT).show();
                        sqlHelper.deleteItemInCart(String.valueOf(book.getId()));
                        setLayout();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }

    private void onDialogLoginShow() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.un_login))
                .setMessage(getString(R.string.sign_in_now))
                .setIcon(R.drawable.user)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bus.getInstance().post(new ELogin());
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }

    private void onDialogOptionBuyShow() {

        boolean[] booleans = {true, false, false, false};
        final List<String> strings = Arrays.asList(getResources().getStringArray(R.array.option_buy));
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.select_option_buy))
                //.setMessage("Yes or No")
                .setIcon(R.drawable.pay)
                .setSingleChoiceItems(R.array.option_buy, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        pos=position;
                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqlHelper.deleteCart();
                        for (Book x : listCart
                        ) {
                            sqlHelper.InsertBookToHistory(x);
                        }
                        listCart.clear();
                        CartAdapter adapter = new CartAdapter(listCart, getContext());
                        binding.listBookInCart.setAdapter(adapter);
                        binding.btnBuy.setText(getString(R.string.nothing));
                        Log.d("LOG",String.valueOf(which));
                        Toast.makeText(getContext(),getString(R.string.your_choice) +" "+ strings.get(pos) , Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), getString(R.string.delete_trans), Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
    }
    public boolean getStatus() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bus.getInstance().register(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        Bus.getInstance().register(getContext());
    }
}
