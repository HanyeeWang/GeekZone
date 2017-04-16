package com.hanyee.geekzone.util;

/**
 * Created by Hanyee on 16/10/16.
 */
public interface Constants {

    int WAVE_FLOW_ANIMATION_TIME         = 1000;
    int WAVE_RISE_ANIMATION_TIME         = 3000;

    interface NavigationItem {
        int TYPE_ZHIHU                   = 101;
        int TYPE_WECHAT                  = 102;
        int TYPE_GANK                    = 103;
        int TYPE_NEWS                    = 104;
        int TYPE_SETTING                 = 105;
        int TYPE_LIKE                    = 106;
        int TYPE_ABOUT                   = 107;
    }

    interface SharePreference {
        String NIGHT_MODE                = "night_mode";
        String CURRENT_ITEM              = "current_item";
        String ARTICLE_IMAGE             = "article_image";
        String ARTICLE_CACHE             = "article_cache";
    }

    interface WebView {
        String ENCODING                  = "utf-8";
        String MIME_TYPE                 = "text/html; charset=utf-8";
        String HIDE_HEADER_STYLE         = "<style>div.headline{display:none;}</style>";
        String NEEDED_FORMAT_CSS_TAG     = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";
        String NEEDED_FORMAT_JS_TAG      = "<script src=\"%s\"></script>";
        String ASSET_FILE_URL            = "file:///android_asset/";
        String COLUMN_CSS_FILE           = "column.css";
        String NEWS_CSS_FILE             = "news.css";
        String NEEDED_HTML_FORMAT_TAG    = "<html class=\"no-touch\">\n" +
                                           "<head>\n" +
                                           "    <style type=\"text/css\">@charset\n" +
                                           "        \"UTF-8\";[ng\\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide{display:none\n" +
                                           "        !important;}ng\\:form{display:block;}.ng-animate-block-transitions{transition:0s\n" +
                                           "        all!important;-webkit-transition:0s all!important;}.ng-hide-add-active,.ng-hide-remove{display:block!important;}\n" +
                                           "    </style>\n" +
                                           "    <meta charset=\"utf-8\">\n" +
                                           "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                                           "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">\n" +
                                           "</head>\n" +
                                           "<body ng-app=\"columnWebApp\" ng-controller=\"AppCtrl\" ng-class=\"pageClass\" class=\"ng-scope ng-animate\">\n" +
                                           "<main class=\"main-container ng-scope\" ng-view=\"\">\n" +
                                           "    <div class=\"main receptacle post-view ng-scope\">\n" +
                                           "        <article class=\"entry ng-scope\" ng-controller=\"EntryCtrl\" ui-lightbox=\"\">\n" +
                                           "            <section class=\"entry-content ng-binding\" ng-bind-html=\"postContentTrustedHtml\" id=\"section\">\n" +
                                           "                %s\n" +
                                           "            </section>\n" +
                                           "        </article>\n" +
                                           "    </div>\n" +
                                           "</main>\n" +
                                           "</body>\n" +
                                           "</html>";
        String INJECT_LOCAL_CSS          = "javascript:(function() {" +
                                           "var parent = document.getElementsByTagName('head').item(0);" +
                                           "var style = document.createElement('style');" +
                                           "style.type = 'text/css';" +
                                           // Tell the browser to BASE64-decode the string into your script !!!
                                           "style.innerHTML = window.atob('%s');" +
                                           "parent.appendChild(style)" +
                                           "})()";
    }

    interface TIANXIN {
        String APPKEY                    = "fc9441fb101609b2cfe74407e992a0b9";
        String WECHAT                    = "wxnew";
        String NEWS                      = "news";
        String SOCIAL                    = "social";
        String GUONEI                    = "guonei";
        String WORLD                     = "world";
        String HUABIAN                   = "huabian";
        String TIYU                      = "tiyu";
        String KEJI                      = "keji";
        String HEALTH                    = "health";
        String APPLE                     = "apple";
    }
    
    interface ARTICLE {
        String EXTRA_THEME_ID            = "extra_theme_id";
        String EXTRA_THEME_NAME          = "extra_theme_name";
        String EXTRA_SPECIAL_ID          = "extra_special_id";
        String EXTRA_SPECIAL_NAME        = "extra_special_name";
        String EXTRA_AUTHOR_NAME         = "extra_author_name";
        String EXTRA_ARTICLE_ID          = "extra_article_id";
        String EXTRA_ARTICLE_URL         = "extra_article_url";
        String EXTRA_ARTICLE_TITLE       = "extra_article_title";
        String EXTRA_ARTICLE_DESC        = "extra_article_desc";
        String EXTRA_ARTICLE_TIME        = "extra_article_time";
        String EXTRA_IMAGE_URL           = "extra_image_url";
        String EXTRA_ARTICLE_TYPE        = "extra_article_type";
        String EXTRA_TYPE_ZHIHU_NEWS     = "extra_type_zhihu_news";
        String EXTRA_TYPE_COLUMN_ARTICLE = "extra_type_column_article";
        String EXTRA_TYPE_COLUMN_AUTHOR  = "extra_type_column_author";
        String EXTRA_TYPE_TIANXIN        = "extra_type_tianxin";
        String EXTRA_TYPE_TIANXIN_TYPE   = "extra_type_tianxin_type";
        String EXTRA_TYPE_GANK           = "extra_type_gank";
    }

    interface GANK {
        String ANDROID                   = "Android";
        String IOS                       = "iOS";
        String WELFARE                   = "福利";
        String RELAX                     = "休息视频";
        String EXPAND                    = "拓展资源";
        String ALL                       = "all";
        String FRONT                     = "前端";
        String RECOMMEND                 = "瞎推荐";
    }
}
