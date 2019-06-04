/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed.Adabter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.karemhamed.Model.ModelClint;
import com.example.karemhamed.Model.ModelOrder;
import com.example.karemhamed.R;
import com.example.karemhamed.ShowOrderActivity;

import java.util.List;

public class AdabterOrder extends RecyclerView.Adapter<AdabterOrder.ViewHolderOrder> {
    private List<ModelOrder> modelOrderArrayList;
    private Context context;
    private String strClintId;
    private String strShowOrderType;

    public AdabterOrder(List<ModelOrder> modelOrderArrayList, Context context, String strClintId, String strShowOrderType) {
        this.modelOrderArrayList = modelOrderArrayList;
        this.context = context;
        this.strClintId = strClintId;
        this.strShowOrderType = strShowOrderType;
    }

    @NonNull
    @Override
    public ViewHolderOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderOrder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrder viewHolderOrder, int i) {
        final ModelOrder modelOrder = modelOrderArrayList.get(i);
        viewHolderOrder.tv_OrderNumber.setText(modelOrder.getStrOrderNumber());
        viewHolderOrder.tv_OrderName.setText(modelOrder.getStrOrderName());
        viewHolderOrder.tv_OrderDate.setText(modelOrder.getStrOrderDate());
        viewHolderOrder.tv_OrderPrice.setText(modelOrder.getStrOrderPrics());
        viewHolderOrder.layout_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowOrderActivity.class);
                Bundle db = new Bundle();
                db.putSerializable("modelOrder", modelOrder);
                db.putString("strClintId", strClintId);
                db.putString("strShowOrderType",strShowOrderType);
                intent.putExtras(db);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelOrderArrayList.size();
    }



    public void filterList(List<ModelOrder> filteredList) {
        modelOrderArrayList = filteredList;
        notifyDataSetChanged();
    }


    class ViewHolderOrder extends RecyclerView.ViewHolder {
        private TextView tv_OrderNumber, tv_OrderName, tv_OrderDate,
                tv_OrderPrice;
        private LinearLayout layout_Order;


        public ViewHolderOrder(@NonNull View itemView) {
            super(itemView);
            tv_OrderNumber = itemView.findViewById(R.id.tv_OrderNumber);
            tv_OrderName = itemView.findViewById(R.id.tv_OrderName);
            tv_OrderDate = itemView.findViewById(R.id.tv_OrderDate);
            tv_OrderPrice = itemView.findViewById(R.id.tv_OrderPrice);
            layout_Order = itemView.findViewById(R.id.layout_Order);

        }
    }
}
