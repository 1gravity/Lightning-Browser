package com.mgensuite.airfox.browser;

import android.content.Intent;
import android.widget.Toast;

import com.mgensuite.airfoxsdk.AirFoxStartupActivity;

import acr.browser.lightning.MainActivity;
import acr.browser.lightning.R;

/**
 * The StartupActivity makes sure there's a sign-up before the browser's main Activity is started.
 */
public class StartupActivity extends AirFoxStartupActivity {

    @Override
    protected void onStartActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestartActivity() {
        Toast.makeText(this, R.string.subscription_failed, Toast.LENGTH_LONG).show();
        finish();
    }

}
