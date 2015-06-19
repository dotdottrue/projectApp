package de.fh_muenster.projectxx;

import java.io.Serializable;

/**
 * Diese klasse verwaltet Appointments
 * @author Niclas Christ
 * @version 1.0 Erstellt am 06.06.15
 */
public class Appointment implements Serializable {
    //Alle Appointment Datentypen
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private String name;
    private String desc;

    /**
     * Nur Day/Month/Year werden im Konstruktor gesetzt,
     * da das Calendar-Widget diese über die grafische Oberfläche bereit stellt
     * @param day
     * @param month
     * @param year
     */
    public Appointment(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }


    /**
     * Set day
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * get Day
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * set Month
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * get Month
     * @return
     */
    public int getMonth() {
        return month;
    }

    /**
     * set Year
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * get Year
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * Set Hour
     * @param hour
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * get Hour
     * @return
     */
    public int getHour() {
        return hour;
    }

    /**
     * set Minute
     * @param minute
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * get Minute
     * @return
     */
    public int getMinute() {
        return minute;
    }

    /**
     * set Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set Describtion
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * get Describtion
     * @return
     */
    public String getDesc() {
        return desc;
    }
}
