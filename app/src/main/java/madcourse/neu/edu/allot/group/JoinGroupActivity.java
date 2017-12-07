package madcourse.neu.edu.allot.group;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import madcourse.neu.edu.allot.R;

public class JoinGroupActivity extends AppCompatActivity {

    Button joinGroupButton;
    EditText joinGroupEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        joinGroupButton = (Button) findViewById(R.id.joinGroupButton);
        joinGroupEditText = (EditText) findViewById(R.id.joinGroupEdittext);

        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (joinGroupEditText.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Group Name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent GroupActivity = new Intent(JoinGroupActivity.this, GroupActivity.class);
                    startActivity(GroupActivity);
                }
            }
        });
    }
}
