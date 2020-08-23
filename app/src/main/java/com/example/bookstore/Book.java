package com.example.bookstore;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Book implements Parcelable, Serializable {
    int id;
    String imageLink;
    String title;
    String author;
    String publisher;
    int releaseYear;
    int numOfPage;
    double price;

    public Book(int id, String imageLink, String title, String author, String publisher, int releaseYear, int numOfPage, double price) {
        this.id = id;
        this.imageLink = imageLink;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.numOfPage = numOfPage;
        this.price = price;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        imageLink = in.readString();
        title = in.readString();
        author = in.readString();
        publisher = in.readString();
        releaseYear = in.readInt();
        numOfPage = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getNumOfPage() {
        return numOfPage;
    }

    public void setNumOfPage(int numOfPage) {
        this.numOfPage = numOfPage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageLink);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeInt(releaseYear);
        dest.writeInt(numOfPage);
        dest.writeDouble(price);
    }
}
