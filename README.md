AppHypeSDK
==========

# About AppHype Ad Sample of TicTacToe

1. This sample show how can easily we can integrate AppHype Ad SDK in our existing Game.
2. In This sample we have integrated AppHype SDK in TicTacToe Game.
3. In This game we are shoing AppHype FullScreen as well as Video Ad as per game request.

# Running Ad Sample

1. [Register](http://apphype.shephertz.com/login) with AppHype platform.
2. If you are already registered, login to [AppHype] (http://apphype.shephertz.com/login/index).
3. 3. After you have successfully logged in, create an Android App by entering app details.
4. Download  AppHype Android [SDK] (https://github.com/VishnuGShephertz/AppHypeSDK/tree/AppHype-Version-1.0/archive/master.zip)
5. Import the Sample Application in Eclipse from SDK.
5. Open the GameActivity.java file of sample project and make the following changes.

```
A. Replace Apphype-Api-Keys and Apphype-Secret-Keys that you have received in step 2 or 3 at line number 34 and 35.

```
6. Build your Android Application and run it on your device.
7. Now, you will be able to see Ads in your Sample Application by making an Ad request

# Android AppHype SDK Integration

_
__1 Download AppHype [SDK] (https://github.com/VishnuGShephertz/AppHypeSDK/tree/AppHype-Version-1.0/archive/master.zip)__


__2 Modify Android Manifest__ Chnage the Application package name with the App Package you have created on AppHype Console on Above Step.
Add permissions 
```
 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

Add Activities

```
  <activity android:name="com.shephertz.android.apphype.sdk.InterstitialAdActivity" android:configChanges="keyboardHidden|orientation|screenSize|smallestScreenSize" />
        <activity android:name="com.shephertz.android.apphype.sdk.VideoAdActivity" android:screenOrientation="landscape"
             android:configChanges="keyboardHidden|orientation|screenSize|smallestScreenSize" />
```
Add Receiver

```
 <receiver android:name="com.shephertz.android.apphype.sdk.AppHypeReceiver">
            <intent-filter>
                <data android:scheme="package"/>
                <action android:name="android.intent.action.PACKAGE_ADDED"/>
            </intent-filter>
        </receiver>
```

__3 Intialize AppHype SDK__ At your Launcher Activty intialize AppHype SDK by providing your Api and Secret key.If you want to receive a CallBack event implement AppHypeListener during intialization 
```
AppHype
			.intialize(
					this,
					"Apphype Api Key",
					"Apphype Secret Key");
```


__4 Enable Logs__ While integrating AppHype Sdk you can also enable Sdk logs.

```
AppHype.enableLogs();

```
__5 Set AppHypeListener__ AppHype allow to handle callback event by adding AppHypeListener.

```
AppHype.setAppHypeListener(appHypeLister);

```

__7 Restrict Ad in Application__ You can also set maximum no. of application launch till you don’t want any Ad. This is an interesting feature to engage users in your app.
```
AppHype.restrictAd(restricLaunch);

```

__8 Load Ad__ You can request Ad by using the following code.

```
AppHype.preLoadAd(AdCode.Interstitial);
AppHype.preLoadAd(AdCode.Video);

```
__9 Show Ad__ If you want to show it on an event then you can use the following code.

```
  if(AppHype.isAvailable(AdCode.Interstitial))
		AppHype.showAd(activity,AdCode.Interstitial
		if(AppHype.isAvailable(AdCode.Video))
		AppHype.showAd(activity,AdCode.Video);
				
```
__10 Close Ad__  If you want to close this by using Api you can use following code.

```

	AppHype.closeAd();
				
```

			
__11 Handling AppHype Callback Events__ If you want to track an event or a message from SDK, you can add AppHypeLisener and gets callBack in following method.
``` 
    public interface AppHypeListener
   //Callback when Ad is shown
    public void onShow(String paramString);

    //Callback when Ad is hide
        public void onHide(String paramString);

   //Callback when Ad is Failed to show
        public void onFailedToShow(String paramString);

     //Callback when Ad is Available and you can call show function to implement Auto Show here
        public void onAdAvailable(String paramString);

    //Callback when Ad Failed to Load
        public abstract void onFailedToLoad(String paramString);

    //CallBack when there is SDK integration errors
        public void onIntegrationError(String error);
}
				
```



