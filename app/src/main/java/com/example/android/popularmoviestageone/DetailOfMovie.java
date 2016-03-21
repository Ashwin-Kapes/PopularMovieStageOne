package com.example.android.popularmoviestageone;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailOfMovie extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_movie);

        String titleText = getIntent().getStringExtra("title");
        String imageUrl = getIntent().getStringExtra("poster");
        String summeryText = getIntent().getStringExtra("summery");
        String releaseDate = getIntent().getStringExtra("releaseDate");
        String userRating = getIntent().getStringExtra("userRating");
        TextView title = (TextView) findViewById(R.id.movie_title);
        TextView summery = (TextView) findViewById(R.id.movie_summery);
        TextView releaseDateTextView = (TextView) findViewById(R.id.releaseDate);
        TextView userRatingText = (TextView) findViewById(R.id.user_rating);
        ImageView moviePoster = (ImageView) findViewById(R.id.movie_imageView
        );
        Picasso.with(this).load(imageUrl).into(moviePoster);
        title.setText(titleText);
        summery.setText(summeryText);
        userRatingText.setText(getString(R.string.rating_text) + userRating + getString(R.string.fill_rating));
        releaseDateTextView.setText(getString(R.string.release_text) + releaseDate);

    }

}
