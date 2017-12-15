package madcourse.neu.edu.allot.blackbox.models;

import java.io.Serializable;

/**
 * User model.
 */
public class User implements Serializable {

    public static final String SHARED_PREF_GROUP = "LOGGED_IN_USER_DETAILS";
    public static final String SHARED_PREF_TAG_FIRST_NAME = "LOGGED_IN_USER_FIRST_NAME";
    public static final String SHARED_PREF_TAG_LAST_NAME = "LOGGED_IN_USER_LAST_NAME";
    public static final String SHARED_PREF_TAG_EMAIL = "LOGGED_IN_USER_EMAIL";
    public static final String SHARED_PREF_TAG_TOKEN = "LOGGED_IN_USER_TOKEN";
    public static final String SHARED_PREF_TAG_ID = "LOGGED_IN_USER_ID";
    public static final String SHARED_PREF_TAG_DEVICE_ID = "LOGGED_IN_DEVICE_TOKEN";
    public static final String SHARED_PREF_TAG_LOGGED_IN_SESSION = "0";

    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String id = "";
    private String token = "";


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


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


}
