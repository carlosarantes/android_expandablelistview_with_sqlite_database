package com.helpers;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;

public class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {
    
	//---------------------------------------------------------------------------------
	int progress_status;
	ProgressBar bar;
	
	public void setProgressBar(ProgressBar bar) {
	    this.bar = bar;
	}
	//---------------------------------------------------------------------------------
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress_status = 0;
        bar.setVisibility(0);
        bar.setProgress(progress_status);
      
    }
   //---------------------------------------------------------------------------------
    @Override
    protected Void doInBackground(Void... params) {
        while (progress_status < 100){
            progress_status += 2;
            publishProgress(progress_status);
            SystemClock.sleep(18);
        }
        return null;
    }
   //---------------------------------------------------------------------------------
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.bar != null) {
            bar.setProgress(values[0]);
        }
    }
   //---------------------------------------------------------------------------------
    @Override
    protected void onPostExecute(Void result) {
    	bar.setProgress(0);
    	bar.setVisibility(4);
        super.onPostExecute(result);
    }
  //---------------------------------------------------------------------------------
}