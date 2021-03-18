package com.books2.read;

public class RecommenderModel {

    private String mRecName;
    private String mRecDes;
    private String mSource;

    public String getmRecName() {
        return mRecName;
    }

    public String getmRecDes() {
        return mRecDes;
    }

    public String getmSource() {
        return mSource;
    }

    public RecommenderModel(String mRecName, String mRecDes, String mSource) {
        this.mRecName = mRecName;
        this.mRecDes = mRecDes;
        this.mSource = mSource;
    }
}

