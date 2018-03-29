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
 * Yay!
 */

public class EditTaskFragment extends Fragment {
    public static final java.lang.String ARG_UUID_STRING_CODE = "UUID_STRING_COAD";
    private HamletTask mHamletTask;

    private EditText mTaskEditText;
    private EditText mNotes;
    private TextView mDifficultyLabel;
    private RatingBar mDifficultyBar;
    private Button mDate;

    private Boolean mNew;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mHamletTask=new HamletTask();

        String uuid_string=getArguments().getString(ARG_UUID_STRING_CODE);
        if(uuid_string!=null)
        {
            UUID uuid=UUID.fromString(uuid_string);
            mHamletTask=AngelLab.getAngelLab(getActivity()).getTaskCopyById(uuid);
            mNew=false;
        }
        else
        {
            mNew =true;
            mHamletTask=new HamletTask();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.add_task_layout,container,false);

        mTaskEditText = v.findViewById(R.id.task);
        mNotes= v.findViewById(R.id.notes);
        mDifficultyLabel=v.findViewById(R.id.duedate);
        mDifficultyBar=v.findViewById(R.id.difficulty_bar);
        mDate=v.findViewById(R.id.date);
        Button dateClear = v.findViewById(R.id.date_clear);
        Button addButton = v.findViewById(R.id.add);
        Button cancelButton = v.findViewById(R.id.cancel);
        Button deleteButton = v.findViewById(R.id.delete);

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

        dateClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHamletTask.setDate(null);
                updateUi();
            }
        });
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        if(!mNew)
        {
            addButton.setText(R.string.modify);
            deleteButton.setVisibility(View.VISIBLE);
        }
        addButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AngelLab.getAngelLab(getActivity()).addUpdateTask(mHamletTask);
                   getActivity().onBackPressed();
               }
           });
           cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Log.d("maan", "Just cancel it maaan");
                    getActivity().onBackPressed();
                }
            });

           deleteButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   AngelLab.getAngelLab(getActivity()).deleteTask(mHamletTask);
                   getActivity().onBackPressed();
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
        String date=mHamletTask.getDate();
        if(date==null)
            mDate.setText(R.string.setdate);
        else
            mDate.setText(date);
        mTaskEditText.setText(mHamletTask.getTaskText());
        mNotes.setText(mHamletTask.getNotes());
        mDifficultyBar.setRating(mHamletTask.getDifficulty());
        Log.d("maan","updated UIII");
    }
}
