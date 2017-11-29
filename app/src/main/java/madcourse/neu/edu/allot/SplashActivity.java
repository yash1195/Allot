package madcourse.neu.edu.allot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME = 4000;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        logo.startAnimation(animation);
        Thread mainThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_SCREEN_TIME);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
