package alarmlunch.groupware.com.kggroup.alarmlunch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static alarmlunch.groupware.com.kggroup.alarmlunch.R.id.webview;

/**
 * Created by itsm02 on 2016. 10. 14..
 */

public class ResultActivity extends Activity {

    String searchName;
    TextView view;
    ArrayList<ModePoI> arrayList;

    public ListView listView;
    public ArrayAdapter<ModePoI> arrayAdapter;

    public WebView webView;

    public Activity fragmentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        fragmentActivity = this;

//        webview = (WebView) findViewById(webview);

        searchName = getIntent().getStringExtra("searchName");

//        arrayList = new ArrayList<ModePoI>();

//        listView = (ListView) findViewById(R.id.listview);
//        arrayAdapter = new CustomArrayAdapter<>(this, R.layout.listview_row_result, arrayList);

//        new AsynctaskTemp().execute();

        String resultUrl = "https://m.map.naver.com/search2/search.nhn?query=" + searchName + "&siteSort=1&sm=clk";

        webView = (WebView) findViewById(webview);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setGeolocationDatabasePath(this.getFilesDir().getPath());

//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.setScrollBarStyle(ScrollView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setUseWideViewPort(true);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setDefaultTextEncodingName("EUC-KR");
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.bringToFront();
        webView.requestFocus();

