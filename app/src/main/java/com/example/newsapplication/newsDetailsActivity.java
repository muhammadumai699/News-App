package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class newsDetailsActivity extends AppCompatActivity {

    String title, description, content, imageUrl, url;

    private TextView newsTitle, sub_description, newsContent;
    private Button btn_readFullNews;
    public ImageView newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        imageUrl = getIntent().getStringExtra("urlToImage");
        url = getIntent().getStringExtra("url");


        newsTitle = findViewById(R.id.newsTitle);
        sub_description = findViewById(R.id.sub_description);
        newsContent = findViewById(R.id.news_content);
        newsImage = findViewById(R.id.newsImage);
        btn_readFullNews = findViewById(R.id.btn);


        newsTitle.setText(title);
        sub_description.setText(description);
        newsContent.setText(content);
        Picasso.get().load(imageUrl).into(newsImage);

        btn_readFullNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }
}