package madcourse.neu.edu.allot.blackbox.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable {

    private String title = "";
    private String description = "";
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String time = "";
    private String id = "";
    private String groupCode = "";
    private boolean isLocationEnabled = false;
    private boolean isDone = false;
    private int repetition = 0;
    List<User> participants;
    List<User> creator;

    public Task(){
        participants = new ArrayList<>();
        creator = new ArrayList<>();
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public boolean getIsLocationEnabled() {
        return isLocationEnabled;
    }
    public void setIsLocationEnabled(boolean isLocationEnabled) {
        this.isLocationEnabled = isLocationEnabled;
    }

    public boolean getIsDone() {
        return isDone;
    }
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public int getRepetition() {
        return repetition;
    }
    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public List<User> getParticipants() {
        return participants;
    }
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<User> getCreator() {
        return creator;
    }
    public void setCreator(List<User> creator) {
        this.creator= creator;
    }

}
