package com.example.bookstore.ui.loginactivity;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentLoginBinding;
import com.example.bookstore.event.Bus;
import com.example.bookstore.event.EHome;
import com.example.bookstore.sqlhelper.SQLHelper;
import com.example.bookstore.ui.account.Account;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookstore.AccountAttribute.ACCOUNT_ADDRESS;
import static com.example.bookstore.AccountAttribute.ACCOUNT_FULL_NAME;
import static com.example.bookstore.AccountAttribute.ACCOUNT_PHONE;
import static com.example.bookstore.AccountAttribute.ACCOUNT_STATUS;
import static com.example.bookstore.AccountAttribute.SHARE_PRE_NAME;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    List<Account> list;
    SQLHelper sqlHelper;
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
        list = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        list = sqlHelper.getAllAccount();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtUserName.getText().toString();
                String password = binding.edtPasWord.getText().toString();
                if(username.length()>0&&password.length()>0){
                    boolean check = false;
                    for (Account x: list) {
                        if(x.getPhone().equals(username)&&x.getPassword().equals(password)){
                            Bus.getInstance().post(new EHome());
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(ACCOUNT_STATUS,true);
                            editor.putString(ACCOUNT_FULL_NAME,x.getFullName());
                            editor.putString(ACCOUNT_PHONE,x.getPhone());
                            editor.putString(ACCOUNT_ADDRESS,x.getAddress());
                            editor.apply();
                            check = true;
                            Toast.makeText(getContext(),getString(R.string.login_sucess),Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(!check){
                        Toast.makeText(getContext(),getString(R.string.check_login_fail),Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(),getString(R.string.check_null),Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.newAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = RegistrationFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_fragment_login, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
