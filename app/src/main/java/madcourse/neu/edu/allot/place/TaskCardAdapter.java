package madcourse.neu.edu.allot.place;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.models.Task;

/**
 * Created by zeko on 12/7/17.
 */

public class TaskCardAdapter extends BaseAdapter implements ListAdapter {


    private List<Task> taskList = new ArrayList<>();
    private Context context;
    private int layout;
    private Class activity;

    public TaskCardAdapter(List<Task> list, Context context, int layout, Class activity) {

        this.taskList = list;
        this.context = context;
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int pos) {
        return taskList.get(pos);
    }

    @Override
    public long getItemId(int pos) {

        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView) view.findViewById(R.id.label_card_title);

        listItemText.setText(taskList.get(position).getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity);

                intent.putExtra("taskData", taskList.get(position));

                context.startActivity(intent);
            }
        });

        return view;
    }

    public void refreshTasks(List<Task> tasks) {
        this.taskList.clear();
        this.taskList.addAll(tasks);
        notifyDataSetChanged();
    }
}
