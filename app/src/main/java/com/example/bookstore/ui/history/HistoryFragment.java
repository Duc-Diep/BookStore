package com.example.bookstore.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentHistoryBinding;
import com.example.bookstore.sqlhelper.SQLHelper;
import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.ui.book.BookItemInfo;
import com.example.bookstore.ui.book.IonClickBook;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;
    SQLHelper sqlHelper;
    List<Book> listHistory,allBook;
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
        listHistory = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        listHistory = sqlHelper.getAllBookInHistory();
        allBook = sqlHelper.getAllBook();
        BookAdapter adapter = new BookAdapter(listHistory, getContext());
        adapter.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Fragment fragment = BookItemInfo.newInstance(allBook, book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        binding.listHistory.setAdapter(adapter);
        binding.listHistory.setLayoutManager(gridLayoutManager);
        binding.btnDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listHistory.clear();
                BookAdapter adapter = new BookAdapter(listHistory, getContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
                binding.listHistory.setAdapter(adapter);
                binding.listHistory.setLayoutManager(gridLayoutManager);
            }
        });
        return binding.getRoot();
    }
}
