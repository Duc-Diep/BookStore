package com.example.bookstore.ui.bookinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentBookInfoBinding;


public class BookInfoFragment extends Fragment {
    FragmentBookInfoBinding binding;
    public static BookInfoFragment newInstance() {

        Bundle args = new Bundle();

        BookInfoFragment fragment = new BookInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_info,container,false);
        return binding.getRoot();
    }
}
