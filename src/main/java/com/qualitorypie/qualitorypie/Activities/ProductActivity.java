package com.qualitorypie.qualitorypie.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.qualitorypie.qualitorypie.Activities.ProductFragments.ProductList;
import com.qualitorypie.qualitorypie.R;

public class ProductActivity extends BaseActivity {

    private String current_fragment;

    public SearchView current_searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        current_searchview = findViewById(R.id.searchView_prod);
        //loading default fragments
        Fragment productList = new ProductList();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.prod_frag_container, productList);
        ft.commit();
        //assigning current fragments
        setCurrentFragment("ProductList");
    }

    /**
     * Handling the action on click navication up button of toolbar
     *
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {


        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        if (current_fragment.equals("ProductForm")) {
            Fragment productList = new ProductList();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.prod_frag_container, productList);
            ft.commit();
        } else {
            // Otherwise defer to system default behavior.
            super.onBackPressed();
        }
    }


    public void setCurrentFragment(String fragment_name) {
        current_fragment = fragment_name;
    }

}
