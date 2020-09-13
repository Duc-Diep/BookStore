package com.example.bookstore.loginactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentLoginBinding;
import com.example.bookstore.event.Bus;
import com.example.bookstore.event.EHome;

import static com.example.bookstore.AccountAttribute.ACCOUNT_STATUS;
import static com.example.bookstore.AccountAttribute.SHARE_PRE_NAME;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    public static LoginFragment newInstance() {

        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getInstance().post(new EHome());
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(ACCOUNT_STATUS,true);
                editor.apply();
                Toast.makeText(getContext(),getString(R.string.login_sucess),Toast.LENGTH_SHORT).show();
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
