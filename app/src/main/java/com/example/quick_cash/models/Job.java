package com.example.quick_cash.models;

public class Job {
    private String title;
    private String category;
    private String deadline;
    private String desc;
    private String userID;

    /**
     * Instantiates a new Job.
     */
    public Job() {
    }

    /**
     * Instantiates a new Job.
     *
     * @param title    the title
     * @param category the category
     * @param deadline the deadline
     * @param desc     the desc
     * @param userID   the user id
     */
    public Job(String title, String category, String deadline, String desc, String userID) {
        this.title = title;
        this.category = category;
        this.deadline = deadline;
        this.desc = desc;
        this.userID = userID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets deadline.
     *
     * @return the deadline
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets deadline.
     *
     * @param deadline the deadline
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets desc.
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets desc.
     *
     * @param desc the desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

}
