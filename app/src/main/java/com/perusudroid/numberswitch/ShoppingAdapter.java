package com.perusudroid.numberswitch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.perusudroid.numberswitch.model.Data;
import com.perusudroid.numberswitcher.NumberSwitch;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private List<Data> productList;

    public void refresh(List<Data> data) {
        this.productList = data;
        notifyDataSetChanged();
    }

    public ShoppingAdapter(List<Data> data) {
        this.productList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inflater_product_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(productList.get(i));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivPic, ivLayer;
        private TextView tvCost, tvName,tvModCost;
        private NumberSwitch numberSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
            setAssets();
        }

        private void bindViews(View itemView) {
            ivPic = itemView.findViewById(R.id.ivPic);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvModCost = itemView.findViewById(R.id.tvModCost);
            tvName = itemView.findViewById(R.id.tvName);
            ivLayer = itemView.findViewById(R.id.ivLayer);
            numberSwitch = itemView.findViewById(R.id.numberSwitch);
        }

        private void setAssets() {
            ivPic.setOnClickListener(this);

            numberSwitch.setValueChangedListener(
                    new NumberSwitch.ValueChangedListener() {
                        @Override
                        public void onValueChanged(int value, NumberSwitch.ValueType valueType) {
                            Data mData = (Data) itemView.getTag();
                            mData.set_selected(true);
                            mData.setProduct_mrp_price(mData.getProduct_mrp_price());
                            mData.setProduct_selected_qty(value);
                            mData.setProduct_selected_mrp_price(mData.getProduct_mrp_price() * value);

                            tvModCost.setText(String.valueOf(mData.getProduct_mrp_price() * value));
                        }
                    }
            );

        }

        public void bindData(Data data) {

            itemView.setTag(data);
            ivLayer.setVisibility(data.isSelected() ? View.VISIBLE : View.GONE);
            if (data.isSelected()) {
                numberSwitch.setCount(data.getProduct_selected_qty());
            }
            tvName.setText(String.valueOf(data.getName()));
            tvCost.setText(String.valueOf(data.getProduct_mrp_price()));


            Glide.
                    with(itemView.getContext()).
                    load(data.getImage()).
                    into(ivPic);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.ivPic:
                    break;

            }
        }
    }
}
