package com.example.bookstore.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;

import com.example.bookstore.databinding.FragmentAccountBinding;
import com.example.bookstore.event.Bus;
import com.example.bookstore.event.ELogin;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;
    public static AccountFragment newInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account,container,false);
        binding.btnLoginAndLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getInstance().post(new ELogin());
            }
        });
        return binding.getRoot();
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
