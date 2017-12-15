package madcourse.neu.edu.allot.blackbox.response;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import static org.junit.Assert.*;

/**
 * Created by zeko on 11/28/17.
 */
public class LoginHandlerResponseTest {

    String LOGIN_URL = "http://allot.zeko.in/api/user/login";

    RequestParams params;
    AsyncHttpClient client;

    @Before
    public void setUp() throws Exception {

        params = new RequestParams();
        params.put("email", "test");
        params.put("password", "test");

        client = new AsyncHttpClient();

    }

    @Test
    public void parseJson() throws Exception {




    }

}