package com.example.quick_cash.models;

/**
 * The type Application.
 */
public class Application {

    private String letter;
    private String applicantId;
    private String status;
    private String jobId;
    private String id;

    /**
     * Instantiates a new Job.
     */
    public Application() {
    }

    /**
     * Instantiates a new Job.
     *
     * @param applicantId the applicantId
     * @param letter      the cover letter
     * @param status      the status
     * @param jobId       the jobId
     */
    public Application(String applicantId, String letter, String status, String jobId) {
        this.applicantId = applicantId;
        this.letter = letter;
        this.status = status;
        this.jobId = jobId;
        this.id = "";
    }

    /**
     * Instantiates a new Job.
     *
     * @param applicantId the applicant id
     * @param letter      the letter
     * @param jobId       the jobId
     */
    public Application(String applicantId, String letter, String jobId) {
        this.applicantId = applicantId;
        this.letter = letter;
        this.status = "pending";
        this.jobId = jobId;
    }

    /**
     * Instantiates a new Application.
     *
     * @param applicantId the applicatant id
     * @param letter      the letter
     * @param status      the status
     * @param jobId       the jobID
     * @param id          the id
     */
    public Application(String applicantId, String letter, String status, String jobId, String id) {
        this.applicantId = applicantId;
        this.letter = letter;
        this.status = status;
        this.jobId = jobId;
        this.id = id;
    }

    /**
     * Gets ApplicantId.
     *
     * @return the ApplicantId
     */
    public String getApplicantId() {
        return applicantId;
    }

    /**
     * Gets letter.
     *
     * @return the letter
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Gets JobId.
     *
     * @return the JobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Gets Status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets ID.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Set status
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
