package com.books2.read;

import android.os.Parcel;
import android.os.Parcelable;

public class BooksModel implements Parcelable {
    private String mBookImage;
    private int mRecBook;
    private String mBookName;
    String Buy;
    String author;
    String reccom;


    protected BooksModel(Parcel in) {
        mBookImage = in.readString();
        mRecBook = in.readInt();
        mBookName = in.readString();
        Buy = in.readString();
        author = in.readString();
        reccom = in.readString();
    }

    public static final Creator<BooksModel> CREATOR = new Creator<BooksModel>() {
        @Override
        public BooksModel createFromParcel(Parcel in) {
            return new BooksModel(in);
        }

        @Override
        public BooksModel[] newArray(int size) {
            return new BooksModel[size];
        }
    };

    public String getmBookImage() {
        return mBookImage;
    }

    public int getmRecBook() {
        return mRecBook;
    }

    public String getmBookName() {
        return mBookName;
    }

    public String getBuy() {
        return Buy;
    }

    public String getAuthor() {
        return author;
    }

    public String getReccom() {
        return reccom;
    }

    public BooksModel(String mBookImage, int mRecBook, String mBookName, String buy, String author, String reccom) {
        this.mBookImage = mBookImage;
        this.mRecBook = mRecBook;
        this.mBookName = mBookName;
        Buy = buy;
        this.author = author;
        this.reccom = reccom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookImage);
        dest.writeInt(mRecBook);
        dest.writeString(mBookName);
        dest.writeString(Buy);
        dest.writeString(author);
        dest.writeString(reccom);
    }
}
