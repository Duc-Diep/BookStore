package com.example.bookstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bookstore.databinding.ActivityMainBinding;

import com.example.bookstore.event.ECloseApp;
import com.example.bookstore.event.EHideToolBar;
import com.example.bookstore.event.ELogin;
import com.example.bookstore.event.EShowToolBar;
import com.example.bookstore.loginactivity.LoginActivity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.bookstore.AccountAttribute.ACCOUNT_STATUS;
import static com.example.bookstore.AccountAttribute.SHARE_PRE_NAME;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";
    //Button btnVi,btnEng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //set language
//        btnEng = findViewById(R.id.btnEnglish);
//        btnVi = findViewById(R.id.btnTiengViet);
//        btnEng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnEng.setBackgroundResource(R.drawable.bgr_language);
//                btnVi.setBackgroundResource(R.drawable.bgr_language_none);
//            }
//        });
//        btnVi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnVi.setBackgroundResource(R.drawable.bgr_language);
//                btnEng.setBackgroundResource(R.drawable.bgr_language_none);
//            }
//        });

        //set ToolBar
        setSupportActionBar(binding.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cart,R.id.nav_history, R.id.nav_other_product,R.id.nav_account)
                .setDrawerLayout(binding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    public void getFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"getFragment"+e.getMessage());
        }
    }
    @Override
    //gọi cái navigation ra
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void Event(EHideToolBar eHideToolBar){
        binding.toolbar.setVisibility(View.GONE);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void Event(EShowToolBar eShowToolBar){
        binding.toolbar.setVisibility(View.VISIBLE);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void Event(ELogin eLogin){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void Event(ECloseApp eCloseApp){
        finish();
    }

}
