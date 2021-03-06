package com.example.plutoacademy;

import org.json.JSONArray;

public class BookDetailedDataModel {
    private String mBookImage;
    private int mRecBookNo;
    private String mTitle;
    private String mBuy;
    private String mAuthor;
    private JSONArray mRecc;

    public BookDetailedDataModel(String mBookImage, int mRecBookNo, String mTitle, String mBuy, String mAuthor, JSONArray mRecc) {
        this.mBookImage = mBookImage;
        this.mRecBookNo = mRecBookNo;
        this.mTitle = mTitle;
        this.mBuy = mBuy;
        this.mAuthor = mAuthor;
        this.mRecc = mRecc;

    }

    public String getmBookImage() {
        return mBookImage;
    }

    public int getmRecBookNo() {
        return mRecBookNo;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmBuy() {
        return mBuy;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public JSONArray getmRecc() {
        return mRecc;
    }
}
