package com.codedleaf.sylveryte.hamletangel;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by sylveryte on 25/3/18.
 */

public class EditActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragent() {
        return new TaskListFragment();
    }
}
