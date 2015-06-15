package de.fh_muenster.projectxx;

import java.io.Serializable;

/**
 * Created by Nic on 06.06.15.
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

    //Nur Day/Month/Year werden im Konstruktor gesetzt,
    // da das Calendar-Widget diese über die grafische Oberfläche bereit stellt
    public Appointment(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }


    //Getter und Setter
    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
