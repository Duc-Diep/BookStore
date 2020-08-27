package com.example.bookstore.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;
    public static HistoryFragment newInstance() {

        Bundle args = new Bundle();

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history,container,false);
        return binding.getRoot();
    }
}
