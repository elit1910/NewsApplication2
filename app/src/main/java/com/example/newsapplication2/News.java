package com.example.newsapplication2;

/**
 * Created by estlander on 19/04/2017.
 */

public class News {
    private String mTitle;

    private String mSectionName;

    private String mTime;

    private String mURL;

    private String mType;

    public News(String title, String sectionName, String time, String url, String type){

        mTitle = title;
        mSectionName = sectionName;
        mTime = time;
        mURL =url;
        mType = type;
    }

    public String getTitle(){return mTitle;}

    public String getSectionName(){return mSectionName;}

    public String getTime(){return mTime;}

    public String getUrl(){return mURL;}

    public String getType(){return mType;}

}
