package com.example.konstantin.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemInfoActivity extends AppCompatActivity {

    ImageView iv;
    Intent intent;
    String Urls,Urlm;
    TextView OwnerName,Title;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        intent = getIntent();
        Urls = intent.getStringExtra("urls");
        Urlm = intent.getStringExtra("urlm");
        iv = (ImageView) findViewById(R.id.itemInfoImageView);
        back=(Button)findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        OwnerName=(TextView)findViewById(R.id.OwnerName);
        OwnerName.setText(intent.getStringExtra("ownername"));
        Title=(TextView)findViewById(R.id.Title);
        Title.setText(intent.getStringExtra("title"));


        Picasso.with(this).load(Urlm).into(iv);

    }


}

