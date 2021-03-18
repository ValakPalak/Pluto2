package com.books2.read;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedBooksFragment extends Fragment implements OnItemClick{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<BooksModel> booksModelList=new ArrayList<>();
    List<String> checkname=new ArrayList<>();
    List<String> name=new ArrayList<>();
    RecyclerView recyclerView;
    BooksAdapter booksAdapter;
    ArrayList<BooksModel> list=new ArrayList<>();

    public SavedBooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedBooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedBooksFragment newInstance(String param1, String param2) {
        SavedBooksFragment fragment = new SavedBooksFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_saved_books, container, false);
        name=loadSharedPreferencesLogNameList();
        booksModelList=loadSharedPreferencesLogModelList();
        recyclerView=view.findViewById(R.id.SavedBooksRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        for(int i=0;i<name.size();i++)
        {
            if(checkname.contains(name.get(i))){

            }
            else{

                for(int j=0;j<booksModelList.size();j++)
                {
                    if(name.get(i).equals(booksModelList.get(j).getmBookName()) && !checkname.contains(name.get(i)))
                    {
                        list.add(booksModelList.get(i));
                        checkname.add(name.get(i));
                    }
                }

            }
        }
        booksAdapter=new BooksAdapter(list,this);
        recyclerView.setAdapter(booksAdapter);
        booksAdapter.notifyDataSetChanged();
        return view;





    }
    public List<BooksModel> loadSharedPreferencesLogModelList() {
        List<BooksModel> booksModelList = new ArrayList<>();
        SharedPreferences mPrefs = getActivity().getSharedPreferences("List", Context.MODE_PRIVATE);
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
        SharedPreferences mPrefs = getActivity().getSharedPreferences("List", Context.MODE_PRIVATE);
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
    public void Onclick(int position) {
        Intent mIntent=new Intent(getContext(),BookDescription.class);
        mIntent.putExtra("array",list.get(position));
        startActivity(mIntent);
    }
}