package com.qualitorypie.qualitorypie.Activities.PeripheralControlFragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualitorypie.qualitorypie.Activities.PeripheralControlActivity;
import com.qualitorypie.qualitorypie.R;

public class PeripheralControlForm extends Fragment {

    private static final String PEPHERAL_ID = "peripheral_id";
    private int peripheral_id = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PeripheralControlActivity)getActivity()).setCurentFragment("PeripheralControlForm");
        ((PeripheralControlActivity)getActivity()).pepheralControlListToolBar();
        if (getArguments() != null) {
            peripheral_id = getArguments().getInt(PEPHERAL_ID);;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.to_peripheral_control_action).setVisibility(View.GONE);
        ((PeripheralControlActivity)getActivity()).pepheralControlFormToolBar();

        return inflater.inflate(R.layout.peripheral_control_form, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }
}
