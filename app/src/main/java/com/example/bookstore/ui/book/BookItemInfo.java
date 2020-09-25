package com.example.bookstore.ui.book;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
//import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.databinding.FragmentBookItemInforBinding;
import com.example.bookstore.event.Bus;
import com.example.bookstore.event.EHideToolBar;
import com.example.bookstore.event.EShowToolBar;
import com.example.bookstore.sqlhelper.SQLHelper;
import com.example.bookstore.ui.home.ListBookFragment;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.bookstore.ui.book.BookAttribute.BOOK_AUTHOR;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_CATEGOTY;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_DESCRIPTION;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_ID;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_IMAGELINK;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_PAGE;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_PRICE;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_PUBLISHER;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_RATESTAR;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_RELEASEYEAR;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_REVIEW;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_TITLE;

public class BookItemInfo extends Fragment {
    FragmentBookItemInforBinding binding;
    Book book;
    List<Book> bookList,similarBook;
    Fragment me = this;
    SQLHelper sqlHelper;
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
        //Picasso.with(getContext()).load(book.getImageLink()).fit().centerInside().into(binding.imgBook);
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
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            double star = book.getRateStar()/book.getNumOfReview();
            binding.tvstarOfBook.setText(decimalFormat.format(star));
        }
        else{
            binding.tvstarOfBook.setText("0");
        }

        binding.tvCategory.setText(book.getCategory());
        binding.tvNumOfReview.setText(book.getNumOfReview()+" "+getString(R.string.numOfEvaluate));
        binding.btnBackToListBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getInstance().post(new EShowToolBar());
                FragmentManager manager =getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                manager.getBackStackEntryCount();
                transaction.remove(me);
                transaction.commit();
//                Fragment fragment = ListBookFragment.newInstance(bookList);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
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
        if (similarBook.size()>0){
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
        }else{

        }
        binding.btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(BOOK_ID, book.getId());
                    jsonObject.put(BOOK_IMAGELINK, book.getImageLink());
                    jsonObject.put(BOOK_TITLE, book.getTitle());
                    jsonObject.put(BOOK_AUTHOR, book.getAuthor());
                    jsonObject.put(BOOK_PUBLISHER, book.getPublisher());
                    jsonObject.put(BOOK_RELEASEYEAR, book.getReleaseYear());
                    jsonObject.put(BOOK_PAGE, book.getNumOfPage());
                    jsonObject.put(BOOK_PRICE, book.getPrice());
                    jsonObject.put(BOOK_DESCRIPTION, book.getDescription());
                    jsonObject.put(BOOK_CATEGOTY, book.getCategory());
                    jsonObject.put(BOOK_RATESTAR, (book.getRateStar()+binding.rateStar.getRating()));
                    jsonObject.put(BOOK_REVIEW,(book.getNumOfReview()+1));
                }catch(JSONException e){
                    e.printStackTrace();
                }
                final String mrequestbody = jsonObject.toString();
                String url = "https://bookshopb.herokuapp.com/api/book";
                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DecimalFormat decimalFormat = new DecimalFormat("#.#");
                        double star = (book.getRateStar()+binding.rateStar.getRating())/(book.getNumOfReview()+1);
                        binding.tvstarOfBook.setText(String.valueOf(decimalFormat.format(star)));
                        binding.tvNumOfReview.setText((book.getNumOfReview()+1)+" "+getString(R.string.numOfEvaluate));
                        Toast.makeText(getContext(),getString(R.string.your_evaluate)+" "+binding.rateStar.getRating()+" "+getString(R.string.star),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),getString(R.string.evaluate_eror),Toast.LENGTH_SHORT).show();
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


                //double star = (book.getRateStar()+1)/(book.getNumOfReview()+1);
                //binding.tvstarOfBook.setText(String.valueOf(star));

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
