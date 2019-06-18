package com.example.price_comparison.customlist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.price_comparison.R;

import java.util.ArrayList;

public class ProductList extends ArrayAdapter<SingleProduct> {

    private ArrayList<SingleProduct> dataSet;
    Context mContext;

    /*public void changeCursor(Cursor lista) {
        super
    }*/


    private static class ViewHolder {
        TextView storeName;
        TextView storePrice;
        ImageView info;
    }

    public ProductList(ArrayList<SingleProduct> data, Context context){
        super(context, R.layout.store_list, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SingleProduct dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ProductList.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ProductList.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.store_list, parent, false);
            viewHolder.storeName = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.storePrice = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.imagelist);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProductList.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.storeName.setText(dataModel.getName());
        viewHolder.storePrice.setText(dataModel.getPrice().toString());
        return convertView;
    }
}
