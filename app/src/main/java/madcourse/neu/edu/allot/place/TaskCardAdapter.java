package madcourse.neu.edu.allot.place;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
        TextView timeText = (TextView) view.findViewById(R.id.tv_date_time);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox_done);

        Task task = taskList.get(position);

        listItemText.setText(task.getTitle());

        if (task.getDescription() == null || task.getDescription().equals("")) {
            description.setText("No Description Available");
        } else {
            description.setText(task.getDescription());
        }

        if (task.getIsDone()) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }

        long unixSeconds = Long.parseLong(task.getTime());
        Date date = new Date(unixSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        String formattedDate = sdf.format(date);
        timeText.setText(formattedDate);

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
