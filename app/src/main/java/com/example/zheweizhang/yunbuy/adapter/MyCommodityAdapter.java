package com.example.zheweizhang.yunbuy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zheweizhang.yunbuy.R;
import com.example.zheweizhang.yunbuy.bean.Commodity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 所有物品的Adapter類別
 * @author autumn_leaf
 */
public class MyCommodityAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private List<Commodity> commodities = new ArrayList<>();

    HashMap<Integer,View> location = new HashMap<>();

    public MyCommodityAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Commodity> commodities) {
        this.commodities = commodities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commodities.size();
    }

    @Override
    public Object getItem(int position) {
        return commodities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(location.get(position) == null){
            convertView = layoutInflater.inflate(R.layout.layout_my_commodity,null);
            Commodity commodity = (Commodity) getItem(position);
            holder = new ViewHolder(convertView,commodity);
            location.put(position,convertView);
            convertView.setTag(holder);
        }else{
            convertView = location.get(position);
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    //定義靜態類別，包含每一個item的所有元素
    static class ViewHolder {
        ImageView ivCommodity;
        TextView tvTitle,tvDescription,tvPrice;

        public ViewHolder(View itemView, Commodity commodity) {
            tvTitle = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivCommodity = itemView.findViewById(R.id.iv_commodity);
            tvTitle.setText(commodity.getTitle());
            tvDescription.setText(commodity.getDescription());
            tvPrice.setText(String.valueOf(commodity.getPrice())+"元");

            byte[] picture = commodity.getPicture();
            Bitmap img = BitmapFactory.decodeByteArray(picture,0,picture.length);
            ivCommodity.setImageBitmap(img);
        }
    }
}
