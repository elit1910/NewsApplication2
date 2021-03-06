package com.example.newsapplication2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by estlander on 19/04/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {


    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }



    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }


        List<News> Nyheter = QueryUtils.fetchNewsData(mUrl);
        return Nyheter;
    }


}
