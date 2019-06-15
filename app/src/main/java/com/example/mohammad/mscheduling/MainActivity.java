package com.example.mohammad.mscheduling;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {
    Activity activity;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        Button bSignIn = (Button) findViewById(R.id.button);
        final EditText fUserName = (EditText)findViewById(R.id.editText);
        final EditText fPassword=(EditText)findViewById(R.id.editText2);
        final TextView reg=(TextView)findViewById(R.id.textView2);
        final TextView forgot=(TextView)findViewById(R.id.textView28);
        final SharedPreferences prefs = this.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName=fUserName.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (final QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("username").toString().equals(userName)) {
                                            final String password=document.getString("password");
                                            final String name=document.getString("name");
                                            new AlertDialog.Builder(activity)
                                                    .setTitle("Success!")
                                                    .setMessage("Your password has been send to your email.")
                                                    .setPositiveButton("OK", null).show();
                                            Thread thread = new Thread(new Runnable(){
                                                public void run() {
                                                    try {
                                                        sendmail(userName,password,name);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });

                                            thread.start();
                                        }
                                    }
                                } else {
                                    Log.w("aba", "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity, Register.class);
                activity.startActivity(myIntent);
            }
        });
        bSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                final String userName=fUserName.getText().toString();
                final String password=fPassword.getText().toString();
                final boolean[] check = {false};
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    check[0] =true;
                                    int userType=0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("username").toString().equals(userName) &&
                                                document.getString("password").toString().equals(password)) {
                                            if (document.getString("ut").toString().equals("admin")) {
                                                userType = 1;
                                                Intent adIntent = new Intent(activity, AdminToolBar.class);
                                                prefs.edit().putString("username", userName).apply();
                                                activity.startActivity(adIntent);
                                            }
                                            else if (document.getString("ut").toString().equals("student")) {
                                                userType = 2;
                                                Intent adIntent = new Intent(activity, StudentToolBar.class);
                                                prefs.edit().putString("username", userName).apply();
                                                prefs.edit().putString("name", document.getString("name")).apply();
                                                activity.startActivity(adIntent);
                                            }
                                            else if (document.getString("ut").toString().equals("lecturer")) {
                                                Intent adIntent = new Intent(activity, LecturerToolBar.class);
                                                prefs.edit().putString("username", userName).apply();
                                                prefs.edit().putString("name", document.getString("name")).apply();
                                                activity.startActivity(adIntent);
                                                userType = 3;
                                            }
                                            break;
                                        }

                                    }
                                    if(userType==0)
                                    {
                                        new AlertDialog.Builder(activity)
                                                .setTitle("Failed!")
                                                .setMessage("Wrong username or password.")
                                                .setPositiveButton("OK", null).show();
                                    }

                                } else {
                                    Log.w("aba", "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }

    public void sendmail(String mail,String passw, String name)
    {
        //   final String username = "apufood2018@gmail.com";
        //  final String password = "!QAZxsw2";

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

            String msg="Dear "+name+","
                    + "\n\n Here is your account credentials\n\n"+
                    "UserName: "+ mail+"\nPassword: "+passw;
            message.setSubject("Account Credentials");
            message.setText(msg);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            //Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
