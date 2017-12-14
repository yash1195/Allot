package madcourse.neu.edu.allot.blackbox.handlers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import madcourse.neu.edu.allot.blackbox.api.AllotApi;
import madcourse.neu.edu.allot.blackbox.models.Task;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.RegisterResponder;
import madcourse.neu.edu.allot.blackbox.response.GenericResponse;
import madcourse.neu.edu.allot.blackbox.response.LoginResponse;

/**
 * Created by zeko on 12/13/17.
 */

public class CreateTaskHandler {

    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public static void createTask(Task taskToBeCreated, String requestorId, String requestorToken) {

        params = new RequestParams();

        // title
        params.put("title", taskToBeCreated.getTitle());

        // participants
        StringBuilder participantsRequestField = new StringBuilder("");
        for (User doer: taskToBeCreated.getParticipants()) {

            participantsRequestField.append(doer.getId() + ",");
        }
        params.put("participants", participantsRequestField);

        // isLocationEnabled
        if (taskToBeCreated.getIsLocationEnabled()) {
            params.put("isLocationEnabled", 1);
        } else {
            params.put("isLocationEnabled", 0);
        }

        // latitude
        params.put("latitude", taskToBeCreated.getLatitude());

        // longitude
        params.put("longitude", taskToBeCreated.getLongitude());

        // time
        params.put("time", taskToBeCreated.getTime());

        // requestor id
        params.put("id", requestorId);

        // requestor token
        params.put("token", requestorToken);

        // group code
        params.put("groupCode", taskToBeCreated.getGroupCode());


        client.post(AllotApi.CREATE_TASK, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.d("CreateTaskHandler", "Task not created" + responseString );
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                GenericResponse resp = GenericResponse.parseJson(responseString);

                int status = resp.getStatus();

                if (status == 200) {

                    Log.d("CreateTaskHandler", "Task created" + responseString );

                }
                else {
                    Log.d("CreateTaskHandler", "Task not created" + responseString );
                }
            }
        });
    }
}
