package madcourse.neu.edu.allot.blackbox.handlers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import madcourse.neu.edu.allot.blackbox.api.AllotApi;
import madcourse.neu.edu.allot.blackbox.responders.CreateGroupResponder;
import madcourse.neu.edu.allot.blackbox.response.SuccessfulCreateGroupResponse;

/**
 * Created by zeko on 12/13/17.
 */

public class MarkTaskAsDoneHandler {

    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public static void markAsDone(String userId, String userToken, String taskId) {

        params = new RequestParams();
        params.put("id", userId);
        params.put("token", userToken);
        params.put("taskId", taskId);

        client.post(AllotApi.MARK_TASK_AS_DONE, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.d("MarkTaskAsDone", "Could not mark as done");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                SuccessfulCreateGroupResponse resp = SuccessfulCreateGroupResponse.parseJson(responseString);

                int status = resp.getStatus();

                if (status == 200) {

                    Log.d("MarkTaskAsDone", "task marked as done");

                } else {
                    Log.d("MarkTaskAsDone", "Could not mark as done");
                }
            }
        });

    }
}
