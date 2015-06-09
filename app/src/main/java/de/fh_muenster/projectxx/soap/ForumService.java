package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import de.fh_muenster.projectxx.Forum;
import de.fh_muenster.projectxx.Project;

/**
 * Created by user on 09.06.15.
 */
public class ForumService {



    public static ArrayList<Forum>  getForumList(Project project) throws SoapFault {
        long projectID = project.getProjectid();
        String method = "getForumList";

        SoapObject result = (SoapObject)SoapService.executeSoapAction(method, SoapService.URL, projectID);
        ArrayList<Forum> forumlist = (ArrayList<Forum>)result.getPrimitiveProperty("forumlist");
        return forumlist;
    }

    public static void addForum(Forum forum) throws SoapFault {
        String method = "addForum";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,forum);

    }

    public static void updateForum(Forum forum) throws SoapFault {
        String method = "updateForum";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,forum);
    }

    public static void deleteForum(Forum forum) throws SoapFault {
        String method = "deleteForum";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,forum);
    }
}
