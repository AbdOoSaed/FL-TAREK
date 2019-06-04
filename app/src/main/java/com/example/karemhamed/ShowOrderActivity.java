/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karemhamed.Model.ModelOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowOrderActivity extends AppCompatActivity implements View.OnClickListener {
    //
    FirebaseDatabase firebaseDatabasem;
    //
    DatabaseReference tDatabaseReference;
    //StringData
    private String strOrderNumber, strOrderName, strOrderDate, strOrderPrics,
            strOrderDeliveryPrice, strOrderAddress, strOrderId;
    //ModelOrder
    private ModelOrder modelOrderEdit;
    /**
     * order_number
     */
    private TextView tvOrderNumberEditClick;
    private TextView tvOrderNumberEdit;
    private EditText edOrderNumberEdit;
    /**
     * order_name
     */
    private TextView tvOrderNameEditClick;
    private TextView tvOrderNameEdit;
    private EditText edOrderNameEdit;
    /**
     * order_date
     */
    private TextView tvOrderDateEditClick;
    private TextView tvOrderDateEdit;
    private EditText edOrderDateEdit;
    /**
     * order_price
     */
    private TextView tvOrderPriceEditClick;
    private TextView tvOrderPriceEdit;
    private EditText edOrderPriceEdit;
    /**
     * order_delivery_price
     */
    private TextView tvOrderDeliveryPriceEditClick;
    private TextView tvOrderDeliveryPriceEdit;
    private EditText edOrderDeliveryPriceEdit;
    private TextView tvOrderStatusEdit;
    /**
     * order_adress
     */
    private TextView tvOrderAddressEditClick;
    private TextView tvOrderAddressEdit;
    private EditText edOrderAddressEdit;
    /**
     * delete
     */
    private Button btnOrderDelete;
    /**
     * save
     */
    private Button btnOrderSave;
    /**
     * set_order_ok
     */
    private Button btnOrderOk;
    //
    private String strClintId;
    //
    private String strShowOrderType;
    //
    private AlertDialog.Builder b;
    //
    private String strNewOrderNumber, strNewOrderName, strNewOrderData,
            strNewOrderPrice, strNewOrderDeliveryPrice, strNewOrderAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        initView();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        modelOrderEdit = (ModelOrder) bundle.getSerializable("modelOrder");
        strClintId = bundle.getString("strClintId");
        strShowOrderType = bundle.getString("strShowOrderType");
        firebaseDatabasem = FirebaseDatabase.getInstance();
        tDatabaseReference = firebaseDatabasem.getReference();
        getData();
        setData();

    }

    private void initView() {
        tvOrderNumberEditClick = findViewById(R.id.tv_Order_Number_Edit_Click);
        tvOrderNumberEdit = findViewById(R.id.tv_Order_Number_Edit);
        edOrderNumberEdit = findViewById(R.id.ed_Order_Number_Edit);
        tvOrderNameEditClick = findViewById(R.id.tv_Order_Name_Edit_Click);
        tvOrderNameEdit = findViewById(R.id.tv_Order_Name_Edit);
        edOrderNameEdit = findViewById(R.id.ed_Order_Name_Edit);
        tvOrderDateEditClick = findViewById(R.id.tv_Order_Date_Edit_Click);
        tvOrderDateEdit = findViewById(R.id.tv_Order_Date_Edit);
        edOrderDateEdit = findViewById(R.id.ed_Order_Date_Edit);
        tvOrderPriceEditClick = findViewById(R.id.tv_Order_Price_Edit_Click);
        tvOrderPriceEdit = findViewById(R.id.tv_Order_Price_Edit);
        edOrderPriceEdit = findViewById(R.id.ed_Order_Price_Edit);
        tvOrderDeliveryPriceEditClick = findViewById(R.id.tv_Order_Delivery_Price_Edit_Click);
        tvOrderDeliveryPriceEdit = findViewById(R.id.tv_Order_Delivery_Price_Edit);
        edOrderDeliveryPriceEdit = findViewById(R.id.ed_Order_Delivery_Price_Edit);
        tvOrderStatusEdit = findViewById(R.id.tv_Order_Status_Edit);
        tvOrderAddressEditClick = findViewById(R.id.tv_Order_Address_Edit_Click);
        tvOrderAddressEdit = findViewById(R.id.tv_Order_Address_Edit);
        edOrderAddressEdit = findViewById(R.id.ed_Order_Address_Edit);
        btnOrderDelete = findViewById(R.id.btn_Order_Delete);
        btnOrderDelete.setOnClickListener(this);
        btnOrderSave = findViewById(R.id.btn_Order_Save);
        btnOrderSave.setOnClickListener(this);
        btnOrderOk = findViewById(R.id.btn_Order_Ok);
        btnOrderOk.setOnClickListener(this);
    }

    public void orderClick(View view) {
        if (strShowOrderType.equals("AllOrder")) {
            int orderId = view.getId();
            switch (orderId) {
                case R.id.tv_Order_Number_Edit_Click:
                    edOrderNumberEdit.setVisibility(View.VISIBLE);
                    tvOrderNumberEdit.setVisibility(View.GONE);
                    btnOrderSave.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_Order_Name_Edit_Click:
                    edOrderNameEdit.setVisibility(View.VISIBLE);
                    btnOrderSave.setVisibility(View.VISIBLE);
                    tvOrderNameEdit.setVisibility(View.GONE);
                    break;
                case R.id.tv_Order_Date_Edit_Click:
                    edOrderDateEdit.setVisibility(View.VISIBLE);
                    tvOrderDateEdit.setVisibility(View.GONE);
                    btnOrderSave.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_Order_Price_Edit_Click:
                    edOrderPriceEdit.setVisibility(View.VISIBLE);
                    tvOrderPriceEdit.setVisibility(View.GONE);
                    btnOrderSave.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_Order_Delivery_Price_Edit_Click:
                    edOrderDeliveryPriceEdit.setVisibility(View.VISIBLE);
                    tvOrderDeliveryPriceEdit.setVisibility(View.GONE);
                    btnOrderSave.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_Order_Address_Edit_Click:
                    edOrderAddressEdit.setVisibility(View.VISIBLE);
                    tvOrderAddressEdit.setVisibility(View.GONE);
                    btnOrderSave.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_Order_Status_Edit_Click:
                    btnOrderOk.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }

    private void getData() {
        strOrderId = modelOrderEdit.getStrOrderId();
        strOrderNumber = modelOrderEdit.getStrOrderNumber();
        strOrderName = modelOrderEdit.getStrOrderName();
        strOrderDate = modelOrderEdit.getStrOrderDate();
        strOrderPrics = modelOrderEdit.getStrOrderPrics();
        strOrderDeliveryPrice = modelOrderEdit.getStrOrderDeliveryPrice();
        strOrderAddress = modelOrderEdit.getStrOrderAddress();
    }

    private void setData() {

        tvOrderNumberEdit.setText(strOrderNumber);
        edOrderNumberEdit.setText(strOrderNumber);

        tvOrderNameEdit.setText(strOrderName);
        edOrderNameEdit.setText(strOrderName);

        tvOrderDateEdit.setText(strOrderDate);
        edOrderDateEdit.setText(strOrderDate);

        tvOrderPriceEdit.setText(strOrderPrics);
        edOrderPriceEdit.setText(strOrderPrics);

        tvOrderDeliveryPriceEdit.setText(strOrderDeliveryPrice);
        edOrderDeliveryPriceEdit.setText(strOrderDeliveryPrice);

        tvOrderAddressEdit.setText(strOrderAddress);
        edOrderAddressEdit.setText(strOrderAddress);

        tvOrderStatusEdit.setText(modelOrderEdit.getOrderStatus() + "");

    }

    private void edToStrAndEditModel() {
        strNewOrderNumber = edOrderNumberEdit.getText().toString();
        strNewOrderName = edOrderNameEdit.getText().toString();
        strNewOrderData = edOrderDateEdit.getText().toString();
        strNewOrderPrice = edOrderPriceEdit.getText().toString();
        strNewOrderDeliveryPrice = edOrderDeliveryPriceEdit.getText().toString();
        strNewOrderAddress = edOrderAddressEdit.getText().toString();
        modelOrderEdit.setStrOrderNumber(strNewOrderNumber);
        modelOrderEdit.setStrOrderName(strNewOrderName);
        modelOrderEdit.setStrOrderDate(strNewOrderData);
        modelOrderEdit.setStrOrderPrics(strNewOrderPrice);
        modelOrderEdit.setStrOrderDeliveryPrice(strOrderDeliveryPrice);
        modelOrderEdit.setStrOrderAddress(strNewOrderAddress);
    }

    @Override
    public void onClick(View v) {
        b = new AlertDialog.Builder(this);
        switch (v.getId()) {
            case R.id.btn_Order_Delete:
                b.setTitle(getString(R.string.delete_order));
                b.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                b.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Query queryDelte = tDatabaseReference.child("clint").child(strClintId).child("Order")
                                .orderByChild("strOrderId").equalTo(modelOrderEdit.getStrOrderId());
                        queryDelte.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(ShowOrderActivity.this, "" + databaseError, Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

                break;
            case R.id.btn_Order_Save:
                b.setTitle(getString(R.string.delete_order));
                b.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                b.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edToStrAndEditModel();
                        Query querySaveEdit = tDatabaseReference.child("clint").child(strClintId).child("Order")
                                .orderByChild("strOrderId").equalTo(modelOrderEdit.getStrOrderId());
                        querySaveEdit.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().setValue(modelOrderEdit);
                                    Toast.makeText(ShowOrderActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(ShowOrderActivity.this, "" + databaseError, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                break;
            case R.id.btn_Order_Ok:
                b.setTitle(getString(R.string.set_order_ok_Alart));
                b.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                b.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Query querySetOk = tDatabaseReference.child("clint").child(strClintId).child("Order")
                                .orderByChild("strOrderId").equalTo(modelOrderEdit.getStrOrderId());
                        querySetOk.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    modelOrderEdit.setOrderStatus(true);
                                    tvOrderStatusEdit.setText("true");
                                    snapshot.getRef().setValue(modelOrderEdit);
                                    Toast.makeText(ShowOrderActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(ShowOrderActivity.this, "" + databaseError, Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });


                break;
        }
        b.show();

    }
}
