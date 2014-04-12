package org.lolt.historiclandscapes.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MapActivity extends ActionBarActivity {

    ListView feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        feed = (ListView) findViewById(R.id.listView);
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

    public class MyAdapter extends ArrayAdapter<Location> {
        private int mResource;

        public MyAdapter(Context context, int resource, List<Location> yaks) {
            super(context, resource, yaks);

            mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final String content = getItem(position).getLocation();
            Log.e("Peek", content);
            View view;

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(mResource, parent, false);
            } else {
                view = convertView;
            }

            TextView locs = (TextView) view.findViewById(R.id.location);
            locs.setText(content);

            notifyDataSetChanged();

            return view;

        }
    }

    public class Location
    {
        private String text;
        private String lat;
        private String lon;
        private String image;


        public Location(String t, String lon, String lat, String d)
        {
            this.lat= lat;
            this.lon =lon;
            text = t;
            image = d;
        }

        public String getImage()
        {
            return image;
        }

        public String getLat()
        {
            return lat;
        }

        public String getLon()
        {
            return lon;
        }

        public String getLocation()
        {
            return text;
        }
    }


}
