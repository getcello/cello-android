### Cello for Android

The Cello SDK for Android enables you to add sharing into your Android app. With a plug-n-play mobile sharing component, your users can easily share their invite link with their friends and network using mobile sharing options convenient for them.

Note, that you will still need to provide access to full featured Cello Referral Component through your web app for your users to be able to claim their rewards

## Installation

You can install Cello for Android using Gradle or manually. A basic installation takes around 15 minutes but will take a little longer if you want to customize the way the Cello Referral Component is launched.

To install Cello for Android, you will need an API key, which you will be provided during onboarding.

### Compatibility

Cello SDK for Android is compatible with API 21 and up.

### Setup

Install Cello to see and give your users the option to spread the word from your iOS app. Cello for Android supports API 21 and above.

Note: We recommend using the latest available `compileSdkVersion`.

### Install Cello

Add the following dependency to your app's build.gradle file:

```kotlin
dependencies {     
  implementation("so.cello.android:cello-sdk:0.1.0") 
}
```

```groovy
dependencies {
    implementation 'so.cello.android:cello-sdk:0.1.0'
}
```

### Maven Central

Cello is hosted on maven central. You will need to add maven central to your root `build.gradle` file.

```kotlin
allprojects {
    repositories {
      mavenCentral()
    }
}
```

## Example app

There are example sample app provided [here](https://github.com/getcello/cello-android/tree/master/sample).

## React Native Support

Looking for React Native support? We have a [React Native Plugin](https://github.com/getcello/cello-react-native) for Cello Referral component 🎉