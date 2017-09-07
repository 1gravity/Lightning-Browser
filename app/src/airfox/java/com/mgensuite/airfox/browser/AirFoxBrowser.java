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
    private static AirFoxBuilder sAirFoxBuilder;

    @Override
    public void onCreate() {
        super.onCreate();

        if (AirFoxMobileSdk.isInitialized()) {
            Logger.enableDebugLogging(BuildConfig.DEBUG);

            AirFoxMobileSdk.setEnvironment(BuildConfig.DEBUG ?
                    Environment.STAGING :
                    Environment.PRODUCTION);

            sAirFoxBuilder = new AirFoxBuilder(CUSTOMER_UUID);

            sAirFoxBuilder
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

            AirFox airfox = sAirFoxBuilder.build(this, false, true, null);
            BrowserMoment.initialize(airfox);
        }
    }

    public static String getPhoneNumber() {
        return sAirFoxBuilder.getMDN();
    }

}
