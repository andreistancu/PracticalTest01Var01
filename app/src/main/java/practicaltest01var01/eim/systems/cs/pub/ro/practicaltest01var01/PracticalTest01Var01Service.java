package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

public class PracticalTest01Var01Service extends Service {

    ProcessingThread pt;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        String indications = extras.getString(Constants.GIVEN_ORIENTATIONS);
        pt = new ProcessingThread(getApplicationContext(), indications);

        pt.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        pt.stopThread();
    }


    private class ProcessingThread extends Thread {

        private Context context = null;
        private boolean isRunning = true;

        private String indications;


        public ProcessingThread(Context context, String indications) {
            this.context = context;

            indications = indications;
        }

        public void run() {
            Log.d("[processing thread]", "Thread has started");
            while (isRunning) {
                sendMessage();
                sleep();
            }
            Log.d("[processing thread]", "Thread has stopped");
        }

        private void sendMessage() {
            Intent intent = new Intent();
            //intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
            intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + indications);
            context.sendBroadcast(intent);
        }

        private void sleep() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        public void stopThread() {
            isRunning = false;
        }
    }
}
