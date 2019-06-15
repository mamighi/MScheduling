package com.example.mohammad.mscheduling;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
 * {@link LabsList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LabsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LabsList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView lv;

    FirebaseFirestore db;

    Context context;

    public LabsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LabsList.
     */
    // TODO: Rename and change types and number of parameters
    public static LabsList newInstance(String param1, String param2) {
        LabsList fragment = new LabsList();
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
        View RootView=inflater.inflate(R.layout.fragment_labs_list, container, false);
        lv=(ListView) RootView.findViewById(R.id.listview);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        db = FirebaseFirestore.getInstance();
        db.collection("labs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int userType=0;
                            List<String> lab_list = new ArrayList<String>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    lab_list.add(document.getString("name").toString());
                            }
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (context, android.R.layout.simple_list_item_1, lab_list);
                            lv.setAdapter(arrayAdapter);
                        } else {
                            Log.w("aba", "Error getting documents.", task.getException());
                        }
                    }
                });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int p = lv.getCheckedItemPosition();
                if(p!=ListView.INVALID_POSITION) {
                    int tindex=lv.getFirstVisiblePosition();
                    TextView textView = (TextView) lv.getAdapter().getView(p, null, lv);
                    final String text=textView.getText().toString();
                    final SharedPreferences prefs = context.getSharedPreferences(
                            "pref", Context.MODE_PRIVATE);
                    prefs.edit().putString("lab", text).apply();
                    Class fragmentClass = EditLab.class;
                    android.support.v4.app.Fragment fragment = null;
                    try {
                        fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final int commit = fragmentManager.beginTransaction().replace(R.id.navmain, fragment).commit();
                }else{
                    Toast.makeText(context, "Nothing Selected..", Toast.LENGTH_LONG).show();
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
