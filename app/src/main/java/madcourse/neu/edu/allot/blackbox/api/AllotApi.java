package madcourse.neu.edu.allot.blackbox.api;

/**
 * Created by zeko on 11/28/17.
 */

public class AllotApi {

    public static final String BASE_URL = "http://allot.zeko.in/api/";

    // login
    public static final String LOGIN_URL = BASE_URL + "user/login";

    // register
    public static final String REGISTER_URL = BASE_URL + "user/register";

    // fetch groups that user has joined
    public static final String FETCH_USER_GROUPS = BASE_URL + "group/joined";

    // create group
    public static final String CREATE_GROUP = BASE_URL + "group/create";
}
