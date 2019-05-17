package ru.ivadimn.servicerecivernotification;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WombleService extends IntentService {

    public WombleService() {
        super("WombleService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.aerosmith);
       mediaPlayer.start();
    }
}
