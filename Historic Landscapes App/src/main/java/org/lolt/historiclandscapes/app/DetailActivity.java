package org.lolt.historiclandscapes.app;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String landScapeName = getIntent().getExtras().getString("name");
        getActionBar().setTitle(landScapeName);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b85f0c")));

        Bitmap pic = getIntent().getParcelableExtra("pic");
        String disc =  getIntent().getStringExtra("disc");
        initUI(pic, landScapeName, disc);

    }

    public void initUI(Bitmap b, String n, String c)
    {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(b);
       TextView content=  (TextView)findViewById(R.id.content);
        content.setText(c);
        TextView name = (TextView) findViewById(R.id.textView);
        name.setText(n);
    }





}
