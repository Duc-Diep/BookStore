package com.example.bookstore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentHomeBinding;
import com.example.bookstore.ui.book.BookItemInfo;
import com.example.bookstore.ui.book.IonClickBook;

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

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        binding.moreNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        list = new ArrayList<>();
        int id,releaseYear,numOfPage,numOfReview;
        String imageLink,title,author,publisher,description,category;
        double price,rateStar;
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                id = object.getInt("id");
                releaseYear = object.getInt("releaseYear");
                numOfPage = object.getInt("numOfPage");
                price = object.getDouble("price");
                imageLink = object.getString("imageLink");
                title = object.getString("title");
                author = object.getString("author");
                publisher = object.getString("publisher");
                numOfReview = object.getInt("numOfReview");
                description = object.getString("description");
                category = object.getString("categoty");
                rateStar = object.getDouble("rateStar");
                list.add(new Book(id, imageLink, title, author, publisher, releaseYear, numOfPage,price,rateStar,numOfReview,description,category));
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
                Fragment fragment = BookItemInfo.newInstance(list,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
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
                Fragment fragment = BookItemInfo.newInstance(list,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
                Fragment fragment = BookItemInfo.newInstance(list,book);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}
