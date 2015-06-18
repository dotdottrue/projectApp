package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import de.fh_muenster.projectxx.Forum;
import de.fh_muenster.projectxx.Post;
import de.project.dto.NoteTO;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 09.06.15.
 */
public class NoteService {

    public static ArrayList<NoteTO> getPosts(DiscussionTO forum, String userid) throws SoapFault {
        String method = "getNotesByDiscussion";
        long forumid = forum.getId();
        ArrayList<NoteTO> posts = new ArrayList<NoteTO>();

        try{

            SoapObject result = (SoapObject) SoapService.executeSoapAction(method, SoapService.URL2, forumid);

            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject object = (SoapObject) result.getProperty(i);
                NoteTO note = new NoteTO();
                note.setNote(object.getProperty("note").toString());
                String id = object.getProperty("id").toString();
                Long lid = Long.valueOf(id).longValue();
                long test = lid;
                note.setId(test);
                posts.add(note);
            }


            return posts;
        }
        catch (Exception e){
            return null;
        }
    }

    public static void addPost(DiscussionTO forum, NoteTO post, String userid) throws SoapFault {
        long forumid = forum.getId();
        String method = "addNoteToDiscussion";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,forumid,post.getNote(),userid);

    }
}
