package org.lolt.historiclandscapes.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b85f0c")));


    }

    public void login (View view)
    {
        EditText pass = (EditText) findViewById(R.id.editText);
        final String realPass = "test";
        String input = pass.getText()+"".trim();

        if(input.equals(realPass))
        {
            startActivity(new Intent(this, AdminInputActivity.class));
        }
    }




}
