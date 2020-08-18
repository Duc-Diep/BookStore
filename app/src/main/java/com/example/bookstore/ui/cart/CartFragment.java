package com.example.bookstore.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {
    FragmentCartBinding binding;


    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false);
        return binding.getRoot();
    }

}
