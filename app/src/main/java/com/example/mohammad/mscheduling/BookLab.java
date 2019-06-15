package com.example.mohammad.mscheduling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.Date;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookLab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookLab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookLab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView tLname;

    CalendarView calendar;

    CheckBox c83;
    CheckBox c103;
    CheckBox c133;
    CheckBox c153;


    Button bSubmit;
    FirebaseFirestore db;
    String userName;
    String lemail;
    String date;
    Context context;
    String lab;
    public BookLab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookLab.
     */
    // TODO: Rename and change types and number of parameters
    public static BookLab newInstance(String param1, String param2) {
        BookLab fragment = new BookLab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void initObj(View view)
    {
        tLname=(TextView) view.findViewById(R.id.textView10);

        calendar=(CalendarView) view.findViewById(R.id.calendarView);
        c83=(CheckBox) view.findViewById(R.id.checkBox);
        c103=(CheckBox) view.findViewById(R.id.checkBox2);
        c133=(CheckBox) view.findViewById(R.id.checkBox3);
        c153=(CheckBox) view.findViewById(R.id.checkBox4);
        c83.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c83.isChecked())
                {
                    c103.setChecked(false);
                    c133.setChecked(false);
                    c153.setChecked(false);
                }
            }
        });
        c103.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c103.isChecked())
                {
                    c83.setChecked(false);
                    c133.setChecked(false);
                    c153.setChecked(false);
                }
            }
        });
        c133.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c133.isChecked())
                {
                    c103.setChecked(false);
                    c83.setChecked(false);
                    c153.setChecked(false);
                }
            }
        });
        c153.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c153.isChecked())
                {
                    c103.setChecked(false);
                    c133.setChecked(false);
                    c83.setChecked(false);
                }
            }
        });


        bSubmit=(Button) view.findViewById(R.id.button4);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView=inflater.inflate(R.layout.fragment_book_lab, container, false);
        db = FirebaseFirestore.getInstance();
        initObj(RootView);
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        userName=prefs.getString("username","name");
        lab=prefs.getString("lab","name");
        tLname.setText(lab);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dayHandler(year, month+1,  dayOfMonth);
            }
        });

        Date todayDate;
        todayDate= Date.newBuilder().build();
        dayHandler(todayDate.getYear(),todayDate.getMonth()+1,todayDate.getDay());
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedTime=-1;
                if(c83.isChecked()) selectedTime=0;
                if(c103.isChecked()) selectedTime=1;
                if(c133.isChecked()) selectedTime=2;
                if(c153.isChecked()) selectedTime=3;
                if(selectedTime==-1)
                {
                    new AlertDialog.Builder(context)
                            .setTitle("Success!")
                            .setMessage("Please Select Date.")
                            .setPositiveButton("OK", null).show();
                    return;
                }
                String stat="booked";
                Map<String, Object> data = new HashMap<>();
                data.put("name",lab);
                data.put("lecturer",userName);
                data.put("date",date);
                data.put("time",selectedTime);
                data.put("stat",stat);
                db.collection("labbook").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Success!")
                                        .setMessage("The lab has been booked.")
                                        .setPositiveButton("OK", null).show();

                            }
                        });

                Class fragmentClass = LabListLecturer.class;
                android.support.v4.app.Fragment fragment = null;
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final int commit = fragmentManager.beginTransaction().replace(R.id.navmain, fragment).commit();

            }
        });






        return RootView;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void dayHandler(int year, int month, int dayOfMonth)
    {
        c83.setEnabled(false);
        c103.setEnabled(false);
        c133.setEnabled(false);
        c153.setEnabled(false);
        Date todayDate;
        todayDate= Date.newBuilder().build();
        date=year+"-"+month+"-"+dayOfMonth;
        Calendar c = Calendar.getInstance();
        c.set(year,month-1,dayOfMonth);// yourdate is an object of type Date
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek--;
        final boolean[] array = new boolean[4];
        final int finalDayOfWeek = dayOfWeek;
        db.collection("labs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("name").equals(lab.trim())) {
                                    array[0]=document.getBoolean("0");
                                    array[1]=document.getBoolean("1");
                                    array[2]=document.getBoolean("2");
                                    array[3]=document.getBoolean("3");
                                }
                            }
                        }
                        c83.setEnabled(array[0]);
                        c103.setEnabled(array[1]);
                        c133.setEnabled(array[2]);
                        c153.setEnabled(array[3]);

                        db.collection("labbook")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (document.getString("name").equals(lab.trim()) && document.getString("date").equals(date)) {
                                                    String time= String.valueOf(document.getLong("time"));
                                                    if(time!=null)
                                                    {
                                                        switch (time){
                                                            case "0": c83.setEnabled(false); break;
                                                            case "1": c103.setEnabled(false); break;
                                                            case "2": c133.setEnabled(false); break;
                                                            case "3": c153.setEnabled(false); break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                });
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
