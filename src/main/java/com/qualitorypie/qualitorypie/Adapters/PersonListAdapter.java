package com.qualitorypie.qualitorypie.Adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qualitorypie.qualitorypie.Activities.PersonActivity;
import com.qualitorypie.qualitorypie.Fragments.BorrowListFragment;
import com.qualitorypie.qualitorypie.Models.PersonModel;
import com.qualitorypie.qualitorypie.R;

import java.util.ArrayList;

public class PersonListAdapter extends ArrayAdapter<PersonModel> {
    int my_resource;

    private ArrayList<PersonModel> personModels;

    public PersonListAdapter(Context context, View view, ArrayList<PersonModel> objects) {
        super(context, R.layout.person_adapter_list, objects);
        this.personModels = objects;
        my_resource = R.layout.person_adapter_list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Log.d("here",String.valueOf(productModels));
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(my_resource, parent, false);
        TextView person_name = row.findViewById(R.id.txt_person_name);
        person_name.setText(personModels.get(position).getPerson_name());

        TextView remaining_amt = row.findViewById(R.id.txt_remaining_amt);
        remaining_amt.setText(String.valueOf(personModels.get(position).getRemaining_amt()));
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //preparing to parameter to new fragment


                Fragment formFragment = new BorrowListFragment();//new fragments

                ((PersonActivity)getContext()).setPersonModel(personModels.get(position));

                FragmentTransaction ft = ((PersonActivity)getContext()).getFragmentManager().beginTransaction();
                ft.replace(R.id.borrow_list_fragment, formFragment);
                ft.commit();

            }
        });
        return row;
    }

}
