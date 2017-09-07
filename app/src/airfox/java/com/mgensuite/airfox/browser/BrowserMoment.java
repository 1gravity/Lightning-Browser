package com.mgensuite.airfox.browser;

import android.content.Context;

import com.mgensuite.ads.AdMoment;
import com.mgensuite.airfoxsdk.AirFox;
import com.mgensuite.sdk.core.util.Logger;

/**
 * The "browser" moment we use to show native ads after a certain amount of pages have loaded.
 */
public class BrowserMoment extends AdMoment {

    private static AirFox sAirfox;
    private static BrowserMoment sMoment;

    static void initialize(AirFox airfox) {
        sAirfox = airfox;
        sMoment = new BrowserMoment();
        airfox.enableMoment(sMoment, true);
    }

    public static void showAd(Context context) {
        if (sAirfox == null) {
            Logger.w(Logger.LOG_TAG, "BrowserMoment not initialized properly");
            return;
        }

        sAirfox.showInterstitial(context, sMoment);
    }

    private BrowserMoment() {
        super("browser");
    }
}
