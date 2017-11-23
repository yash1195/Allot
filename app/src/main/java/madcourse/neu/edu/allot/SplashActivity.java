package madcourse.neu.edu.allot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread mainThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_SCREEN_TIME);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        mainThread.start();
    }
}
