package org.lolt.historiclandscapes.app;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    Typeface futuraMedium;
    Typeface gearedSlab;
    Typeface myriadPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        futuraMedium = Typeface.createFromAsset(getAssets(), "fonts/Futura Medium.ttf");
        gearedSlab = Typeface.createFromAsset(getAssets(), "fonts/GearedSlab.ttf");
        myriadPro = Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Semibold.ttf");

        ((Button) findViewById(R.id.button)).setTypeface(myriadPro);
        ((Button) findViewById(R.id.button2)).setTypeface(myriadPro);

        ((TextView) findViewById(R.id.name)).setTypeface(myriadPro);
        ((TextView) findViewById(R.id.description)).setTypeface(gearedSlab);
        ((TextView) findViewById(R.id.distance)).setTypeface(futuraMedium);

        String landScapeName = getIntent().getExtras().getString("name");
        getActionBar().setTitle(landScapeName.toUpperCase());
        getActionBar().setIcon(R.drawable.actionbar_logo);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b85f0c")));

        Bitmap pic = getIntent().getParcelableExtra("pic");
        String disc = getIntent().getStringExtra("disc");
        initUI(pic, landScapeName, disc);
    }

    public void initUI(Bitmap b, String n, String c) {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(b);
        TextView content = (TextView) findViewById(R.id.description);
        content.setText(c);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(n);
    }


}
