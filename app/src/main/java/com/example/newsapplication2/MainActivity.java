package com.example.newsapplication2;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<News>> {


    private static final String LOG_TAG = MainActivity.class.getName();
    private NewsAdapter mNewsAdapter;
    private TextView tomTextView;
    private String mSearchQuery;
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/search";
    private static final int News_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);

        tomTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(tomTextView);

        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());

        listView.setAdapter(mNewsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News currentNyhet = mNewsAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNyhet.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(News_LOADER_ID, null, this);

        } else {

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            tomTextView.setVisibility(View.VISIBLE);
            tomTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", "62681fba-c281-4c7d-b207-629005bd0b19");
        if (mSearchQuery != null)
            uriBuilder.appendQueryParameter("q", mSearchQuery);

        Log.d(LOG_TAG, uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> nyheter) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        tomTextView.setText(R.string.no_news);

        mNewsAdapter.clear();
        if (nyheter != null && !nyheter.isEmpty()) {

            mNewsAdapter.addAll(nyheter);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsAdapter.clear();
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchQuery = query;
        getLoaderManager().restartLoader(News_LOADER_ID, null, this);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        searchView.setOnQueryTextListener(this);
        return true;
    }

}

