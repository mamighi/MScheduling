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
import android.widget.EditText;

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
 * {@link StudentProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentProfile extends Fragment {
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
    EditText fPassword=null;

    Button bSubmit=null;
    FirebaseFirestore db;
    final String[] docIdUser = {""};
    Context context;

    public StudentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentProfile newInstance(String param1, String param2) {
        StudentProfile fragment = new StudentProfile();
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
        db = FirebaseFirestore.getInstance();
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        View RootView = inflater.inflate(R.layout.fragment_student_profile, container, false);
        email=prefs.getString("username","name");
        fName=(EditText) RootView.findViewById(R.id.editText6);
        fPassword=(EditText) RootView.findViewById(R.id.editText8);
        bSubmit=(Button) RootView.findViewById(R.id.button3);
        task();
        return RootView;
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
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=fName.getText().toString();
                String pass=fPassword.getText().toString();

                if(name.length()<1 || pass.length()<1)
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
                                new AlertDialog.Builder(context)
                                        .setTitle("Success!")
                                        .setMessage("Profile has been updated.")
                                        .setPositiveButton("OK", null).show();
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
