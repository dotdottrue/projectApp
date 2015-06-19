package de.fh_muenster.projectxx;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Diese klasse verwaltet und erzeugt eine Diskussion
 * Ist veraltet!
 * @author Niclas Christ
 * @author Dennis Russ
 * @version 1.0 Erstellt am 02.06.15
 */
public class Forum implements Serializable {
    private String title;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private String question;
    private long forumid;

    /**
     * Default Konstruktor
     * @param title
     * @param question
     */
    public Forum(String title, String question){
        this.title = title;
        Post p = new Post(question);
        posts.add(p);
    }

    /**
     * Get Title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get Posts
     * @return
     */
    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**
     * Speichern eines Posts
     * @param post
     */
    public void savePost(String post) {
        Post p = new Post(post);
        posts.add(p);
    }

    /**
     * get Forumid
     * @return
     */
    public long getForumid() {
        return forumid;
    }

    /**
     * set Forumid
     * @param forumid
     */
    public void setForumid(long forumid) {
        this.forumid = forumid;
    }
}
