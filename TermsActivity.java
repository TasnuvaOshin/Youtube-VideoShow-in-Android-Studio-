package net.skillbooster.projectone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdExtendedListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;

public class TermsActivity extends AppCompatActivity {
    private com.facebook.ads.InterstitialAd interstitialAd;
    private com.google.android.gms.ads.AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        NavigationView navigationView = findViewById(R.id.nav);
        setUpTool();
        MobileAds.initialize(this,"ca-app-pub-9227724296138087~1251009283");

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        /*
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

        */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.cf:
                        Intent i = new Intent(TermsActivity.this,CategoryVideo.class);
                        i.putExtra("key","cf");
                        startActivity(i);
                        break;


                    case  R.id.md:
                        Intent j = new Intent(TermsActivity.this,CategoryVideo.class);
                        j.putExtra("key","md");
                        startActivity(j);
                        break;

                    case  R.id.p:
                        Intent k = new Intent(TermsActivity.this,CategoryVideo.class);
                        k.putExtra("key","p");
                        startActivity(k);
                        break;


                    case  R.id.web:
                        Intent l = new Intent(TermsActivity.this,CategoryVideo.class);
                        l.putExtra("key","web");
                        startActivity(l);
                        break;

                    case  R.id.app:
                        Intent m = new Intent(TermsActivity.this,CategoryVideo.class);
                        m.putExtra("key","app");
                        startActivity(m);
                        break;

                    case  R.id.pl:
                        Intent n = new Intent(TermsActivity.this,CategoryVideo.class);
                        n.putExtra("key","pl");
                        startActivity(n);
                        break;

                    case  R.id.ls:
                        Intent o = new Intent(TermsActivity.this,CategoryVideo.class);
                        o.putExtra("key","ls");
                        startActivity(o);
                        break;

                    case  R.id.cg:
                        Intent p = new Intent(TermsActivity.this,CategoryVideo.class);
                        p.putExtra("key","cg");
                        startActivity(p);
                        break;

                    case  R.id.e:
                        Intent q = new Intent(TermsActivity.this,CategoryVideo.class);
                        q.putExtra("key","e");
                        startActivity(q);
                        break;

                    case  R.id.about:
                        Intent r = new Intent(TermsActivity.this,AboutUs.class);
                        startActivity(r);
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
}
