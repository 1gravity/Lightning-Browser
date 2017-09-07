/*
 * Copyright 2014 A.C.R. Development
 */
package acr.browser.lightning.constant;

import android.app.Application;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;

import com.anthonycr.bonsai.Single;
import com.anthonycr.bonsai.SingleAction;
import com.anthonycr.bonsai.SingleSubscriber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import acr.browser.lightning.R;
import acr.browser.lightning.BrowserApp;
import acr.browser.lightning.search.SearchEngineProvider;
import acr.browser.lightning.search.engine.BaseSearchEngine;
import acr.browser.lightning.utils.UrlUtils;
import acr.browser.lightning.utils.Utils;

public class StartPage {

    public static final String FILENAME = "homepage.html";

    @NonNull
    public static File getStartPageFile(@NonNull Application application) {
        return new File(application.getFilesDir(), FILENAME);
    }

    @NonNull private final String mTitle;

    @Inject Application mApp;
    @Inject SearchEngineProvider mSearchEngineProvider;

    public StartPage() {
        BrowserApp.getAppComponent().inject(this);
        mTitle = mApp.getString(R.string.home);
    }

    @NonNull
    public Single<String> getHomepage() {
        return Single.create(subscriber -> {
            BaseSearchEngine currentSearchEngine = mSearchEngineProvider.getCurrentSearchEngine();
            String baseUrl = getBaseUrl(currentSearchEngine.getQueryUrl());
            subscriber.onItem(baseUrl);
        });
    }

    private String getBaseUrl(String input) {
        try {
            URL url = new URL(input);
            return url.getProtocol() + "://" + url.getAuthority() + "/";
        } catch (MalformedURLException e) {
            return input;
        }
    }
}
