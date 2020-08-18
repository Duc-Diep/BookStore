package com.example.bookstore.ui.home;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentHomeBinding;
import com.example.bookstore.ui.bookinfo.BookInfoFragment;
import com.example.bookstore.ui.search.SearchByNameFragment;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        binding.moreBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = BookInfoFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                //fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }

}
