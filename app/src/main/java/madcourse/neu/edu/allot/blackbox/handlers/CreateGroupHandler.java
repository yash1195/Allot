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
 * Created by zeko on 12/6/17.
 */

public class CreateGroupHandler {

    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public static void doCreate(final CreateGroupResponder responder, String userId, String userToken, String groupName) {

        params = new RequestParams();
        params.put("id", userId);
        params.put("token", userToken);
        params.put("groupName", groupName);

        Log.d("BackendBug", userId);
        Log.d("BackendBug", userToken);

        client.post(AllotApi.CREATE_GROUP, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.d("Backend", responseString);
                responder.onFailedCreateGroup("Error");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                SuccessfulCreateGroupResponse resp = SuccessfulCreateGroupResponse.parseJson(responseString);

                int status = resp.getStatus();

                if (status == 200) {

                    responder.onSuccessfulCreateGroup(resp.getCode());

                } else {
                    Log.d("Backend", responseString);
                    responder.onFailedCreateGroup("Create Group Failed");
                }
            }
        });

    }
}
