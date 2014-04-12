package org.lolt.historiclandscapes.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AdminInputActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView iv;
    Location l;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b85f0c")));

        GPSTracker gps = new GPSTracker(this);
        l = gps.getLocation();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            iv = (ImageView) findViewById(R.id.imageView);
            iv.setBackground(null);
            iv.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public File saveMapAsFile(Bitmap bm) throws IOException {
        Log.d("saveMapAsFile", "Saving map to file...");

        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        File file = new File(path, "landscape_image.jpg");

        file.createNewFile();

        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("saveMapAsFile", "Compressing to JPEG...");

        bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();

        Log.d("saveMapAsFile", "File saved as " + file.getAbsolutePath() + ".");

        return file;

    }

    public void takePhoto(View view) {
        dispatchTakePictureIntent();
    }

    public void submit(View view) throws IOException {
        view = (RelativeLayout) view.getParent();

        //Send stuff to db here
        String lat = l.getLatitude() + "";
        String lon = l.getLongitude() + "";
        RequestParams params = new RequestParams();

        params.put("image", saveMapAsFile(imageBitmap));
        params.add("latitude", lat);
        params.add("longitude", lon);

        String name = ((EditText) view.findViewById(R.id.name)).getText() + "";
        params.add("name", name);

        String description = ((EditText) view.findViewById(R.id.description)).getText() + "";
        params.add("description", description);

        Client.post("landscapes", params);
    }
}
