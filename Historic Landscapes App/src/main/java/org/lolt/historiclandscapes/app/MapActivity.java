package org.lolt.historiclandscapes.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends ActionBarActivity {

    ListView feed;
    List places = new ArrayList<Location2>();
    MyAdapter adapter;
    Bitmap placeImage;
    String urlPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        feed = (ListView) findViewById(R.id.listView);
        adapter = new MyAdapter(this, R.layout.list_element, places);
        getActionBar().setTitle("Historic Landmarks");
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b85f0c")));
        initUI();


        feed.setAdapter(adapter);


        feed
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Location2 data = (Location2) places.get(position);
                        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                        i.putExtra("name", ((Location2) places.get(position)).getName());
                        i.putExtra("pic", ((Location2) places.get(position)).getImage());
                        i.putExtra("disc", ((Location2) places.get(position)).getDiscription());
                        startActivity(i);
                    }
                });

    }

    public void initUI() {

        GPSTracker gps = new GPSTracker(this);
        Location l = gps.getLocation();
        //load info from DB here and add to feed list
        RequestParams params = new RequestParams();
        Log.e("yo", "my content: " + "hello people in UI now");

        Client.get("landscapes", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        places.clear();
                        try {
                            JSONArray messages = response
                                    .getJSONArray("landscapes");
                            for (int i = 0; i < messages.length(); i++) {
                                //places.add(new Location2();
                                final JSONObject o = messages.getJSONObject(i);

                                Log.e("yo", "my content: " + o.getString("description"));

                                new AsyncTask<String, String, Bitmap>() {

                                    /**
                                     * Creating product
                                     * */
                                    @Override
                                    protected Bitmap doInBackground(String... args) {

                                        try {
                                            HttpGet httpRequest = null;
                                            try {
                                                httpRequest = new HttpGet(o.getString("imageurl"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            HttpClient httpclient = new DefaultHttpClient();
                                            HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
                                            HttpEntity entity = response.getEntity();
                                            BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
                                            InputStream instream = bufHttpEntity.getContent();
                                            // if (input != null) Log.d("getImageFromUrl", "InputStream is not null.");
                                            BitmapFactory.Options options = new BitmapFactory.Options();
                                            options.inSampleSize = 4;
                                            Bitmap myBitmap = BitmapFactory.decodeStream(instream, null, options);
                                            Log.e("yooo", "are we null? " + myBitmap.toString());
                                            //  if (input == null) Log.d("getImageFromUrl", "But the bitmap is...");

                                            return myBitmap;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            return null;
                                        }


                                    }


                                    protected void onPostExecute(Bitmap result) {

                                        try {
                                            places.add(new Location2(o.getString("name"), o.getString("description"), o.getString("longitude"), o.getString("latitude"), result, o.getString("name")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        adapter.notifyDataSetChanged();
                                    }



                                }.execute();

                                feed.invalidateViews();

                            }
                        } catch (JSONException ex) {
                            Log.e(this.getClass().getName(),
                                    response.toString()
                                            + " could not be parsed.");
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable e,
                                          JSONObject errorResponse) {
                        //Toast.makeText(context, "Update failed",
                        //Toast.LENGTH_SHORT).show();
                        Log.e("skewp", errorResponse.toString() + " more  " + e.toString());
                    }
                });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends ArrayAdapter<Location2> {
        private int mResource;

        public MyAdapter(Context context, int resource, List<Location2> yaks) {
            super(context, resource, yaks);

            mResource = resource;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final String content = getItem(position).getDiscription();
            Log.e("yo", "my content: " + content);
            Bitmap pic = getItem(position).getImage();
            Log.e("yoo", "am i null?   " + pic.toString());
            View view;

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(mResource, parent, false);
            } else {
                view = convertView;
            }
           String words[] = content.split(" ");
           String info="";
            for (int i=0; i<words.length;i++)
            {
                info+=words[i]+" ";
                if(info.length()>50)
                {
                    info+="...";
                    break;
                }
            }

            TextView placeInfo = (TextView) view.findViewById(R.id.textView);
            placeInfo.setText(info);
            ImageView iv = (ImageView) view.findViewById(R.id.imageView);
            iv.setImageBitmap(pic);
            iv.setBackground(null);

            notifyDataSetChanged();
            feed.invalidateViews();


            return view;

        }
    }

    public class Location2 {
        private String text;
        private String lat;
        private String lon;
        private Bitmap image;
        private String discription;
        private String name;

        public Location2(String t, String disc, String lon, String lat, Bitmap d,String n) {
            this.lat = lat;
            this.lon = lon;
            text = t;
            image = d;
            discription = disc;
            name= n;
        }

        public String getName()
        {
            return name;
        }
        public String getDiscription() {
            return discription;

        }

        public Bitmap getImage() {
            return image;
        }

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }

        public String getLocation() {
            return text;
        }
    }


}
