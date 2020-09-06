package com.example.bookstore.sqlhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bookstore.ui.book.BookAttribute;
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
    BookAttribute b;
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
        contentValues.put(b.BOOK_ID, book.getId());
        contentValues.put(b.BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(b.BOOK_TITLE, book.getTitle());
        contentValues.put(b.BOOK_TITLE, book.getAuthor());
        contentValues.put(b.BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(b.BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(b.BOOK_PAGE, book.getNumOfPage());
        contentValues.put(b.BOOK_PRICE, book.getPrice());
        contentValues.put(b.BOOK_RATESTAR, book.getRateStar());
        contentValues.put(b.BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(b.BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(b.BOOK_CATEGORY, book.getCategory());
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
            int id = cursor.getInt(cursor.getColumnIndex(b.BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(b.BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(b.BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(b.BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(b.BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(b.BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(b.BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(b.BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(b.BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(b.BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(b.BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(b.BOOK_CATEGORY));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
    //Query Table Cart
    public void InsertBookToCart(Book book) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(b.BOOK_ID, book.getId());
        contentValues.put(b.BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(b.BOOK_TITLE, book.getTitle());
        contentValues.put(b.BOOK_TITLE, book.getAuthor());
        contentValues.put(b.BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(b.BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(b.BOOK_PAGE, book.getNumOfPage());
        contentValues.put(b.BOOK_PRICE, book.getPrice());
        contentValues.put(b.BOOK_RATESTAR, book.getRateStar());
        contentValues.put(b.BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(b.BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(b.BOOK_CATEGORY, book.getCategory());
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
            int id = cursor.getInt(cursor.getColumnIndex(b.BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(b.BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(b.BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(b.BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(b.BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(b.BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(b.BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(b.BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(b.BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(b.BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(b.BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(b.BOOK_CATEGORY));
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
        contentValues.put(b.BOOK_ID, book.getId());
        contentValues.put(b.BOOK_IMAGELINK, book.getImageLink());
        contentValues.put(b.BOOK_TITLE, book.getTitle());
        contentValues.put(b.BOOK_TITLE, book.getAuthor());
        contentValues.put(b.BOOK_PUBLISHER, book.getPublisher());
        contentValues.put(b.BOOK_RELEASEYEAR, book.getReleaseYear());
        contentValues.put(b.BOOK_PAGE, book.getNumOfPage());
        contentValues.put(b.BOOK_PRICE, book.getPrice());
        contentValues.put(b.BOOK_RATESTAR, book.getRateStar());
        contentValues.put(b.BOOK_REVIEW, book.getNumOfReview());
        contentValues.put(b.BOOK_DESCRIPTION, book.getDescription());
        contentValues.put(b.BOOK_CATEGORY, book.getCategory());
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
            int id = cursor.getInt(cursor.getColumnIndex(b.BOOK_ID));
            String imageLink =cursor.getString(cursor.getColumnIndex(b.BOOK_IMAGELINK));
            String title =cursor.getString(cursor.getColumnIndex(b.BOOK_TITLE));
            String author =cursor.getString(cursor.getColumnIndex(b.BOOK_AUTHOR));
            String publisher =cursor.getString(cursor.getColumnIndex(b.BOOK_PUBLISHER));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(b.BOOK_RELEASEYEAR));
            int numOfPage = cursor.getInt(cursor.getColumnIndex(b.BOOK_PAGE));
            double price = cursor.getDouble(cursor.getColumnIndex(b.BOOK_PRICE));
            double rateStar = cursor.getDouble(cursor.getColumnIndex(b.BOOK_RATESTAR));
            int numOfReview = cursor.getInt(cursor.getColumnIndex(b.BOOK_REVIEW));
            String description =cursor.getString(cursor.getColumnIndex(b.BOOK_DESCRIPTION));
            String category =cursor.getString(cursor.getColumnIndex(b.BOOK_CATEGORY));
            list.add(new Book(id,imageLink,title,author,publisher,releaseYear,numOfPage,price,rateStar,numOfReview,description,category));
        }
        return list;
    }
    public int deleteHistory() {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_TABLE_HISTORY, null, null);
    }
}
