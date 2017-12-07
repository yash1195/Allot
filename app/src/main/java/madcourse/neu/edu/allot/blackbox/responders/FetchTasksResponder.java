package madcourse.neu.edu.allot.blackbox.responders;

import java.util.List;

import madcourse.neu.edu.allot.blackbox.models.Task;

/**
 * Created by zeko on 12/7/17.
 */
public interface FetchTasksResponder {

    void onSuccessFulTaskFetch(List<Task> tasks);
    void onFailedTaskFetch(String msg);
}
