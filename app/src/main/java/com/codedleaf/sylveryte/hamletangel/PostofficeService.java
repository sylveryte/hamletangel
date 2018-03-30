package com.codedleaf.sylveryte.hamletangel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PostofficeService extends Service {
    PostOffice mPostOffice;
    public PostofficeService() {
        mPostOffice=new PostOffice(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mPostOffice.getIBinder();
    }
}
