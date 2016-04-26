package com.example.anna.flickr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String[] rlt = intent.getStringArrayExtra(Intent.EXTRA_TEXT);

        GridView gridview = (GridView)findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this, rlt));
    }
}
