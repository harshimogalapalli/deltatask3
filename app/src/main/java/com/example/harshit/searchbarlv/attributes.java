package com.example.harshit.searchbarlv;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class attributes extends AppCompatActivity {
    TextView name,house,pageRank,books;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);
        name=(TextView)findViewById(R.id.name);
        house=(TextView)findViewById(R.id.house);
        pageRank=(TextView)findViewById(R.id.pageRank);
        books=(TextView)findViewById(R.id.books);
        image = (ImageView) findViewById(R.id.image);

        GOTChar gotchar=(GOTChar)(this.getIntent().getSerializableExtra("gotchar"));
        Picasso.with(this).load("https://api.got.show"+gotchar.imageLink).into(image);
//        Picasso.with(this)
//                .load(gotchar.imageLink)
//                .resize(50, 50)
//                .centerCrop()
//                .into(image);
//     //   Picasso.with(this).load(gotchar.imageLink).into(image);
        name.setText(gotchar.name);
        house.setText("House---"+gotchar.housename);
        pageRank.setText("pageRank---"+gotchar.pageRank);
        books.setText("Books---"+gotchar.books);

    }
}
