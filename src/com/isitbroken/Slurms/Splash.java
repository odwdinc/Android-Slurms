package com.isitbroken.Slurms;

import android.app.Activity;
import android.os.Bundle;

public class Splash extends Activity{
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.splash);
	      Thread splashThread = new Thread() {
	          @Override
	          public void run() {
	             try {
	                int waited = 0;
	                while (waited < 5000) {
	                   sleep(100);
	                   waited += 100;
	                }
	             } catch (InterruptedException e) {
	                // do nothing
	             } finally {
	                finish();
	             }
	          }
	       };
	       splashThread.start();
	   }

}
