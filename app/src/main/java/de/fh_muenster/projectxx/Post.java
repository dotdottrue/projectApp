package de.fh_muenster.projectxx;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Diese klasse stellt einen Post einer diskussion da
 * Veraltete Klasse!
 * @author Niclas Christ
 * @author Dennis Russ
 * @version 1.0 Erstellt am 02.05.15
 */
public class Post implements Serializable {
    private String post;
    private Timestamp time;

    /**
     * Default konstruktor
     * @param post
     */
    public Post(String post) {
        this.post = post;
        //time = new java.sql.Timestamp(now.getTime());
    }

    /**
     * get Post
     * @return
     */
    public String getPost() {
        return post;
    }

    /**
     * get Timestamp
     * @return
     */
    public Timestamp getTime() {
        return time;
    }
}
