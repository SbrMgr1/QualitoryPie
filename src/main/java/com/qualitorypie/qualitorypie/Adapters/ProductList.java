package com.qualitorypie.qualitorypie.Adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qualitorypie.qualitorypie.Activities.ProductActivity;
import com.qualitorypie.qualitorypie.Activities.ProductFragments.ProductForm;
import com.qualitorypie.qualitorypie.Models.ProductModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;

public class ProductList extends ArrayAdapter<ProductModel> {
    int my_resource;
    private Context my_activity;
    private ArrayList<ProductModel> productModels;
    private Boolean enable_adapter_click;

    public ProductList(Context context, View view, ArrayList<ProductModel> objects) {

        super(context, R.layout.product_adapter_list, objects);
        my_activity = context;
        this.productModels = objects;
        my_resource = R.layout.product_adapter_list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Log.d("here",String.valueOf(productModels));
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(my_resource, parent, false);
        TextView product_name = row.findViewById(R.id.txt_prod_name);
        product_name.setText(productModels.get(position).getProduct_name());

        TextView retail_price = row.findViewById(R.id.txt_retail_price);
        retail_price.setText(productModels.get(position).getRetail_price());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //preparing to parameter to new fragment
                Bundle bundle = new Bundle();
                bundle.putInt("product_id", productModels.get(position).getId());

                Fragment formFragment = new ProductForm();//new fragments
                formFragment.setArguments(bundle);//setting new arguements

                FragmentTransaction ft = ((ProductActivity) my_activity).getFragmentManager().beginTransaction();
                ft.replace(R.id.prod_frag_container, formFragment);
                ft.commit();
                ProductActivity tmp_activity = (ProductActivity) my_activity;
                tmp_activity.current_searchview.setQuery("", false);

            }
        });

        return row;
    }

}
