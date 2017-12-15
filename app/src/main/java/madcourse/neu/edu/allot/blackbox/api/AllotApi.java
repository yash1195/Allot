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

    // join group
    public static final String JOIN_GROUP = BASE_URL + "group/join";

    // get group tasks
    public static final String FETCH_GROUP_TASKS = BASE_URL + "task/getGroupTasks";

    // create a task
    public static final String CREATE_TASK = BASE_URL + "task/create";

    // mark task as done
    public static final String MARK_TASK_AS_DONE = BASE_URL + "task/markAsDone";

    // nudge task participants
    public static final String NUDGE_TASK_PARTICIPANTS = BASE_URL + "task/nudgeParticipants";
}
