package com.mgensuite.airfox.browser;

import com.mgensuite.airfoxsdk.AirFox;
import com.mgensuite.airfoxsdk.AirFoxBuilder;
import com.mgensuite.sdk.core.api.AirFoxMobileSdk;
import com.mgensuite.sdk.core.api.Environment;
import com.mgensuite.sdk.core.util.Logger;

import acr.browser.lightning.BrowserApp;
import acr.browser.lightning.BuildConfig;

public class AirFoxBrowser extends BrowserApp {

    private static final String CUSTOMER_UUID = "596e1de7ac";

    @Override
    public void onCreate() {
        super.onCreate();

        if (AirFoxMobileSdk.isInitialized()) {
            Logger.enableDebugLogging(BuildConfig.DEBUG);

            AirFoxMobileSdk.api().setEnvironment(BuildConfig.DEBUG ?
                    Environment.STAGING :
                    Environment.PRODUCTION);

            AirFoxBuilder builder = new AirFoxBuilder(CUSTOMER_UUID);

            builder
                    .setOfferWallEnabled(true)
                    .setAllowRecharge(true)
                    .setEarnTabEnabled(true)
                    .setPurchaseTabEnabled(true)
                    .setRedeemTabEnabled(true)
                    .setActivityMenuItemEnabled(true)
                    .setLoanTabEnabled(true)
                    .setAccountTabEnabled(false)
                    .setManageTabEnabled(false)
                    .setDataEnabled(false)
                    .setWifiDataEnabled(false)
                    .setDataNotificationsEnabled(false)
                    .setMomentsEnabled(true)
                    .setLocalWaterfall(true);

            AirFox airfox = builder.build(this, false, true, null);
            BrowserMoment.initialize(airfox);
        }
    }

}
