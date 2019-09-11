package com.qualitorypie.qualitorypie.Activities.BorrowFragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.qualitorypie.qualitorypie.Activities.BorrowActivity;
import com.qualitorypie.qualitorypie.Adapters.ProductList;
import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Models.PersonModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * create an instance of this fragment.
 */
public class PersonList extends Fragment {

    ArrayList<PersonModel> person_model_list = new ArrayList<>();
    private ListView product_list_container;
    private View view;
    private ListView mList;
    private ProductList adapter;
    private Integer page_size = 15;
    private Integer next_offset = 0;

    private LocalDb localDb;
    private SearchView current_searchview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDb = new LocalDb(getActivity(), new PersonModel());

        current_searchview = getActivity().findViewById(R.id.searchView_borrow);
//        current_searchview.setQuery("",false);
        current_searchview.setVisibility(View.VISIBLE);
        enableSearchProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View activityView = getActivity().findViewById(R.id.to_borrow_action);
        activityView.setVisibility(View.VISIBLE);
        ((BorrowActivity)getActivity()).getSupportActionBar().setTitle("Person List");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.person_fragment_list, container, false);

        load_data(true);

        ((BorrowActivity)getActivity()).setCurrentFragment("PersonList");
        addClickListnerToFloatingActionButton();

        return view;
    }

    private void load_data(Boolean make_empty) {

        Map<String, String> search_query = new HashMap<String, String>();
        if (String.valueOf(current_searchview.getQuery()).equals("")) {
        } else {
            search_query.put("person_name", String.valueOf(current_searchview.getQuery()));
        }

        //getting cursor Object before getting data
        Cursor datas = localDb.getData(page_size, next_offset, search_query);
        next_offset = next_offset + page_size;

        if (datas.moveToFirst()) {//valid if data found
            do {
                PersonModel personModel = new PersonModel();
                personModel.setId(datas.getInt(datas.getColumnIndex("id")));
                personModel.setPerson_type(datas.getInt(datas.getColumnIndex("person_type")));
                personModel.setPerson_name(datas.getString(datas.getColumnIndex("person_name")));
                personModel.setRemaining_amt(datas.getFloat(datas.getColumnIndex("remaining_amt")));
                personModel.setPhone_no(datas.getString(datas.getColumnIndex("phone_no")));
                personModel.setAddress(datas.getString(datas.getColumnIndex("address")));
                personModel.setRemarks(datas.getString(datas.getColumnIndex("remarks")));
                personModel.setAdded_at(datas.getString(datas.getColumnIndex("added_at")));
                personModel.setIs_sinked(datas.getInt(datas.getColumnIndex("is_sinked")));

                //adding productModel to array list
                person_model_list.add(personModel);

            } while (datas.moveToNext());
        } else {
            //data not found
        }
        datas.close();
        if (make_empty) {
            mList = view.findViewById(R.id.person_list_container);
            com.qualitorypie.qualitorypie.Adapters.PersonList adapter = new com.qualitorypie.qualitorypie.Adapters.PersonList((BorrowActivity) getActivity(), view, person_model_list);
            mList.setAdapter(adapter);
        } else {
            ((BaseAdapter) mList.getAdapter()).notifyDataSetChanged();
        }
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {//0 for drag end
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {//for touch at last column
//                        Log.d("here"," touch at last");
//                        Snackbar.make(view, "Loading more persons...", Snackbar.LENGTH_SHORT)
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

    private void addClickListnerToFloatingActionButton() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.to_borrow_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment formFragment = new PersonForm();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.borrow_list_fragment, formFragment);
                ft.commit();
            }
        });
    }

    private void enableSearchProduct() {
        current_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                person_model_list = new ArrayList<>();
                next_offset = 0;
                load_data(true);
            }

        });
    }

}
