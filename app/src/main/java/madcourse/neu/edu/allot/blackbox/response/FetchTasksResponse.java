package madcourse.neu.edu.allot.blackbox.response;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.blackbox.models.Task;

/**
 * Created by zeko on 12/7/17.
 */

public class FetchTasksResponse {

    private int status;
    private String msg;
    private List<Task> tasks;

    public FetchTasksResponse() {
        tasks = new ArrayList<>();
    }



    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMsg() { return msg; }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> value) {
        tasks = value;
    }

    public static FetchTasksResponse parseJson(String response) {

        Gson gson = new GsonBuilder().create();

        FetchTasksResponse fetchTasksResponse = gson.fromJson(response, FetchTasksResponse.class);

        return fetchTasksResponse;
    }
}
