package com.qualitorypie.qualitorypie.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Activities.PersonActivity;
import com.qualitorypie.qualitorypie.Adapters.ProductDropdownListAdapter;
import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Models.BorrowModel;
import com.qualitorypie.qualitorypie.Models.ProductModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class BorrowFormFragment extends Fragment {


    private Spinner input_product_id_spinner;
    private EditText address;
    private EditText remarks;
    private View view;

    private LocalDb localDb;
    private AutoCompleteTextView input_borrow_amt;
    private SearchView current_searchview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDb = new LocalDb(getActivity(), new BorrowModel());
        current_searchview = getActivity().findViewById(R.id.searchView_borrow);
        current_searchview.setVisibility(View.GONE);

        if (getArguments() != null) {
            Toast.makeText(getActivity(), "Person data has been loaded successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View activityView = getActivity().findViewById(R.id.to_borrow_action);
        activityView.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.borrow_fragment_form, container, false);
        input_product_id_spinner = (Spinner) view.findViewById(R.id.input_product_id);
        set_spinner_item();

        input_borrow_amt = (AutoCompleteTextView) view.findViewById(R.id.input_borrow_amt);
        remarks = (EditText) view.findViewById(R.id.input_borrow_form_remarks);

        initBorrowSaveBtn(view);
        ((PersonActivity)getActivity()).setCurrentFragment("BorrowFormFragment");
        return view;
    }

    public void set_spinner_item() {
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();


        LocalDb product_loacaldb = new LocalDb(getActivity(), new ProductModel());
        Cursor product_datas = product_loacaldb.getData(10000, 0);

        if (product_datas.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setId(product_datas.getInt(product_datas.getColumnIndex("id")));
                productModel.setProduct_name(product_datas.getString(product_datas.getColumnIndex("product_name")));
                products.add(productModel);
            } while (product_datas.moveToNext());
        }
        ProductDropdownListAdapter product_list = new ProductDropdownListAdapter(getActivity(), view, products);

        product_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        product_list.setDropDownViewResource(R.layout.custom_spinner_layout);

        input_product_id_spinner.setAdapter(product_list);
    }

    private void initBorrowSaveBtn(View view) {
        Button button = view.findViewById(R.id.borrow_save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate_product_form(view)) {
                    ProductModel selected_model = (ProductModel) input_product_id_spinner.getSelectedItem();

                    BorrowModel borrowModel = new BorrowModel();
                    Map<String, String> items = new HashMap<String, String>();

                    items.put("user_id", String.valueOf(((PersonActivity)getActivity()).getPersonModel().getId()));
                    items.put("prod_id", String.valueOf(selected_model.getId()));
                    items.put("borrow_amt", input_borrow_amt.getText().toString());
                    items.put("remarks", remarks.getText().toString());
                    items.put("is_sinked", "0");
                    items.put("deleted", "0");
                    items.put("added_at", Calendar.getInstance().getTime().toString());

                    if (localDb.addData(items)) {

                        PersonActivity personActivity = (PersonActivity) getActivity();
                        ((PersonActivity) getActivity()).manageRemainingAmt(((PersonActivity)getActivity()).getPersonModel().getId());

                        input_borrow_amt.setText("");

                        Toast.makeText(getActivity(), "Saved Successfully.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Borrow has not been saved Successfully.", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }

    private boolean validate_product_form(View view) {
        boolean error_found = false;

        if (input_product_id_spinner.getSelectedItem().toString().equals("")) {
            error_found = true;
            TextView spinner_field = (TextView) input_product_id_spinner.getSelectedView();
            spinner_field.setError(getString(R.string.product_required));
        }
        if (input_borrow_amt.getText().toString().equals("")) {
            error_found = true;
            input_borrow_amt.setError(getString(R.string.amount_required));
        }
        if (error_found) {
            return false;
        }
        return true;
    }
}
