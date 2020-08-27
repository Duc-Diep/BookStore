package com.example.bookstore.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
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

import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentListBookInfoBinding;
import com.example.bookstore.ui.book.BookItemInfo;
import com.example.bookstore.ui.book.IonClickBook;

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
        adapter.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Fragment fragment = BookItemInfo.newInstance(listBook,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3, RecyclerView.VERTICAL,false);
        binding.listBook.setAdapter(adapter);
        binding.listBook.setLayoutManager(gridLayoutManager);
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Book> templist =new ArrayList<>();
                for(int i=0;i<listBook.size();i++){
                    if(listBook.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())){
                        templist.add(listBook.get(i));
                    }
                }
                BookAdapter adapter = new BookAdapter(templist, getContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3, RecyclerView.VERTICAL,false);
                binding.listBook.setAdapter(adapter);
                binding.listBook.setLayoutManager(gridLayoutManager);
                adapter.setIonClickBook(new IonClickBook() {
                    @Override
                    public void onClickItem(Book book) {
                        Fragment fragment = BookItemInfo.newInstance(listBook,book);
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
