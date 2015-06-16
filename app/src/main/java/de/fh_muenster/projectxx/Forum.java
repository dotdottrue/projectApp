package de.fh_muenster.projectxx;

/**
 * Created by user on 02.06.15.
 */
public class Forum {


    private String title;
    private String question;

    public Forum(String title, String question){
        this.title = title;
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }
}
