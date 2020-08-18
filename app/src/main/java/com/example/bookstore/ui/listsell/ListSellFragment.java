package com.example.bookstore.ui.listsell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentListsellBinding;

public class ListSellFragment extends Fragment {
    FragmentListsellBinding binding;
    public static ListSellFragment newInstance() {

        Bundle args = new Bundle();

        ListSellFragment fragment = new ListSellFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_listsell,container,false);
        return binding.getRoot();
    }
}
