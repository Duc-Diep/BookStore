package com.example.bookstore.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.bookstore.sqlhelper.SQLHelper;
import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.ui.book.BookItemInfo;
import com.example.bookstore.ui.book.IlongClickBook;
import com.example.bookstore.ui.book.IonClickBook;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    SQLHelper sqlHelper;
    List<Book> listCart, allBook;

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
        listCart = sqlHelper.getAllBookInCart();
        allBook = sqlHelper.getAllBook();
        BookAdapter adapter = new BookAdapter(listCart, getContext());
        adapter.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Fragment fragment = BookItemInfo.newInstance(allBook, book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        adapter.setIlongClickBook(new IlongClickBook() {
            @Override
            public void longClickItem(Book book) {
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
            binding.btnBuy.setText(getString(R.string.buy) + " " + money + " vnđ");
        } else {
            binding.btnBuy.setText(getString(R.string.nothing));
        }
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCart.size()>0) {
                    Toast.makeText(getContext(), getString(R.string.buy_success), Toast.LENGTH_SHORT).show();
                    sqlHelper.deleteCart();
                    for (Book x : listCart
                    ) {
                        sqlHelper.InsertBookToHistory(x);
                    }
                    listCart.clear();
                    BookAdapter adapter = new BookAdapter(listCart, getContext());
                    binding.listBookInCart.setAdapter(adapter);
                    binding.btnBuy.setText(getString(R.string.nothing));
                } else {
                    Toast.makeText(getContext(), getString(R.string.nothing), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return binding.getRoot();
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
                        sqlHelper.deleteItemInCart(String.valueOf(book.getId()));
                        listCart = sqlHelper.getAllBookInCart();
                        BookAdapter adapter = new BookAdapter(listCart, getContext());
                        adapter.setIonClickBook(new IonClickBook() {
                            @Override
                            public void onClickItem(Book book) {
                                Fragment fragment = BookItemInfo.newInstance(allBook, book);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });
                        adapter.setIlongClickBook(new IlongClickBook() {
                            @Override
                            public void longClickItem(Book book) {
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
                            binding.btnBuy.setText(getString(R.string.buy) + " " + money + "đ");
                        } else {
                            binding.btnBuy.setText(getString(R.string.nothing));
                        }
                        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), getString(R.string.buy_success), Toast.LENGTH_SHORT);

                            }
                        });
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }
}
