package com.qualitorypie.qualitorypie.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Activities.PersonActivity;
import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Models.PersonModel;
import com.qualitorypie.qualitorypie.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class PersonFormFragment extends Fragment {

    private int user_id = 0;
    private AutoCompleteTextView person_name;
    private AutoCompleteTextView phone_no;
    private EditText address;
    private EditText remarks;

    private LocalDb localDb;

    private SearchView current_searchview;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDb = new LocalDb(getActivity(), new PersonModel());

        current_searchview = getActivity().findViewById(R.id.searchView_borrow);
        current_searchview.setVisibility(View.GONE);

        if (getArguments() != null) {
            user_id = getArguments().getInt("user_id");
            Toast.makeText(getActivity(), "Person data has been loaded successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View activityView = getActivity().findViewById(R.id.to_borrow_action);
        activityView.setVisibility(View.GONE);
        if (user_id > 0) {
            ((PersonActivity)getActivity()).getSupportActionBar().setTitle("Update old Person");

        } else {
            ((PersonActivity)getActivity()).getSupportActionBar().setTitle("Add new Person");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.person_fragment_form, container, false);
        person_name = (AutoCompleteTextView) view.findViewById(R.id.input_person_name);
        phone_no = (AutoCompleteTextView) view.findViewById(R.id.input_person_name);
        address = (EditText) view.findViewById(R.id.input_address);
        remarks = (EditText) view.findViewById(R.id.input_remarks);
        if (user_id > 0) {
            localDb = new LocalDb(getActivity(), new PersonModel());
            Cursor single_data = localDb.getSingleData(user_id);
            if (single_data.moveToFirst()) {
                do {
                    person_name.setText(single_data.getString(single_data.getColumnIndex("person_name")));
                    phone_no.setText(single_data.getString(single_data.getColumnIndex("phone_no")));
                    address.setText(single_data.getString(single_data.getColumnIndex("address")));
                    remarks.setText(single_data.getString(single_data.getColumnIndex("remarks")));
                } while (single_data.moveToNext());
            }
        }
        ((PersonActivity)getActivity()).setCurrentFragment("PersonFormFragment");
        initPersonSaveBtn(view);
        return view;
    }

    private void initPersonSaveBtn(View view) {
        Button button = view.findViewById(R.id.person_save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate_product_form(view)) {
                    PersonModel personModel = new PersonModel();
                    Map<String, String> items = new HashMap<String, String>();
                    items.put("person_name", person_name.getText().toString());
                    items.put("phone_no", phone_no.getText().toString());
                    items.put("address", address.getText().toString());
                    items.put("remarks", remarks.getText().toString());
                    items.put("is_sinked", "0");
                    items.put("deleted", "0");
                    items.put("person_type", "2");
                    items.put("added_at", Calendar.getInstance().getTime().toString());

                    if (user_id > 0) {
                        if (localDb.updateData(items, user_id)) {

                            Toast.makeText(getActivity(), "Saved Successfully.", Toast.LENGTH_LONG).show();

                            Fragment fragment_container = new BorrowPersonListFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.borrow_list_fragment, fragment_container);
                            ft.commit();

                        } else {
                            Toast.makeText(getActivity(), "Error Occured. Please try again later", Toast.LENGTH_LONG);
                        }
                    } else {
                        items.put("remaining_amt", "0.00");

                        if (localDb.addData(items)) {

                            ((PersonActivity)getActivity()).setCurrentFragment("BorrowPersonListFragment");
                            Toast.makeText(getActivity(), "Saved Successfully.", Toast.LENGTH_LONG).show();

                            Fragment fragment_container = new BorrowPersonListFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.borrow_list_fragment, fragment_container);
                            ft.commit();

                        } else {
                            Toast.makeText(getActivity(), "Borrow has not been saved Successfully.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

        });
    }

    private boolean validate_product_form(View view) {
        boolean error_found = false;
        if (person_name.getText().toString().equals("")) {
            error_found = true;
            person_name.setError(getString(R.string.person_name_required));
        }
        if (phone_no.getText().toString().equals("")) {
            error_found = true;
            phone_no.setError(getString(R.string.phone_no_required));
        }
        if (address.getText().toString().equals("")) {
            error_found = true;
            address.setError(getString(R.string.address_required));
        }
        if (error_found) {
            return false;
        }
        return true;
    }
}
