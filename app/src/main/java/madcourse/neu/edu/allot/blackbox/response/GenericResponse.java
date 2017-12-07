package madcourse.neu.edu.allot.blackbox.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by zeko on 12/6/17.
 */

public class GenericResponse {

    private int status;
    private String msg;


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


    public static GenericResponse parseJson(String response) {

        Gson gson = new GsonBuilder().create();
        GenericResponse genericResponse = gson.fromJson(response, GenericResponse.class);

        return genericResponse;
    }
}
