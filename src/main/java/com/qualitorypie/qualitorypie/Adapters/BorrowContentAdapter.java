package com.qualitorypie.qualitorypie.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Models.BorrowModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;

public class BorrowContentAdapter extends ArrayAdapter<BorrowModel> {
    private ArrayList<BorrowModel> borrowModels;

    public BorrowContentAdapter(Context context, View view, ArrayList<BorrowModel> objects) {
        super(context, R.layout.borrow_adapter_list, objects);
        borrowModels = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(R.layout.borrow_adapter_list, parent, false);

        BorrowModel borrowModel = borrowModels.get(position);

        TextView txt_adapter_item_name_obj = row.findViewById(R.id.txt_adapter_borrow_item_name);
        txt_adapter_item_name_obj.setText(String.valueOf(borrowModel.getProduct_name()));

        TextView txt_adapter_amt_obj = row.findViewById(R.id.txt_adapter_borrow_amt);
        txt_adapter_amt_obj.setText(String.valueOf(borrowModel.getBorrow_amt()));


        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Pending to do", Toast.LENGTH_SHORT).show();

            }
        });
        return row;
    }

}
