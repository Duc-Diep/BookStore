package com.example.bookstore.sqlhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bookstore.ui.account.Account;
import com.example.bookstore.ui.book.BookAttribute;
import com.example.bookstore.ui.book.Book;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookstore.ui.book.BookAttribute.BOOK_AUTHOR;
import static com.example.bookstore.ui.book.BookAttribute.BOOK_CATEGORY;
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

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    static final String DB_NAME = "BookStore.db";
    static final String DB_TABLE_ALL_BOOK = "AllBooks";
    static final String DB_TABLE_CART = "Cart";
    static final String DB_TABLE_HISTORY = "History";
    static final String DB_TABLE_ACCOUNT = "Account";
    static final int DB_VERSION = 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table acc
        String queryCreateTableAccount = "CREATE TABLE "+ DB_TABLE_ACCOUNT +"(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "phone Text," +
                "password Text," +
                "fullname Text,"+
                "address Text)";
        db.execSQL(queryCreateTableAccount);
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
        contentValues.put(BOOK_ID, book.getId());
        contentValues.put(BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(BOOK_TITLE, book.getTitle());
        contentValues.put(BOOK_AUTHOR, book.getAuthor());
        contentValues.put(BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(BOOK_PAGE, book.getNumOfPage());
        contentValues.put(BOOK_PRICE, book.getPrice());
        contentValues.put(BOOK_RATESTAR, book.getRateStar());
        contentValues.put(BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(BOOK_CATEGORY, book.getCategory());
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
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
    //Query Table Cart
    public void InsertBookToCart(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(BOOK_ID, book.getId());
        contentValues.put(BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(BOOK_TITLE, book.getTitle());
        contentValues.put(BOOK_AUTHOR, book.getAuthor());
        contentValues.put(BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(BOOK_PAGE, book.getNumOfPage());
        contentValues.put(BOOK_PRICE, book.getPrice());
        contentValues.put(BOOK_RATESTAR, book.getRateStar());
        contentValues.put(BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(BOOK_CATEGORY, book.getCategory());
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
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY));
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
        contentValues.put(BOOK_ID, book.getId());
        contentValues.put(BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(BOOK_TITLE, book.getTitle());
        contentValues.put(BOOK_AUTHOR, book.getAuthor());
        contentValues.put(BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(BOOK_PAGE, book.getNumOfPage());
        contentValues.put(BOOK_PRICE, book.getPrice());
        contentValues.put(BOOK_RATESTAR, book.getRateStar());
        contentValues.put(BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(BOOK_CATEGORY, book.getCategory());
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
            int id = cursor.getInt(cursor.getColumnIndex(BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(BOOK_CATEGORY));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
    public int deleteHistory() {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_HISTORY, null, null);
    }
    //query table Account
    public void InsertAccount(Account account) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("phone", account.getPhone());
        contentValues.put("password", account.getPassword());
        contentValues.put("fullname", account.getFullName());
        contentValues.put("address", account.getAddress());
        sqLiteDatabase.insert(DB_TABLE_ACCOUNT, null, contentValues);
    }
    public List<Account> getAllAccount(){
        List<Account> list = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(false,DB_TABLE_ACCOUNT,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String phone =cursor.getString(cursor.getColumnIndex("phone"));
            String password =cursor.getString(cursor.getColumnIndex("password"));
            String fullname =cursor.getString(cursor.getColumnIndex("fullname"));
            String address =cursor.getString(cursor.getColumnIndex("address"));
            list.add(new Account(id,phone,password,fullname,address));
        }
        return list;
    }
}
