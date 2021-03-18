package com.books2.read;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements  OnItemClick{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    SearchView searchView;
    ArrayList<BooksModel> BooksList ;
    TextView knowMore;
    TextView bookname,AuthorName,Recommendation,expertHead, expertSubhead;
    TextView viewallbooks;
    ImageView bookimage, expertImage;
    BooksModel booksModel;
    RelativeLayout expertColor;
    Dialog dialog;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        BooksList = new ArrayList<>();
        bookimage=view.findViewById(R.id.bookImage);
        bookname=view.findViewById(R.id.bookname);
        knowMore=view.findViewById(R.id.knowMore);
        AuthorName=view.findViewById(R.id.authorname);
        Recommendation=view.findViewById(R.id.recommendedBy);
        viewallbooks=view.findViewById(R.id.ViewAllBooks);
        expertImage = view.findViewById(R.id.expertImage);
        expertHead = view.findViewById(R.id.expertHead);
        expertSubhead = view.findViewById(R.id.expertSubhead);
        expertColor = view.findViewById(R.id.expertColor);
        BookOftheDay();
        expertquote();
        knowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),BookDescription.class);
                intent.putExtra("array",booksModel);
                startActivity(intent);
            }
        });
        viewallbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.homefragment,new BooksFragment()).commit();

            }
        });


        mRecyclerView = view.findViewById(R.id.HomeRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        getData();

        ImageSlider carousel = view.findViewById(R.id.carousel);

        List<SlideModel> list = new ArrayList<>();

        list.add(new SlideModel(R.drawable.rb, ScaleTypes.CENTER_CROP));
        list.add(new SlideModel(R.drawable.ra, ScaleTypes.CENTER_CROP));
        list.add(new SlideModel(R.drawable.rc, ScaleTypes.CENTER_CROP));
//        list.add(new SlideModel(R.drawable.temp, ScaleTypes.CENTER_CROP));


        carousel.setImageList(list);

        ImageSlider quotes= view.findViewById(R.id.quotes);

        List<SlideModel> qlist = new ArrayList<>();
        
        qlist.add(new SlideModel(R.drawable.b, ScaleTypes.CENTER_INSIDE));
        qlist.add(new SlideModel(R.drawable.a, ScaleTypes.CENTER_INSIDE));
        qlist.add(new SlideModel(R.drawable.c, ScaleTypes.CENTER_INSIDE));
        qlist.add(new SlideModel(R.drawable.d, ScaleTypes.CENTER_INSIDE));



        quotes.setImageList(qlist);


        return view;

    }

    private void BookOftheDay() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="https://plutoacademy.in/api/home/init";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject bookoftheday=jsonObject.getJSONObject("bookoftheday");
                    String title=bookoftheday.getString("title");
                    String author=bookoftheday.getString("author");
                    String image=bookoftheday.getString("image");
                    String buy=bookoftheday.getString("buy");
                    String recommeners="";
                    JSONArray recommenders=bookoftheday.getJSONArray("recommenders");
                    for(int i=0;i<recommenders.length();i++)
                    {
                        JSONObject jsonObject1=recommenders.getJSONObject(i);
                        String expert=jsonObject1.getString("expert");
                        recommeners=recommeners+expert+",";
                    }
                    booksModel=new BooksModel(image,recommenders.length(),title,"",author,recommeners.toString());
                    Recommendation.setText(recommeners);
                    bookname.setText(title);
                    AuthorName.setText(author);
                    Picasso.get().load(image).into(bookimage);
                }
                catch (JSONException jsonException)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void expertquote() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="https://plutoacademy.in/api/home/init";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject bookoftheday=jsonObject.getJSONObject("expertqoute");
                    String color=bookoftheday.getString("color");
                    String headText=bookoftheday.getString("headText");
                    String image=bookoftheday.getString("image");
                    String subText=bookoftheday.getString("subText");

                    expertHead.setText(headText);
                    expertSubhead.setText(subText);
                    expertColor.setBackgroundColor(Color.parseColor(color));
                    Picasso.get().load(image).into(expertImage);
                }
                catch (JSONException jsonException)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void getData() {

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="https://plutoacademy.in/api/books/list?page=1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Hello",response.toString());
                String image = null;
                String title=null;
                String buy=null;
                String author=null;
                String recommenders=null;
                int size = 0;
                JSONArray rec = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("books");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        image=jsonObject1.getString("mainImage");
                        buy=jsonObject1.getString("buy");
                        title=jsonObject1.getString("title");
                        author=jsonObject1.getString("author");
                        if(!jsonObject1.isNull("recommenders")) {
                            recommenders = jsonObject1.getJSONArray("recommenders").toString();
                            rec = jsonObject1.getJSONArray("recommenders");

                            size = rec.length();
                        }
//                        if (size <25) size = 10;
//                        else if(size>25 && size<50) size = 25;
//                        else if(size>50 && size<100) size = 50;
//                        else size = 100;
                        BooksList.add(new BooksModel(image, size, title,buy, author, recommenders));
                    }
                    mAdapter = new BooksAdapter(BooksList,HomeFragment.this);
                    mRecyclerView.setAdapter(mAdapter);
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



    public void Onclick(int position) {
        Intent mIntent=new Intent(getContext(),BookDescription.class);
        mIntent.putExtra("array",BooksList.get(position));
        startActivity(mIntent);
        //image,name(title),by,recommender,author
    }

}