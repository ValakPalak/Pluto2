package com.example.plutoacademy;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpertsModel implements Parcelable{
    private String mExpertImage;
    private String mExpertName;
    private String mExpertType;
    String slug;

    protected ExpertsModel(Parcel in) {
        mExpertImage = in.readString();
        mExpertName = in.readString();
        mExpertType = in.readString();
        slug = in.readString();
    }

    public static final Creator<ExpertsModel> CREATOR = new Creator<ExpertsModel>() {
        @Override
        public ExpertsModel createFromParcel(Parcel in) {
            return new ExpertsModel(in);
        }

        @Override
        public ExpertsModel[] newArray(int size) {
            return new ExpertsModel[size];
        }
    };

    public String getmExpertImage() {
        return mExpertImage;
    }

    public String getmExpertName() {
        return mExpertName;
    }

    public String getmExpertType() {
        return mExpertType;
    }

    public String getSlug() {
        return slug;
    }

    public ExpertsModel(String mExpertImage, String mExpertName, String mExpertType, String slug) {
        this.mExpertImage = mExpertImage;
        this.mExpertName = mExpertName;
        this.mExpertType = mExpertType;
        this.slug = slug;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mExpertImage);
        parcel.writeString(mExpertName);
        parcel.writeString(mExpertType);
        parcel.writeString(slug);
    }
}
