/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karemhamed.Model.ModelOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.karemhamed.HomeActivity.strUId;

public class AddOrderActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * order_number
     */
    private EditText edOrderNumberAdd;
    /**
     * order_name
     */
    private EditText edOrderNameAdd;
    /**
     * order_price
     */
    private EditText edOrderPriceAdd;
    /**
     * order_delivery_price
     */
    private EditText edOrderDeliveryPriceAdd;
    /**
     * order_adress
     */
    private EditText edOrderAddressAdd;
    /**
     * add
     */
    private Button btnAddOrder;
    //
    private DatabaseReference databaseReference;
    //
    private String strClintId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        Intent intent = getIntent();
        strClintId = intent.getStringExtra("strClintId");
//        Toast.makeText(this, strClintId, Toast.LENGTH_LONG).show();
        databaseReference = FirebaseDatabase.getInstance().getReference(strUId).child("clint").child(strClintId).child("Order");
        //clint
        initView();
    }

    private void initView() {
        edOrderNumberAdd = findViewById(R.id.edOrderNumberAdd);
        edOrderNameAdd = findViewById(R.id.edOrderNameAdd);
        edOrderPriceAdd = findViewById(R.id.edOrderPriceAdd);
        edOrderDeliveryPriceAdd = findViewById(R.id.edOrderDeliveryPriceAdd);
        edOrderAddressAdd = findViewById(R.id.edOrderAddressAdd);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        btnAddOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str_OrderNumberAdd = edOrderNumberAdd.getText().toString().trim();
        String str_OrderNameAdd = edOrderNameAdd.getText().toString().trim();
        String str_OrderPriceAdd = edOrderPriceAdd.getText().toString().trim();//OrderPriceAdd
        String str_OrderDeliveryPriceAdd = edOrderDeliveryPriceAdd.getText().toString().trim();
        String str_OrderAddressAdd = edOrderAddressAdd.getText().toString().trim();

        if (TextUtils.isEmpty(str_OrderNumberAdd)) {
            edOrderNumberAdd.setError("Enter ur Number Plzz");

        } else if (TextUtils.isEmpty(str_OrderNameAdd)) {
            edOrderNameAdd.setError("Enter ur Name Plzz");

        } else if (TextUtils.isEmpty(str_OrderPriceAdd)) {
            edOrderPriceAdd.setError("Enter ur Price Plzz");

        } else if (TextUtils.isEmpty(str_OrderDeliveryPriceAdd)) {
            edOrderDeliveryPriceAdd.setError("Enter ur DeliveryPrice Plzz");

        } else if (TextUtils.isEmpty(str_OrderAddressAdd)) {
            edOrderAddressAdd.setError("Enter ur Address Plzz");

        } else {
            btnAddOrder.setEnabled(false);

            SimpleDateFormat formatter = new SimpleDateFormat("M/dd hh:mm a", new Locale("EN"));
            String str_Time = formatter.format(new Date());
            String str_id = databaseReference.push().getKey();

            ModelOrder upload = new ModelOrder(false, str_Time, str_OrderNumberAdd,
                    str_OrderNameAdd, str_OrderPriceAdd, str_OrderDeliveryPriceAdd, str_OrderAddressAdd, str_id);

            databaseReference.child(str_id).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        btnAddOrder.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        btnAddOrder.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            });


        }
    }
}
