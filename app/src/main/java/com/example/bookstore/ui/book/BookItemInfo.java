package com.example.bookstore.ui.book;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentBookItemInforBinding;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookItemInfo extends Fragment {
    FragmentBookItemInforBinding binding;
    Book book;
    List<Book> bookList,similarBook;
    Fragment me = this;
    public static BookItemInfo newInstance(List<Book> bookList,Book book) {
        
        Bundle bundle = new Bundle();
        bundle.putSerializable("book",book);
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) bookList);
        BookItemInfo fragment = new BookItemInfo();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_item_infor,container,false);
        //get Data
        book = (Book) getArguments().getSerializable("book");
        bookList= (List<Book>) getArguments().getSerializable("list");

        //set atribute
        Picasso.with(getContext()).load(book.getImageLink()).into(binding.imgBook);
        binding.tvBookName.setText(book.getTitle());
        binding.tvAuthor.setText("Tác giả: "+book.getAuthor());
        binding.tvNumberOfPage.setText(String.valueOf(book.getNumOfPage())+" "+getString(R.string.page));
        Locale local =new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(book.getPrice());
        binding.tvPrice.setText(money+" đ");
        binding.tvDescrition.setText(book.getDescription());
        binding.tvstarOfBook.setText(String.valueOf(book.getRateStar()));
        binding.tvCategory.setText(book.getCategory());
        binding.tvNumOfReview.setText(book.getNumOfReview()+" "+getString(R.string.numOfEvaluate));
        binding.btnBackToListBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager =getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                manager.getBackStackEntryCount();
                transaction.remove(me);
                transaction.commit();
            }
        });
        //set adater
        similarBook = new ArrayList<>();
        for (Book x : bookList
             ) {
            if(x.getCategory().equals(book.getCategory())&&!x.getTitle().equals(book.getTitle())){
                similarBook.add(x);
            }
        }
        BookAdapter adapter = new BookAdapter(similarBook, getContext());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.similarBook.setAdapter(adapter);
        binding.similarBook.setLayoutManager(layoutManager2);
        adapter.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Fragment fragment = BookItemInfo.newInstance(bookList,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}
