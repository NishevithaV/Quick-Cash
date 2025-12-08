package com.example.quick_cash.models;

import java.io.Serializable;

/**
 * The type Job.
 */
public class Job implements Serializable {
    private String title;
    private String category;
    private String location;
    private String deadline;
    private String desc;
    private String userID;
    private String id;
    private double latitude;
    private double longitude;

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
     * @param location the location
     * @param deadline the deadline
     * @param desc     the desc
     * @param userID   the user id
     */
    public Job(String title, String category, String location, String deadline, String desc, String userID) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.deadline = deadline;
        this.desc = desc;
        this.userID = userID;
        this.id = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    /**
     * Instantiates a new Job.
     *
     * @param title    the title
     * @param category the category
     * @param location the location
     * @param deadline the deadline
     * @param desc     the desc
     * @param userID   the user id
     * @param id       the job id
     */
    public Job(String title, String category, String location, String deadline, String desc, String userID, String id) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.deadline = deadline;
        this.desc = desc;
        this.userID = userID;
        this.id = id;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    /**
     * Instantiates a new Job with coordinates.
     *
     * @param title     the title
     * @param category  the category
     * @param location  the location
     * @param deadline  the deadline
     * @param desc      the desc
     * @param userID    the user id
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    public Job(String title, String category, String location, String deadline, String desc, String userID, double latitude, double longitude) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.deadline = deadline;
        this.desc = desc;
        this.userID = userID;
        this.latitude = latitude;
        this.longitude = longitude;
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
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
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

    /**
     * Get Job Id
     *
     * @return the job id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
