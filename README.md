# BubbleLoader

![Alt Text](https://media.giphy.com/media/HWy4whdzO2dP9FCPcy/giphy.gif)


>Bubble loading library for android 


>Step 1. Add the to package level build.gradle



```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  
  Step 2. Add this dependency to app level build.gradle
  
  ```gradle
  dependencies {
          ...
	      implementation 'com.github.git-kishan:BubbleLoader:v1.0.0'
	}
  ```
  
  ```xml
  <com.kk.bubbleloader.BubbleLoader
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:duration_cycle="800"
        app:color="@color/black"
    /> 
   ```
   
   >duration_cycle : Integer (time in millisecond)
   
   >color : Color (color of bubble)
