package net.skillbooster.projectone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdExtendedListener;

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
import java.util.Objects;

import static net.skillbooster.projectone.MainActivity.Extra_url;

public class CategoryVideo extends AppCompatActivity implements RecylerviewAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ArrayList<PerItemVariable> arrayList = new ArrayList<>();
    private Intent myintent;
    private TextView textView;
private SwipeRefreshLayout swipeRefreshLayout;
  private   ProgressDialog progressDialog;
    private com.facebook.ads.InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_video);
        recyclerView = findViewById(R.id.recycelerview_categories);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout = findViewById(R.id.pull);
        textView = findViewById(R.id.internet);
        progressDialog = new ProgressDialog(CategoryVideo.this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setMessage("Loading Please Wait........");
        interstitialAd = new InterstitialAd(this, "420052028739574_422341771843933");
        interstitialAd.setAdListener(new InterstitialAdExtendedListener() {
            @Override
            public void onInterstitialActivityDestroyed() {

            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });


        interstitialAd.loadAd();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(true);
            }
        });






        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }

        arrayList = new ArrayList<PerItemVariable>();
        final jsonclass jsonclass = new jsonclass();

        if (CheckNetwork.isInternetAvailable(CategoryVideo.this)) //returns true if internet available
        {
            textView.setVisibility(View.GONE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jsonclass.execute();

                }
            }, 10);

        }else{

            textView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onItemClick(int position) {
        myintent = new Intent(this,ViewVideo.class);
        PerItemVariable itemRow =  arrayList.get(position);
      //myintent.putExtra(Extra_url,itemRow.getVideourl());
     // startActivity(myintent);

    }

    public class jsonclass extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader;
            String fullfile;
            String category = getIntent().getStringExtra("key");

            String siteurl = null;
            //{ "adob", "dig","web","html","photo","print"};
            if(category.equals("cf")){



                siteurl = "https://www.skillbooster.net/calculation/monetization/cf.php";
            }
            if(category.equals("md")){
                siteurl = "https://www.skillbooster.net/calculation/monetization/md.php";

            }if(category.equals("p")) {

                siteurl = "https://www.skillbooster.net/calculation/monetization/p.php";
            }if(category.equals("web")){


                siteurl = "https://www.skillbooster.net/calculation/monetization/web.php";

            }if(category.equals("app")){
                siteurl = "https://www.skillbooster.net/calculation/monetization/app.php";


            }if(category.equals("pl")){

                siteurl = "https://www.skillbooster.net/calculation/monetization/pl.php";

            }if(category.equals("ls")){

                siteurl = "https://www.skillbooster.net/calculation/monetization/ls.php";

            }if(category.equals("cg")){

                siteurl = "https://www.skillbooster.net/calculation/monetization/cg.php";

            }if(category.equals("e")){

                siteurl = "https://www.skillbooster.net/calculation/monetization/e.php";

            }

            try {



                URL url = new URL(siteurl);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line ="";
                while ((line =bufferedReader.readLine())!= null){
                    stringBuffer.append(line);

                }

                //return stringBuffer.toString();
                fullfile = stringBuffer.toString();
                JSONArray json = new JSONArray(fullfile);
                int i=0;

                while(i <=json.length()){

                    JSONObject e = json.getJSONObject(i);

                    String  title = e.getString("title");
                    String imgurl = e.getString("thumbnail");
                    String  vurl = e.getString("video_id");
                    arrayList.add(new PerItemVariable(imgurl,title,vurl));

                    i++;


                }
                return  String.valueOf(json.length());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
       progressDialog.dismiss();

            RecylerviewAdapter videoAdapter  = new RecylerviewAdapter(CategoryVideo.this,arrayList);
            recyclerView.setAdapter(videoAdapter);
            videoAdapter.setOnItemClickListener(CategoryVideo.this);

        }
    }


    public static class CheckNetwork {


        private final String TAG = MainActivity.CheckNetwork.class.getSimpleName();

        public static boolean isInternetAvailable(Context context) {
            NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                    Objects.requireNonNull(context.getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();

            if (info == null) {

                return false;
            } else {
                if (info.isConnected()) {

                    return true;
                } else {

                    return true;
                }

            }
        }
    }

}
