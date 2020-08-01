package com.alisu.vocabsapps.Model.Entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "vocabs_table")
public class Vocab implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "engWord")
     public String mWordEng;

    @NonNull
    @ColumnInfo(name = "indWord")
    public String mWordInd;

    public Vocab(@NonNull String wordEng, @NonNull String wordInd){
        this.mWordEng = wordEng;
        this.mWordInd = wordInd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmWordEng() {
        return mWordEng;
    }

    public void setmWordEng(String mWordEng) {
        this.mWordEng = mWordEng;
    }

    public String getmWordInd() {
        return mWordInd;
    }

    public void setmWordInd(String mWordInd) {
        this.mWordInd = mWordInd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mWordEng);
        dest.writeString(this.mWordInd);
    }

    protected Vocab(Parcel in) {
        this.id = in.readInt();
        this.mWordEng = in.readString();
        this.mWordInd = in.readString();
    }

    public static final Creator<Vocab> CREATOR = new Creator<Vocab>() {
        @Override
        public Vocab createFromParcel(Parcel source) {
            return new Vocab(source);
        }

        @Override
        public Vocab[] newArray(int size) {
            return new Vocab[size];
        }
    };
}
