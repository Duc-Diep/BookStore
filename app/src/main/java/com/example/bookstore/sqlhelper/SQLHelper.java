package com.example.bookstore.sqlhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bookstore.ui.book.Book;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    static final String DB_NAME = "BookStore.db";
    static final String DB_TABLE_ALL_BOOK = "AllBooks";
    static final String DB_TABLE_CART = "Cart";
    static final String DB_TABLE_HISTORY = "History";
    static final int DB_VERSION = 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table all books
        String queryCreateTableAllBook = "CREATE TABLE "+ DB_TABLE_ALL_BOOK +"(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "imageLink Text," +
                "title Text," +
                "author Text," +
                "publisher Text,"+
                "releaseYear INTEGER,"+
                "numOfPage INTEGER,"+
                "price INTEGER,"+
                "rateStar INTEGER,"+
                "numOfReview INTEGER,"+
                "description Text,"+
                "category Text)";
        db.execSQL(queryCreateTableAllBook);
        //create table Cart
        String queryCreateTableCart = "CREATE TABLE "+ DB_TABLE_CART +"(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "imageLink Text," +
                "title Text," +
                "author Text," +
                "publisher Text,"+
                "releaseYear INTEGER,"+
                "numOfPage INTEGER,"+
                "price INTEGER,"+
                "rateStar INTEGER,"+
                "numOfReview INTEGER,"+
                "description Text,"+
                "category Text)";
        db.execSQL(queryCreateTableCart);
        //create table HISTORY
        String queryCreateTableHistory = "CREATE TABLE "+ DB_TABLE_HISTORY +"(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "imageLink Text," +
                "title Text," +
                "author Text," +
                "publisher Text,"+
                "releaseYear INTEGER,"+
                "numOfPage INTEGER,"+
                "price INTEGER,"+
                "rateStar INTEGER,"+
                "numOfReview INTEGER,"+
                "description Text,"+
                "category Text)";
        db.execSQL(queryCreateTableHistory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }
    //Query Table AllBook
    public void InsertBookToAllBook(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("id", book.getId());
        contentValues.put("imageLink", book.getImageLink());
        contentValues.put("title", book.getTitle());
        contentValues.put("author", book.getAuthor());
        contentValues.put("publisher", book.getPublisher());
        contentValues.put("releaseYear", book.getReleaseYear());
        contentValues.put("numOfPage", book.getNumOfPage());
        contentValues.put("price", book.getPrice());
        contentValues.put("rateStar", book.getRateStar());
        contentValues.put("numOfReview", book.getNumOfReview());
        contentValues.put("description", book.getDescription());
        contentValues.put("category", book.getCategory());
        sqLiteDatabase.insert(DB_TABLE_ALL_BOOK, null, contentValues);
    }
    public List<Book> getAllBook(){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_ALL_BOOK,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String imageLink =cursor.getString(cursor.getColumnIndex("imageLink"));
            String title =cursor.getString(cursor.getColumnIndex("title"));
            String author =cursor.getString(cursor.getColumnIndex("author"));
            String publisher =cursor.getString(cursor.getColumnIndex("publisher"));
            int releaseYear = cursor.getInt(cursor.getColumnIndex("releaseYear"));
            int numOfPage = cursor.getInt(cursor.getColumnIndex("numOfPage"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            double rateStar = cursor.getDouble(cursor.getColumnIndex("rateStar"));
            int numOfReview = cursor.getInt(cursor.getColumnIndex("numOfReview"));
            String description =cursor.getString(cursor.getColumnIndex("description"));
            String category =cursor.getString(cursor.getColumnIndex("category"));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
    //Query Table Cart
    public void InsertBookToCart(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("id", book.getId());
        contentValues.put("imageLink", book.getImageLink());
        contentValues.put("title", book.getTitle());
        contentValues.put("author", book.getAuthor());
        contentValues.put("publisher", book.getPublisher());
        contentValues.put("releaseYear", book.getReleaseYear());
        contentValues.put("numOfPage", book.getNumOfPage());
        contentValues.put("price", book.getPrice());
        contentValues.put("rateStar", book.getRateStar());
        contentValues.put("numOfReview", book.getNumOfReview());
        contentValues.put("description", book.getDescription());
        contentValues.put("category", book.getCategory());
        sqLiteDatabase.insert(DB_TABLE_CART, null, contentValues);
    }
    public List<Book> getAllBookInCart(){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_CART,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String imageLink =cursor.getString(cursor.getColumnIndex("imageLink"));
            String title =cursor.getString(cursor.getColumnIndex("title"));
            String author =cursor.getString(cursor.getColumnIndex("author"));
            String publisher =cursor.getString(cursor.getColumnIndex("publisher"));
            int releaseYear = cursor.getInt(cursor.getColumnIndex("releaseYear"));
            int numOfPage = cursor.getInt(cursor.getColumnIndex("numOfPage"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            double rateStar = cursor.getDouble(cursor.getColumnIndex("rateStar"));
            int numOfReview = cursor.getInt(cursor.getColumnIndex("numOfReview"));
            String description =cursor.getString(cursor.getColumnIndex("description"));
            String category =cursor.getString(cursor.getColumnIndex("category"));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
    public int deleteCart() {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_CART, null, null);
    }
    public int deleteItemInCart(String id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_CART, "id=?", new String[]{id});
    }
    //Query table History
    public void InsertBookToHistory(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("id", book.getId());
        contentValues.put("imageLink", book.getImageLink());
        contentValues.put("title", book.getTitle());
        contentValues.put("author", book.getAuthor());
        contentValues.put("publisher", book.getPublisher());
        contentValues.put("releaseYear", book.getReleaseYear());
        contentValues.put("numOfPage", book.getNumOfPage());
        contentValues.put("price", book.getPrice());
        contentValues.put("rateStar", book.getRateStar());
        contentValues.put("numOfReview", book.getNumOfReview());
        contentValues.put("description", book.getDescription());
        contentValues.put("category", book.getCategory());
        sqLiteDatabase.insert(DB_TABLE_HISTORY, null, contentValues);
    }
    public List<Book> getAllBookInHistory(){
        List<Book> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_HISTORY,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String imageLink =cursor.getString(cursor.getColumnIndex("imageLink"));
            String title =cursor.getString(cursor.getColumnIndex("title"));
            String author =cursor.getString(cursor.getColumnIndex("author"));
            String publisher =cursor.getString(cursor.getColumnIndex("publisher"));
            int releaseYear = cursor.getInt(cursor.getColumnIndex("releaseYear"));
            int numOfPage = cursor.getInt(cursor.getColumnIndex("numOfPage"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            double rateStar = cursor.getDouble(cursor.getColumnIndex("rateStar"));
            int numOfReview = cursor.getInt(cursor.getColumnIndex("numOfReview"));
            String description =cursor.getString(cursor.getColumnIndex("description"));
            String category =cursor.getString(cursor.getColumnIndex("category"));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
}
