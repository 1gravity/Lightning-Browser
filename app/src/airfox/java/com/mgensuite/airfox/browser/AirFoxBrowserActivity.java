package com.mgensuite.airfox.browser;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mgensuite.airfoxsdk.AirFoxModule;
import com.mgensuite.datalayer.TrackingObserver;
import com.mgensuite.datalayer.model.topup.TopupInfo;
import com.mgensuite.datalayer.model.topup.TopupInfoData;
import com.mgensuite.datalayer.model.wallet.Balance;
import com.mgensuite.datalayer.model.wallet.Wallet;
import com.mgensuite.datalayer.viewmodel.Resource;
import com.mgensuite.datalayer.viewmodel.TopupViewModel;
import com.mgensuite.datalayer.viewmodel.WalletViewModel;
import com.mgensuite.datalayer.wallet.WalletHelper;
import com.mgensuite.datalayer.wallet.WalletProvider;
import com.mgensuite.sdk.core.util.Logger;
import com.mgensuite.sdk.core.util.MainThreadUtil;
import com.mgensuite.sdk.core.util.PreferenceUtil;

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

    private TrackingObserver<Resource<Wallet>> mWalletObserver = new TrackingObserver<Resource<Wallet>>() {
        @Override
        public void onChanged(@Nullable final Resource<Wallet> resource) {
            switch (resource.getStatus()) {
                case SUCCESS:
                    MainThreadUtil.post(() -> updateTokenBalance(resource.getData()));
                    setHasChanged();
                    break;
                case ERROR:
                    String errorMsg = getString(com.mgensuite.airfoxsdk.R.string.airfox_account_request_failed);
                    //showErrorMessage(errorMsg);
                    break;
                case LOGGED_OUT:
                    //AirFoxModule.logout();
                    break;
            }
        }
    };


    private TrackingObserver<Resource<TopupInfo>> mTopupObserver =
            new TrackingObserver<Resource<TopupInfo>>() {
                @Override
                public void onChanged(@Nullable Resource<TopupInfo> resource) {
                    switch (resource.getStatus()) {
                        case SUCCESS:
                            MainThreadUtil.post(() -> updateTokenBalance(resource.getData()));
                            setHasChanged();
                            break;
                        case ERROR:
                            String errorMsg = getString(com.mgensuite.airfoxsdk.R.string.topup_info_request_failed);
//                            showErrorMessage(errorMsg);
                            break;
                        case LOGGED_OUT:
//                            AirFoxModule.logout();
                            break;
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
            mProgressBar.setProgress(balance.floatValue());

            // balance text
            String currency = mBalance != null ? mBalance.getCurrencyShort() : "";
            String amount = TOKEN_CURRENCY.equals(currency) ?
                    WalletHelper.formatAmountLocale(balance, null) :
                    WalletHelper.formatAmountLocaleTwoDigits(balance,null);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);

        WalletViewModel walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        walletViewModel.getWallet().observe(this, mWalletObserver);

        TopupViewModel topupViewModel = ViewModelProviders.of(this).get(TopupViewModel.class);
        String phoneNumber = AirFoxBrowser.getPhoneNumber();
        topupViewModel.getTopupInfo(phoneNumber).observe(this, mTopupObserver);
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
