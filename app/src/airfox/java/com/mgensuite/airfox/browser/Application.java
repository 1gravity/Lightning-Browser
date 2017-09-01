package com.mgensuite.airfox.browser;

import com.mgensuite.ads.AdModule;
import com.mgensuite.ads.AdModuleBuilder;
import com.mgensuite.ads.AdMoment;
import com.mgensuite.airfoxsdk.AirFoxBuilder;
import com.mgensuite.sdk.core.api.AirFoxMobileSdk;
import com.mgensuite.sdk.core.util.Logger;

import acr.browser.lightning.BrowserApp;
import acr.browser.lightning.BuildConfig;

public class Application extends BrowserApp {

    private static AdModule sAdModule;

    private static final String CUSTOMER_UUID = "596e1de7ac";
    private static final String APP_UUID = CUSTOMER_UUID + "_4";
    private static final AdMoment BROWSER = new AdMoment("browser");

    @Override
    public void onCreate() {
        super.onCreate();

        if (AirFoxMobileSdk.isInitialized()) {
            Logger.enableDebugLogging(BuildConfig.DEBUG);

            AirFoxBuilder afBuilder = new AirFoxBuilder(CUSTOMER_UUID);

            afBuilder
                    .setOfferWallEnabled(true)
                    .setAllowRecharge(true)
                    .setEarnTabEnabled(true)
                    .setPurchaseTabEnabled(true)
                    .setRedeemTabEnabled(true)
                    .setActivityMenuItemEnabled(true)
                    .setLoanTabEnabled(false)
                    .setAccountTabEnabled(false)
                    .setManageTabEnabled(false)
                    .setDataEnabled(false)
                    .setWifiDataEnabled(false)
                    .setDataNotificationsEnabled(false)
                    .setMomentsEnabled(true)
                    .setLocalWaterfall(true);

            afBuilder.build(this, false, true, null);

            AdModuleBuilder amBuilder = new AdModuleBuilder(this, APP_UUID);
            sAdModule = amBuilder.build(BROWSER.name());
            if (sAdModule != null) {
                sAdModule.enable(true);
            }
        }
    }

}
