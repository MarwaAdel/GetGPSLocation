package com.example.getgpslocation.helper;

/**
 * Created by Eman on 11/27/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class WebServicesFunctions {


    Context context;

    public WebServicesFunctions(Context context) {
        this.context = context;
    }


    public void sendJsonArrayGetRequestToWs(String url, Response.Listener listener, Response.ErrorListener errorListener) {

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(url, listener, errorListener);


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, "");
    }


    public void sendJsonObjectGetRequestToWs(String url, Response.Listener listener, Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, "", listener, errorListener);

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppController.getInstance().addToRequestQueue(jsonObjReq, "search");
    }



    public void sendPostJsonObjectRequestToWs(final Activity activity, String url, JSONObject jsonObject, Response.Listener listener, final Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, errorListener) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                return super.parseNetworkResponse(response);
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, "search");
    }

    public void sendPostJsonObjectRequestToWs(String url, JSONObject jsonObject, Response.Listener listener, final Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, errorListener) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                return super.parseNetworkResponse(response);
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, "search");
    }

    public void sendPostJsonArrayRequestToWs(String url, JSONObject jsonObject, Response.Listener listener, Response.ErrorListener errorListener) {

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST, url, jsonObject, listener, errorListener) {

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, "search");
    }

    public void sendPostRequestToWs(String url, String jsonString, Response.Listener listener, Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonString, listener, errorListener) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                Log.e("network response", response.statusCode + "");

                return super.parseNetworkResponse(response);
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, "search");
    }


    public static void printError(VolleyError error) {

        if (error != null && error.networkResponse != null && error.networkResponse.data != null) {
            try {
                Log.e("Error", new String(error.networkResponse.data, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
            }
        }
    }
}
