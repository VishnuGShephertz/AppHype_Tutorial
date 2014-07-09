AppHypeSDK
==========

# About AppHype Ad Sample of TicTacToe

1. This sample show how can easily we can integrate AppHype Ad SDK in our existing Game.
2. In This sample we have integrated AppHype SDK in TicTacToe Game.
3. In This game we are shoing AppHype FullScreen as well as Video Ad as per game request.

# Running Ad Sample

1. [Register](http://50.112.109.96:8080/login) with AppHype platform.
2. If you are already registered, login to [AppHype] (http://50.112.109.96:8080/login/index).
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

__1 Download AppHype [SDK] (https://github.com/VishnuGShephertz/AppHypeSDK/tree/AppHype-Version-1.0/archive/master.zip)__

__2 Add AppHype library Jars__ Ad apphype.jar and android-support.jar file in your libs folder.

__3 Modify Android Manifest__ 
Add permissions 
```
 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

Add Activities

```
  <activity android:name="com.shephertz.android.apphype.sdk.FullScreenAdActivity" android:configChanges="keyboardHidden|orientation|screenSize|smallestScreenSize" />
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

__4 Intialize AppHype SDK__ At your Launcher Activty intialize AppHype SDK by providing your Api and Secret key.If you want to receive a CallBack event implement AppHypeListener during intialization 
```
AppHypeAPI
			.intialize(
					this,
					"<Apphype Api Keys>",
					"Apphype Secret Key");
```

__5 Enable Logs__ While integrating AppHype Sdk you can also enable Sdk logs.

```
AppHypeAPI.enableLogs();

```
__6 Set AppHypeListener__ AppHype allow to handle callback event by adding AppHypeListener.

```
AppHypeAPI.setAppHypeListener(appHypeLister);

```

__7 Reset\Remove AppHypeListener__ You can also remove calback appHypeListener by using following code.

```
AppHypeAPI.resetAppHypeListener();

```
__8 Set Max App Launch without Ad__ You can also set maximum no. of application launch till you don’t want any Ad. This is an interesting feature to engage users in your app.
```
AppHypeAPI.setLaunchNoAd(maxLaunch);

```

__9 FullScreen Ad__ You can request FullScreen Ad by using the following code.

```
AppHypeAPI.loadFullScreenAd();

```
Show FullScreen Ad: If you want to show it on an event then you can use the following code.

```
  if(AppHypeAPI.isFullScreenAvailable())
		AppHypeAPI.showFullScreenAd(activity);
				
```
Close FullScreen Ad: If you want to close this by using Api you can use following code.

```

	AppHypeAPI.closeFullScreenAd();
				
```
__10 Video Ad__ You can request Video Ad by using the following code.

```
AppHypeAPI.loadVideoAd()

```
Show Video Ad: If you want to show it on an event then you can use the following code.

```
   if(AppHypeAPI.isVideoAvailable())
		AppHypeAPI.showVideoAd(activity);
				
				
```
Close Video Ad: If you want to close this by using Api you can use following code.

```

	AppHypeAPI.closeVideoAd();
				
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



