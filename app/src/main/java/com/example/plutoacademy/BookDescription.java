package com.example.plutoacademy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookDescription extends AppCompatActivity {

    private String mBookImage;
    private int mRecBookNo;
    private String mTitle;
    private String mBuy;
    private String mAuthor;
    CardView cardView;
    TextView bookNameTxtview,authorNameTxtview,bookRecNoTxtView, SourceTxtView;
    ImageView bookImage;
    RecyclerView recyclerView;
    ImageView imageView;
    Button buy;
    ImageView Back1;
    List<String> name=new ArrayList<>();
    RecommenderAdapter recommenderAdapter;
    ArrayList<RecommenderModel> Recom=new ArrayList<>();
    int num=0;
    List<BooksModel> booksModelList=new ArrayList<>();
    String jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);
        cardView=findViewById(R.id.ViewMore);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        Back1 = findViewById(R.id.Back1);
        Back1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView=findViewById(R.id.Bookmark);
        buy=findViewById(R.id.buybutton);
        recyclerView=findViewById(R.id.BookRecRecyclerView);
        bookNameTxtview=findViewById(R.id.BookName);
        authorNameTxtview=findViewById(R.id.AuthorName);
        bookRecNoTxtView=findViewById(R.id.BookRec);
        bookImage=findViewById(R.id.BookImg);
        SourceTxtView=findViewById(R.id.ViewSource);

        Intent intent=getIntent();
        BooksModel booksModel=intent.getParcelableExtra("array");
         jsonArray= booksModel.getReccom();
         String buy_url=booksModel.getBuy();

//        Toast.makeText(this, ""+booksModel.getmBookName(), Toast.LENGTH_SHORT).show();
        booksModelList=loadSharedPreferencesLogModelList();
        name=loadSharedPreferencesLogNameList();
        Log.d("booklist",booksModelList.toString());
        if(name.contains(booksModel.getmBookName()))
        {
            imageView.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.contains(booksModel.getmBookName()))
                {

                    name.remove(booksModel.getmBookName());
                    booksModelList.remove(booksModel);
                    saveSharedPreferencesModeLogList(booksModelList);
                    saveSharedPreferencesLogNameList(name);


                    Toast.makeText(BookDescription.this, "Book Unsaved", Toast.LENGTH_SHORT).show();
                    imageView.setBackgroundColor(0xffffff);
                }
                else{
                    name.add(booksModel.getmBookName());
                    saveSharedPreferencesLogNameList(name);
                    booksModelList.add(booksModel);
                    saveSharedPreferencesModeLogList(booksModelList);
                    imageView.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
                    Toast.makeText(BookDescription.this, "Book Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buy_url.startsWith("https://") || buy_url.startsWith("http://")) {
                        Uri uri = Uri.parse(buy_url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Url", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mBookImage=booksModel.getmBookImage();
            mRecBookNo=booksModel.getmRecBook();
            mTitle=booksModel.getmBookName();
          //  mBuy=bundle.getString(BooksFragment.BOOK_BUY);
            mAuthor=booksModel.getAuthor();

            bookNameTxtview.setText(mTitle);
            authorNameTxtview.setText(mAuthor);
            bookRecNoTxtView.setText(String.valueOf(mRecBookNo));
            Picasso.get().load(mBookImage).into(bookImage);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

        load();
        recommenderAdapter = new RecommenderAdapter(Recom);
        recommenderAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recommenderAdapter);
    }

    private void load() {
        try {
            if(jsonArray!=null) {
                JSONArray jsonArray1=new JSONArray(jsonArray);
//                Toast.makeText(this, ""+num, Toast.LENGTH_SHORT).show();
                if(jsonArray1.length()<=3) {
                    cardView.setVisibility(View.GONE);
                }
                for(int i=num;i<Math.min(jsonArray1.length(),num+3);i++)
                {
                    JSONObject jsonObject=jsonArray1.getJSONObject(i);
                    String expert=jsonObject.getString("expert");
                    String source=jsonObject.getString("source");
                    String quote=jsonObject.isNull("quote")? "No quote" : jsonObject.getString("quote") ;
                    Recom.add(new RecommenderModel(expert, quote, source));
                }
                num = num + 3;
                if(num>=jsonArray1.length()) {
                    cardView.setVisibility(View.GONE);
                }

                recommenderAdapter = new RecommenderAdapter(Recom);
                recyclerView.setAdapter(recommenderAdapter);

            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
    public void saveSharedPreferencesModeLogList(List<BooksModel> callLog) {
        SharedPreferences mPrefs = getSharedPreferences("List", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(callLog);
        prefsEditor.putString("model", json);
        prefsEditor.apply();
    }
    public void saveSharedPreferencesLogNameList(List<String> callLog) {
        SharedPreferences mPrefs = getSharedPreferences("List", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(callLog);
        prefsEditor.putString("name", json);
        prefsEditor.apply();
    }
    public List<BooksModel> loadSharedPreferencesLogModelList() {
        List<BooksModel> booksModelList = new ArrayList<>();
        SharedPreferences mPrefs = getSharedPreferences("List", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("model", "");
        if (json.isEmpty()) {
            booksModelList = new ArrayList<BooksModel>();
        } else {
            Type type = new TypeToken<List<BooksModel>>() {
            }.getType();
            booksModelList = gson.fromJson(json, type);
        }
        return booksModelList;
    }
    public List<String> loadSharedPreferencesLogNameList() {
        List<String> booksModelList = new ArrayList<>();
        SharedPreferences mPrefs = getSharedPreferences("List", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("name", "");
        if (json.isEmpty()) {
            booksModelList = new ArrayList<String>();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            booksModelList = gson.fromJson(json, type);
        }
        return booksModelList;
    }

    @Override
    protected void onStart() {
        booksModelList=loadSharedPreferencesLogModelList();
        super.onStart();
    }
}