package com.example.bookstore.ui.introduce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentIntroduceBinding;

public class IntroduceFragment extends Fragment {
    FragmentIntroduceBinding binding;

    public static IntroduceFragment newInstance() {
        
        Bundle args = new Bundle();
        
        IntroduceFragment fragment = new IntroduceFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_introduce,container,false);
        
        return binding.getRoot();
    }
}
