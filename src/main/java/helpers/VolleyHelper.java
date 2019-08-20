package helpers;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyHelper {



    public static void put_request(Context context,String url,String type){
        RequestQueue queue = Volley.newRequestQueue(context);
        int method = 0;
        if(type.toLowerCase() == "post"){
            method =  Request.Method.POST;
        }else{
            method =  Request.Method.GET;
        }
        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

}