        webView.loadUrl(resultUrl);

    }

    @Override
    public void onBackPressed() {


        try {

            if (webView != null && webView.canGoBack()) {

                webView.goBack();


            } else {


                super.onBackPressed();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {

//        Log.e("DEBUG", "11111");
        super.onDestroy();


    }

    public void network() throws IOException, XmlPullParserException {


        String query = URLEncoder.encode("판교역 " + searchName, "UTF-8");

        String reqUrl = "https://openapi.naver.com/v1/search/local.xml?" + "query=" + query + "&" + "display=100&start=1&sort=random";

        Log.e("DEBUG", "reqUrl = " + reqUrl);

        URL url = new URL(reqUrl);

        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setRequestProperty("X-Naver-Client-Id", "MyP67gRKRGCYqvAwnSog");
        httpsURLConnection.setRequestProperty("X-Naver-Client-Secret", "XvHAAgXZEa");

//        httpsURLConnection.setDoOutput(true);
//        httpsURLConnection.setDoInput(true);
//        httpsURLConnection.setConnectTimeout(1000 * 10);
//      httpsURLConnection.setReadTimeout(1000 * 10);
//        httpsURLConnection.setDefaultUseCaches(false);
//        httpsURLConnection.setUseCaches(false);
//        httpsURLConnection.setRequestProperty("Accept", "*/*");
//        httpsURLConnection.setRequestProperty("Content-Type", "application/xml");


        if (httpsURLConnection != null) {

            int responseCode = httpsURLConnection.getResponseCode();

            Log.e("DEBUG", "responseCode = " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));  //inputstream 으로부터 xml 입력받기

                String tag;

                xpp.next();
                int eventType = xpp.getEventType();

                StringBuffer buffer = new StringBuffer();

                ModePoI poi = new ModePoI();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:

//                            buffer.append("start NAVER XML parsing...\n\n");

                            break;

                        case XmlPullParser.START_TAG:

                            tag = xpp.getName();    //테그 이름 얻어오기

//                            Log.e("DEBUG", "START_TAG tag " + tag);
//                            Log.e("DEBUG", "START_TAG txt " + xpp.getText());

                            if (tag.equals("item")) ;// 첫번째 검색결과

                            else if (tag.equals("title")) {

                                buffer.append("업소명 : ");
                                xpp.next();
//                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                                buffer.append("\n");          //줄바꿈 문자 추가

                                poi.title = xpp.getText();

                            } else if (tag.equals("category")) {

                                buffer.append("업종 : ");
                                xpp.next();
//                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                                buffer.append("\n");          //줄바꿈 문자 추가

                                poi.category = xpp.getText();

                            } else if (tag.equals("description")) {

                                buffer.append("세부설명 :");
                                xpp.next();
//                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                                buffer.append("\n");          //줄바꿈 문자 추가

                                poi.description = xpp.getText();

                            } else if (tag.equals("telephone")) {

                                buffer.append("연락처 :");
                                xpp.next();
//                                buffer.append(xpp.getText()); //telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                                buffer.append("\n");          //줄바꿈 문자 추가

                                poi.tel = xpp.getText();

                            } else if (tag.equals("address")) {

                                buffer.append("주소 :");
                                xpp.next();

//                                buffer.append(xpp.getText()); //address 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                                buffer.append("\n");          //줄바꿈 문자 추가

                                poi.addr = xpp.getText();

                            } else if (tag.equals("mapx")) {

                                buffer.append("지도 위치 X :");
                                xpp.next();

//                                buffer.append(xpp.getText()); //mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                                buffer.append("  ,  ");          //줄바꿈 문자 추가

                                poi.mapX = xpp.getText();

                            } else if (tag.equals("mapy")) {

                                buffer.append("지도 위치 Y :");
                                xpp.next();

//                                buffer.append(xpp.getText()); //mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                                buffer.append("\n");          //줄바꿈 문자 추가

                                poi.mapY = xpp.getText();

                            }
                            break;

                        case XmlPullParser.TEXT:

                            tag = xpp.getName();    //테그 이름 얻어오기


//                            Log.e("DEBUG", "TEXT tag " + tag);
//                            Log.e("DEBUG", "TEXT tag " + xpp.getText());

                            break;

                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();    //테그 이름 얻어오기

//                            Log.e("DEBUG", "END_TAG tag " + tag);

                            if (tag.equals("item")) {
                                buffer.append("\n"); // 첫번째 검색결과종료..줄바꿈

                                arrayList.add(poi);
                                Log.e("DEBUG", "" + poi.toString());

                                poi = new ModePoI();

                            }


                            break;
                    }

                    eventType = xpp.next();
                }

//                Log.e("DEBUG", buffer.toString());

//                BufferedReader br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));
//                StringBuilder stringBuilder = new StringBuilder();
//                String output;
//                while ((output = br.readLine()) != null) {
//                    stringBuilder.append(output);
//                }
//
//                Log.e("DEBUG", stringBuilder.toString());
            }
        }


    }

    public class AsynctaskTemp extends AsyncTask

    {

        @Override
        protected Object doInBackground(Object[] params) {

            try {

                Log.e("DEBUG", "doInBackground");

                network();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            listView.setAdapter(arrayAdapter);
        }
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("DEBUG", "1 url = " + url);


            try {

                if (url.startsWith("intent")) {
                    Intent intent = null;
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        // Log.e("intent getScheme     +++===>", intent.getScheme());
                        // Log.e("intent getDataString +++===>", intent.getDataString());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();

                        return false;
                    }
                    // chrome 버젼 방식 : 2014.01 추가
                    // chrome 버젼 방식
                    // 앱설치 체크를 합니다.
                    if (fragmentActivity.getPackageManager().resolveActivity(intent, 0) == null) {
                        String packagename = intent.getPackage();
                        if (packagename != null) {
                            Uri uri = Uri.parse("market://search?q=pname:" + packagename);
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            fragmentActivity.startActivity(intent);
                            return true;
                        }
                    }

                    Uri uri = Uri.parse(intent.getDataString());
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    fragmentActivity.startActivity(intent);
                    return true;

                } else if (url.startsWith("tel:")) { // 구 방식

                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    fragmentActivity.startActivity(intent);
                    return true;

                }

            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            Kog.i("3 url = " + url);
//            dismissProgressDialog();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("DEBUG", "2 url = " + url);
//            showProgressDialog();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//            dismissProgressDialog();
        }
    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            if (fragmentActivity == null || fragmentActivity.isFinishing())
                return true;

            Log.e("DEBUG", "3 url = " + url);

            new AlertDialog.Builder(fragmentActivity).setTitle("알림").setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setCancelable(false).create().show();

            return true;
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(origin, callback);

            Log.e("DEBUG", "origin = " + origin);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                callback.invoke(origin, true, false);
            }


        }

//        @Override
//        public void onGeolocationPermissionsHidePrompt() {
//            super.onGeolocationPermissionsHidePrompt();
//        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final android.webkit.JsResult result) {
            if (fragmentActivity == null || fragmentActivity.isFinishing())
                return true;

            Log.e("DEBUG", "4 url = " + url);

            new AlertDialog.Builder(fragmentActivity).setTitle("알림").setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).setCancelable(false).create().show();
            return true;
        }
    }


}
