package com.example.mohammad.mscheduling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentConsDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentConsDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentConsDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView tDate;
    TextView tTime;
    TextView tLec;
    TextView tStat;

    EditText eLectFeed;
    EditText eStuFeed;

    Button bSave;
    Button bCancel;

    String userName;
    Context context;

    String docId;
    FirebaseFirestore db;
    public StudentConsDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentConsDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentConsDetail newInstance(String param1, String param2) {
        StudentConsDetail fragment = new StudentConsDetail();
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
        View RootView=inflater.inflate(R.layout.fragment_student_cons_detail, container, false);
        tDate=(TextView) RootView.findViewById(R.id.textView16);
        tTime=(TextView) RootView.findViewById(R.id.textView17);
        tLec=(TextView) RootView.findViewById(R.id.textView18);
        tStat=(TextView) RootView.findViewById(R.id.textView19);

        eLectFeed=(EditText) RootView.findViewById(R.id.editText13);
        eStuFeed=(EditText) RootView.findViewById(R.id.editText14);
        eLectFeed.setEnabled(false);
        bSave=(Button) RootView.findViewById(R.id.button5);
        bCancel=(Button) RootView.findViewById(R.id.button6);

        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        userName=prefs.getString("username","name");
        String det=prefs.getString("det","name");

        final String Date=det.substring(0,det.indexOf(" /")).trim();
        String Time=det.substring(det.indexOf("/ ")+1).trim();
        tDate.setText(Date);
        tTime.setText(Time);

        int iTime=-1;
        switch (Time){
            case "8:30": iTime=0; break;
            case "9:00": iTime=1; break;
            case "9:30": iTime=2; break;
            case "10:00": iTime=3; break;
            case "10:30": iTime=4; break;
            case "11:00": iTime=5; break;
            case "11:30": iTime=6; break;
            case "12:00": iTime=7; break;
            case "13:30": iTime=8; break;
            case "14:00": iTime=9; break;
            case "14:30": iTime=10; break;
            case "15:00": iTime=11; break;
            case "15:30": iTime=12; break;
            case "16:00": iTime=13; break;
            case "16:30": iTime=14; break;
            case "17:00": iTime=15; break;
        }
        db = FirebaseFirestore.getInstance();
        final int finalITime = iTime;
        db.collection("cons")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int userType=0;
                            List<String> upcoming = new ArrayList<String>();
                            List<String> history=new ArrayList<String>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("student").equals(userName) &&
                                        document.getString("date").equals(Date) &&
                                        document.getLong("time")== finalITime) {
                                    docId=document.getId();
                                    final String lecId=document.getString("lecturer");
                                    String stat=document.getString("stat");
                                    tStat.setText(stat.toUpperCase());
                                    db.collection("users")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            if(document.getString("username").toString().equals(lecId)) {
                                                                tLec.setText(document.getString("name"));
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                    String lf=document.getString("lf");
                                    String sf=document.getString("sf");
                                    if(lf!=null)
                                    {
                                        eLectFeed.setText(lf);
                                    }
                                    if(sf!=null)
                                    {
                                        eStuFeed.setText(sf);
                                    }
                                    if(stat.equals("canceled"))
                                    {
                                        eLectFeed.setEnabled(false);
                                        eStuFeed.setEnabled(false);
                                        bCancel.setEnabled(false);
                                        bSave.setEnabled(false);
                                    }
                                    else {
                                        String date = document.getString("date");
                                        Calendar c = Calendar.getInstance();
                                        int cmonth = c.getTime().getMonth() + 1;
                                        int cday = c.getTime().getDate();
                                        int cyear = c.getTime().getYear() + 1900;
                                        int chour = c.getTime().getHours();
                                        int cmin = c.getTime().getMinutes();
                                        int fdash = date.indexOf("-");
                                        int year = Integer.parseInt(date.substring(0, fdash));
                                        int month = Integer.parseInt(date.substring(fdash + 1, date.indexOf("-", fdash + 1)));
                                        int day = Integer.parseInt(date.substring(date.indexOf("-", fdash + 1) + 1));

                                        if (cyear > year || (cyear == year && cmonth > month) || (cyear == year && cmonth == month && cday > day)) {
                                            bCancel.setEnabled(false);
                                            tStat.setText("DONE");
                                        } else {

                                        }
                                    }
                                }
                            }
                        } else {
                            Log.w("aba", "Error getting documents.", task.getException());
                        }
                    }
                });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("stat","canceled");
                db.collection("cons").document(docId).set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Success!")
                                        .setMessage("The consultaion has been canceled.")
                                        .setPositiveButton("OK", null).show();
                                Class fragmentClass = StudentConsultaionList.class;
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
            }
        });
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                String feed=eStuFeed.getText().toString();
                data.put("sf",feed);
                db.collection("cons").document(docId).set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                new AlertDialog.Builder(context)
                                        .setTitle("Success!")
                                        .setMessage("The feedback has been saved.")
                                        .setPositiveButton("OK", null).show();
                                Class fragmentClass = StudentConsultaionList.class;
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
            }
        });




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
