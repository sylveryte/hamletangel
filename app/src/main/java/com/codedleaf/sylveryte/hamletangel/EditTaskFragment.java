package com.codedleaf.sylveryte.hamletangel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.UUID;


/**
 * Created by sylveryte on 25/3/18.
 */

public class EditTaskFragment extends Fragment {
    private HamletTask mHamletTask;
    private UUID mUUID;

    private EditText mTaskEditText;
    private EditText mNotes;
    private TextView mDifficultyLabel;
    private RatingBar mDifficultyBar;
    private Button mDate;
    private Button mAddButton;
    private Button mCancelButton;
    private Button mDateClear;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mHamletTask=new HamletTask();
        Log.d("maan","Creating new Hamlet");
        Bundle bundle=getArguments();
        mUUID=UUID.fromString((String)bundle.get(HamletTask.ID));
        mHamletTask=AngelLab.getAngelLab(getActivity()).getTaskById(mUUID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.add_task_layout,container,false);

        mTaskEditText = v.findViewById(R.id.task);
        mNotes= v.findViewById(R.id.notes);
        mDifficultyLabel=v.findViewById(R.id.difficulty_label);
        mDifficultyBar=v.findViewById(R.id.difficulty_bar);
        mDate=v.findViewById(R.id.date);
        mDateClear=v.findViewById(R.id.date_clear);
        mAddButton=v.findViewById(R.id.add);
        mCancelButton=v.findViewById(R.id.cancel);

        mTaskEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentional
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mHamletTask.setTaskText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentional
            }
        });
        mNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentional
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mHamletTask.setNotes(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentional
            }
        });
        mDifficultyBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                mHamletTask.setDifficulty((int)v);
                mDifficultyLabel.setText(mHamletTask.getDifficultyString());
            }
        });

        mDateClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHamletTask.setDate(null);
                updateUi();
            }
        });
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                DialogFragment newFragment = new DatePickerDialogFragment(this);
//                newFragment.show(ft, "dialog");

                final java.util.Calendar c = java.util.Calendar.getInstance();
                int year = c.get(java.util.Calendar.YEAR);
                int month = c.get(java.util.Calendar.MONTH);
                int day = c.get(java.util.Calendar.DAY_OF_MONTH);

                Dialog dialog =new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Log.d("maan","year : "+i+" month :"+i1+" day : "+i2);
                        mHamletTask.setDate(i2+"/"+i1+1+"/"+i);

                        mDate.setText(mHamletTask.getDate());
                        updateUi();
                    }
                },year,month,day);

                dialog.show();
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("maan",mHamletTask.toString());
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("maan","Just cancel it maaan");
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("maan",mHamletTask.toString()+"\nid="+mUUID.toString());
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("maan","saving");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("maan","just paused it maan");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
        Log.d("maan","just resumed it maan");
    }

    private void updateUi()
    {
        mDate.setText(mHamletTask.getDate());
        mTaskEditText.setText(mHamletTask.getTaskText());
        mNotes.setText(mHamletTask.getNotes());
        mDifficultyBar.setRating(mHamletTask.getDifficulty());
        Log.d("maan","updated UIII");
    }
}
