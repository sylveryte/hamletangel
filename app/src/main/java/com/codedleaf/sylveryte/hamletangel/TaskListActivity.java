package com.codedleaf.sylveryte.hamletangel;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class TaskListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragent() {
        Fragment fragment=new EditTaskFragment();
        Bundle bundle=new Bundle();

        HamletTask task=new HamletTask();
        task.setTaskText("wallah");
        task.setDifficulty(HamletTask.HARD);

        AngelLab.getAngelLab(getApplicationContext()).addTask(task);

        task.setNotes("meri bhais ko andi");


        bundle.putString(HamletTask.ID,task.getId().toString());
        fragment.setArguments(bundle);

        return fragment;
    }
}
