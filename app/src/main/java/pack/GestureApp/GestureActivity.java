package pack.GestureApp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.gesture.*;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GestureActivity extends Activity {
    private GestureLibrary gLib;
    private static final String TAG = "GestureActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        openOptionsMenu();
        gLib = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "gesture.txt");
        gLib.load();

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(handleGestureListener);
        gestures.setGestureStrokeAngleThreshold(90.0f);
    }

    /**
     * our gesture listener
     */
    private OnGesturePerformedListener handleGestureListener = new OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView gestureView,
                                       Gesture gesture) {

            final ArrayList<Prediction> predictions = gLib.recognize(gesture);
            Log.d(TAG, "recog thayu");

            // one prediction needed
            if (predictions.size() > 0) {
                final Prediction prediction = predictions.get(0);
                // checking prediction
                if (prediction.score > 4) {
                    // and action


                        if(prediction.name.equalsIgnoreCase("dialer"))
                        {
                            Toast.makeText(GestureActivity.this, prediction.name,
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            startActivity(intent);
                        }
                        else if(prediction.name.equalsIgnoreCase("CAMERA"))
                        {
                            Toast.makeText(GestureActivity.this, prediction.name,
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivity(intent);
                        }
                        else if(prediction.name.equalsIgnoreCase("whatsapp")) {
                            Toast.makeText(GestureActivity.this, prediction.name,
                                    Toast.LENGTH_SHORT).show();
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                            startActivity(launchIntent);
                        }
                        else if(prediction.name.equalsIgnoreCase("instagram"))
                        {
                            Uri uri = Uri.parse("http://instagram.com/_u");


                            Intent i= new Intent(Intent.ACTION_VIEW,uri);

                            i.setPackage("com.instagram.android");

                            try {
                                Toast.makeText(GestureActivity.this, prediction.name,
                                        Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {

                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://instagram.com/xxx")));
                            }
                        }
                        else if(prediction.name.equalsIgnoreCase("gaana"))
                        {    Toast.makeText(GestureActivity.this, prediction.name,
                                Toast.LENGTH_SHORT).show();
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.gaana");
                            startActivity(launchIntent);
                        }
                        else if(prediction.name.equalsIgnoreCase("vitgo"))
                        {
                            Toast.makeText(GestureActivity.this, prediction.name,
                                    Toast.LENGTH_SHORT).show();
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.vitgo.mayankaggarwal.viteventsapp");
                            startActivity(launchIntent);
                        }
                        else if(prediction.name.equalsIgnoreCase("truecaller"))
                        {
                            String name= prediction.name;
                            String both ="com."+name ;
                            both=both.toLowerCase();
                            Toast.makeText(GestureActivity.this, prediction.name,
                                    Toast.LENGTH_SHORT).show();
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(both);
                            startActivity(launchIntent);
                        }
                        else
                        {
                            Toast.makeText(GestureActivity.this, "No such gesture",
                                    Toast.LENGTH_SHORT).show();
                        }
                       /* else
                        {
                            final PackageManager pm = getPackageManager();
//get a list of installed apps.
                            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

                            for (ApplicationInfo packageInfo : packages) {
                                Log.d(TAG, "Installed package :" + packageInfo.packageName);
                                Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
                                Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
                                final String packageName = "my.application.package";
                                PackageManager packageManager= getApplicationContext().getPackageManager();
                                try {
                                    String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                                    if(prediction.name.equalsIgnoreCase(appName))
                                    {
                                        Intent intent=pm.getLaunchIntentForPackage(packageName);
                                        startActivity(intent);
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }

                            }

                        }*/


                }
                else
                {

                    Toast.makeText(GestureActivity.this, "No such gesture defined",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(GestureActivity.this, "No such gesture defined",
                      Toast.LENGTH_SHORT).show();

            }
        }
    };
}