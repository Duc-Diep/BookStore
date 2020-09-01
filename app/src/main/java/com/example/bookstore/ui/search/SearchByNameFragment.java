package com.example.bookstore.ui.search;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.bookstore.databinding.FragmentSearchBinding;
import com.example.bookstore.sqlhelper.SQLHelper;
import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.ui.book.BookItemInfo;
import com.example.bookstore.ui.book.IonClickBook;

import java.util.ArrayList;
import java.util.List;

public class SearchByNameFragment extends Fragment {
    FragmentSearchBinding binding;
    SQLHelper sqlHelper;
    List<Book> allBook;
    public static SearchByNameFragment newInstance() {

        Bundle args = new Bundle();

        SearchByNameFragment fragment = new SearchByNameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false);
        allBook = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        allBook = sqlHelper.getAllBook();
        BookAdapter adapter = new BookAdapter(allBook,getContext());
        adapter.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Fragment fragment = BookItemInfo.newInstance(allBook,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3, RecyclerView.VERTICAL,false);
        binding.listBookSearch.setAdapter(adapter);
        binding.listBookSearch.setLayoutManager(gridLayoutManager);
        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Book> templist =new ArrayList<>();
                for(int i=0;i<allBook.size();i++){
                    if(allBook.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())){
                        templist.add(allBook.get(i));
                    }
                }
                BookAdapter adapter = new BookAdapter(templist, getContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3, RecyclerView.VERTICAL,false);
                binding.listBookSearch.setAdapter(adapter);
                binding.listBookSearch.setLayoutManager(gridLayoutManager);
                adapter.setIonClickBook(new IonClickBook() {
                    @Override
                    public void onClickItem(Book book) {
                        Fragment fragment = BookItemInfo.newInstance(allBook,book);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }
        });
        return binding.getRoot();
    }
}
