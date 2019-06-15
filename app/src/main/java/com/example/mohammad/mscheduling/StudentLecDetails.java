package com.example.mohammad.mscheduling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentLecDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentLecDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentLecDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String email;
    EditText fName=null;
    EditText fEmail=null;
    EditText fEducation=null;
    EditText fField=null;
    EditText fDB=null;
    EditText fPno=null;
    Button bSubmit=null;
    FirebaseFirestore db;

    Context context;

    public StudentLecDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentLecDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentLecDetails newInstance(String param1, String param2) {
        StudentLecDetails fragment = new StudentLecDetails();
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
        View RootView=inflater.inflate(R.layout.fragment_student_lec_details, container, false);

        db = FirebaseFirestore.getInstance();
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);

        email=prefs.getString("lec","name");
        fName=(EditText) RootView.findViewById(R.id.editText15);
        fEmail=(EditText) RootView.findViewById(R.id.editText6);
        fEducation=(EditText) RootView.findViewById(R.id.editText7);
        fField=(EditText) RootView.findViewById(R.id.editText9);
        fDB=(EditText) RootView.findViewById(R.id.editText10);
        fPno=(EditText) RootView.findViewById(R.id.editText11);
        bSubmit=(Button) RootView.findViewById(R.id.button3);
        fName.setEnabled(false);
        fEmail.setEnabled(false);
        fEducation.setEnabled(false);
        fField.setEnabled(false);
        fDB.setEnabled(false);
        fPno.setEnabled(false);
        fEmail.setText(email);
        fName.setText(prefs.getString("lecname","name"));


        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adIntent = new Intent(context, ChatStudent.class);
                context.startActivity(adIntent);
            }
        });


        db.collection("lecpro")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> student_list = new ArrayList<String>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("username").equals(email)) {
                                    fEducation.setText(document.get("edu").toString());
                                    fField.setText(document.get("field").toString());
                                    fDB.setText(document.get("db").toString());
                                    fPno.setText(document.get("pno").toString());
                                }
                            }
                        }
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
