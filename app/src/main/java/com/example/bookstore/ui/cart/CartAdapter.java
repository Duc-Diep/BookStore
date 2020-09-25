package com.example.bookstore.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.ui.book.Book;
import com.example.bookstore.ui.book.BookAdapter;
import com.example.bookstore.ui.book.IonClickBook;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Book> list;
    Context context;
    IonClickDelete ionClickDelete;
    IonClickBook ionClickBook;

    public void setIonClickBook(IonClickBook ionClickBook) {
        this.ionClickBook = ionClickBook;
    }

    public void setIonClickDelete(IonClickDelete ionClickDelete) {
        this.ionClickDelete = ionClickDelete;
    }

    public CartAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_in_cart,parent,false);
        CartAdapter.ViewHolder viewHolder = new CartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        final Book book = list.get(position);
        //Picasso.with(context).load(book.getImageLink()).fit().centerInside().into(holder.imgBook);
        Picasso.with(context).load(book.getImageLink()).into(holder.imgBook);
        holder.tvTitle.setText(book.getTitle());
        Locale local =new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(book.getPrice());
        holder.tvPricebook.setText(money+"đ");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ionClickBook.onClickItem(book);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ionClickDelete.onClickItem(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBook,imgDelete;
        TextView tvTitle,tvPricebook;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.btnDeleteItemInCart);
            imgBook = itemView.findViewById(R.id.imgBookInCart);
            tvTitle = itemView.findViewById(R.id.tvTitleInCart);
            tvPricebook= itemView.findViewById(R.id.tvPriceBookInCart);
            layout = itemView.findViewById(R.id.layoutClickerInCart);
        }
    }
}
