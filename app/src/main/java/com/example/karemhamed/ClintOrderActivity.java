/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.karemhamed.Fragment.AllOrderFragment;
import com.example.karemhamed.Fragment.OkOrderFragment;
import com.example.karemhamed.Model.ModelClint;
import com.google.firebase.database.DatabaseReference;

public class ClintOrderActivity extends AppCompatActivity {
    //
    private String strClintId;
    private FrameLayout frame;
    private Fragment fragment;
    //
    private ModelClint clintIntent;
    //DatabaseReference
    private DatabaseReference databaseReference;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_all_order) {

                Bundle bundle = new Bundle();
                bundle.putString("strClintId", strClintId);
                AllOrderFragment f = new AllOrderFragment();
                f.setArguments(bundle);
                fragment = f;

            } else if (item.getItemId() == R.id.navigation_ok_order) {

                Bundle bundle = new Bundle();
                bundle.putString("strClintId", strClintId);
                OkOrderFragment ff = new OkOrderFragment();
                ff.setArguments(bundle);
                fragment = ff;
//                fragment = new OkOrderFragment();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
            return true;
        }
    };
//    private Toolbar toolbarOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clint_order);

//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();


        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        clintIntent = (ModelClint) bundle.getSerializable("ModelClint");
        strClintId = clintIntent.getStr_id();
//        databaseReference = FirebaseDatabase.getInstance().getReference("clint").child(strClintId);
//        Toast.makeText(this, strClintId, Toast.LENGTH_SHORT).show();

        frame = findViewById(R.id.frame);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_all_order);
    }

//    private void initView() {
//        toolbarOrder = (Toolbar) findViewById(R.id.toolbarOrder);
//    }
}
