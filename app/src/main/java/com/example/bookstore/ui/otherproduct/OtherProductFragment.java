package com.example.bookstore.ui.otherproduct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentOtherProductBinding;

public class OtherProductFragment extends Fragment {
    FragmentOtherProductBinding binding;

    public static OtherProductFragment newInstance() {
        
        Bundle args = new Bundle();
        
        OtherProductFragment fragment = new OtherProductFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_other_product,container,false);
        return binding.getRoot();
    }
}
