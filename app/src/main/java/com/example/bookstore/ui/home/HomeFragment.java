package com.example.bookstore.ui.home;

import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore.Book;
import com.example.bookstore.BookAdapter;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentHomeBinding;
import com.example.bookstore.ui.bookinfo.BookInfoFragment;
import com.example.bookstore.ui.search.SearchByNameFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        binding.moreHotBook.setVisibility(View.INVISIBLE);
        binding.moreNewBook.setVisibility(View.INVISIBLE);
        binding.moreOfferBook.setVisibility(View.INVISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                binding.moreHotBook.setVisibility(View.VISIBLE);
                binding.moreNewBook.setVisibility(View.VISIBLE);
                binding.moreOfferBook.setVisibility(View.VISIBLE);
                result = response.toString();
                getJson();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.listHotBook.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(stringRequest);
//        (new DoGetData()).execute();
        return binding.getRoot();

    }
    private void getJson() {

        list = new ArrayList<>();
        int id,releaseYear,numOfPage;
        String imageLink,title,author,publisher;
        double price;
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
                list.add(new Book(id, imageLink, title, author, publisher, releaseYear, numOfPage,price));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //add list 1
        BookAdapter adapter1 = new BookAdapter(list, getContext());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.listNewBook.setAdapter(adapter1);
        binding.listNewBook.setLayoutManager(layoutManager1);
        //add list 2
        list2 = new ArrayList<>();
        for(int i =list.size()-1;i>=0;i--){
            list2.add(list.get(i));
        }
        BookAdapter adapter2 = new BookAdapter(list2, getContext());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.listHotBook.setAdapter(adapter2);
        binding.listHotBook.setLayoutManager(layoutManager2);
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
    }

}
