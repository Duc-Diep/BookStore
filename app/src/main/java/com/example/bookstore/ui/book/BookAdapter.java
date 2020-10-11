package com.example.bookstore.ui.book;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    List<Book> list;
    Context context;
    IonClickBook ionClickBook;


    public void setIonClickBook(IonClickBook ionClickBook) {
        this.ionClickBook = ionClickBook;
    }

    public BookAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.book_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        final Book book = list.get(position);
        //Picasso.with(context).load(book.getImageLink()).fit().centerInside().into(holder.imgBook);
        Picasso.with(context).load(book.getImageLink()).into(holder.imgBook);
        holder.tvTitle.setText(book.getTitle());
        holder.tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.tvTitle.setHorizontallyScrolling(true);
        holder.tvTitle.setSelected(true);
        holder.tvTitle.setMarqueeRepeatLimit(-1);
        holder.tvTitle.setFocusable(true);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView tvTitle,tvPricebook;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPricebook= itemView.findViewById(R.id.tvPriceBook);
            layout = itemView.findViewById(R.id.layoutClicker);
        }
    }
}
