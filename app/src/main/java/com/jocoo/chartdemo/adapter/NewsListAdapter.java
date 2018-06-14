package com.jocoo.chartdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jocoo.chartdemo.R;

public class NewsListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        ImageView imageView;
        PanelViewHolder panelViewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.news_item, parent, false);
            textView = convertView.findViewById(R.id.news_item_index);
            imageView = convertView.findViewById(R.id.news_item_image);
            panelViewHolder = new PanelViewHolder();
            panelViewHolder.textView = textView;
            panelViewHolder.imageView = imageView;
            convertView.setTag(panelViewHolder);
        } else {
            panelViewHolder = (PanelViewHolder) convertView.getTag();
            textView = panelViewHolder.textView;
            imageView = panelViewHolder.imageView;
        }
        textView.setText(String.valueOf(position));
        return convertView;
    }

    class PanelViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
