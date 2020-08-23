package com.example.bookstore;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.bookstore.databinding.ActivityMainBinding;
import com.example.bookstore.ui.bookinfo.BookInfoFragment;
import com.example.bookstore.ui.cart.CartFragment;
import com.example.bookstore.ui.home.HomeFragment;
import com.example.bookstore.ui.listsell.ListSellFragment;
import com.example.bookstore.ui.search.SearchByNameFragment;
import com.example.bookstore.ui.upload.UploadFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.hideOverflowMenu();
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_search, R.id.nav_cart, R.id.nav_upload,R.id.nav_listSell)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.nav_cart:
//                        getFragment(CartFragment.newInstance());
//                        drawer.closeDrawers();
//                    case R.id.nav_search:
//                        getFragment(SearchByNameFragment.newInstance());
//                        drawer.closeDrawers();
//                    case R.id.nav_listSell:
//                        getFragment(ListSellFragment.newInstance());
//                        drawer.closeDrawers();
//                    case R.id.nav_upload:
//                        getFragment(UploadFragment.newInstance());
//                        drawer.closeDrawers();
//                }
//                return false;
//            }
//        });
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

}
