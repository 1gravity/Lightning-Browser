package com.mgensuite.airfox.browser;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mgensuite.airfoxsdk.AirFoxMainActivity;
import com.mgensuite.datalayer.TrackingObserver;
import com.mgensuite.datalayer.model.topup.TopupInfo;
import com.mgensuite.datalayer.model.topup.TopupInfoData;
import com.mgensuite.datalayer.model.wallet.Balance;
import com.mgensuite.datalayer.model.wallet.Wallet;
import com.mgensuite.datalayer.viewmodel.Resource;
import com.mgensuite.datalayer.viewmodel.TopupViewModel;
import com.mgensuite.datalayer.viewmodel.WalletViewModel;
import com.mgensuite.datalayer.wallet.WalletHelper;
import com.mgensuite.sdk.core.util.Logger;
import com.mgensuite.sdk.core.util.MainThreadUtil;

import acr.browser.lightning.MainActivity;
import acr.browser.lightning.R;
import butterknife.BindView;

/**
 * The StartupActivity makes sure there's a sign-up before the browser's main Activity is started.
 */
public class AirFoxBrowserActivity extends MainActivity implements LifecycleRegistryOwner {

    private static final String TOKEN_CURRENCY = "AIR";

    /*
     * We need to do all the calls to the registry manually due to:
     * https://issuetracker.google.com/issues/62160522.
     *
     * Can be removed once the bug is fixed.
     */
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    private Balance mBalance;
    private Double mBalanceAmount;
    private Double mMinimumRecharge;

    private boolean mLoading;

    private TrackingObserver<Resource<Wallet>> mWalletObserver =
            new TrackingObserver<Resource<Wallet>>() {
                @Override
                public void onChanged(@Nullable final Resource<Wallet> resource) {
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        MainThreadUtil.post(() -> updateTokenBalance(resource.getData()));
                        setHasChanged();
                    }
                }
            };

    private TrackingObserver<Resource<TopupInfo>> mTopupObserver =
            new TrackingObserver<Resource<TopupInfo>>() {
                @Override
                public void onChanged(@Nullable Resource<TopupInfo> resource) {
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        MainThreadUtil.post(() -> updateTokenBalance(resource.getData()));
                        setHasChanged();
                    }
                }
            };

    @BindView(R.id.airfox_progress_bar) RoundCornerProgressBar mProgressBar;
    @BindView(R.id.airfox_progress_text) TextView mProgressText;

    public AirFoxBrowserActivity() {}

    private void updateTokenBalance(Wallet wallet) {
        if (wallet != null && wallet.getBalance() != null &&
                wallet.getBalance().getSubscriber() != null) {
            mBalance = wallet.getBalance();
            mBalanceAmount = mBalance.getSubscriber();
            updateBalance(mBalanceAmount, mMinimumRecharge);
        }
    }

    private void updateTokenBalance(TopupInfo topupInfo) {
        if (topupInfo != null && topupInfo.getData() != null) {
            TopupInfoData data = topupInfo.getData();
            mMinimumRecharge = data.getOpenRangeMinimumAmountAirfoxCurrency().doubleValue();
            updateBalance(mBalanceAmount, mMinimumRecharge);
        }
    }

    private void updateBalance(Double balance, Double maximum) {
        if (mProgressText != null && mProgressBar != null && mBalance != null && balance != null
                && maximum != null) {
            // progress bar
            mProgressBar.setMax(maximum.floatValue());
            float maxValue = (float) Math.min(balance, maximum);
            ProgressBarAnimation anim = new ProgressBarAnimation(mProgressBar, 0f, maxValue);
            anim.setDuration(2000);
            mProgressBar.startAnimation(anim);

            // balance text
            String currency = mBalance != null ? mBalance.getCurrencyShort() : "";
            String amount = TOKEN_CURRENCY.equals(currency) ?
                    WalletHelper.formatAmountLocale(maximum, null) :
                    WalletHelper.formatAmountLocaleTwoDigits(maximum,null);
            mProgressText.setText(amount);
        }
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);

        WalletViewModel walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        walletViewModel.getWallet().observe(this, mWalletObserver);

        TopupViewModel topupViewModel = ViewModelProviders.of(this).get(TopupViewModel.class);
        String phoneNumber = AirFoxBrowser.getPhoneNumber();
        topupViewModel.getTopupInfo(phoneNumber).observe(this, mTopupObserver);

        ActionBar actionBar = getSupportActionBar();
        View airFoxButton = actionBar.getCustomView().findViewById(R.id.airfox_button);
        if (airFoxButton != null) airFoxButton.setOnClickListener(view -> {
            Intent intent = new Intent(AirFoxBrowserActivity.this, AirFoxMainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void updateProgress(int n) {
        super.updateProgress(n);

        if (! mLoading && n > 0 && n < 100) {
            // start loading a page
            Logger.i(Logger.LOG_TAG, "Start loading page");
            mLoading = true;
        } else if (mLoading && n == 100) {
            // we're done loading -> let's show an ad
            Logger.i(Logger.LOG_TAG, "Finished loading page");
            BrowserMoment.showAd(this);
            mLoading = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }
}
