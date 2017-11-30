package madcourse.neu.edu.allot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskFragment extends Fragment {

    private ListView taskList;
    private ArrayList<String> list;
    private CardAdapter cardAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        list = new ArrayList<>();
        list.add("Task 1");
        list.add("Task 2");
        cardAdapter = new CardAdapter(list, rootView.getContext(), R.layout.card_task, TaskActivity.class);
        taskList = rootView.findViewById(R.id.list_tasks);
        taskList.setAdapter(cardAdapter);

        return rootView;
    }
}
