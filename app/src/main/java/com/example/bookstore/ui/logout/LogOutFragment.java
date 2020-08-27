package com.example.bookstore.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;

import com.example.bookstore.databinding.FragmentLogoutBinding;

public class LogOutFragment extends Fragment {
    FragmentLogoutBinding binding;
    public static LogOutFragment newInstance() {

        Bundle args = new Bundle();

        LogOutFragment fragment = new LogOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_logout,container,false);
        return binding.getRoot();
    }
}