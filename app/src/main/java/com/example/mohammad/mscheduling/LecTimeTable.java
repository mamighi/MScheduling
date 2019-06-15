package com.example.mohammad.mscheduling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LecTimeTable.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LecTimeTable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LecTimeTable extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    //////////////////////////////////////////////////////////////////////////
    RadioButton rMon;
    RadioButton rTue;
    RadioButton rWed;
    RadioButton rThu;
    RadioButton rFri;

    CheckBox c83;
    CheckBox c90;
    CheckBox c93;
    CheckBox c10;
    CheckBox c103;
    CheckBox c11;
    CheckBox c113;
    CheckBox c12;
    CheckBox c133;
    CheckBox c14;
    CheckBox c143;
    CheckBox c15;
    CheckBox c153;
    CheckBox c16;
    CheckBox c163;
    CheckBox c17;

    Button bSubmit;
    FirebaseFirestore db;
    String userName;
    Context context;

    final String[] docId = {""};
    public void initObj(View view)
    {
        rMon=(RadioButton) view.findViewById(R.id.radioButton3);
        rTue=(RadioButton) view.findViewById(R.id.radioButton4);
        rWed=(RadioButton) view.findViewById(R.id.radioButton5);
        rThu=(RadioButton) view.findViewById(R.id.radioButton6);
        rFri=(RadioButton) view.findViewById(R.id.radioButton7);

        c83=(CheckBox) view.findViewById(R.id.checkBox);
        c90=(CheckBox) view.findViewById(R.id.checkBox2);
        c93=(CheckBox) view.findViewById(R.id.checkBox3);
        c10=(CheckBox) view.findViewById(R.id.checkBox4);
        c103=(CheckBox) view.findViewById(R.id.checkBox5);
        c11=(CheckBox) view.findViewById(R.id.checkBox6);
        c113=(CheckBox) view.findViewById(R.id.checkBox7);
        c12=(CheckBox) view.findViewById(R.id.checkBox8);
        c133=(CheckBox) view.findViewById(R.id.checkBox9);
        c14=(CheckBox) view.findViewById(R.id.checkBox11);
        c143=(CheckBox) view.findViewById(R.id.checkBox10);
        c15=(CheckBox) view.findViewById(R.id.checkBox12);
        c153=(CheckBox) view.findViewById(R.id.checkBox13);
        c16=(CheckBox) view.findViewById(R.id.checkBox14);
        c163=(CheckBox) view.findViewById(R.id.checkBox15);
        c17=(CheckBox) view.findViewById(R.id.checkBox16);

        bSubmit=(Button) view.findViewById(R.id.button4);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day=1;
                if(rTue.isChecked()) day=2;
                if(rWed.isChecked()) day=3;
                if(rThu.isChecked()) day=4;
                if(rFri.isChecked()) day=5;

                final boolean[] array = new boolean[16];

                array[0]=c83.isChecked();
                array[1]=c90.isChecked();
                array[2]=c93.isChecked();
                array[3]=c10.isChecked();
                array[4]=c103.isChecked();
                array[5]=c11.isChecked();
                array[6]=c113.isChecked();
                array[7]=c12.isChecked();
                array[8]=c133.isChecked();
                array[9]=c14.isChecked();
                array[10]=c143.isChecked();
                array[11]=c15.isChecked();
                array[12]=c153.isChecked();
                array[13]=c16.isChecked();
                array[14]=c163.isChecked();
                array[15]=c17.isChecked();

                Map<String, Object> data = new HashMap<>();
                data.put("username",userName);
                Map<String,Object> dTime=new HashMap<>();
                for(int i=0;i<16;i++)
                {
                    dTime.put(String.valueOf(i),array[i]);
                }
                data.put(String.valueOf(day),dTime);
                if(docId[0].length()>2)
                {
                    db.collection("timetable").document(docId[0]).set(data, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    new AlertDialog.Builder(context)
                                            .setTitle("Success!")
                                            .setMessage("The timetable has been updated.")
                                            .setPositiveButton("OK", null).show();
                                }
                            });
                }
                else
                {
                    db.collection("timetable").add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    new AlertDialog.Builder(context)
                                            .setTitle("Success!")
                                            .setMessage("The timetable has been updated.")
                                            .setPositiveButton("OK", null).show();

                                }
                            });
                }








            }
        });
        radioListener();
        getSelectedDay(1);
    }
    public void radioListener()
    {
        rMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rMon.isChecked())
                {
                    rTue.setChecked(false);
                    rWed.setChecked(false);
                    rThu.setChecked(false);
                    rFri.setChecked(false);
                    getSelectedDay(1);
                }
            }
        });
        rTue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rTue.isChecked())
                {
                    rMon.setChecked(false);
                    rWed.setChecked(false);
                    rThu.setChecked(false);
                    rFri.setChecked(false);
                    getSelectedDay(2);
                }
            }
        });
        rWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rWed.isChecked())
                {
                    rTue.setChecked(false);
                    rMon.setChecked(false);
                    rThu.setChecked(false);
                    rFri.setChecked(false);
                    getSelectedDay(3);
                }
            }
        });
        rThu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rThu.isChecked())
                {
                    rTue.setChecked(false);
                    rWed.setChecked(false);
                    rMon.setChecked(false);
                    rFri.setChecked(false);
                    getSelectedDay(4);
                }
            }
        });
        rFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rFri.isChecked())
                {
                    rTue.setChecked(false);
                    rWed.setChecked(false);
                    rThu.setChecked(false);
                    rMon.setChecked(false);
                    getSelectedDay(5);
                }
            }
        });
    }
    public void getSelectedDay(final int day)
    {
        final boolean[] array = new boolean[16];
        docId[0]="";
        db.collection("timetable")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("username").equals(userName.trim())) {
                                    docId[0]=document.getId();
                                    Map<String, Boolean> times= (Map<String, Boolean>) document.get(String.valueOf(day));

                                    if(times!=null)
                                    {
                                        int index=0;
                                        for(Map.Entry<String, Boolean> entry : times.entrySet()) {
                                            array[Integer.parseInt(entry.getKey())]=entry.getValue();
                                            index++;
                                        }

                                    }
                                }
                            }
                        }
                        c83.setChecked(array[0]);
                        c90.setChecked(array[1]);
                        c93.setChecked(array[2]);
                        c10.setChecked(array[3]);
                        c103.setChecked(array[4]);
                        c11.setChecked(array[5]);
                        c113.setChecked(array[6]);
                        c12.setChecked(array[7]);
                        c133.setChecked(array[8]);
                        c14.setChecked(array[9]);
                        c143.setChecked(array[10]);
                        c15.setChecked(array[11]);
                        c153.setChecked(array[12]);
                        c16.setChecked(array[13]);
                        c163.setChecked(array[14]);
                        c17.setChecked(array[15]);

                    }
                });
    }



    public LecTimeTable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LecTimeTable.
     */
    // TODO: Rename and change types and number of parameters
    public static LecTimeTable newInstance(String param1, String param2) {
        LecTimeTable fragment = new LecTimeTable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_lec_time_table, container, false);
        db = FirebaseFirestore.getInstance();
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        userName=prefs.getString("username","name");
        initObj(RootView);
        return RootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
