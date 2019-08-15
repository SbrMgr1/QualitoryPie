package com.qualitorypie.qualitorypie.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.qualitorypie.qualitorypie.Activities.ProductActivity;
import com.qualitorypie.qualitorypie.Adapters.ProductListAdapter;
import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Models.ProductModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * create an instance of this fragment.
 */
public class ProdListFragment extends Fragment {

    ArrayList<ProductModel> product_Model_list = new ArrayList<>();
    private ListView product_list_container;
    private View view;
    private ListView mList;
    private ProductListAdapter adapter;
    private Integer page_size = 15;
    private Integer next_offset = 0;
    private LocalDb localDb;// making productModel for database connection


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDb = new LocalDb(getActivity(), new ProductModel());
        ((ProductActivity)getActivity()).current_searchview.setVisibility(View.VISIBLE);
        enableSearchProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View activityView = getActivity().findViewById(R.id.to_product_action);
        activityView.setVisibility(View.VISIBLE);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.product_fragment_list_layout, container, false);

        load_data(true);
        ((ProductActivity)getActivity()).setCurrentFragment("ProdListFragment");

        addClickListnerToFloatingActionButton();
        return view;
    }

    private void load_data(Boolean make_empty) {


        Map<String, String> search_query = new HashMap<String, String>();
        if (String.valueOf(((ProductActivity)getActivity()).current_searchview.getQuery()).equals("")) {
        } else {
            search_query.put("product_name", String.valueOf(((ProductActivity)getActivity()).current_searchview.getQuery()));
        }

        Cursor datas = localDb.getData(page_size, next_offset, search_query);

        //getting cursor Object before getting data
//        Cursor datas = localDb.getData(page_size,next_offset);
        next_offset = next_offset + page_size;

        if (datas.moveToFirst()) {//valid if data found
            do {
                ProductModel productModel = new ProductModel();
                productModel.setId(datas.getInt(datas.getColumnIndex("id")));
                productModel.setProduct_name(datas.getString(datas.getColumnIndex("product_name")));
                productModel.setWholesale_price(datas.getString(datas.getColumnIndex("wholesale_price")));
                productModel.setRetail_price(datas.getString(datas.getColumnIndex("retail_price")));
                productModel.setRemarks(datas.getString(datas.getColumnIndex("remarks")));
                productModel.setAdded_at(datas.getString(datas.getColumnIndex("added_at")));
                productModel.setIs_sinked(datas.getInt(datas.getColumnIndex("is_sinked")));

                product_Model_list.add(productModel);
            } while (datas.moveToNext());
        } else {
            Log.d("here", "data not found");
        }
        datas.close();

        if (make_empty) {
            mList = view.findViewById(R.id.product_list_container);
            adapter = new ProductListAdapter((ProductActivity) getActivity(), view, product_Model_list);
            mList.setAdapter(adapter);
        } else {
            Log.d("hering", String.valueOf(product_Model_list.size()));
            ((BaseAdapter) mList.getAdapter()).notifyDataSetChanged();
        }
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {//0 for drag end
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {//for touch at last column
//                        Log.d("here"," touch at last");
//                        Snackbar.make(view, "Loading more products...", Snackbar.LENGTH_SHORT)
//                                .setAction("Action", null).show();
                        load_data(false);
                    }
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void enableSearchProduct() {
        ((ProductActivity)getActivity()).current_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                product_Model_list = new ArrayList<>();
                next_offset = 0;
                load_data(true);
            }

        });
    }

    private void addClickListnerToFloatingActionButton() {

        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.to_product_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment formFragment = new ProductFormFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.prod_frag_container, formFragment);
                ft.commit();
                ((ProductActivity)getActivity()).current_searchview.setQuery("", false);
            }
        });
    }


}