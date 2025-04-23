package com.os0.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Messenger extends AppCompatActivity implements OnClickListener, View.OnLongClickListener {
    private EditText txtMessenger;
    int size;
    String username;
    RelativeLayout myLayout, replyLayout, footerLayout;
    ScrollView scroll;
    String dateVar = "";
    TextView txt1, replyTerminate;
    boolean fuck_off = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences spn = getSharedPreferences("curUserPr", MODE_PRIVATE);
        setContentView(R.layout.messenger);
        username = spn.getString("currentUser", "Anonymous");
        txtMessenger = findViewById(R.id.txtMessageSend);
        ImageButton btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        myLayout = findViewById(R.id.main);
        footerLayout = findViewById(R.id.footer);
        replyLayout = findViewById(R.id.replyLayout);
        replyTerminate = findViewById(R.id.replyTerminate);
        txt1 = findViewById(R.id.MessengerTxt1);
        size = R.id.MessengerTxt1;
        scroll = findViewById(R.id.scroll);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Context this2 = this;
        db.collection("messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.out.println("TVA SE GRUMNA");
                    return;
                }
                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    if (fuck_off) {
                        Timestamp temp = dc.getDocument().getTimestamp("date");
                        Date d = temp.toDate();
                        SharedPreferences sp = getSharedPreferences("Clock", MODE_PRIVATE);
                        long millis = sp.getLong("mss", 0);
                        if (millis == 0) sp.edit().putLong("mss", 0);
                        d.setTime(d.getTime() - millis);
                        String date = dateFormat.format(d);
                        String[] dateCompare = date.split(" ");
                        if(!dateVar.equals(dateCompare[0])) {
                            DateLine(new TextView(this2), dateCompare[0]);
                            dateVar=dateCompare[0];
                        }
                        FormatText(new TextView(this2), dc.getDocument().getData().get("msg").toString(), dc.getDocument().getData().get("name").toString(), dateCompare[1]);
                        replyLayout.setVisibility(View.INVISIBLE);
                        autoText = "";
                        scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        txtMessenger.setText("");
                    }
                }
            }
        });
        replyTerminate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                autoText = "";
                replyLayout.setVisibility(View.INVISIBLE);
            }
        });
        Context this3 = this;
        db.collection("messages").orderBy("date").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Timestamp temp = document.getTimestamp("date");
                        Date d = temp.toDate();
                        SharedPreferences sp = getSharedPreferences("Clock", MODE_PRIVATE);
                        long millis = sp.getLong("mss", 0);
                        if (millis == 0) sp.edit().putLong("mss", 0);
                        d.setTime(d.getTime() - millis);
                        String date = dateFormat.format(d);
                        String[] dateCompare = date.split(" ");
                        if(!dateVar.equals(dateCompare[0])) {
                            DateLine(new TextView(this3), dateCompare[0]);
                            dateVar=dateCompare[0];
                        }
                        FormatText(new TextView(this3), document.getData().get("msg").toString(), document.getData().get("name").toString(), dateCompare[1]);
                    }
                    fuck_off = true;
                    footerLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        scroll.fullScroll(ScrollView.FOCUS_DOWN);
    }

    String autoText = "";

    @Override
    public void onClick(View view) {
        if (txtMessenger.getText().toString().equals("")) return;
        Map<String, Object> msg = new HashMap<>();
        msg.put("name", username);
        msg.put("msg", String.format("%s%s", autoText, txtMessenger.getText().toString()));
        msg.put("date", new Timestamp(new Date()));
        Context this2 = this;
        FirebaseFirestore.getInstance().collection("messages").add(msg).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(this2, "Message sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this2, "Error! Unable to send message!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        autoText = String.format("Replying to: %s%n\n", ((TextView) view).getText().toString());
        txtMessenger.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtMessenger, InputMethodManager.SHOW_IMPLICIT);
        replyLayout.setVisibility(View.VISIBLE);
        return true;
    }

    //Visual setup for TextView
    private void FormatText(TextView newMessage, String text, String name, String date) {
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams textParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, 20, 0, 0);
        textParams.addRule(RelativeLayout.BELOW, size);
        TextView nameText = new TextView(this);
        nameText.setText(String.format("%s - %s", name, date));
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
        nameText.setTypeface(null, Typeface.BOLD);
        newMessage.setBackgroundColor(getResources().getColor(R.color.sendGray));
        if (name.equals(username)) {
            textParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            newMessage.setTextColor(getResources().getColor(R.color.white));
            newMessage.setBackgroundColor(getResources().getColor(R.color.sendPurple));
        }
        nameText.setId(View.generateViewId());
        size = nameText.getId();
        nameText.setLayoutParams(textParams);
        myLayout.addView(nameText);
        textParams1.addRule(RelativeLayout.BELOW, size);
        newMessage.setLayoutParams(textParams1);
        newMessage.setPadding(25, 25, 25, 25);
        newMessage.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
        newMessage.setText(text);
        newMessage.setTypeface(null, Typeface.BOLD);
        newMessage.setOnLongClickListener(this);
        newMessage.setId(View.generateViewId());
        size = newMessage.getId();
        myLayout.addView(newMessage);
    }
    private void DateLine(TextView newMessage, String date){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                4);
        params.addRule(RelativeLayout.BELOW, size);
        params.setMargins(0, 25, 0, 0);
        View e = new View(this);
        e.setLayoutParams(params);
        e.setBackgroundColor(0xFF4f4f4f);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, size);
        params.setMargins(0, 20, 0, 0);
        newMessage.setLayoutParams(params);
        newMessage.setText(date);
        if(dateFormat.format(new Date()).split(" ")[0].equals(date)) newMessage.setText("Today");
        newMessage.setTypeface(null, Typeface.BOLD);
        newMessage.setTextSize(TypedValue.COMPLEX_UNIT_PT, 7);
        newMessage.setId(View.generateViewId());
        size = newMessage.getId();
        myLayout.addView(e);
        myLayout.addView(newMessage);
    }
}