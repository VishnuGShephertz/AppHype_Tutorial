App42-Tic-Tac-Toe
===========================

# About Game

1. It’s a multi-player turn based game.
2. The winning motive of the game is to connect three respective cross or circle in a vertical, horizontal or diagonal direction.
3. When opponent played his turn, a push notification is being sent to other opponent about his turn.

# Running Sample

This is a sample Android gaming app is made by using App42 backend platform. It uses User, Storage, Push Notification and Social APIs of App42 platform. 
Here are the few easy steps to run this sample app.


1. [Register] (https://apphq.shephertz.com/register) with App42 platform.
2. Create an app once you are on Quick start page after registration.
3. If you are already registered, login to [AppHQ] (http://apphq.shephertz.com) console and create an app from App Manager Tab.
4. [To use Push Notification see video] (http://www.youtube.com/watch?feature=player_embedded&v=4FtpoRkPuPo).
5. To use push Notification service in your application go to https://code.google.com/apis/console,create a new project here.
6. Click on services option in Google console and enable Google Cloud Messaging for Android service.
7. Click on API Access tab and create a new server key for your application with blank server information.
8. Go to AppHQ console and click on Push Notification and select Android setting in Settings option.
9. Select your app and copy server key that is generated by using Google api console, and submit it.
10. Download the eclipse project from this repo and import it in the same.
11. Open Constants.java file in sample app and give the value of app42APIkey app42SecretKey that you have received in step 2 or 3.
12. Enter your Google Project Number in Constant.java at line # 53 in Sender variable.
13. [Download Facebook SDK] (https://github.com/facebook/facebook-android-sdk) and add it as a library project in your application.
14. If you want to use your own facebook app, you have to create it in facebook developer console and modify FB_APP_ID variable in Constants.java file to use your own facebook app id. 
15. Build and Run 



# Design Details:

__Initialize Services:__ First you have to initialize all APP42 Services that you are going to use in your application.

Initialization has been done in AsyncApp42ServiceApi.java

```
        		ServiceAPI sp = new ServiceAPI(Constants.App42ApiKey,
  				Constants.App42ApiSecret);
				this.userService = sp.buildUserService();
				this.storageService = sp.buildStorageService();
				this.pushService = sp.buildPushNotificationService();
				this.socialService=sp.buildSocialService();
```

_Get Face-book Friends:__ You have to use Social Service API to get all face-book friends.

This has been done in  AsyncApp42ServiceApi.java

```
                	Social linkObj = socialService.linkUserFacebookAccount(userID,	accessToken);
		   			Social socialObj = socialService.getFacebookFriendsFromLinkUser(userID);
		       		final ArrayList<Friends> friendList =socialObj.getFriendList();
```

__Register User:__ First register yourself using our User Service API.

 User registration has been done in AsyncApp42ServiceApi.java

```
            	User user = userService.createUser(name, pswd, email);
```
__Authenticate User:__ If you already registered with App42 than use User Service API for authentication.

  User Authentication has been done in AsyncApp42ServiceApi.java

```
           		App42Response response = userService.authenticate(
							name, pswd);
```
__Push Service registration :__ To get Push Notification you have to register your device on APP42 using PushNotification Service API.

Device Registration is done in AsyncApp42ServiceApi.java

```
      	  public void registerForPushNotification(Context context,final String userID) {
			GCMRegistrar.checkDevice(context);
			GCMRegistrar.checkManifest(context);
			final String deviceId = GCMRegistrar.getRegistrationId(context);
			if (deviceId.equals("")) {
				GCMRegistrar.register(context, Constants.SenderId);
			} else {
				final Handler callerThreadHandler = new Handler();
				new Thread() {
					@Override
					public void run() {
						try {
							ServiceAPI sp = new ServiceAPI(
									Constants.App42ApiKey,
									Constants.App42ApiSecret);
							String userName = Constants.GameName+ userID;
							pushService.storeDeviceToken(userName, deviceId);
							callerThreadHandler.post(new Runnable() {
								@Override
								public void run() {

								}
							});
						} catch (Exception e) {

						}
					}
				}.start();
			}
	}
```
__Create Game with face-book friends:__ To challenge your friend to play game with you, you have to create a game using our Storage Service API.

Game creation has been done in AsyncApp42ServiceApi.java
 
```
		public void createGameWithFbFriend(final String friendId,final String friendName,final String frindPicUrl,
			final FriendList callBack){

			final Handler callerThreadHandler = new Handler();
			new Thread() {
				@Override
				public void run() {
					try {
						final JSONObject gameObject = new JSONObject();
						gameObject.put(Constants.GameFirstUserKey, UserContext.MyUserName);
						gameObject.put(Constants.GameSecondUserKey, friendId);
					
						gameObject.put(Constants.GameFbName, UserContext.MyDisplayName);
						gameObject.put(Constants.GameFbFriendName, friendName);
					
						gameObject.put(Constants.GameMyPicUrl, UserContext.MyPicUrl);
						gameObject.put(Constants.GameFriendPicUrl, frindPicUrl);
					
						gameObject.put(Constants.GameStateKey,
							Constants.GameStateIdle);
						gameObject.put(Constants.GameBoardKey,Constants.GameIdleState);
						gameObject.put(Constants.GameWinnerKey, "");
						gameObject.put(Constants.GameNextMoveKey, friendId);
		
						gameObject.put(Constants.GameIdKey, java.util.UUID
								.randomUUID().toString());
						storageService.insertJSONDocument(
								Constants.App42DBName,
								Constants.App42UserGamesCollectionPrefix
										+ UserContext.MyUserName, gameObject.toString());
						storageService
								.insertJSONDocument(
										Constants.App42DBName,
										Constants.App42UserGamesCollectionPrefix
												+ friendId,
										gameObject.toString());


						callerThreadHandler.post(new Runnable() {
							@Override
							public void run() {
								callBack.onCreateGame(true,gameObject,friendId);
							}
						});

					} catch (Exception e) {
						callerThreadHandler.post(new Runnable() {
							@Override
							public void run() {
								if (callBack != null) {
									callBack.onCreateGame(false,null,null);
								}
							}
						});
					}
				}
			}.start();
	
		}
```
__Create Game:__ While starting a new game with opponent you have to create a game using our Storage Service API.
 
 Game creation has been done in AsyncApp42ServiceApi.java

```
                    final JSONObject gameObject = new JSONObject();
					gameObject.put(Constants.GameFirstUserKey, uname1);
					gameObject.put(Constants.GameSecondUserKey, remoteUserName);
					gameObject.put(Constants.GameStateKey,
							Constants.GameStateIdle);
					gameObject.put(Constants.GameBoardKey,
							Constants.GameIdleState);
					gameObject.put(Constants.GameWinnerKey, "");
					gameObject.put(Constants.GameNextMoveKey, uname1);
					gameObject.put(Constants.GameIdKey, java.util.UUID
							.randomUUID().toString());

					// Insert in to user1's game collection
					storageService.insertJSONDocument(Constants.App42DBName,
							Constants.App42UserGamesCollectionPrefix + uname1,
							gameObject.toString());
					// Insert in to user2's game collection
					storageService.insertJSONDocument(Constants.App42DBName,
							Constants.App42UserGamesCollectionPrefix
									+ remoteUserName, gameObject.toString());
```
 

__Update Game:__ While playing game , you have to use our Storage Service API to update the game.
 
 Game updating has been done in AsyncApp42ServiceApi.java
```
                    final JSONObject gameObject = new JSONObject();
					gameObject.put(Constants.GameFirstUserKey, uname1);
					gameObject.put(Constants.GameSecondUserKey, remoteUserName);
					gameObject.put(Constants.GameStateKey,
							Constants.GameStateIdle);
					gameObject.put(Constants.GameBoardKey,
							Constants.GameIdleState);
					gameObject.put(Constants.GameWinnerKey, "");
					gameObject.put(Constants.GameNextMoveKey, uname1);
					gameObject.put(Constants.GameIdKey, java.util.UUID
							.randomUUID().toString());

					// Insert in to user1's game collection
					storageService.insertJSONDocument(Constants.App42DBName,
							Constants.App42UserGamesCollectionPrefix + uname1,
							gameObject.toString());
					// Insert in to user2's game collection
					storageService.insertJSONDocument(Constants.App42DBName,
							Constants.App42UserGamesCollectionPrefix
									+ remoteUserName, gameObject.toString());
```

__Push message:__ You have to use our PushNotification Service API in following scenarios.

 Whenever a game is created with friend or APP42 user you have to send notification to opponent.
 When Game is running, once you have played your turn you have to send Push Notification to your opponent that now its his turn.
 
 Push message has been sent in AsyncApp42ServiceApi.java

```
            		pushService.sendPushMessageToUser(Constants.GameName
							+ userName, newGameObj.toString());
```
