package com.example.bookstore.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentBookItemInforBinding;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class BookItemInfo extends Fragment {
    FragmentBookItemInforBinding binding;
    Book book;
    Fragment me = this;
    public static BookItemInfo newInstance(Book book) {
        
        Bundle bundle = new Bundle();
        bundle.putSerializable("book",book);
        BookItemInfo fragment = new BookItemInfo();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_item_infor,container,false);
        book = (Book) getArguments().getSerializable("book");
        Picasso.with(getContext()).load(book.getImageLink()).into(binding.imgBook);
        binding.tvBookName.setText(book.getTitle());
        binding.tvAuthor.setText(book.getAuthor());
        binding.tvNumberOfPage.setText(String.valueOf(book.getNumOfPage()+"trang"));
        Locale local =new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(book.getPrice());
        binding.btnPrice.setText(money+"đ");
        binding.btnBackToListBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager =getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                manager.getBackStackEntryCount();
                transaction.remove(me);
                transaction.commit();
            }
        });
        return binding.getRoot();
    }
}
