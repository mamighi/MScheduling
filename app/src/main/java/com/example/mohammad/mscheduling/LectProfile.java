package com.example.mohammad.mscheduling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LectProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LectProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LectProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String email;
     EditText fName=null;
     EditText fPassword=null;
     EditText fEducation=null;
    EditText fField=null;
     EditText fDB=null;
     EditText fPno=null;
     Button bSubmit=null;
    FirebaseFirestore db;
    final String[] docIdUser = {""};
    final String[] docIdPro = {""};
    Context context;


    private OnFragmentInteractionListener mListener;

    public LectProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LectProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static LectProfile newInstance(String param1, String param2) {
        LectProfile fragment = new LectProfile();
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
    public void onResume() {

        super.onResume();

    }
    public void task()
    {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> student_list = new ArrayList<String>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("username").equals(email.trim())) {
                                    fName.setText(document.get("name").toString());
                                    fPassword.setText(document.get("password").toString());
                                    docIdUser[0]=document.getId();
                                }
                            }
                        }
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
                                    docIdPro[0]=document.getId();
                                }
                            }
                        }
                    }
                });
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=fName.getText().toString();
                String pass=fPassword.getText().toString();
                String edu=fEducation.getText().toString();
                String field=fField.getText().toString();
                String db1=fDB.getText().toString();
                String pno=fPno.getText().toString();
                if(name.length()<1 || pass.length()<1 ||edu.length()<1 ||field.length()<1 ||db1.length()<1 ||pno.length()<1)
                {
                    new AlertDialog.Builder(context)
                            .setTitle("Failed!")
                            .setMessage("Please insert all the information.")
                            .setPositiveButton("OK", null).show();
                    return;
                }
                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("password", pass);

                db.collection("users").document(docIdUser[0]).set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                Map<String, Object> data2 = new HashMap<>();
                data2.put("username", email);
                data2.put("edu", edu);
                data2.put("field", field);
                data2.put("db", db1);
                data2.put("pno", pno);
                if(docIdPro[0].length()>3) {
                    db.collection("lecpro").document(docIdUser[0]).set(data2, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    new AlertDialog.Builder(context)
                                            .setTitle("Success!")
                                            .setMessage("The profile has been updated successfully.")
                                            .setPositiveButton("OK", null).show();
                                }
                            });
                }
                else
                {
                    db.collection("lecpro").add(data2)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    new AlertDialog.Builder(context)
                                            .setTitle("Success!")
                                            .setMessage("The profile has been updated successfully.")
                                            .setPositiveButton("OK", null).show();

                                }
                            });
                }

            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        View RootView = inflater.inflate(R.layout.fragment_lect_profile, container, false);
        email=prefs.getString("username","name");
        fName=(EditText) RootView.findViewById(R.id.editText6);
        fPassword=(EditText) RootView.findViewById(R.id.editText8);
        fEducation=(EditText) RootView.findViewById(R.id.editText7);
        fField=(EditText) RootView.findViewById(R.id.editText9);
        fDB=(EditText) RootView.findViewById(R.id.editText10);
        fPno=(EditText) RootView.findViewById(R.id.editText11);
        bSubmit=(Button) RootView.findViewById(R.id.button3);
        task();
        return RootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(final Context context) {
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
