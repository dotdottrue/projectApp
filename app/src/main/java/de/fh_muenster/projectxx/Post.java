package de.fh_muenster.projectxx;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by user on 02.06.15.
 */
public class Post implements Serializable {
    private String post;
    private Timestamp time;

    public Post(String post) {
        this.post = post;
        //time = new java.sql.Timestamp(now.getTime());
    }

    public String getPost() {
        return post;
    }

    public Timestamp getTime() {
        return time;
    }
}
