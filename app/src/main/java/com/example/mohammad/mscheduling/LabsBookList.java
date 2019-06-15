package com.example.mohammad.mscheduling;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
 * {@link LabsBookList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LabsBookList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LabsBookList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView lupcoming;
    ListView lhistory;

    FirebaseFirestore db;
    String userName;
    Context context;


    public LabsBookList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LabsBookList.
     */
    // TODO: Rename and change types and number of parameters
    public static LabsBookList newInstance(String param1, String param2) {
        LabsBookList fragment = new LabsBookList();
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
        View RootView = inflater.inflate(R.layout.fragment_labs_book_list, container, false);
        lupcoming=(ListView) RootView.findViewById(R.id.listview);
        lhistory=(ListView) RootView.findViewById(R.id.listview2);
        lupcoming.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lhistory.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        userName=prefs.getString("username","name");

        db = FirebaseFirestore.getInstance();
        db.collection("labbook")
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
                                if(document.getString("lecturer").equals(userName)) {
                                    String time= String.valueOf(document.getLong("time"));
                                    String t="";
                                    if(time!=null)
                                    {
                                        switch (time){
                                            case "0": t="8:30"; break;
                                            case "1": t="10:30"; break;
                                            case "2": t="13:30"; break;
                                            case "3": t="15:30"; break;
                                        }
                                    }
                                    String date=document.getString("date");
                                    String det=document.getString("date")+"  /  "+t;
                                    Calendar c = Calendar.getInstance();
                                    int cmonth=c.getTime().getMonth()+1;
                                    int cday=c.getTime().getDate();
                                    int cyear=c.getTime().getYear()+1900;
                                    int chour=c.getTime().getHours();
                                    int cmin=c.getTime().getMinutes();
                                    int fdash=date.indexOf("-");
                                    int year= Integer.parseInt(date.substring(0,fdash));
                                    int month= Integer.parseInt(date.substring(fdash+1,date.indexOf("-",fdash+1)));
                                    int day= Integer.parseInt(date.substring(date.indexOf("-",fdash+1)+1));

                                    if(cyear>year || (cyear==year && cmonth>month) || (cyear==year && cmonth==month &&cday>day))
                                    {
                                        history.add(det);
                                    }
                                    else
                                    {
                                        upcoming.add(det);
                                    }
                                }
                            }
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (context, android.R.layout.simple_list_item_1, upcoming);
                            final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>
                                    (context, android.R.layout.simple_list_item_1, history);
                            lupcoming.setAdapter(arrayAdapter);
                            lhistory.setAdapter(arrayAdapter2);
                        } else {
                            Log.w("aba", "Error getting documents.", task.getException());
                        }
                    }
                });
        lupcoming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int p = lupcoming.getCheckedItemPosition();
                if(p!=ListView.INVALID_POSITION) {
                    int tindex=lupcoming.getFirstVisiblePosition();
                    TextView textView = (TextView) lupcoming.getAdapter().getView(p, null, lupcoming);
                    final String text=textView.getText().toString();
                    loadDetails(text);
                }
            }
        });
        lhistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int p = lhistory.getCheckedItemPosition();
                if(p!=ListView.INVALID_POSITION) {
                    int tindex=lhistory.getFirstVisiblePosition();
                    TextView textView = (TextView) lhistory.getAdapter().getView(p, null, lhistory);
                    final String text=textView.getText().toString();
                    loadDetails(text);
                }
            }
        });
        return RootView;
    }
    public void loadDetails(String det)
    {
        final SharedPreferences prefs = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        prefs.edit().putString("det",det).apply();

        Class fragmentClass = LabBookDetails.class;
        android.support.v4.app.Fragment fragment = null;
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final int commit = fragmentManager.beginTransaction().replace(R.id.navmain, fragment).commit();


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
