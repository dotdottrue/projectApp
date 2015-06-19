package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import de.fh_muenster.projectxx.Forum;
import de.fh_muenster.projectxx.Post;

import de.project.dto.discussion.DiscussionTO;
import de.project.dto.note.NoteTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse stellt alle Service zum Thema Diskussionsposts bereit und bereitet die Soap verbindung vor
 * @author Dennis Russ
 * @version 1.0 Erstellt am 09.06.15
 */
public class NoteService {

    /**
     * Diese Methode ruft alle Posts zu einer Diskussion ab
     * @param forum Aktuelle Diskussiom
     * @param userid    eigene Rufnummer
     * @return  ArrayList<NoteTo>
     * @throws SoapFault
     */
    public static ArrayList<NoteTO> getPosts(DiscussionTO forum, String userid) throws SoapFault {
        String method = "getNotesByDiscussion";
        long forumid = forum.getId();
        ArrayList<NoteTO> posts = new ArrayList<NoteTO>();
        try{
            SoapObject result = (SoapObject) SoapService.executeSoapAction(method, SoapService.URL2, forumid);
            for (int i = 1; i < result.getPropertyCount(); i++) {
                SoapObject object = (SoapObject) result.getProperty(i);
                NoteTO note = new NoteTO();
                note.setNote(object.getProperty("note").toString());
                String id = object.getProperty("id").toString();
                Long lid = Long.valueOf(id).longValue();
                long test = lid;
                note.setId(test);
                note.setUser(object.getProperty("user").toString());
                posts.add(note);
            }
            return posts;
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Diese Methode fügt einer Diskussion einen Post hinzu
     * @param forum Aktuelle Diskussion
     * @param post  Neuer Post
     * @param userid    Eigene Rufnummer
     * @throws SoapFault
     */
    public static void addPost(DiscussionTO forum, NoteTO post, String userid) throws SoapFault {
        long forumid = forum.getId();
        String method = "addNoteToDiscussion";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,forumid,post.getNote(),userid);
    }
}
