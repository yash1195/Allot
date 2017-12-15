package madcourse.neu.edu.allot.blackbox.handlers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import madcourse.neu.edu.allot.blackbox.api.AllotApi;
import madcourse.neu.edu.allot.blackbox.response.SuccessfulCreateGroupResponse;

/**
 * Created by zeko on 12/14/17.
 */

public class NudgeHandler {

    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public static void doNudge(String userId, String userToken, String taskId) {

        params = new RequestParams();
        params.put("id", userId);
        params.put("token", userToken);
        params.put("taskId", taskId);

        client.post(AllotApi.NUDGE_TASK_PARTICIPANTS, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.d("NudgeHandler", "Nudge Failed");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                SuccessfulCreateGroupResponse resp = SuccessfulCreateGroupResponse.parseJson(responseString);

                int status = resp.getStatus();

                if (status == 200) {

                    Log.d("NudgeHandler", "Nudge Done");

                } else {
                    Log.d("NudgeHandler", "Nudge Failed");
                }
            }
        });

    }
}
