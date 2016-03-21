package com.example.android.popularmoviestageone;

public class Movie {
    private String mTitle;
    private String mPosterPath;
    private String mSummary;
    private String mReleaseDate;
    private String mBackgroundPath;
    private String mUserRating;

    public String getUserRating() {
        return mUserRating;
    }

    public void setUserRating(String mUserRating) {
        this.mUserRating = mUserRating;
    }

    public String getBackgroundPath() {
        return mBackgroundPath;
    }

    public void setBackgroundPath(String mBackgroundPath) {
        this.mBackgroundPath = mBackgroundPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
