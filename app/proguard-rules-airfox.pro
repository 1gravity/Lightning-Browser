-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class * extends android.preference.Preference {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
}

# Keep SafeParcelable value, needed for reflection. This is required to support backwards
# compatibility of some classes.
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

# Keep the names of classes/members we need for client functionality.
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes EnclosingMethod

# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# library-core
# this is needed for the upgrade mechanism to work
-keepclasseswithmembers class com.mgensuite.sdk.core.AppInstaller {*;}
-keepclasseswithmembers class android.content.pm.IPackageInstallObserver {*;}
-keepclasseswithmembers class android.content.pm.IPackageDeleteObserver {*;}

-keep class com.mgensuite.sdk.core.util.security.CredentialManager {
  public *;
  public static *;
}

-keep interface com.mgensuite.sdk.core.api.AirFoxMobileApi {*;}
-keep interface com.mgensuite.sdk.core.api.AirFoxMobileApi$SubscriptionCallback {*;}
-keep interface com.mgensuite.sdk.core.api.AirFoxMobileApi$AuthTokenCallback {*;}
-keep class com.mgensuite.sdk.core.api.AirFoxMobileSdk {
  public protected *;
}
-keepnames class com.mgensuite.sdk.core.api.AirFoxMobileSdk {
  public static **[] api();
}
-keepnames class com.mgensuite.sdk.core.**
-keep class com.mgensuite.sdk.core.startup.AirFoxModuleIds {
  public protected *;
}
-keep class com.mgensuite.moments.ui.UnlockedEvent
-keepclassmembers class com.mgensuite.moments.ui.UnlockedEvent {*;}
-keep class com.mgensuite.sdk.core.LogoutEvent {
  public protected *;
}
-keep class com.mgensuite.sdk.core.LogoutException {
  public protected *;
}
-keep class com.mgensuite.sdk.core.startup.UnifiedModuleBuilder {
  public protected *;
}
-keep public class com.mgensuite.sdk.core.models.** {
  public protected *;
}
-keep public class com.mgensuite.sdk.core.ui.** {
  public protected *;
}
-keep public class com.mgensuite.sdk.core.update.** {
  public protected *;
}
-keep public class com.mgensuite.sdk.core.util.** {
  public protected *;
}
-keep public class com.mgensuite.sdk.core.util.dagger.*** {
  public protected *;
}

-keep class com.mgensuite.sdk.core.event.NetworkChangeReceiver {
  public protected *;
  public static **[] processConnectionChange();
}

#Interface definitions (former AIDL)
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable,*Annotation*
-keep interface com.mgensuite.sdk.core.api.I** {
  public protected *;
}
-keepclasseswithmembers class com.mgensuite.sdk.core.api.I$** {
  public protected *;
}

# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep public class * extends android.preference.Preference {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
}

# Keep SafeParcelable value, needed for reflection. This is required to support backwards
# compatibility of some classes.
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

# Keep the names of classes/members we need for client functionality.
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes EnclosingMethod
# Keep public classes and methods.
-keep class com.avocarrot.** { *; }
-dontwarn com.avocarrot.**
##---------------Begin: proguard configuration for Inneractive SDK  ----------

-keep class com.inneractive.api.ads.** {*;}
-keepclassmembers class com.inneractive.api.ads.** {*;}
-keepclassmembers class com.inneractive.api.ads.sdk.nativead.** {*;}

##---------------End: proguard configuration for Inneractive SDK  ----------


##---------------Begin: proguard configuration for Google play services  ----------

-dontwarn com.google.android.gms.**

# for Google play services – general – based on Google's documentation
-keep class * extends java.util.ListResourceBundle
{ protected Object[][] getContents(); }
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable
{ public static final *** NULL; }
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class *
{ @com.google.android.gms.common.annotation.KeepName *; }
-keepnames class * implements android.os.Parcelable
{ public static final ** CREATOR; }

# for google play services, to support Inneractive’s reflection
-keep class com.google.android.gms.common.GooglePlayServicesUtil
{ int isGooglePlayServicesAvailable (android.content.Context); }

