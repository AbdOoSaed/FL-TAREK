/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karemhamed.Adabter.AdabterOrder;
import com.example.karemhamed.Model.ModelOrder;
import com.example.karemhamed.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.karemhamed.HomeActivity.strUId;

/**
 * A simple {@link Fragment} subclass.
 */
public class OkOrderFragment extends Fragment {
    private RecyclerView recyclerV_okOrder;
    private String strClintId;
    private FloatingActionButton fab_add_order;
    private DatabaseReference RDatabase;
    private FirebaseDatabase fDatabase;
    private List<ModelOrder> orderArrayList;
    private AdabterOrder viewAdapter;

    public OkOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        strClintId = getArguments().getString("strClintId");
        fDatabase = FirebaseDatabase.getInstance();
        RDatabase = fDatabase.getReference(strUId).child("clint").child(strClintId).child("Order");
        RDatabase.keepSynced(true);
        View view = inflater.inflate(R.layout.fragment_ok_order, container, false);
        getDataAndPutInList();
        recyclerV_okOrder = view.findViewById(R.id.recyclerV_okOrder);
//        fab_add_order = view.findViewById(R.id.fab_add_order);
//        getDataAndPutInList();
        orderArrayList = new ArrayList<>();
        viewAdapter = new AdabterOrder(orderArrayList, getActivity(), strClintId, "okOrder");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerV_okOrder.setLayoutManager(layoutManager);
        recyclerV_okOrder.setAdapter(viewAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void getDataAndPutInList() {
        RDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelOrder modelOrder = snapshot.getValue(ModelOrder.class);
                    if (modelOrder.getOrderStatus()) {
                        orderArrayList.add(modelOrder);
                    }

                }
                Collections.reverse(orderArrayList);
                viewAdapter.notifyDataSetChanged();
                setHasOptionsMenu(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_fragment);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                CharSequence query = searchView.getQuery();
//                filterOrder(s);
                if (s == null || s.isEmpty()) {
                    filterOrder("");
                } else {
                    filterOrder(s.trim());
                }
                return false;
            }
        });
        searchView.setQueryHint("Search");

//        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }


    private void filterOrder(String text) {
        List<ModelOrder> filteredList = new ArrayList<>();
        for (ModelOrder item : orderArrayList) {
            if (item.getStrOrderName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        viewAdapter.filterList(filteredList);
    }
}
