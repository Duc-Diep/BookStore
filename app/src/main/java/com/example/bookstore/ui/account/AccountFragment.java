package com.example.bookstore.ui.account;

import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;
import static com.example.bookstore.AccountAttribute.ACCOUNT_STATUS;
import static com.example.bookstore.AccountAttribute.SHARE_PRE_NAME;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        if (getStatus()) {
            binding.email.setVisibility(View.VISIBLE);
            binding.avatar.setVisibility(View.VISIBLE);
            binding.layoutPic.setVisibility(View.VISIBLE);
            binding.layoutAddress.setVisibility(View.VISIBLE);
            binding.layoutEmailAddress.setVisibility(View.VISIBLE);
            binding.layoutPhone.setVisibility(View.VISIBLE);
            binding.isLogin.setVisibility(View.GONE);
            binding.btnLoginAndLogout.setText(getString(R.string.sign_out));
        } else {
            binding.btnLoginAndLogout.setText(getString(R.string.sign_in));
        }
        binding.btnLoginAndLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStatus()) {
                    binding.email.setVisibility(View.GONE);
                    binding.avatar.setVisibility(View.GONE);
                    binding.layoutPic.setVisibility(View.GONE);
                    binding.layoutAddress.setVisibility(View.GONE);
                    binding.layoutEmailAddress.setVisibility(View.GONE);
                    binding.layoutPhone.setVisibility(View.GONE);
                    binding.isLogin.setVisibility(View.VISIBLE);
                    binding.btnLoginAndLogout.setText(getString(R.string.sign_in));
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(ACCOUNT_STATUS, false);
                    editor.apply();
                } else {
                    Bus.getInstance().post(new ELogin());
                }

            }
        });
        return binding.getRoot();
    }

    public boolean getStatus() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
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
