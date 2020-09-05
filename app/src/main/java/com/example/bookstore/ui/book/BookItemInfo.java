package com.example.bookstore.ui.book;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore.BookAttribute;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentBookItemInforBinding;
import com.example.bookstore.sqlhelper.SQLHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookItemInfo extends Fragment {
    FragmentBookItemInforBinding binding;
    Book book;
    List<Book> bookList,similarBook;
    Fragment me = this;
    SQLHelper sqlHelper;
    BookAttribute b;
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
        sqlHelper = new SQLHelper(getContext());
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
        if(book.getNumOfReview()>0){
            double star = book.getRateStar()/book.getNumOfReview();
            binding.tvstarOfBook.setText(String.valueOf(star));
        }
        else{
            binding.tvstarOfBook.setText("0");
        }

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
        binding.btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(b.BOOK_ID, book.getId());
                    jsonObject.put( b.BOOK_IMAGELINK, book.getImageLink());
                    jsonObject.put(b.BOOK_TITLE, book.getTitle());
                    jsonObject.put(b.BOOK_AUTHOR, book.getAuthor());
                    jsonObject.put(b.BOOK_PUBLISHER, book.getPublisher());
                    jsonObject.put(b.BOOK_RELEASEYEAR, book.getReleaseYear());
                    jsonObject.put(b.BOOK_PAGE, book.getNumOfPage());
                    jsonObject.put(b.BOOK_PRICE, book.getPrice());
                    jsonObject.put(b.BOOK_DESCRIPTION, book.getDescription());
                    jsonObject.put(b.BOOK_CATEGOTY, book.getCategory());
                    jsonObject.put(b.BOOK_RATESTAR, (book.getRateStar()+binding.rateStar.getRating()));
                    jsonObject.put(b.BOOK_REVIEW,(book.getNumOfReview()+1));
                }catch(JSONException e){
                    e.printStackTrace();
                }
                final String mrequestbody = jsonObject.toString();
                String url = "https://bookshopb.herokuapp.com/api/book";
                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),getString(R.string.your_evaluate)+" "+binding.rateStar.getRating()+" "+getString(R.string.star),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),getString(R.string.your_evaluate),Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //xu li du lieu cho body

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            if(mrequestbody==null){
                                return null;
                            }
                            else{
                                return mrequestbody.getBytes("utf-8");
                            }
                        }catch (Exception e){
                            e.getMessage();
                            return null;
                        }
                    }
                };
                requestQueue.add(stringRequest);

            }
        });
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.InsertBookToCart(book);
                Toast.makeText(getContext(),getString(R.string.add_to_cart_sucess),Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }
}
