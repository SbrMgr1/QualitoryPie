package com.qualitorypie.qualitorypie.Activities.ProductFragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Activities.ProductActivity;
import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Models.ProductModel;
import com.qualitorypie.qualitorypie.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ProductForm extends Fragment {

    private int product_id = 0;
    private AutoCompleteTextView product_name;
    private AutoCompleteTextView whole_sale_price;
    private AutoCompleteTextView retail_price;
    private EditText remarks;

    private LocalDb localDb;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDb = new LocalDb(getActivity(), new ProductModel());

        ((ProductActivity)getActivity()).current_searchview.setVisibility(View.GONE);
        ((ProductActivity)getActivity()).setCurrentFragment("ProductForm");
        if (getArguments() != null) {
            product_id = getArguments().getInt("product_id");
            Toast.makeText(getActivity(), "ProductModel data has been loaded successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View activityView = getActivity().findViewById(R.id.to_product_action);
        activityView.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_fragment_form, container, false);
        product_name = (AutoCompleteTextView) view.findViewById(R.id.input_product_name);
        whole_sale_price = (AutoCompleteTextView) view.findViewById(R.id.input_whole_sale_price);
        retail_price = (AutoCompleteTextView) view.findViewById(R.id.input_retail_price);
        remarks = (EditText) view.findViewById(R.id.input_remarks);
        if (product_id > 0) {
            localDb = new LocalDb(getActivity(), new ProductModel());
            Cursor single_data = localDb.getSingleData(product_id);
            if (single_data.moveToFirst()) {
                do {
                    product_name.setText(single_data.getString(single_data.getColumnIndex("product_name")));
                    whole_sale_price.setText(single_data.getString(single_data.getColumnIndex("wholesale_price")));
                    retail_price.setText(single_data.getString(single_data.getColumnIndex("retail_price")));
                    remarks.setText(single_data.getString(single_data.getColumnIndex("remarks")));
                } while (single_data.moveToNext());
            }
        }
        initProdSaveBtn(view);
        return view;
    }

    private void initProdSaveBtn(View view) {
        Button button = view.findViewById(R.id.product_save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate_product_form(view)) {
                    ProductModel product_Model_model = new ProductModel();
                    Map<String, String> items = new HashMap<String, String>();
                    items.put("product_name", product_name.getText().toString());
                    items.put("wholesale_price", whole_sale_price.getText().toString());
                    items.put("retail_price", retail_price.getText().toString());
                    items.put("remarks", remarks.getText().toString());
                    items.put("is_sinked", "0");
                    items.put("deleted", "0");
                    items.put("added_at", Calendar.getInstance().getTime().toString());

                    if (product_id > 0) {
                        if (localDb.updateData(items, product_id)) {

                            ProductActivity productActivity = (ProductActivity) getActivity();
                            productActivity.setCurrentFragment("ProductList");
                            Toast.makeText(getActivity(), "Saved Successfully.", Toast.LENGTH_LONG).show();

                            Fragment fragment_container = new ProductList();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.prod_frag_container, fragment_container);
                            ft.commit();

                        } else {
                            Toast.makeText(getActivity(), "Error Occured. Please try again later", Toast.LENGTH_LONG);
                        }
                    } else if (localDb.addData(items)) {

                        ProductActivity productActivity = (ProductActivity) getActivity();
                        productActivity.setCurrentFragment("ProductList");
                        Toast.makeText(getActivity(), "Saved Successfully.", Toast.LENGTH_LONG).show();

                        Fragment fragment_container = new ProductList();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.prod_frag_container, fragment_container);
                        ft.commit();

                    } else {
                        Toast.makeText(getActivity(), "Produce has not been saved Successfully.", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }

    private boolean validate_product_form(View view) {
        boolean error_found = false;
        if (product_name.getText().toString().equals("")) {
            error_found = true;
            product_name.setError(getString(R.string.product_name_required));
        }
        if (whole_sale_price.getText().toString().equals("")) {
            error_found = true;
            whole_sale_price.setError(getString(R.string.product_whole_sale_price_required));
        }
        if (retail_price.getText().toString().equals("")) {
            error_found = true;
            retail_price.setError(getString(R.string.retail_price_required));
        }
        if (error_found) {
            return false;
        }
        return true;
    }
}
