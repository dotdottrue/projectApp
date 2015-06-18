package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.projectxx.Forum;
import de.fh_muenster.projectxx.Project;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 09.06.15.
 */
public class ForumService {



    public static ArrayList<DiscussionTO> getForumList(ProjectTO project, String userid) throws SoapFault {
        long projectID = project.getId();
        String method = "getDiscussionsByProject";

        ArrayList<DiscussionTO> discussionList = new ArrayList<DiscussionTO>();
        ArrayList<DiscussionTO> discussionList1 = new ArrayList<DiscussionTO>();

        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL2,project.getId());


        //ArrayList zu Projekten Aufbauen
        for(int i = 0;i< result.getPropertyCount();i++)
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

    public static void addForum(DiscussionTO forum, String userid, ProjectTO project) throws SoapFault {
        String method = "addDiscussionToProject";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,project.getId(),forum.getTopic());

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
