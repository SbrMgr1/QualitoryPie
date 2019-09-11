package com.qualitorypie.qualitorypie.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Adapters.BorrowListSwipeAdapter;
import com.qualitorypie.qualitorypie.Models.BorrowModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;

public class BorrowListItem extends ArrayAdapter<BorrowModel> {

    private ArrayList<BorrowModel> borrowModels;

    public BorrowListItem(Context context, View view, ArrayList<BorrowModel> objects) {
        super(context, R.layout.borrow_adapter_list, objects);
        borrowModels = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(R.layout.borrow_adapter_list, parent, false);

        ViewPager viewPager = row.findViewById(R.id.borrow_list_swipe_adapter);
        BorrowListSwipeAdapter borrowListSwipeAdapter = new BorrowListSwipeAdapter(getContext(), borrowModels.get(position));
        viewPager.setAdapter(borrowListSwipeAdapter);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Pending to do", Toast.LENGTH_SHORT).show();

            }
        });
        return row;
    }

}
