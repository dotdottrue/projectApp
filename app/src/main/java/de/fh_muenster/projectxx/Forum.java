package de.fh_muenster.projectxx;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 02.06.15.
 */
public class Forum implements Serializable {


    private String title;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private String question;

    public Forum(String title, String question){
        this.title = title;
        Post p = new Post(question);
        posts.add(p);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void savePost(String post) {
        Post p = new Post(post);
        posts.add(p);
    }
}
