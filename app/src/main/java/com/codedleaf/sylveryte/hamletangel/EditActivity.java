package com.codedleaf.sylveryte.hamletangel;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by sylveryte on 25/3/18.
 * Yay!
 */

public class EditActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragent() {
        Fragment fragment=new EditTaskFragment();

        Bundle bundle=new Bundle(1);
        bundle.putString(EditTaskFragment.ARG_UUID_STRING_CODE,getIntent().getStringExtra(EditTaskFragment.ARG_UUID_STRING_CODE));

        fragment.setArguments(bundle);

        return fragment;

    }
}
