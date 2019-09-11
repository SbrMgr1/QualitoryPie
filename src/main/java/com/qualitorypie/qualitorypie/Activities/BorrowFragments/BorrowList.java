package com.qualitorypie.qualitorypie.Activities.BorrowFragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qualitorypie.qualitorypie.Activities.BorrowActivity;
import com.qualitorypie.qualitorypie.Adapters.BorrowListItem;
import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Models.BorrowModel;
import com.qualitorypie.qualitorypie.Models.ProductModel;
import com.qualitorypie.qualitorypie.R;

import java.util.AbstractList;
import java.util.ArrayList;


public class BorrowList extends Fragment {

    private Integer person_id;
    private LocalDb LocalDb;
    private float remaining_amt;
    private ListView blist;
    private Integer page_size = 15;
    private Integer next_offset = 0;
    private AbstractList<BorrowModel> borrowList = new ArrayList<>();
    private View view;
    private BorrowListItem adapter;
    private SearchView current_searchview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalDb = new LocalDb(getActivity(), new BorrowModel());// making LocalDb for database connection

        current_searchview = getActivity().findViewById(R.id.searchView_borrow);
        current_searchview.setVisibility(View.GONE);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.borrow_fragment_list, container, false);

        ((BorrowActivity)getActivity()).getSupportActionBar().setTitle("Borrow List of " + ((BorrowActivity)getActivity()).getPersonModel().getPerson_name());


        load_data(true);

        addClickListnerToFloatingActionButton();
        ((BorrowActivity)getActivity()).setCurrentFragment("BorrowList");
        return view;
    }

    private void load_data(Boolean make_empty) {
        BorrowModel borrowModel_tmp = new BorrowModel();
        ProductModel productModel_tmp = new ProductModel();
        String stmt = "SELECT B.*,P.product_name FROM " + borrowModel_tmp.getTableName() + " B ";
        stmt += " LEFT JOIN " + productModel_tmp.getTableName() + " P ON B.prod_id = P.id ";
        stmt += " WHERE B.user_id = " + ((BorrowActivity)getActivity()).getPersonModel().getId();
        stmt += " AND B.deleted = 0 ";
        stmt += " LIMIT " + page_size + " OFFSET " + next_offset;
        Cursor datas = LocalDb.rowQuery(stmt);
        if (datas.moveToFirst()) {
            do {
                BorrowModel borrowModel = new BorrowModel();
                borrowModel.setId(datas.getInt(datas.getColumnIndex("id")));
                borrowModel.setProd_id(datas.getInt(datas.getColumnIndex("prod_id")));
                borrowModel.setProduct_name(datas.getString(datas.getColumnIndex("product_name")));
                borrowModel.setBorrow_amt(datas.getFloat(datas.getColumnIndex("borrow_amt")));
                borrowModel.setAdded_at(datas.getString(datas.getColumnIndex("added_at")));
                borrowModel.setUser_id(datas.getInt(datas.getColumnIndex("user_id")));
                borrowList.add(borrowModel);
            } while (datas.moveToNext());
        }
        if (make_empty) {
            blist = view.findViewById(R.id.borrow_list_container);
            adapter = new BorrowListItem(getActivity(), view, (ArrayList<BorrowModel>) borrowList);
            blist.setAdapter(adapter);
        }
    }

    private void addClickListnerToFloatingActionButton() {

        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.to_borrow_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment formFragment = new BorrowForm();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.borrow_list_fragment, formFragment);
                ft.commit();
            }
        });
    }


}
