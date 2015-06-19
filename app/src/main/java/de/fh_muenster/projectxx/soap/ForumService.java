package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse stellt alle Service zum Thema Diskussionen bereit und bereitet die Soap verbindung vor
 * @author Dennis Russ
 * @version 1.0 Erstellt am 09.06.15
 */
public class ForumService {

    /**
     * Diese Methode erzeugt eine Diskussionsliste mit allen Diskussionen zum Projekt
     * @param project   Aktuelles Projekt
     * @param userid    Die eigene Telefonnummer
     * @return
     * @throws SoapFault    Wirft sopa Exceptions
     */
    public static ArrayList<DiscussionTO> getForumList(ProjectTO project, String userid) throws SoapFault {
        long projectID = project.getId();
        String method = "getDiscussionsByProject";
        ArrayList<DiscussionTO> discussionList = new ArrayList<DiscussionTO>();
        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL2,project.getId());
        //ArrayList zu Projekten Aufbauen
        for(int i = 1;i< result.getPropertyCount();i++)
        {
            SoapObject object = (SoapObject) result.getProperty(i);
            DiscussionTO discussion = new DiscussionTO();
            discussion.setTopic(object.getProperty("topic").toString());
            String id = object.getProperty("id").toString();
            Long lid = Long.valueOf(id).longValue();
            long test = lid;
            discussion.setId(test);
            discussionList.add(discussion);
        }
        return discussionList;
    }

    /**
     * Diese Methode erzeugt eine neue Diskussion auf dem Server
     * @param forum Erzeugte Diskussion
     * @param userid    Die eigene Telefonnummer
     * @param project   Aktuelles Projekt
     * @throws SoapFault    Wirft soap Exceptions
     */
    public static void addForum(DiscussionTO forum, String userid, ProjectTO project) throws SoapFault {
        String method = "addDiscussionToProject";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,project.getId(),forum.getTopic());
    }
}
