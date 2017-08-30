package com.mgensuite.airfox.browser;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.webkit.WebView;

import com.anthonycr.bonsai.Schedulers;
import com.mgensuite.ads.AdModule;
import com.mgensuite.ads.AdModuleBuilder;
import com.mgensuite.ads.AdMoment;
import com.mgensuite.airfoxsdk.AirFox;
import com.mgensuite.airfoxsdk.AirFoxBuilder;
import com.mgensuite.sdk.core.api.AirFoxMobileSdk;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import javax.inject.Inject;

import acr.browser.lightning.BrowserApp;
import acr.browser.lightning.BuildConfig;
import acr.browser.lightning.database.HistoryItem;
import acr.browser.lightning.database.bookmark.BookmarkExporter;
import acr.browser.lightning.database.bookmark.BookmarkModel;
import acr.browser.lightning.database.bookmark.legacy.LegacyBookmarkManager;
import acr.browser.lightning.di.AppComponent;
import acr.browser.lightning.di.AppModule;
import acr.browser.lightning.di.DaggerAppComponent;
import acr.browser.lightning.preference.PreferenceManager;
import acr.browser.lightning.utils.FileUtils;
import acr.browser.lightning.utils.MemoryLeakUtils;
import acr.browser.lightning.utils.Preconditions;

public class Application extends BrowserApp {

    private static AdModule sAdModule;

    private static final String CUSTOMER_UUID = "596e1de7ac";
    private static final String APP_UUID = CUSTOMER_UUID + "_4";
    private static final AdMoment BROWSER = new AdMoment("browser");

    @Override
    public void onCreate() {
        super.onCreate();

        if (AirFoxMobileSdk.isInitialized()) {
            AirFoxBuilder afBuilder = new AirFoxBuilder(CUSTOMER_UUID);

            afBuilder
                    .setOfferWallEnabled(false)
                    .setEarnTabEnabled(false)
                    .setAccountTabEnabled(false)
                    .setManageTabEnabled(false)
                    .setRedeemTabEnabled(false)
                    .setAllowRecharge(false)
                    .setDataEnabled(false)
                    .setWifiDataEnabled(false)
                    .setDataNotificationsEnabled(false)
                    .setMomentsEnabled(true)
                    .setLocalWaterfall(true);

            AirFox airFox = afBuilder.build(this, false, true, null);
            if (airFox != null) {
                airFox.enableLockscreen(false);
                airFox.enableAdWall(false);
                airFox.enablePostUnlockAds(false);
                airFox.enablePostCallAds(false);
            }

            AdModuleBuilder amBuilder = new AdModuleBuilder(this, APP_UUID);
            sAdModule = amBuilder.build(BROWSER.name());
            if (sAdModule != null) {
                sAdModule.enable(true);
            }
        }
    }

}
