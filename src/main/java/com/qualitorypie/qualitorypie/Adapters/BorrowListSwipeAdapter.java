package com.qualitorypie.qualitorypie.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Activities.PersonActivity;
import com.qualitorypie.qualitorypie.DataProviders.LocalDb;
import com.qualitorypie.qualitorypie.Fragments.BorrowFormFragment;
import com.qualitorypie.qualitorypie.Models.BorrowModel;
import com.qualitorypie.qualitorypie.R;

public class BorrowListSwipeAdapter extends PagerAdapter {
    private BorrowModel borrowModel_tmp;
    private View view;
    private int[] row_views = new int[]{R.layout.borrow_swip_main_content_fragment, R.layout.borrow_swip_action_content_fragment};

    public BorrowListSwipeAdapter(Context context, BorrowModel borrowModel) {
        borrowModel_tmp = borrowModel;

    }

    @Override
    public int getCount() {
        return row_views.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        view = layoutInflater.inflate(row_views[position], container, false);
        if (position == 0) {
            TextView txt_adapter_item_name_obj = view.findViewById(R.id.txt_adapter_borrow_item_name);
            txt_adapter_item_name_obj.setText(borrowModel_tmp.getProduct_name());
            TextView txt_adapter_amt_obj = view.findViewById(R.id.txt_adapter_borrow_amt);
            txt_adapter_amt_obj.setText(String.valueOf(borrowModel_tmp.getBorrow_amt()));
        } else {
            ImageButton edit_btn = view.findViewById(R.id.edit_borrow_item);
            edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment_container = new BorrowFormFragment();
                    FragmentTransaction ft = ((Activity) container.getContext()).getFragmentManager().beginTransaction();
                    ft.replace(R.id.borrow_list_fragment, fragment_container);
                    ft.commit();
                }
            });

            ImageButton delete_btn = view.findViewById(R.id.delete_borrow_item);
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(container.getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                    container.setVisibility(View.GONE);

                    if (borrowModel_tmp.getId() > 0 && borrowModel_tmp.getUser_id() > 0) {
                        LocalDb borrowDb = new LocalDb(container.getContext(), borrowModel_tmp);
                        borrowDb.deleteData(borrowModel_tmp.getId());

                        PersonActivity personActivity = (PersonActivity) container.getContext();
                        ((PersonActivity) container.getContext()).manageRemainingAmt(borrowModel_tmp.getUser_id());

                    } else {
                        Toast.makeText(container.getContext(), "Error Occured while deleting Borrow", Toast.LENGTH_SHORT).show();
                    }


                }

            });
        }
        container.addView(view, 0);
        return view;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public float getPageWidth(int position) {
        if (position == 1) {

//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            ((Activity) current_activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            int width = displayMetrics.widthPixels;
//            Log.d("here",String.valueOf(width));

            float content_width = 2;//in dp
            return 1 / content_width;
        }
        return 1;

    }

}
