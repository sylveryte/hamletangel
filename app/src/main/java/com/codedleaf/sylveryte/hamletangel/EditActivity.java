package com.codedleaf.sylveryte.hamletangel;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by sylveryte on 25/3/18.
 * Yay!
 */

public class EditActivity extends SingleFragmentActivity {

    public static String UUID_STRING_CODE_NAME="UUidStringCodeName";

    @Override
    protected Fragment createFragent() {
        return new EditTaskFragment();

    }
}
