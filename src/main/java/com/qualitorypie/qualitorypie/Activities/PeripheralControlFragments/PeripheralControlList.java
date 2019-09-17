package com.qualitorypie.qualitorypie.Activities.PeripheralControlFragments;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualitorypie.qualitorypie.Activities.PeripheralControlActivity;
import com.qualitorypie.qualitorypie.Activities.ProductFragments.ProductList;
import com.qualitorypie.qualitorypie.R;

public class PeripheralControlList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((PeripheralControlActivity)getActivity()).pepheralControlListToolBar();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addClickListnerToFloatingActionButton();
        ((PeripheralControlActivity)getActivity()).setCurentFragment("PeripheralControlList");
        return inflater.inflate(R.layout.fragment_peripheral_control_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }
    private void addClickListnerToFloatingActionButton() {
        getActivity().findViewById(R.id.to_peripheral_control_action).setVisibility(View.VISIBLE);
        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.to_peripheral_control_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment peripheral_control_form = new PeripheralControlForm();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.peripheral_frag_container,peripheral_control_form);
                ft.commit();
            }
        });
    }


//    private void enableSearchProduct() {
//        ((ProductActivity)getActivity()).current_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                callSearch(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                callSearch(newText);
//                return true;
//            }
//
//            public void callSearch(String query) {
////                product_Model_list = new ArrayList<>();
////                next_offset = 0;
////                load_data(true);
//            }
//
//        });
//    }
}
