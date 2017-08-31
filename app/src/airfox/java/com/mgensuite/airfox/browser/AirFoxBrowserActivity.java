package com.mgensuite.airfox.browser;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mgensuite.datalayer.TrackingObserver;
import com.mgensuite.datalayer.model.wallet.Balance;
import com.mgensuite.datalayer.model.wallet.Wallet;
import com.mgensuite.datalayer.viewmodel.Resource;
import com.mgensuite.datalayer.viewmodel.WalletViewModel;
import com.mgensuite.datalayer.wallet.WalletHelper;
import com.mgensuite.datalayer.wallet.WalletProvider;
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

    private WalletViewModel mModel;
    private Balance mBalance;

    private TrackingObserver<Resource<Wallet>> mObserver = new TrackingObserver<Resource<Wallet>>() {
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

    @BindView(R.id.airfox_progress_bar) RoundCornerProgressBar mProgressBar;
    @BindView(R.id.airfox_progress_text) TextView mProgressText;

    public AirFoxBrowserActivity() {}

    private void updateTokenBalance(Wallet wallet) {
        if (mProgressText != null && wallet != null && wallet.getBalance() != null) {
            mBalance = wallet.getBalance();
            String currency = mBalance != null ? mBalance.getCurrencyShort() : "";
            if (wallet.getBalance().getSubscriber() != null) {
                Double balance = wallet.getBalance().getSubscriber();

                float progress = balance == null ? 0f : balance.floatValue();
                mProgressBar.setProgress(progress);

                String amount = TOKEN_CURRENCY.equals(currency) ?
                        WalletHelper.formatAmountLocale(balance, null) :
                        WalletHelper.formatAmountLocaleTwoDigits(balance,null);
                mProgressText.setText(amount);
            }
        }
    }

    public Double getBalance(WalletProvider.Type type) {
        if (mBalance != null) {
            return getBalance(mBalance, type);
        }

        try {
            Balance balance = mModel.getWallet().getValue().getData().getBalance();
            return getBalance(balance, type);
        } catch (Exception e) {
            Logger.e(Logger.LOG_TAG, "Can't retrieve balance", e);
        }
        return null;
    }

    private Double getBalance(Balance balance, WalletProvider.Type type) {
        switch (type) {
            case EARNED:
                return balance.getEarned();
            case CARRIER:
                return balance.getCarrier();
            case SUBSCRIBER:
                return balance.getSubscriber();
        }
        return null;
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

        mModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        mModel.getWallet().observe(this, mObserver);
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
