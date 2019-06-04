/*
 * Created by AbdOo Saed from Egypt
 *  all Copyright(c) reserved  2019.
 */

package com.example.karemhamed.Adabter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karemhamed.ClintOrderActivity;
import com.example.karemhamed.Model.ModelClint;
import com.example.karemhamed.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdabterClint extends RecyclerView.Adapter<AdabterClint.ViewHolderClint> {
    private List<ModelClint> clintArrayList;
    private Context context;

    public AdabterClint(List<ModelClint> clintArrayList, Context context) {
        this.clintArrayList = clintArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderClint onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderClint(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_clint, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClint viewHolderClint, int i) {
        final ModelClint mModelClint = clintArrayList.get(i);
        viewHolderClint.tv_clint_name.setText(mModelClint.getStr_Clint_name());
        viewHolderClint.tv_clint_phone.setText(mModelClint.getStr_Clint_PhoneNumber());
        viewHolderClint.tv_clint_address.setText(mModelClint.getStr_Clint_Address());
        viewHolderClint.progBarimg.setVisibility(View.VISIBLE);
        Picasso.get().load(mModelClint.getStr_Image_url())
                .fit().centerCrop().into(viewHolderClint.profile_image_clint, new Callback() {
            @Override
            public void onSuccess() {
                viewHolderClint.progBarimg.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        viewHolderClint.layout_Clint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClintOrderActivity.class);
                Bundle db = new Bundle();
                db.putSerializable("ModelClint", mModelClint);
                intent.putExtras(db);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clintArrayList.size();
    }

    public void filterList(List<ModelClint> filteredList) {
        clintArrayList = filteredList;
        notifyDataSetChanged();
    }

    class ViewHolderClint extends RecyclerView.ViewHolder {
        private TextView tv_clint_name, tv_clint_phone, tv_clint_address;
        private ImageView profile_image_clint;
        private ConstraintLayout layout_Clint;
        private ProgressBar progBarimg;

        ViewHolderClint(@NonNull View itemView) {
            super(itemView);
            tv_clint_name = itemView.findViewById(R.id.tv_clint_name);
            tv_clint_phone = itemView.findViewById(R.id.tv_clint_phone);
            tv_clint_address = itemView.findViewById(R.id.tv_clint_address);
            profile_image_clint = itemView.findViewById(R.id.profile_image_clint);
            layout_Clint = itemView.findViewById(R.id.layout_Clint);
            progBarimg = itemView.findViewById(R.id.progBarimg);
        }
    }
}
