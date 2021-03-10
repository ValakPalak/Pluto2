package com.example.plutoacademy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Random;

public class ExpertsDescription extends AppCompatActivity {

    CircularImageView profile_image;
    TextView expert_name;
    TextView designation,about;
    TextView viewallbook;
    TextView viewallexpert;
    ImageView cover_image, BackE;
    RecyclerView recyclerView;
    ArrayList<BooksModel> booksModelsList=new ArrayList<>();
    BooksAdapter booksAdapter;
    RecyclerView SocialMediaRV;
    SmAdapter SocialMediaApdapter;
    RecyclerView.LayoutManager SocialMediaLM;
    public static Boolean Books=false;
    public static Boolean Expert=false;
    private RecyclerView mRecyclerView;
    ExpertsAdapter mAdapter;
    SearchView searchView;
    ArrayList<ExpertsModel> ExpertsList=new ArrayList<>() ;
    TextView viewmore;
    GridLayoutManager gridLayoutManager;

    ArrayList<SmModel> SocialMediaList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts_description);
        profile_image=findViewById(R.id.profile_image);
        expert_name=findViewById(R.id.expertName);
        viewallexpert=findViewById(R.id.ViewAllExperts);
        designation=findViewById(R.id.expertDesig);
        cover_image=findViewById(R.id.cover_image);
        about=findViewById(R.id.about);
        recyclerView=findViewById(R.id.RecBooksRecyclerView);
        viewallbook=findViewById(R.id.ViewAllBooks);
        Intent intent=getIntent();
        ExpertsModel expertsModel=intent.getParcelableExtra("details");
        expert_name.setText(expertsModel.getmExpertName());
        designation.setText(expertsModel.getmExpertType());
        Picasso.get().load(expertsModel.getmExpertImage()).into(profile_image);
        String slug=expertsModel.getSlug();
        getData(slug);
        BackE = findViewById(R.id.BackE);
        BackE.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Books=false;
        viewallbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Books=true;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        Expert=false;
        viewallexpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expert=true;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        booksAdapter=new BooksAdapter(booksModelsList, new OnItemClick() {
            @Override
            public void Onclick(int position) {
                Intent mIntent=new Intent(getApplicationContext(),BookDescription.class);
                mIntent.putExtra("array",booksModelsList.get(position));
                startActivity(mIntent);
            }
        });
        recyclerView.setAdapter(booksAdapter);

        SocialMediaRV = findViewById(R.id.socialmdedia);
        SocialMediaApdapter = new SmAdapter(SocialMediaList,getApplicationContext());
        SocialMediaRV.setAdapter(SocialMediaApdapter);


        mRecyclerView = findViewById(R.id.ExpertsYMLRecyclerView);


        mAdapter=new ExpertsAdapter(ExpertsList, new OnItemClick() {
            @Override
            public void Onclick(int position) {
                Intent intent=new Intent(getApplicationContext(),ExpertsDescription.class);
                intent.putExtra("details",ExpertsList.get(position));
                startActivity(intent);
            }
        });
        getDataE(1);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData(String slug) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://plutoacademy.in/api/experts/details?val="+slug;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String intro= jsonObject.isNull("shortintro") ? "NULL" : jsonObject.getString("shortintro");

                    about.setText(intro);
                    JSONArray books=jsonObject.getJSONArray("books");
                    JSONArray logos=jsonObject.getJSONArray("links");
                    for(int i=0;i<books.length();i++)
                    {

                        JSONObject jsonObject1=books.getJSONObject(i);
                        String author=jsonObject1.getString("author");
                        String buy=jsonObject1.getString("buy");
                        String title= jsonObject1.isNull("title") ? "NULL" : jsonObject1.getString("title");
                        String mainImage=jsonObject1.isNull("mainImage") ? "" : jsonObject1.getString("mainImage");
                        String quote=jsonObject1.isNull("quote") ? "" :jsonObject1.getString("quote");
                        JSONArray  recomm= jsonObject1.isNull("recommenders") ? new JSONArray("hello") : jsonObject1.getJSONArray("recommenders");
                        booksModelsList.add(new BooksModel(mainImage,recomm.length(),title,buy,author,recomm.toString()));
                    }
                    for(int i=0;i<logos.length();i++)
                    {
                        JSONObject logo=logos.getJSONObject(i);
                        System.out.println("working");
                        SocialMediaList.add(new SmModel(logo.getString("logo"),logo.getString("link")));
                     //   Toast.makeText(ExpertsDescription.this, ""+logo.getString("logo"), Toast.LENGTH_SHORT).show();

                    }
                    SocialMediaApdapter.notifyDataSetChanged();

                    booksAdapter.notifyDataSetChanged();
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    private void getDataE(int v) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://plutoacademy.in/api/experts/list?page="+ 1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Hello",response.toString());
                String image = null;
                String slug = null;
                String designation = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    String title=jsonObject.getString("title");
                    JSONArray jsonArray = jsonObject.getJSONArray("experts");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        image=jsonObject1.getString("mainImage");
                        slug= (jsonObject1.isNull("slug")) ? "NULL" : jsonObject1.getString("slug");
                        String name=jsonObject1.getString("name");
                        designation=jsonObject1.getString("designation");
                        ExpertsList.add(new ExpertsModel(image, name, designation,slug));
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}