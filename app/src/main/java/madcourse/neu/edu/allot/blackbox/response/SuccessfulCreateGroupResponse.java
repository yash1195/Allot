package madcourse.neu.edu.allot.blackbox.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zeko on 12/6/17.
 */

public class SuccessfulCreateGroupResponse {

    private int status;
    private String msg;
    private String code;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public static SuccessfulCreateGroupResponse parseJson(String response) {

        Gson gson = new GsonBuilder().create();
        SuccessfulCreateGroupResponse successfulCreateGroupResponse = gson.fromJson(response, SuccessfulCreateGroupResponse.class);

        return successfulCreateGroupResponse;
    }
}
