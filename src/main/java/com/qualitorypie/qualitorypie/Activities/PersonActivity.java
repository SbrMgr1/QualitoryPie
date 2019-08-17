package com.qualitorypie.qualitorypie.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Fragments.BorrowListFragment;
import com.qualitorypie.qualitorypie.Fragments.BorrowPersonListFragment;
import com.qualitorypie.qualitorypie.Models.BorrowModel;
import com.qualitorypie.qualitorypie.Models.PersonModel;
import com.qualitorypie.qualitorypie.R;

import java.util.HashMap;
import java.util.Map;

public class PersonActivity extends BaseActivity {

    private LocalDb localDb;
    private String currentFragment;
    private PersonModel personModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        personModel = new PersonModel();
        //loading default fragments
        Fragment fragment_container = new BorrowPersonListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.borrow_list_fragment, fragment_container);
        ft.commit();

        setSupportActionBar(toolbar);

        //adding back button to toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    /**
     * Handling the action on click navication up button of toolbar
     *
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("here", currentFragment);
        if (currentFragment == "PersonFormFragment" || currentFragment == "BorrowListFragment") {
            Fragment fragment_container = new BorrowPersonListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.borrow_list_fragment, fragment_container);
            ft.commit();
        } else if (currentFragment == "BorrowFormFragment") {
            Fragment fragment_container = new BorrowListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.borrow_list_fragment, fragment_container);
            ft.commit();
        } else {
            // Otherwise defer to system default behavior.
            super.onBackPressed();
        }
    }

    public void setCurrentFragment(String currentFragment) {
        this.currentFragment = currentFragment;
    }

    public PersonModel getPersonModel() {
        return personModel;
    }

    public void setPersonModel(PersonModel model) {
        personModel = model;
    }

    public Float manageRemainingAmt(Integer user_id) {

        BorrowModel borrowModel = new BorrowModel();
        String stmt = "SELECT B.borrow_amt ";
        stmt += " FROM " + borrowModel.getTableName() + " B ";
        stmt += " WHERE B.user_id = " + user_id + " AND B.deleted = 0";
        Cursor borrow_datas = new LocalDb(this, borrowModel).rowQuery(stmt);

        Float remaining_amt = Float.valueOf(0);
        if (borrow_datas.moveToFirst()) {
            do {
                remaining_amt += Float.valueOf(borrow_datas.getString(borrow_datas.getColumnIndex("borrow_amt")));
            } while (borrow_datas.moveToNext());
        }
        Log.d("here remaining", String.valueOf(remaining_amt));

        Map<String, String> items_tmp = new HashMap<String, String>();
        items_tmp.put("remaining_amt", remaining_amt.toString());
        items_tmp.put("is_sinked", "0");
        new LocalDb(this, new PersonModel()).updateData(items_tmp, user_id);
        return remaining_amt;
    }

}
