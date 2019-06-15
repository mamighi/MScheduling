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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
 * {@link LabBookDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LabBookDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LabBookDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tName;
    TextView tDate;
    TextView tTime;
    TextView tStatus;
    Button bCancel;

    FirebaseFirestore db;
    String userName;
    Context context;
    String docId;
    private OnFragmentInteractionListener mListener;

    public LabBookDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LabBookDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static LabBookDetails newInstance(String param1, String param2) {
        LabBookDetails fragment = new LabBookDetails();
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
        View RootView=inflater.inflate(R.layout.fragment_lab_book_details, container, false);
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        userName=prefs.getString("username","name");
        String det=prefs.getString("det","name");
        final String date=det.substring(0,det.indexOf(" / ")).trim();
        String sTime=det.substring(det.indexOf("/ ")+1).trim();
        int time=0;
        switch (sTime)
        {
            case "8:30": time=0; break;
            case "10:30": time=1; break;
            case "13:30": time=2; break;
            case "15:30": time=3; break;
        }
        tDate=(TextView) RootView.findViewById(R.id.textView25);
        tTime=(TextView) RootView.findViewById(R.id.textView26);
        tName=(TextView) RootView.findViewById(R.id.textView23);
        tStatus=(TextView) RootView.findViewById(R.id.textView27);
        bCancel=(Button) RootView.findViewById(R.id.button8);
        db = FirebaseFirestore.getInstance();
        tDate.setText(date);
        tTime.setText(sTime);
        final int finalTime = time;
        db.collection("labbook")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("lecturer").equals(userName) &&
                                        document.getString("date").equals(date) &&
                                        document.getLong("time")== finalTime) {
                                        tName.setText(document.getString("name"));
                                        String stat=document.getString("stat");
                                        tStatus.setText(stat.toUpperCase());
                                        if(stat.equals("canceled"))
                                            bCancel.setEnabled(false);
                                        docId=document.getId();
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
                db.collection("labbook").document(docId).set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Success!")
                                        .setMessage("The lab booking has been canceled.")
                                        .setPositiveButton("OK", null).show();
                                Class fragmentClass = LabsBookList.class;
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
