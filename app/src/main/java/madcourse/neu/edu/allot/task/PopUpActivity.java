package madcourse.neu.edu.allot.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.github.tbouron.shakedetector.library.ShakeDetector;

import madcourse.neu.edu.allot.R;

public class PopUpActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(hight*.6));

        ShakeDetector.create(this, new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {
                Toast.makeText(getApplicationContext(), "Device shaken!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        ShakeDetector.destroy();
        super.onDestroy();
    }
}
