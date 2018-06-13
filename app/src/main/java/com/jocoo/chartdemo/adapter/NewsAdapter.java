package com.jocoo.chartdemo.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jocoo.chartdemo.R;

public class NewsAdapter extends BaseRecyclerViewAdapter {

    private static final int TYPE_TOP_PANEL = 0;
    private static final int TYPE_NEWS_ITEM = 1;
    private int topPanelHeight = 0;

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_TOP_PANEL : TYPE_NEWS_ITEM;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_TOP_PANEL) {
            return new TopPanelViewHolder(inflater.inflate(R.layout.top_panel, parent, false));
        } else {
            return new NewsItemViewHolder(inflater.inflate(R.layout.news_item, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public void notifyHeightChanged(int height) {
        topPanelHeight = height;
        notifyDataSetChanged();
    }

    class NewsItemViewHolder extends AbstractViewHolder {
        private final TextView index;

        public NewsItemViewHolder(View view) {
            super(view);
            index = view.findViewById(R.id.news_item_index);
        }

        @Override
        public void onBindViewHolder(int position) {
            index.setText(String.valueOf(position));
        }
    }

    class TopPanelViewHolder extends AbstractViewHolder {
        public TopPanelViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(int position) {
//            ViewGroup.LayoutParams params = itemView.getLayoutParams();
//            if (params != null) {
//                params.height = topPanelHeight;
//                itemView.setLayoutParams(params);
//            }
        }
    }
}
