package com.example.bookstore.loginactivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.example.bookstore.databinding.ActivityLoginBinding;
import com.example.bookstore.event.EHome;
import com.example.bookstore.event.ELogin;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar ac = getSupportActionBar();
        ac.hide();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getFragment(LoginFragment.newInstance());
    }
    public void getFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment_login,fragment).commit();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"getFragment"+e.getMessage());
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void Event(EHome eHome){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}