-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient
{ static com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info
{ *; }
-keep class com.google.android.gms.common.GoogleApiAvailability
{ static com.google.android.gms.common.GoogleApiAvailability getInstance(); int isGooglePlayServicesAvailable(android.content.Context);}

##---------------End: proguard configuration for Google play services  ----------


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

-keep class com.google.gson.Gson { *; }
-keep class com.google.gson.GsonBuilder { *; }
-keep class com.google.gson.FieldNamingStrategy { *; }

##---------------End: proguard configuration for Gson  ----------
-keep public interface com.mgensuite.ads.AdModule {*;}
-keep public interface com.mgensuite.ads.AdModule$LoadListener {*;}
-keep public interface com.mgensuite.ads.AdModule$ShowListener {*;}
-keep class com.mgensuite.ads.AdFormat {
  public protected *;
}
-keep class com.mgensuite.ads.AdMoment {
  public protected *;
}
-keep class com.mgensuite.ads.AdModuleBuilder {
  public protected *;
}
-keep class com.mgensuite.ads.handler.notification.NotificationBuilder {
  public protected *;
}
-keep class com.mgensuite.ads.handler.notification.NotificationFormat {
  public protected *;
}
-keep class com.mgensuite.ads.handler.notification.Notification {
  public protected *;
}
-keep class com.mgensuite.ads.util.AdClickedEvent {
  public protected *;
}
-keep class com.noqoush.adfalcon.android.sdk.images.c {*;}
-keepclassmembers class com.noqoush.adfalcon.android.sdk.images.c {*;}
-keep public interface com.mgensuite.ads.util.AdFormatsAllowed {
  public protected *;
}
-keep public interface com.mgensuite.ads.util.RunOnUIThread {
  public protected *;
}

-keepclasseswithmembers class com.mgensuite.ads.config.** {*;}

# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

-keepclassmembers class * extends com.mgensuite.ads.handler.BaseHandler {
  public protected *;
}
# MoPub Proguard Config
# NOTE: You should also include the Android Proguard config found with the build tools:
# $ANDROID_HOME/tools/proguard/proguard-android.txt

# Keep public classes and methods.
-keepclassmembers class com.mopub.** { public *; }
-keep public class com.mopub.**
-keep public class android.webkit.JavascriptInterface {}

# Explicitly keep any custom event classes in any package.
-keep class * extends com.mopub.mobileads.CustomEventBanner {}
-keep class * extends com.mopub.mobileads.CustomEventInterstitial {}
-keep class * extends com.mopub.nativeads.CustomEventNative {}
-keepclassmembers class com.mopub.mobileads.CustomEventBannerAdapter {
    !private !public !protected *;
}

# Support for Android Advertiser ID.
-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {*;}

# Support for Google Play Services
# http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
# ---- PRESAGE - start

-dontnote io.presage.**
-dontwarn shared_presage.**
-dontwarn org.codehaus.**

-keepattributes Signature

-keep class shared_presage.** { *; }
-keep class io.presage.** { *; }
-keepclassmembers class io.presage.** {
 *;
}

-keepattributes *Annotation*
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# ---- OKHTTP
-dontnote okhttp3.**
-dontnote okio.**
-dontwarn okhttp3.**
-dontwarn okio.**

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-dontnote sun.misc.Unsafe
-dontnote android.net.http.*

-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

-dontwarn org.apache.commons.collections.BeanMap
-dontwarn java.beans.**

# ---- GOOGLE
-dontnote com.google.gson.**
-dontnote com.google.android.gms.ads.**
-dontnote com.google.android.**
-dontnote com.google.ads.**

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ---- PRESAGE - end

# Keep public classes and methods.
-keepattributes Signature
-keep class net.pubnative.** { *; }

-keep class com.mgensuite.airfoxsdk.AirFoxBuilder {*;}
-keep class com.mgensuite.airfoxsdk.AirFox {*;}
-keep class com.mgensuite.airfoxsdk.AirFoxStartupActivity {*;}

-keepnames class com.mgensuite.airfoxsdk.AirFoxModule
-keep interface com.mgensuite.airfoxsdk.AirFoxModule$Callback {*;}

-keep class com.mgensuite.airfoxsdk.signup.phonefield.Countries {*;}
-keep class com.mgensuite.airfoxsdk.signup.phonefield.Country {*;}

-keep public class com.mgensuite.airfoxsdk.models.** {
  public protected *;
}

# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keep class com.mgensuite.moments.service.FBMProcessor {
  public protected *;
}
-keep interface com.mgensuite.moments.Moments {*;}
-keep class com.mgensuite.moments.MomentsImpl {
  public protected *;
}
-keep class com.mgensuite.moments.MomentsModule {
  public protected *;
}
-keep class com.mgensuite.moments.MomentsInitializer {
  public protected *;
}
-keep class com.mgensuite.moments.MomentsConst {
  public protected *;
}

-keep class com.mgensuite.moments.ui.UnlockEvent {
  static public *;
}
-keep class com.mgensuite.moments.ui.UnlockEventIf {
  static public *;
}

# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*
-keep class com.fyber.mediation.annotations.** { *; }
-keep class com.fyber.annotations.** { *; }
-dontwarn com.fyber.mediation.annotations.**
-dontwarn com.fyber.annotations.**

# Google Advertising Id
-keep class com.google.android.gms.ads.identifier.** { *; }
-keep class me.kiip.** { *; }
-keepclassmembers class me.kiip.** {*;}
-keep interface com.mgensuite.datarewards.DataRewards {
  public *;
  public static *;
}
-keep class com.mgensuite.datarewards.DataRewardsImpl {
  public protected *;
  public static *;
}
-keep class com.mgensuite.datarewards.DataRewardsModule {
  public protected *;
  public static *;
}
-keep interface com.mgensuite.datarewards.DataRewardsModule$Callback {*;}
-keep interface com.mgensuite.datarewards.supersonic.OfferwallListener {*;}
-keep interface com.mgensuite.datarewards.RewardsVideoListener {*;}
-keep interface com.mgensuite.datarewards.RewardsOfferListener {*;}

-keep class com.mgensuite.datarewards.fragment.OfferWallFragment {*;}
-keepclasseswithmembers class com.mgensuite.datarewards.fragment.OfferWallFragment {
    *;
}
-keep class com.mgensuite.datarewards.util.Preferences {*;}
-keepclassmembers class com.mgensuite.datarewards.util.Preferences {*;}

-keepclasseswithmembers class com.mgensuite.datarewards.model.** {*;}

-keep class com.mgensuite.datarewards.util.Utils {
  public protected *;
  public static *;
}
-keep class com.mgensuite.datarewards.Balance {
  public protected *;
  public static *;
}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*
-dontwarn com.squareup.okhttp.**
-keepclassmembers class com.supersonicads.sdk.controller.SupersonicWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.supersonic.adapters.** { *;
}
-keepattributes InnerClasses
# JS Interfaces and other reflection/dynamic invokations are not
# properly tracked by obfuscation engine
# Prevent issues with Google GAID
-keep public class com.google.** {
    *;
}
# Prevent renaming/obfuscation of JS Interfaces,
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
