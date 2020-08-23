package com.example.bookstore.ui.bookinfo;

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

import com.example.bookstore.Book;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentBookItemInforBinding;


public class BookInfoFragment extends Fragment {
    FragmentBookItemInforBinding binding;

    public static BookInfoFragment newInstance(Book book) {

        Bundle args = new Bundle();

        BookInfoFragment fragment = new BookInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_book_info,container,false);


        return binding.getRoot();
    }
}
