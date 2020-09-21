package com.bdl.willy_webview;

import androidx.appcompat.app.AppCompatActivity;

//import android.os.Bundle;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import android.net.Uri;
import android.content.Intent;
import android.os.Build;
import android.content.pm.ApplicationInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String url ="https://iter01.com/49313.html";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);//給許可權
/*
        setContentView(webview);
        webview.setWebViewClient(new WebViewClient());
        //webview.loadUrl("googlechrome://navigate?url="+url);
        webview.loadUrl(url);*/
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webview, String url) {
                // 這個方法我沒有重寫的話也還是使用webView來載入連結
                // 而且我這裡測試返回的true/false貌似沒什麼影響

                /*if (Uri.parse(url).getHost().endsWith(url)) {
                    //若是指定伺服器的連結則在當前webView中跳轉
                    webview.loadUrl(url);
                    return false;
                } else if (Uri.parse(url).getHost().length() == 0) {
                    // 本地連結的話直接在webView中跳轉
                    return false;
                }*/

                // 其他情況則使用系統瀏覽器開啟網址
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setClassName("com.android.chrome","com.google.android.apps.chrome.Main");//開啟chrome瀏覽器
                startActivity(intent);
                return true;
            }
        });

        webview.loadUrl(url);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 官網: https://developer.chrome.com/devtools/docs/remote-debugging#debugging-webviews
            // 官網說WebView不受manifest的debuggable標籤的影響,若需要在該標籤啟用時才允許除錯,則新增如下條件判斷(注意:儘量不要在manifest中顯式指定debuggable屬性,放空即可,這樣Android Studio會自動在除錯時設定成true,在release版本中設定成false)
            int debuggable = getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE;
            if (0 != debuggable) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
    }

}
//referance:
// https://iter01.com/49313.html
//https://www.itread01.com/content/1556457608.html
//https://jerrynest.io/app-android-webview/
//https://www.itread01.com/content/1548203591.html