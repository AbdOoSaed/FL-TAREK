package com.example.karemhamed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.karemhamed.Adabter.AdabterClint;
import com.example.karemhamed.Model.ModelClint;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public static String strUId;
    private FirebaseDatabase fDatabase;
    private DatabaseReference RDatabase;
    private RecyclerView recyclerV;
    private AdabterClint viewAdapter;
    private List<ModelClint> clintArrayList;
    private FirebaseAuth mAuth;
    private ShimmerFrameLayout shimmer_view_container;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            strUId = mAuth.getCurrentUser().getUid();
        } else {
            Toast.makeText(this, "There's no user ID", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginAct.class));
        }
        recyclerV = findViewById(R.id.recyclerV);
        shimmer_view_container = findViewById(R.id.shimmer_view_container);
        // Read from the database
        fDatabase = FirebaseDatabase.getInstance();
        RDatabase = fDatabase.getReference().child(strUId).child("clint");
        RDatabase.keepSynced(true);
        getDataAndPutInList();
        clintArrayList = new ArrayList<>();
        viewAdapter = new AdabterClint(clintArrayList, HomeActivity.this);
        recyclerV.setLayoutManager(new LinearLayoutManager(this));
        recyclerV.setAdapter(viewAdapter);
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddClintActivity.class));
            }
        });
        //hide fab when scrol
        recyclerV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.isEmpty()) {
                    filter("");
                } else {
                    filter(newText.trim());
                }
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        List<ModelClint> filteredList = new ArrayList<>();

        for (ModelClint item : clintArrayList) {
            if (item.getStr_Clint_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        viewAdapter.filterList(filteredList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginAct.class));
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDataAndPutInList() {

        RDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clintArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ModelClint modelClint = snapshot.getValue(ModelClint.class);

                    clintArrayList.add(modelClint);
                }

                Collections.reverse(clintArrayList);
                viewAdapter.notifyDataSetChanged();
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(HomeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)

                .setMessage(getString(R.string.exit_messege))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        HomeActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}
