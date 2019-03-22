package net.skillbooster.projectone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdExtendedListener;

import java.util.Objects;

import static net.skillbooster.projectone.MainActivity.Extra_url;

public class ViewVideo extends AppCompatActivity {
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;
    private com.facebook.ads.InterstitialAd interstitialAd;
    private TextView textView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_video);
        String url_code = getIntent().getStringExtra(Extra_url);
        final String url ="https://www.skillbooster.net/embed/"+url_code;
     //   Toast.makeText(this, ""+url_code, Toast.LENGTH_SHORT).show();
        textView = findViewById(R.id.internet);
        webView = findViewById(R.id.webView);
        customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
        mWebViewClient = new myWebViewClient();
        webView.setWebViewClient(mWebViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new AutoPlayVideoWebViewClient());
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

        if (CheckNetwork.isInternetAvailable(ViewVideo.this)) //returns true if internet available
        {
            textView.setVisibility(View.GONE);
            webView.loadUrl(url);
        }else{

            textView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);

        }
    }
    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        if (inCustomView()) {
            hideCustomView();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.

        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;

        }

        @SuppressLint("InflateParams")
        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(ViewVideo.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }


            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {

            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }





    }

    class myWebViewClient extends WebViewClient {
        boolean loadingFinished = true;
        boolean redirect = false;
        private boolean alreadyLoaded = false;


        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }




        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (alreadyLoaded)
                return;
            alreadyLoaded = true;


            super.onPageStarted(view,url,favicon);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

        }


        @Override
        public void onPageFinished(WebView view, String url) {

            if (!redirect) {
                loadingFinished = true;
            }

            if (loadingFinished && !redirect) {
                //HIDE LOADING IT HAS FINISHED
            } else {
                redirect = false;
            }
        }

        /*
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!loadingFinished) {
                redirect = true;
            }

            loadingFinished = false;
            webView.loadUrl(url);
            return  false;    //To change body of overridden methods use File | Settings | File Templates.
        }

*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    private class AutoPlayVideoWebViewClient extends WebViewClient {



        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // mimic onClick() event on the center of the WebView
            long delta = 100;
            long downTime = SystemClock.uptimeMillis();
            float x = view.getLeft() + (view.getWidth()/2);
            float y = view.getTop() + (view.getHeight()/2);

            MotionEvent tapDownEvent = MotionEvent.obtain(downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0);
            tapDownEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);
            MotionEvent tapUpEvent = MotionEvent.obtain(downTime, downTime + delta + 1, MotionEvent.ACTION_UP, x, y, 0);
            tapUpEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);

            view.dispatchTouchEvent(tapDownEvent);
            view.dispatchTouchEvent(tapUpEvent);




        }


        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.cancel();
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
