package com.example.newsapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by estlander on 19/04/2017.
 */

public class NewsAdapter extends ArrayAdapter<News>{
    public NewsAdapter( Context context, List<News> nyheter) {
        super(context, 0, nyheter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_items, parent, false);
        }

        News currentNyhet = getItem(position);


        TextView titleView = (TextView)listItemView.findViewById(R.id.news_title);
        String title = currentNyhet.getTitle();
        titleView.setText(title);


        TextView sectionView = (TextView)listItemView.findViewById(R.id.news_section);
        String sectionName = currentNyhet.getSectionName();
        sectionView.setText(sectionName);

        TextView typeView = (TextView)listItemView.findViewById(R.id.news_type);
        String type = currentNyhet.getType();
        typeView.setText(type);

        TextView dateView = (TextView)listItemView.findViewById(R.id.news_date);
        String date = currentNyhet.getTime();
        dateView.setText(date);


    return listItemView;}






}
