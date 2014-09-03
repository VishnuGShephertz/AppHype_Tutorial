AppHypeSDK
==========

# About AppHype Ad Sample of TicTacToe

1. This sample will show how easily a developer can integrate AppHype SDK in his existing application
2. Here we have integrated AppHype SDK in TicTacToe Game
3. AppHype opens an easy gateway for Android developers to serve a quality Video & FullScreen Ads
4. Offers a cross promotional solution to the developer by showcasing Ads to his app user
5. Read complete [API Documentation](http://apphype.shephertz.com/docs) on AppHype
6. A complete [Turtorial](http://apphype.shephertz.com/tutorial-android), To integrate it in your existing Android application

# Running Ad Sample

1. [Register](http://apphype.shephertz.com/login) with AppHype platform
2. If you are already registered, login to [AppHype] (http://apphype.shephertz.com/login/index).
3. After successfully logging in, create an Android App by entering app details
4. Download  AppHype Android [SDK] (https://github.com/VishnuGShephertz/AppHypeSDK/tree/AppHype-Version-1.0/archive/master.zip)
5. Import the Sample Application in Eclipse from SDK
6. Open the GameActivity.java file of sample project and make the following changes

```
A. Replace Apphype-Api-Keys and Apphype-Secret-Keys that you have received

```
# Running  Sample

1. [Register/Login](http://apphype.shephertz.com/login) to use AppHype
2. After signing up, create App(s) that you want to promote by submitting App's package name on  [Create App ](http://apphype.shephertz.com/app/apps#/addApp)page
3. Create [Cross Promotion Campaign ](http://apphype.shephertz.com/app/apps#/createPromo)of the added App(s) to promote it in other App(s) 
4. Now, create another App(s) by adding it on [Create App ](http://apphype.shephertz.com/app/apps#/addApp)in which you wish to cross promote
5. You will get [Application Keys](http://apphype.shephertz.com/app/apps#/all) after App creation for SDK integration, which will be needed to initialize AppHype SDK
6.  [Download Tutorial] (https://github.com/VishnuGShephertz/AppHype_Tutorial/archive/master.zip) with Sample Application
7. Import application in your IDE e.g Eclipse
8. Change package name of Application with your application package name created in step 4 
9. Put your API and Secret Key of the App created in step 4 in GameActivity.java file, generated in step 5
```
A. Replace Apphype-Api-Keys and Apphype-Secret-Keys that you have received

```

10. Build your Android application and install it in your device
11. After Game ends you will be able to see AppHype Ad

# To use AppHype SDK in existing Android Application

1. Download  [AppHype Android SDK] (https://github.com/VishnuGShephertz/AppHypeSDK/archive/AppHype-Version-1.0.zip)

2. Add apphype.jar and android-support-v4.jar in your application

3. In AndroidManifest.xml file, package name of your App and the App added on AppHype in which you wish to cross promote should be same

4.Copy the code given below in your AndroidManifest.xml


```
		/Add Android Permissions
		<uses-permission android:name="android.permission.INTERNET"/> 
                <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
                <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/> 
                <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
	
         //Add Android Activities
    <activity android:name="com.shephertz.android.apphype.sdk.InterstitialAdActivity						android:configChanges="keyboardHidden|orientation|screenSize|smallestScreenSize"/>
    <activity android:name="com.shephertz.android.apphype.sdk.VideoAdActivity"
     android:configChanges="keyboardHidden|orientation|screenSize|smallestScreenSize"
    android:screenOrientation="landscape" />
	
    //Add Android Receiver
    <receiver android:name="com.shephertz.android.apphype.sdk.AppHypeReceiver">
        <intent-filter>
            <data android:scheme="package" />
            <action android:name="android.intent.action.PACKAGE_ADDED" />
        </intent-filter>
    </receiver>
```


5. Initialize AppHype SDK with the application Keys of the App in which you are cross promoting
```
AppHype.intialize("Android application context","Apphype Api Key","Apphype Secret Key");
```

6. To enable logs in application

```
AppHype.enableLogs();

```
7. To handle callBack events from AppHype SDK developer should set AppHypeListener

```
AppHype.setAppHypeListener(appHypeLister);

```

8. Developer can put restrictions on when to show ads in App(s)
```
AppHype.restrictAd(restricLaunch);

```

9. To show ads in application, developer has to preLoad them e.g Video or Interstitial

```
AppHype.preLoadAd(AdCode.Interstitial);
AppHype.preLoadAd(AdCode.Video);

```
10. Developer can show ads in application only if, they are available.

```
  if(AppHype.isAvailable(AdCode.Interstitial))
		AppHype.showAd(activity,AdCode.Interstitial
		if(AppHype.isAvailable(AdCode.Video))
		AppHype.showAd(activity,AdCode.Video);
				
```
11. Developer can close Ad with API as well

```
	AppHype.closeAd();
```

			
12. To track an event or a message from SDK, you can add AppHypeLisener and gets callBack in following method
``` 
    public interface AppHypeListener
   //Callback when Ad is shown
    public void onShow(AdCode adCode);

    //Callback when Ad is hide
        public void onHide(AdCode adCode);

   //Callback when Ad is Failed to show
        public void onFailedToShow(AppHypeException appHypeEx);

     //Callback when Ad is Available and you can call show function to implement Auto Show here
        public void onAdAvailable(AdCode adCode);

    //Callback when Ad Failed to Load
        public abstract void onFailedToLoad(AppHypeException appHypeEx);

    //CallBack when there is SDK integration exception
        public void onIntegrationError(AppHypeException appHypeEx);
}
				
```
