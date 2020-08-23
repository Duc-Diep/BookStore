package com.example.bookstore.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.Book;
import com.example.bookstore.BookAdapter;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentListBookInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class ListBookFragment extends Fragment {
    List<Book> listBook;
    FragmentListBookInfoBinding binding;
    final Fragment me = this;
    public static ListBookFragment newInstance(List<Book> list) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        ListBookFragment fragment = new ListBookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_book_info,container,false);
        binding.btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager =getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                manager.getBackStackEntryCount();
                transaction.remove(me);
                transaction.commit();
            }
        });
        listBook= (List<Book>) getArguments().getSerializable("list");
        BookAdapter adapter = new BookAdapter(listBook,getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3, RecyclerView.VERTICAL,false);
        binding.listBook.setAdapter(adapter);
        binding.listBook.setLayoutManager(gridLayoutManager);
        return binding.getRoot();
    }
}
