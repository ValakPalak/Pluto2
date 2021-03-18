package com.books2.read;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChangeInterests extends AppCompatActivity {
    LinearLayout one,two,three,four,five,six;
    String t="";
    int num=1;
    Button saveChnages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_interests);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        saveChnages=findViewById(R.id.savechanges);
        saveChnages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(num<3)
                {
                    if(!t.contains("1"))
                    {
                        num++;
                        t=t+"1";
                        one.setBackgroundColor(0x00000000);
                    }
                    else{
                        num--;
                        t.replaceAll("1","");
                        one.setBackgroundColor(0xffffff);
                    }
                }
                else{

                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!t.contains("2")) {

                    two.setBackgroundColor(0xFFFF0000);
                    t=t+"2";
                    Toast.makeText(ChangeInterests.this, ""+t, Toast.LENGTH_SHORT).show();
                    if(num<3) {
                        num++;
                    }
                    else{
                        Toast.makeText(ChangeInterests.this, "you can only select three", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    two.setBackgroundColor(0xffffff);
                    num--;
                    t.replaceAll("2","");

                    Toast.makeText(ChangeInterests.this, ""+t, Toast.LENGTH_SHORT).show();

                }

            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!t.contains("3")) {

                    three.setBackgroundColor(0xFFFF0000);
                    t=t+"3";
                    Toast.makeText(ChangeInterests.this, ""+t, Toast.LENGTH_SHORT).show();
                    if(num<3) {
                        num++;
                    }
                    else{
                        Toast.makeText(ChangeInterests.this, "you can only select three", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    three.setBackgroundColor(0xffffff);
                    num--;
                    t.replaceAll("3","");

                    Toast.makeText(ChangeInterests.this, ""+t, Toast.LENGTH_SHORT).show();
                }

            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!t.contains("4")) {

                    four.setBackgroundColor(0xFFFF0000);
                    t=t+"4";
                    if(num<3) {
                        num++;
                    }
                    else{
                        Toast.makeText(ChangeInterests.this, "you can only select three", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    four.setBackgroundColor(0xffffff);
                    num--;
                    t.replaceAll("4","");

                }

            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!t.contains("5")) {

                    five.setBackgroundColor(0xFFFF0000);
                    t=t+"5";
                    if(num<3) {
                        num++;
                    }
                    else{
                        Toast.makeText(ChangeInterests.this, "you can only select three", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    five.setBackgroundColor(0xffffff);
                    num--;
                    t.replaceAll("5","");

                }

            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!t.contains("1")) {

                    six.setBackgroundColor(0xFFFF0000);
                    t=t+"6";
                    if(num<3) {
                        num++;
                    }
                    else{
                        Toast.makeText(ChangeInterests.this, "you can only select three", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    six.setBackgroundColor(0xffffff);
                    num--;
                    t.replaceAll("6","");
                }

            }
        });
    }
    private void loadShaaredPrefrence(String s) {
        SharedPreferences sharedPreferences=getSharedPreferences("choice",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(s,"");
        editor.apply();
    }
}