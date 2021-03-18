package com.books2.read;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Feedback extends AppCompatActivity {
    RatingBar mRatingBar;
    ImageView BackP, done;
    EditText mname, mmobile, body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mRatingBar = findViewById(R.id.rating);
        String rating = mRatingBar.getRating() + "";
//        Toast.makeText(Feedback.this, rating, Toast.LENGTH_LONG).show();
        BackP = findViewById(R.id.BackP);
        done = findViewById(R.id.done);
        BackP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postData();
                Toast.makeText(getApplicationContext(), "Thank You for your Feedback!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    // Post Request For JSONObject
    public void postData() {
//
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(name);
//        jsonArray.put(mobile);
//        jsonArray.put(rating);
//        final String mRequestBody = jsonArray.toString();

        try {
            mname = findViewById(R.id.name);
            mmobile = findViewById(R.id.mobile);
            mRatingBar = findViewById(R.id.rating);
            body = findViewById(R.id.FeedbackBody);
            String rating = mRatingBar.getRating() + "";
            String name = mname.getText().toString().trim();
            String fbody = body.getText().toString().trim();
//        String rating = editTextEmail.getText().toString().trim();
            String mobile = mmobile.getText().toString().trim();


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://plutoacademy.in/api/home/feedback?name="+ name + "&mobile=" + mobile +"&rating=" + rating;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Body", fbody);
            final String requestBody = fbody;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.v("LOG_VOLLEY", response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("LOG_VOLLEY", error.toString());
//            }
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                    return null;
//                }
//            }
//
//        };
//        stringRequest.setShouldCache(false);
//        VollySupport.getmInstance(RegisterActivity.this).addToRequestque(stringRequest);
    }
}