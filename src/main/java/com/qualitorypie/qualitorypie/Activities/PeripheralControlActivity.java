package com.qualitorypie.qualitorypie.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.qualitorypie.qualitorypie.Activities.PeripheralControlFragments.PeripheralControlList;

import com.qualitorypie.qualitorypie.Activities.PeripheralControlFragments.PeripheralControlList;
import com.qualitorypie.qualitorypie.R;

import helpers.ToolBarHelper;

public class PeripheralControlActivity extends AppCompatActivity {

    private String currentFragment;
    public void setCurentFragment(String FragmentName){
        currentFragment = FragmentName;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripheral_control);

        Fragment peripheral_control_list = new PeripheralControlList();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.peripheral_frag_container, peripheral_control_list);
        ft.commit();
    }
    public void pepheralControlListToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_peripharal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//back botton
        getSupportActionBar().setDisplayShowHomeEnabled(true);//display
    }
    public void pepheralControlFormToolBar(){
        findViewById(R.id.searchView_peripheral).setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_peripharal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//back botton
        getSupportActionBar().setDisplayShowHomeEnabled(true);//display
    }
    @Override
    public void onBackPressed() {
        if(currentFragment == "PeripheralControlForm"){
            Fragment peripheral_control_list = new PeripheralControlList();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.peripheral_frag_container, peripheral_control_list);
            ft.commit();
        }else{
            super.onBackPressed();
        }

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
