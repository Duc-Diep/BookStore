package com.example.bookstore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore.event.Bus;
import com.example.bookstore.event.EHideToolBar;
import com.example.bookstore.event.ELogin;
import com.example.bookstore.ui.book.BookAttribute;
import com.example.bookstore.sqlhelper.SQLHelper;
import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentHomeBinding;
import com.example.bookstore.ui.book.BookItemInfo;
import com.example.bookstore.ui.book.IlongClickBook;
import com.example.bookstore.ui.book.IonClickBook;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    String url ="https://bookshopb.herokuapp.com/api/books";
    String result = "";
    List<Book> list,list2,list3;
    SQLHelper sqlHelper;
    List<String> mangquangcao = new ArrayList<>();
    BookAttribute b;


    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        sqlHelper = new SQLHelper(getContext());
        list = new ArrayList<>();
        //set view Flipper
        ViewFlipper();
        //set click
        binding.moreNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getInstance().post(new EHideToolBar());
                Fragment fragment = ListBookFragment.newInstance(list);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
            }
        });
        binding.moreHotBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getInstance().post(new EHideToolBar());
                Fragment fragment = ListBookFragment.newInstance(list2);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
            }
        });
        binding.moreOfferBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getInstance().post(new EHideToolBar());
                Fragment fragment = ListBookFragment.newInstance(list3);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
            }
        });

        binding.moreHotBook.setVisibility(View.INVISIBLE);
        binding.moreNewBook.setVisibility(View.INVISIBLE);
        binding.moreOfferBook.setVisibility(View.INVISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                binding.progessbar.setVisibility(View.INVISIBLE);
                binding.moreHotBook.setVisibility(View.VISIBLE);
                binding.moreNewBook.setVisibility(View.VISIBLE);
                binding.moreOfferBook.setVisibility(View.VISIBLE);
                result = response;
                getJson();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.listHotBook.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(stringRequest);

        return binding.getRoot();

    }
    private void getJson() {
        int id,releaseYear,numOfPage,numOfReview;
        String imageLink,title,author,publisher,description,category;
        double price,rateStar;
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                id = object.getInt(b.BOOK_ID);
                releaseYear = object.getInt(b.BOOK_RELEASEYEAR);
                numOfPage = object.getInt(b.BOOK_PAGE);
                price = object.getDouble(b.BOOK_PRICE);
                imageLink = object.getString(b.BOOK_IMAGELINK);
                title = object.getString(b.BOOK_TITLE);
                author = object.getString(b.BOOK_AUTHOR);
                publisher = object.getString(b.BOOK_PUBLISHER);
                numOfReview = object.getInt(b.BOOK_REVIEW);
                description = object.getString(b.BOOK_DESCRIPTION);
                category = object.getString(b.BOOK_CATEGOTY);
                rateStar = object.getDouble(b.BOOK_RATESTAR);
                Book book = new Book(id, imageLink, title, author, publisher, releaseYear, numOfPage,price,rateStar,numOfReview,description,category);
                list.add(book);
                sqlHelper.InsertBookToAllBook(book);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //add list 1
        BookAdapter adapter1 = new BookAdapter(list, getContext());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.listNewBook.setAdapter(adapter1);
        binding.listNewBook.setLayoutManager(layoutManager1);
        adapter1.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Bus.getInstance().post(new EHideToolBar());
                Fragment fragment = BookItemInfo.newInstance(list,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
            }
        });
        adapter1.setIlongClickBook(new IlongClickBook() {
            @Override
            public void longClickItem(Book book) {

            }
        });
        //add list 2
        list2 = new ArrayList<>();
        for(int i =list.size()-1;i>=0;i--){
            list2.add(list.get(i));
        }
        BookAdapter adapter2 = new BookAdapter(list2, getContext());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.listHotBook.setAdapter(adapter2);
        binding.listHotBook.setLayoutManager(layoutManager2);
        adapter2.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Bus.getInstance().post(new EHideToolBar());
                Fragment fragment = BookItemInfo.newInstance(list,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        adapter2.setIlongClickBook(new IlongClickBook() {
            @Override
            public void longClickItem(Book book) {

            }
        });
        //add list3
        list3 = new ArrayList<>();
        for(int i =list.size()/2;i>=0;i--){
            list3.add(list.get(i));
        }
        for(int i =list.size()/2+1;i<list.size()-1;i++){
            list3.add(list.get(i));
        }
        BookAdapter adapter3 = new BookAdapter(list3, getContext());
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.listOfferBook.setAdapter(adapter3);
        binding.listOfferBook.setLayoutManager(layoutManager3);
        adapter3.setIonClickBook(new IonClickBook() {
            @Override
            public void onClickItem(Book book) {
                Bus.getInstance().post(new EHideToolBar());
                Fragment fragment = BookItemInfo.newInstance(list,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        adapter3.setIlongClickBook(new IlongClickBook() {
            @Override
            public void longClickItem(Book book) {

            }
        });
    }
    //ViewFlipper
    private void ViewFlipper() {
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978768/Ma-giam-gia-Fahasa_kn9mcd.png");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978870/M%C3%A3-gi%E1%BA%A3m-gi%C3%A1-S%C3%A1ch_evuiwm.jpg");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978884/ma-giam-gia-sach_fbnt0s.png");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978884/m%C3%A3-gi%E1%BA%A3m-gi%C3%A1-vinabook-1_dfgmq2.jpg");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978884/ma-giam-gia-vinabook_m8cvi2.png");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978885/voucher_qslawk.png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Picasso.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.viewFlipper.addView(imageView);
        }
        binding.viewFlipper.setFlipInterval(3000);
        binding.viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        binding.viewFlipper.setInAnimation(animation_slide_in);
        binding.viewFlipper.setOutAnimation(animation_slide_out);
    }
    @Override
    public void onStart() {
        super.onStart();
        Bus.getInstance().register(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        Bus.getInstance().register(getContext());
    }
}
