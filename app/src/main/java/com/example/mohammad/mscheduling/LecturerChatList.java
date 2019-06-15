package com.example.mohammad.mscheduling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LecturerChatList extends AppCompatActivity {

    ListView lv;
    Firebase reference;
    Context context;
    HashMap<String, String> hm = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_chat_list);
        lv=(ListView) findViewById(R.id.listview);
        context=this;
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final SharedPreferences prefs = this.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        String userName=prefs.getString("username","name");
        final String tempUsername=userName.replace(".","?");
        Firebase.setAndroidContext(this);
        reference = new Firebase("https://msschedule-e6462.firebaseio.com//");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                hm.put(document.getString("username"),document.getString("name"));
                            }
                            reference.addChildEventListener(new com.firebase.client.ChildEventListener() {
                                @Override
                                public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                                    Map map = dataSnapshot.getValue(Map.class);
                                    Set<String> keys = map.keySet();
                                    List<String> list=new ArrayList<String>();

                                    for(String key: keys){
                                        if(key.indexOf(tempUsername)==0)
                                        {
                                            String to=key.substring(tempUsername.length()+1);
                                            to=to.replace("?",".");
                                            String name=hm.get(to).toString();

                                            list.add(name.toUpperCase()+"  /  "+to);
                                        }
                                    }
                                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                            (context, android.R.layout.simple_list_item_1, list);
                                    lv.setAdapter(arrayAdapter);
                                }

                                @Override
                                public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(com.firebase.client.FirebaseError firebaseError) {

                                }
                            });


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
                    String lecturer=text.substring(text.indexOf("/ ")+1).trim();
                    prefs.edit().putString("stu", lecturer).apply();
                    Intent adIntent = new Intent(context, LecturerChat.class);
                    context.startActivity(adIntent);
                }
            }
        });
    }
}
