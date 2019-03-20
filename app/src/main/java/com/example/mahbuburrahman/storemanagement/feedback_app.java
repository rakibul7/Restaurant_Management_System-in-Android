package com.example.mahbuburrahman.storemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.MainActivity;

public class feedback_app extends AppCompatActivity implements View.OnClickListener {

    private Button send,clear;
    private EditText name,content;
    Intent intent;
    private String[] Email={"sahidhossain420@gmail.com"};
    private String Subject="FeedBack From App";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_app);
        intent=new Intent(this, MainActivity.class);
        send=(Button)findViewById(R.id.feedsend);
        clear=(Button)findViewById(R.id.feedclear);
        name=(EditText)findViewById(R.id.feedname);
        content=(EditText)findViewById(R.id.feedcontent);


        send.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        try
        {
            String nameofsender=name.getText().toString();
            String messegeofsender=name.getText().toString();
            if(view.getId()==R.id.feedsend)
            {

                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.setType("text/email");
                intent1.putExtra(Intent.EXTRA_EMAIL,Email);
                intent1.putExtra(Intent.EXTRA_SUBJECT,Subject);
                intent1.putExtra(Intent.EXTRA_TEXT,"Name: "+nameofsender+" Messege:"+messegeofsender);
                startActivity(Intent.createChooser(intent1,"Feedback With"));

            }
            else if(view.getId()==R.id.feedclear)
            {
                name.setText("");
                content.setText("");
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Not send any Feedback",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Exception: "+e,Toast.LENGTH_SHORT).show();
        }
    }
}
