package com.example.getgpslocation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.getgpslocation.Constant.Constant;
import com.example.getgpslocation.Constant.SessionManager;
import com.example.getgpslocation.R;
import com.example.getgpslocation.VisitedStoreDetail;
import com.example.getgpslocation.adapter.VistedStoresAdapter;
import com.example.getgpslocation.helper.WebServicesFunctions;
import com.example.getgpslocation.models.VistedStore;
import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class FragmentList extends Fragment {

    public static RecyclerView recyclerView;
    public static VistedStoresAdapter mAdapter;
    public static ProgressBar progressBar;
    private SwipyRefreshLayout swipeContainer;
    public static ArrayList<VistedStore> visitedStore;

    int size = 1;

    public static FragmentList newInstance() {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentList() {
        /* Required empty public constructor*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_fragment_list, container, false);
        View rootView = inflater.inflate(R.layout.fragment_fragment_list, container, false);
        initViews(rootView);
        assignViews();
        initEventDriven();
        getVisitedStores(0);
        return rootView;
    }

    private void assignViews() {
        visitedStore = new ArrayList<VistedStore>();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        mAdapter = new VistedStoresAdapter(visitedStore, getActivity(), new VistedStoresAdapter.VisitedOnClickHolder() {
            @Override
            public void onClick(VistedStoresAdapter.MyViewHolder vh, int position) {

                Intent intent = new Intent(getActivity(), VisitedStoreDetail.class);
                intent.putExtra("Store", visitedStore.get(position));
                startActivity(intent);
                swipeContainer.setRefreshing(false);

            }
        });
        recyclerView.setAdapter(mAdapter);


        swipeContainer.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));

                if (SwipyRefreshLayoutDirection.TOP.equals(direction)) {
                    Log.e("MainActivity", "Top ");
                    getVisitedStores(0);

                } else {
                    Log.e("MainActivity", "bottom ");
                    size = size + 20;
                    getVisitedStores(size);
                }
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void initViews(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_visited);
        swipeContainer = (SwipyRefreshLayout) rootView.findViewById(R.id.swipeContainer);


    }

    private void initEventDriven() {
    }


    private void getVisitedStores(final int pagination) {

        String url = Constant.BASE_URL + Constant.CENTERS;
        JSONObject jsonObject = new JSONObject();
        swipeContainer.setRefreshing(true);
        try {

            String token = new SessionManager().UserTokenPref(getActivity(), "user_token_pref").toString();
            int id = new SessionManager().UserIdPref(getActivity(), "user_id_pref");

            jsonObject.put("userId", id);
            jsonObject.put("token", token);
            jsonObject.put("lang", "en");
            jsonObject.put("limit", 20);
            jsonObject.put("lastId", pagination);


            Log.e("request", jsonObject.toString() + url);
//            final ProgressDialog progressDialog = ProgressDialog.show(VisitedStores.this, "", "Loading");
            new WebServicesFunctions(getActivity()).sendPostJsonObjectRequestToWs(url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("response1 ", response.toString());
                    //    progressDialog.cancel();
                    swipeContainer.setRefreshing(false);


                    try {
                        int status = response.getInt("status");
                        if (status == 1) {
                            JSONArray data = response.getJSONArray("data");
                            if (pagination == 0) {
                                FragmentList.visitedStore.clear();
                            }
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jresponse = data.getJSONObject(i);
                                VistedStore visitedStore = new Gson().fromJson(jresponse.toString(), VistedStore.class);
                                FragmentList.visitedStore.add(visitedStore);
                            }
                            FragmentList.mAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    WebServicesFunctions.printError(error);
                    Log.e("error", "error");
                    //  progressDialog.cancel();
                    swipeContainer.setRefreshing(false);
                }
            });


        } catch (JSONException e) {
            Log.e("json ex", e.getMessage());
            e.printStackTrace();
        }


    }

}
