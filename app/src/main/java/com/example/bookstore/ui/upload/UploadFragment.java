package com.example.bookstore.ui.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {
    FragmentUploadBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_upload,container,false);
        return binding.getRoot();
    }
}
