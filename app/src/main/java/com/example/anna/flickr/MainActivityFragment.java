package com.example.anna.flickr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    final String TAG = MainActivity.class.getSimpleName();
    public static String[] rlt;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    Button button;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button = (Button)getView().findViewById(R.id.button);
        button.setOnClickListener(button_listener);



    }

    private View.OnClickListener button_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText editText = (EditText)getView().findViewById(R.id.editText);
            String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&text=";
            url += editText.getText().toString();


//            GridView gridview = (GridView)getView().findViewById(R.id.gridView);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
//            Log.i("URL", rlt[0]);



        }

    };

    private class DownloadTask extends AsyncTask<String, Void, String[]> {

//        private Context context;
//        private View rootView;

//        public DownloadTask(Context context, View rootView) {
//            this.context = context;
//            this.rootView = rootView;
//        }

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

//            android.util.Log.i("Test", "123");
            // Will contain the raw JSON response as a string.
            String jsonStr = null;
//            Log.i("JSON", params[0]);
            try {
                final String BASE_URL = params[0];

                URL url = new URL(BASE_URL);
//                Log.i("Hello",url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    jsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    jsonStr = null;
                }
                jsonStr = buffer.toString();
            } catch (IOException e) {
                android.util.Log.e("DOWNLOAD", "Error ", e);
                jsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        android.util.Log.e("DOWNLOAD", "Error closing stream", e);
                    }
                }
            }

//            Log.i("JSON",jsonStr);

            //ArrayList<String> urlList = new ArrayList<String>();
            String[] rlt = null;
            try {
                JSONObject jsonObject = new JSONObject(jsonStr).getJSONObject("photos");

                JSONArray jsonArray = (JSONArray) jsonObject.get("photo");
                rlt = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
//        	System.out.println(jsonArray.get(i));
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    //System.out.println(obj.toString());
                    int farm = obj.getInt("farm");
                    String server = obj.getString("server");
                    String id = obj.getString("id");
                    String secret = obj.getString("secret");
                    //System.out.println("http://farm"+farm+".static.flickr.com/"+server+"/"+id+"_"+secret+".jpg");
//                    urlList.add("http://farm"+farm+".static.flickr.com/"+server+"/"+id+"_"+secret+".jpg");
                    rlt[i] = "http://farm"+farm+".static.flickr.com/"+server+"/"+id+"_"+secret+".jpg";
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

//            Log.i("JSON",urlList.toString());

            return rlt;
        }

        @Override
        protected void onPostExecute(String[] rlt) {
//            rlt = new String[list.size()];
//            for(int i = 0; i < list.size(); i++) {
//                rlt[i] = list.get(i);
//            }

            Intent intent = new Intent(getActivity(), Main2Activity.class);
            intent.putExtra(Intent.EXTRA_TEXT, rlt);
            startActivity(intent);
        }
    }
}
