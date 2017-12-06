package madcourse.neu.edu.allot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import madcourse.neu.edu.allot.blackbox.handlers.FetchGroupsHandler;
import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.blackbox.responders.FetchGroupsResponder;

public class BackendTestActivity extends AppCompatActivity implements FetchGroupsResponder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FetchGroupsHandler handler = new FetchGroupsHandler();

        String id = "5a287944cb9c44a843853270";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0Bmb28iLCJpYXQiOjE1MTI2MDI5MTYsImV4cCI6MTUxMjYxMzcxNn0.OuDmUB-ujUe-cjLfHkkx3-uvKm-R1NPInZzTObefY2o";


        FetchGroupsHandler.doFetch(this, id, token);
    }

    @Override
    public void onSuccessfullGroupsFetch(List<Group> groups) {

    }

    @Override
    public void onFailedGroupsFetch(String msg) {

    }
}
