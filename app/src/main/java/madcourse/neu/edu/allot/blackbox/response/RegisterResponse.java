package madcourse.neu.edu.allot.blackbox.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Captures response on register.
 */

public class RegisterResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private Integer status;
    private String msg;
    private String token;


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


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


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public static RegisterResponse parseJson(String response) {

        Gson gson = new GsonBuilder().create();
        RegisterResponse registerResponse = gson.fromJson(response, RegisterResponse.class);

        return registerResponse;
    }
}
