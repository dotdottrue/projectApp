package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import de.fh_muenster.projectxx.Forum;
import de.fh_muenster.projectxx.Post;

/**
 * Created by user on 09.06.15.
 */
public class NoteService {

    public static ArrayList<Post> getPosts(Forum forum) throws SoapFault {
        String method = "getPosts";
        long forumid = forum.getForumid();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,forumid);
        ArrayList<Post> posts = (ArrayList<Post>) result.getPrimitiveProperty("Notelist");
        return posts;
    }

    public static ArrayList<Post> addPost(Forum forum, Post post) throws SoapFault {
        long forumid = forum.getForumid();
        String method = "addNote";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,forumid,post);
        ArrayList<Post> posts = (ArrayList<Post>) result.getPrimitiveProperty("Notelist");
        return posts;
    }
}
