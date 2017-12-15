package madcourse.neu.edu.allot.place;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.handlers.FetchGroupTasksHandler;
import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.blackbox.models.Task;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.FetchTasksResponder;
import madcourse.neu.edu.allot.task.TaskActivity;

import static android.content.Context.MODE_PRIVATE;

public class TaskFragment extends Fragment implements FetchTasksResponder {

    private ListView taskList;
    private List<Task> list;
    private TaskCardAdapter cardAdapter;
    Group groupData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        groupData = (Group) getArguments().getSerializable("groupData");

        SharedPreferences sharedPref = getContext().getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);

        String userId = sharedPref.getString(User.SHARED_PREF_TAG_ID, "NA");
        String userToken = sharedPref.getString(User.SHARED_PREF_TAG_TOKEN, "NA");

        FetchGroupTasksHandler.doFetch(this, userId, userToken, groupData.getCode());


        list = new ArrayList<>();

        cardAdapter = new TaskCardAdapter(list, rootView.getContext(), R.layout.card_task, TaskActivity.class);
        taskList = rootView.findViewById(R.id.list_tasks);
        taskList.setAdapter(cardAdapter);

        return rootView;
    }

    @Override
    public void onSuccessFulTaskFetch(List<Task> tasks) {

        cardAdapter.refreshTasks(tasks);
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getContext().getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);

        String userId = sharedPref.getString(User.SHARED_PREF_TAG_ID, "NA");
        String userToken = sharedPref.getString(User.SHARED_PREF_TAG_TOKEN, "NA");
        FetchGroupTasksHandler.doFetch(this, userId, userToken, groupData.getCode());
    }




    @Override
    public void onFailedTaskFetch(String msg) {

    }
}
