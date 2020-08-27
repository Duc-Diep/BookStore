package com.example.bookstore.ui.listbookinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.ui.book.Book;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentBookItemInforBinding;


public class ListBookInfoFragment extends Fragment {
    FragmentBookItemInforBinding binding;

    public static ListBookInfoFragment newInstance(Book book) {

        Bundle args = new Bundle();

        ListBookInfoFragment fragment = new ListBookInfoFragment();
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
