package com.qualitorypie.qualitorypie.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qualitorypie.qualitorypie.Models.ProductModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;

public class ProductDropdownListAdapter extends ArrayAdapter<ProductModel> {
    int my_resource;
    private Context my_activity;
    private ArrayList<ProductModel> productModels;
    private Boolean enable_adapter_click;

    public ProductDropdownListAdapter(Context context, View view, ArrayList<ProductModel> objects) {

        super(context, R.layout.adapter_product_dropdownlist, objects);
        my_activity = context;
        this.productModels = objects;
        my_resource = R.layout.adapter_product_dropdownlist;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(my_resource, parent, false);

        TextView product_name = row.findViewById(R.id.txt_prod_name_dropdown);
        product_name.setText(productModels.get(position).getProduct_name());

        return row;
    }

    @Override
    public TextView getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(productModels.get(position).getProduct_name());

        return label;
    }

    @Override
    public ProductModel getItem(int position) {
        return productModels.get(position);
    }

}
