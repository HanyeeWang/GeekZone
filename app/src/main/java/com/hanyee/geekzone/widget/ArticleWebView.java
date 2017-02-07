package com.hanyee.geekzone.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.hanyee.androidlib.utils.NetWorkUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.List;

import static com.hanyee.geekzone.util.Constants.WebView.ASSET_FILE_URL;
import static com.hanyee.geekzone.util.Constants.WebView.ENCODING;
import static com.hanyee.geekzone.util.Constants.WebView.HIDE_HEADER_STYLE;
import static com.hanyee.geekzone.util.Constants.WebView.MIME_TYPE;
import static com.hanyee.geekzone.util.Constants.WebView.NEEDED_FORMAT_CSS_TAG;
import static com.hanyee.geekzone.util.Constants.WebView.NEEDED_FORMAT_JS_TAG;
import static com.hanyee.geekzone.util.Constants.WebView.NEEDED_HTML_FORMAT_TAG;

/**
 * Created by hanyee on 16/10/29.
 */
public class ArticleWebView extends WebView {

    private OnArticleViewLoadListener mLoadListener;

    public ArticleWebView(Context context) {
        this(context, null);
    }

    public ArticleWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArticleWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, false);
    }

    public ArticleWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }

        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName(ENCODING);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        setHorizontalScrollBarEnabled(false);
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String s, Bitmap bitmap) {
                if (mLoadListener != null)
                    mLoadListener.onPageStarted(view, s, bitmap);
            }

            @Override
            public void onLoadResource(WebView view, String s) {
                if (mLoadListener != null)
                    mLoadListener.onLoadResource(view, s);
            }

            @Override
            public void onPageFinished(WebView view, String s) {
                if (mLoadListener != null)
                    mLoadListener.onPageFinished(view, s);
            }

            @Override
            public void onReceivedError(WebView view, int erroCode, String description, String failingUrl) {
                if (mLoadListener != null)
                    mLoadListener.onReceivedError(view, erroCode, description, failingUrl);
            }
        });
    }

    public void setWebArticleImageState(boolean noImage) {
        if (noImage) {
            getSettings().setBlockNetworkImage(true);
        } else {
            getSettings().setBlockNetworkImage(false);
        }
        getSettings().setLoadsImagesAutomatically(true);
    }

    public void setWebArticleCacheState(boolean isNeedCache) {
        if (isNeedCache) {
            getSettings().setAppCacheEnabled(true);
            getSettings().setDomStorageEnabled(true);
            getSettings().setDatabaseEnabled(true);
            if (NetWorkUtil.isNetWorkAvailable(getContext())) {
                getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            } else {
                getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
            String cacheDir = getContext().getFilesDir().getAbsolutePath() + "web_cache";
            getSettings().setAppCachePath(cacheDir);
            getSettings().setAppCacheEnabled(true);
        }
    }

    public void loadUrl(String url, OnArticleViewLoadListener listener) {
        mLoadListener = listener;
        loadUrl(url);
    }

    public void loadDataWithServerStyle(String data, List<String> cssList, List<String> jsList) {
        String html = createHtmlData(data, cssList, jsList);
        loadDataWithBaseURL(null, html, MIME_TYPE, ENCODING, null);
    }

    public void loadDataWithLocalStyle(String data, String css, List<String> jsList) {
        String html = createHtmlData(data, css, jsList);
        loadDataWithBaseURL(ASSET_FILE_URL, html, MIME_TYPE, ENCODING, null);
    }

    public String formatCssTag(String url) {
        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    public String createCssTag(List<String> urls) {
        if (urls == null || urls.isEmpty())
            return "";
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(formatCssTag(url));
        }
        return sb.toString();
    }

    public String formatJsTag(String url) {
        return String.format(NEEDED_FORMAT_JS_TAG, url);
    }

    public String createJsTag(List<String> urls) {
        if (urls == null || urls.isEmpty())
            return "";
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(formatJsTag(url));
        }
        return sb.toString();
    }

    public String createHtmlTag(String html) {
        if (html == null || html.isEmpty())
            return "";
        return String.format(NEEDED_HTML_FORMAT_TAG, html);
    }

    public String createHtmlData(String orgHtml, String orgCss, List<String> jsList) {
        final String html = createHtmlTag(orgHtml);
        final String css = formatCssTag(orgCss);
        final String js = createJsTag(jsList);
        return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
    }

    public String createHtmlData(String orgHtml, List<String> cssList, List<String> jsList) {
        final String html = createHtmlTag(orgHtml);
        final String css = createCssTag(cssList);
        final String js = createJsTag(jsList);
        return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
    }

    public interface OnArticleViewLoadListener {
        void onPageStarted(WebView view, String s, Bitmap bitmap);

        void onLoadResource(WebView view, String s);

        void onPageFinished(WebView view, String s);

        void onReceivedError(WebView view, int erroCode, String description, String failingUrl);
    }
}
