package com.example.mohammad.mscheduling;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewLab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewLab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewLab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText tName;
    CheckBox c83;
    CheckBox c103;
    CheckBox c133;
    CheckBox c153;

    Button bSubmit;

    FirebaseFirestore db;
    String userName;
    Context context;

    public AddNewLab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewLab.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewLab newInstance(String param1, String param2) {
        AddNewLab fragment = new AddNewLab();
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
        View RootView=inflater.inflate(R.layout.fragment_add_new_lab, container, false);
        tName=(EditText) RootView.findViewById(R.id.editText17);
        c83=(CheckBox) RootView.findViewById(R.id.checkBox);
        c103=(CheckBox) RootView.findViewById(R.id.checkBox2);
        c133=(CheckBox) RootView.findViewById(R.id.checkBox3);
        c153=(CheckBox) RootView.findViewById(R.id.checkBox4);

        bSubmit=(Button) RootView.findViewById(R.id.button7);

        db = FirebaseFirestore.getInstance();

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] check = {false};
                final String name=tName.getText().toString();
                final Boolean T0=c83.isChecked();
                final Boolean T1=c83.isChecked();
                final Boolean T2=c83.isChecked();
                final Boolean T3=c83.isChecked();
                if(name.length()<2)
                {
                    new AlertDialog.Builder(context)
                            .setTitle("Failed!")
                            .setMessage("Please enter the lab name.")
                            .setPositiveButton("OK", null).show();
                    return;
                }

                db.collection("labs")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    check[0]=false;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("name").toString().equals(name)) {
                                            check[0]=true;
                                            break;
                                        }
                                    }
                                    if(check[0]==true)
                                    {
                                        new AlertDialog.Builder(context)
                                                .setTitle("Failed!")
                                                .setMessage("The lab is already exists.")
                                                .setPositiveButton("OK", null).show();
                                    }
                                    else
                                    {
                                        Map<String, Object> docData = new HashMap<>();
                                        docData.put("name",name);
                                        docData.put("0",T0);
                                        docData.put("1",T1);
                                        docData.put("2",T2);
                                        docData.put("3",T3);
                                        db.collection("labs").add(docData)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        new AlertDialog.Builder(context)
                                                                .setTitle("Success!")
                                                                .setMessage("The new account has been created successfully.")
                                                                .setPositiveButton("OK", null).show();
                                                    }
                                                });

                                    }

                                } else {
                                    Log.w("aba", "Error getting documents.", task.getException());
                                }
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
