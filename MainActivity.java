package net.skillbooster.projectone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecylerviewAdapter.OnItemClickListener {
    private RecyclerView recyclerView,recyclerView2,recyclerView3;
    public static final String Extra_url = "imgurl";
    private ArrayList<PerItemVariable> arrayList = new ArrayList<>();
    private ArrayList<PerItemVariable> arrayList2 = new ArrayList<>();
    private ArrayList<PerItemVariable> arrayList3 = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
private com.google.android.gms.ads.AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.internet);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setMessage("Loading Please Wait........");
        MobileAds.initialize(this,"ca-app-pub-9227724296138087~1251009283");

         adView = findViewById(R.id.adView);
         AdRequest adRequest = new AdRequest.Builder().build();
         adView.loadAd(adRequest);

        //mostviewed recylerview
        recyclerView2 = findViewById(R.id.recycelerview_mostviwed);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));


        //latest video recylerview
        recyclerView = findViewById(R.id.recycelerview_latest);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));


      //featured recylerview
        recyclerView3 = findViewById(R.id.recycelerview_featured);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));




        swipeRefreshLayout = findViewById(R.id.pull);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(true);
            }
        });



        arrayList = new ArrayList<PerItemVariable>();
        arrayList2 = new ArrayList<PerItemVariable>();
        arrayList3 = new ArrayList<PerItemVariable>();
        final jsonclass jsonclass = new jsonclass();
        final jsonclass2 jsonclass2 = new jsonclass2();
        final jsonclass3 jsonclass3 = new jsonclass3();


        if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
        {
                textView.setVisibility(View.GONE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jsonclass.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    jsonclass2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    jsonclass3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }, 10);
        }else{

            textView.setVisibility(View.VISIBLE);

        }

        NavigationView navigationView = findViewById(R.id.nav);
        setUpTool();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.cf:
                        Intent i = new Intent(MainActivity.this,CategoryVideo.class);
                        i.putExtra("key","cf");
                        startActivity(i);
                        break;


                    case  R.id.md:
                        Intent j = new Intent(MainActivity.this,CategoryVideo.class);
                        j.putExtra("key","md");
                        startActivity(j);
                        break;

                    case  R.id.p:
                        Intent k = new Intent(MainActivity.this,CategoryVideo.class);
                        k.putExtra("key","p");
                        startActivity(k);
                        break;


                    case  R.id.web:
                        Intent l = new Intent(MainActivity.this,CategoryVideo.class);
                        l.putExtra("key","web");
                        startActivity(l);
                        break;

                    case  R.id.app:
                        Intent m = new Intent(MainActivity.this,CategoryVideo.class);
                        m.putExtra("key","app");
                        startActivity(m);
                        break;

                    case  R.id.pl:
                        Intent n = new Intent(MainActivity.this,CategoryVideo.class);
                        n.putExtra("key","pl");
                        startActivity(n);
                        break;

                    case  R.id.ls:
                        Intent o = new Intent(MainActivity.this,CategoryVideo.class);
                        o.putExtra("key","ls");
                        startActivity(o);
                        break;

                    case  R.id.cg:
                        Intent p = new Intent(MainActivity.this,CategoryVideo.class);
                        p.putExtra("key","cg");
                        startActivity(p);
                        break;

                    case  R.id.e:
                        Intent q = new Intent(MainActivity.this,CategoryVideo.class);
                        q.putExtra("key","e");
                        startActivity(q);
                        break;

                    case  R.id.about:
                        Intent r = new Intent(MainActivity.this,AboutUs.class);
                        startActivity(r);
                        break;

                    case  R.id.term:
                        Intent s = new Intent(MainActivity.this,TermsActivity.class);
                        startActivity(s);
                        break;
                    case  R.id.share:
                        shareIt();
                        break;

                }
                return false;
            }
        });

}

    private void shareIt() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SkillBooster");
            String shareMessage= "\nI recommend you to Install this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    private void setUpTool() {
        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }


    @Override
    public void onItemClick(int position) {

        Intent myintent = new Intent(this, ViewVideo.class);
        PerItemVariable itemRow =  arrayList.get(position);
      //  myintent.putExtra(Extra_url,itemRow.getVideourl());
       // startActivity(myintent);


    }


    public class jsonclass extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader;
            String fullfile;



            try {



                URL url = new URL("https://www.skillbooster.net/calculation/monetization/activity.php");
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


            //Toast.makeText(DisplayActivity.this, ""+s, Toast.LENGTH_LONG).show();
            //  Toast.makeText(DisplayActivity.this, ""+s, Toast.LENGTH_LONG).show();
            RecylerviewAdapter videoAdapter  = new RecylerviewAdapter(MainActivity.this,arrayList);
            recyclerView.setAdapter(videoAdapter);
            videoAdapter.setOnItemClickListener(MainActivity.this);

        }
    }

//2ndwork

    public  class jsonclass2 extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader;
            String fullfile;



            try {



                URL url = new URL("https://www.skillbooster.net/calculation/monetization/activity2.php");
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
                    arrayList2.add(new PerItemVariable(imgurl,title,vurl));

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

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            //Toast.makeText(DisplayActivity.this, ""+s, Toast.LENGTH_LONG).show();
            //  Toast.makeText(DisplayActivity.this, ""+s, Toast.LENGTH_LONG).show();
            RecylerviewAdapter videoAdapter  = new RecylerviewAdapter(MainActivity.this,arrayList2);
            recyclerView2.setAdapter(videoAdapter);
            videoAdapter.setOnItemClickListener(MainActivity.this);

        }
    }

//3rdndwork-featured video

    public  class jsonclass3 extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader;
            String fullfile;



            try {



                URL url = new URL("https://www.skillbooster.net/calculation/monetization/activity3.php");
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
                    arrayList3.add(new PerItemVariable(imgurl,title,vurl));

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

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            //Toast.makeText(DisplayActivity.this, ""+s, Toast.LENGTH_LONG).show();
            //  Toast.makeText(DisplayActivity.this, ""+s, Toast.LENGTH_LONG).show();
            RecylerviewAdapter videoAdapter  = new RecylerviewAdapter(MainActivity.this,arrayList3);
            recyclerView3.setAdapter(videoAdapter);
            videoAdapter.setOnItemClickListener(MainActivity.this);

        }
    }

    public static class CheckNetwork {


        private final String TAG = CheckNetwork.class.getSimpleName();

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
