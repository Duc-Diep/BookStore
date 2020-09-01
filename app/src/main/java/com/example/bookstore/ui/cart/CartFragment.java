package com.example.bookstore.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.bookstore.ui.book.IonClickBook;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    SQLHelper sqlHelper;
    List<Book> listCart,allBook;

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false);
        listCart = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        listCart = sqlHelper.getAllBookInCart();
        allBook = sqlHelper.getAllBook();
        BookAdapter adapter = new BookAdapter(listCart,getContext());
        adapter.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Fragment fragment = BookItemInfo.newInstance(allBook,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3, RecyclerView.VERTICAL,false);
        binding.listBookInCart.setAdapter(adapter);
        binding.listBookInCart.setLayoutManager(gridLayoutManager);
        return binding.getRoot();
    }

}
