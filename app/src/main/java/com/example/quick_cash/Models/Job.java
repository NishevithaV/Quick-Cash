package com.example.quick_cash.Models;

public class Job {
    private String title;
    private String category;
    private String deadline;
    private String desc;
    private String userID;

    public Job() {
    }

    public Job(String title, String category, String deadline, String desc, String userID) {
        this.title = title;
        this.category = category;
        this.deadline = deadline;
        this.desc = desc;
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
//  Map<String, String> jobObject = new HashMap<>();
//            jobObject.put("title", enteredJobName);
//            jobObject.put("deadline", enteredApplicationDeadline);
//            jobObject.put("desc", enteredJobDescription);
}
