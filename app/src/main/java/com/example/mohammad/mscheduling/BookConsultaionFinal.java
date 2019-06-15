package com.example.mohammad.mscheduling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookConsultaionFinal.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookConsultaionFinal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookConsultaionFinal extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    TextView tLname;
    TextView tField;
    TextView tEmail;
    TextView tPno;

    CalendarView calendar;

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
    String lemail;
    String date;
    public void initObj(View view)
    {
         tLname=(TextView) view.findViewById(R.id.textView10);
         tField=(TextView) view.findViewById(R.id.textView11);;
         tEmail=(TextView) view.findViewById(R.id.textView12);
         tPno=(TextView) view.findViewById(R.id.textView13);
         calendar=(CalendarView) view.findViewById(R.id.calendarView);
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
        c83.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c83.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c90.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c90.isChecked())
                {
                    c83.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c93.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c93.isChecked())
                {
                    c90.setChecked(false);
                    c83.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c10.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c83.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c103.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c103.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c83.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c11.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c83.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c113.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c113.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c83.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c12.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c83.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c133.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c133.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c83.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c14.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c83.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c143.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c143.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c83.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c15.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c83.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c153.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c153.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c83.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c16.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c83.setChecked(false);
                    c163.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c163.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c163.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c83.setChecked(false);
                    c17.setChecked(false);
                }
            }
        });
        c17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(c17.isChecked())
                {
                    c90.setChecked(false);
                    c93.setChecked(false);
                    c10.setChecked(false);
                    c103.setChecked(false);
                    c11.setChecked(false);
                    c113.setChecked(false);
                    c12.setChecked(false);
                    c133.setChecked(false);
                    c14.setChecked(false);
                    c143.setChecked(false);
                    c15.setChecked(false);
                    c153.setChecked(false);
                    c16.setChecked(false);
                    c163.setChecked(false);
                    c83.setChecked(false);
                }
            }
        });

        bSubmit=(Button) view.findViewById(R.id.button4);
    }

    private OnFragmentInteractionListener mListener;

    public BookConsultaionFinal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookConsultaionFinal.
     */
    // TODO: Rename and change types and number of parameters
    public static BookConsultaionFinal newInstance(String param1, String param2) {
        BookConsultaionFinal fragment = new BookConsultaionFinal();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_book_consultaion_final, container, false);
        db = FirebaseFirestore.getInstance();
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        userName=prefs.getString("username","name");
        initObj(RootView);
        lemail=prefs.getString("lec","name");
        tLname.setText(prefs.getString("lecname","name"));
        tEmail.setText(prefs.getString("lec","name"));
        db.collection("lecpro")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            int userType=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("username").toString().equals(lemail)) {
                                    String field=document.getString("field").toString();
                                    String pno=document.getString("pno").toString();
                                    tPno.setText(pno);
                                    tField.setText(field);
                                    break;
                                }
                            }
                        } else {
                            Log.w("aba", "Error getting documents.", task.getException());
                        }
                    }
                });
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
                if(c90.isChecked()) selectedTime=1;
                if(c93.isChecked()) selectedTime=2;
                if(c10.isChecked()) selectedTime=3;
                if(c103.isChecked()) selectedTime=4;
                if(c11.isChecked()) selectedTime=5;
                if(c113.isChecked()) selectedTime=6;
                if(c12.isChecked()) selectedTime=7;
                if(c133.isChecked()) selectedTime=8;
                if(c14.isChecked()) selectedTime=9;
                if(c143.isChecked()) selectedTime=10;
                if(c15.isChecked()) selectedTime=11;
                if(c153.isChecked()) selectedTime=12;
                if(c16.isChecked()) selectedTime=13;
                if(c163.isChecked()) selectedTime=14;
                if(c17.isChecked()) selectedTime=15;
                if(selectedTime==-1)
                {
                    new AlertDialog.Builder(context)
                            .setTitle("Success!")
                            .setMessage("Please Select Consultation Date.")
                            .setPositiveButton("OK", null).show();
                    return;
                }
                String stat="booked";
                Map<String, Object> data = new HashMap<>();
                data.put("student",userName);
                data.put("lecturer",lemail);
                data.put("date",date);
                data.put("time",selectedTime);
                data.put("stat",stat);
                db.collection("cons").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Success!")
                                        .setMessage("The consultation has been booked.")
                                        .setPositiveButton("OK", null).show();
                                Thread thread = new Thread(new Runnable(){
                                    public void run() {
                                        try {
                                            String msg="Dear Student,\n" +
                                                    "You have booked a consultation for "+date+
                                                    "\nFor more details please log in to your account.";
                                            sendmail(userName,"Consultation Booking",msg);
                                            msg="Dear Lecturer,\n" +
                                                    "A Student has book a consultation with you for "+date+
                                                    "\nFor more details please log in to your account.";
                                            sendmail(lemail,"Consultation Booking",msg);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                thread.start();

                            }
                        });

                Class fragmentClass = BookConsultatio.class;
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
        c90.setEnabled(false);
        c93.setEnabled(false);
        c10.setEnabled(false);
        c103.setEnabled(false);
        c11.setEnabled(false);
        c113.setEnabled(false);
        c12.setEnabled(false);
        c133.setEnabled(false);
        c14.setEnabled(false);
        c143.setEnabled(false);
        c15.setEnabled(false);
        c153.setEnabled(false);
        c16.setEnabled(false);
        c163.setEnabled(false);
        c17.setEnabled(false);
        Date todayDate;
        todayDate= Date.newBuilder().build();

        date=year+"-"+month+"-"+dayOfMonth;
        Calendar c = Calendar.getInstance();
        c.set(year,month-1,dayOfMonth);// yourdate is an object of type Date
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek--;
        final boolean[] array = new boolean[16];
        final int finalDayOfWeek = dayOfWeek;
        db.collection("timetable")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("username").equals(lemail.trim())) {
                                    Map<String, Boolean> times= (Map<String, Boolean>) document.get(String.valueOf(finalDayOfWeek));
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
                        c83.setEnabled(array[0]);
                        c90.setEnabled(array[1]);
                        c93.setEnabled(array[2]);
                        c10.setEnabled(array[3]);
                        c103.setEnabled(array[4]);
                        c11.setEnabled(array[5]);
                        c113.setEnabled(array[6]);
                        c12.setEnabled(array[7]);
                        c133.setEnabled(array[8]);
                        c14.setEnabled(array[9]);
                        c143.setEnabled(array[10]);
                        c15.setEnabled(array[11]);
                        c153.setEnabled(array[12]);
                        c16.setEnabled(array[13]);
                        c163.setEnabled(array[14]);
                        c17.setEnabled(array[15]);
                        db.collection("cons")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (document.getString("lecturer").equals(lemail.trim()) && document.getString("date").equals(date)) {
                                                    String time= String.valueOf(document.getLong("time"));
                                                    if(time!=null)
                                                    {
                                                        switch (time){
                                                            case "0": c83.setEnabled(false); break;
                                                            case "1": c90.setEnabled(false); break;
                                                            case "2": c93.setEnabled(false); break;
                                                            case "3": c10.setEnabled(false); break;
                                                            case "4": c103.setEnabled(false); break;
                                                            case "5": c11.setEnabled(false); break;
                                                            case "6": c113.setEnabled(false); break;
                                                            case "7": c12.setEnabled(false); break;
                                                            case "8": c133.setEnabled(false); break;
                                                            case "9": c14.setEnabled(false); break;
                                                            case "10": c143.setEnabled(false); break;
                                                            case "11": c15.setEnabled(false); break;
                                                            case "12": c153.setEnabled(false); break;
                                                            case "13": c16.setEnabled(false); break;
                                                            case "14": c163.setEnabled(false); break;
                                                            case "15": c17.setEnabled(false); break;
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
    public void sendmail(String mail,String subject,String msg)
    {

        try {
            String from="msschedule2019@gmail.com"; //enter ur email and password GMAIL
            String pass="!QAZxsw2";

            Properties props = System.getProperties();
            String host = "smtp.gmail.com";
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(mail);
            // To get the array of addresses
            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(msg);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            //Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
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
