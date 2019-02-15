# circle-progress-ad-android
A small Android library allowing you to have a smooth and customizable circular  ProgressBar like whatsapp and Tez app, it can be use while uploding or downloading file.

I decided to do this because I was really tired to find progressbar like whatsapp and gradient color progressbar and also you can contribute more color style, or new idea to me.

I took reference or take some code from [CircleProgress](https://github.com/lzyzsd/CircleProgress) and change it as i need.also add code for set vector image as background and gradient colors.

![AdProgressBar](https://github.com/Adilhusen/circle-progress-ad-android-/blob/master/app/src/main/res/drawable/demo_gif.gif)


## Integration
Gradle:

```gradle
implementation 'ad:1.0.0'
```

## Usage

You can either simply use the [`AdCircleProgress`](https://github.com/Adilhusen/circle-progress-ad-android-/blob/master/adprogressbarlib/src/main/java/com/app/adprogressbarlib/AdCircleProgress.java) widge from this library on a regular `ProgressBar`.

### Using Widget

Simply replace your `ProgressBar` with `AdCircleProgress`, and remember to apply corresponding style and attribute for correct behavior.

For example, to create a first `AdCircleProgress` like whatsapp:



```xml
<com.app.adprogressbarlib.AdCircleProgress
        android:id="@+id/pgb_progress"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_alignParentTop="true"
        android:backgroundTintMode="add"
        app:adpgb_finished_color="@color/colorAccent"
        app:adpgb_unfinished_color="@android:color/white"
        custom:adpgb_finished_stroke_width="5dp"
        custom:adpgb_inner_drawable="@drawable/ic_close_icon"
        android:layout_margin="20dp"
        custom:adpgb_show_text="false"
        custom:adpgb_unfinished_stroke_width="5dp" />
```

Second `AdCircleProgress` in which you can change `adpgb_finished_stroke_width` like below code:

```xml
<com.app.adprogressbarlib.AdCircleProgress
        android:id="@+id/pgb_progress2"
        android:layout_width="60dp"
        android:layout_margin="20dp"
        android:layout_height="60dp"
        android:layout_toRightOf="@id/pgb_progress"
        android:backgroundTintMode="add"
        app:adpgb_finished_color="@color/colorPrimary"
        app:adpgb_progress="30"
        app:adpgb_background_color="@android:color/transparent"
        app:adpgb_unfinished_color="@android:color/white"
        custom:adpgb_finished_stroke_width="10dp"
        custom:adpgb_show_text="false"
        custom:adpgb_unfinished_stroke_width="5dp" />
```


Third `AdCircleProgress` in which you can change `adpgb_inner_drawable` means you can change image or vector image in inner circle like below code:

   ```xml
<com.app.adprogressbarlib.AdCircleProgress
        android:id="@+id/pgb_progress3"
        android:layout_width="60dp"
        android:layout_margin="20dp"
        android:layout_height="60dp"
        app:adpgb_background_color="@android:color/holo_red_dark"
        android:layout_toRightOf="@id/pgb_progress2"
        android:backgroundTintMode="add"
        app:adpgb_finished_color="@android:color/white"
        app:adpgb_unfinished_color="@android:color/holo_red_dark"
        custom:adpgb_finished_stroke_width="5dp"
        custom:adpgb_inner_drawable="@drawable/ic_delete_icon"
        custom:adpgb_show_text="false"
        custom:adpgb_unfinished_stroke_width="5dp" />
```




