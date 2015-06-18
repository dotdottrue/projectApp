package de.fh_muenster.projectxx.soap;


import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.projectxx.Project;
import de.project.dto.project.ProjectResponse;
import de.project.dto.project.ProjectTO;
import de.project.dto.project.ProjectsResponse;

/**
 * Created by user on 08.06.15.
 */
public class ProjectService {



    public static ArrayList<ProjectTO> getProjectList(String userid) throws SoapFault {
        List<ProjectTO> projectList = null;
        try{
            String method = "getProjectsByPhone";
            ArrayList<ProjectTO> projecttoList = new ArrayList<ProjectTO>();

            SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL2,userid);


            //ArrayList zu Projekten Aufbauen
            for(int i = 2;i< result.getPropertyCount();i++)
            {
                SoapObject object = (SoapObject) result.getProperty(i);
                ProjectTO project = new ProjectTO();
                project.setProjectName(object.getProperty("projectName").toString());
                project.setDescription(object.getProperty("description").toString());
                String id = object.getProperty("id").toString();
                Long lid = Long.valueOf(id).longValue();
                long test = lid;
                project.setId(test);
                projecttoList.add(project);
            }

            return projecttoList;

        }
        catch (NullPointerException e){
            System.out.println("Error ALTER! " + e);
            return null;
        }


    }

    public static void addProject(Project project, String userid) throws SoapFault {
        String method = "createProject";
        String title = project.getProjectname();
        String desc = project.getDescription();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,userid,title,desc);
        //Response code "OK", "Error"

    }

    public static void updateProject(Project project, String userid) throws SoapFault {
        String method ="updateProject";
        String title = project.getProjectname();
        String desc = project.getDescription();
        long id = project.getProjectid();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,id,title,desc);
    }

    public static void updateStatus(Project project, String status) throws SoapFault {
        String method = "updateStatus";
        long id = project.getProjectid();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,id,status);
    }

    public static void removeMember(ProjectTO project,String userid) throws SoapFault {
        String method = "removeProjectMember";
        long id = project.getId();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,id, userid);
    }

    public static void addUserToProject(String phonenumber, long projectid) throws SoapFault {
        String method = "addUserToProject";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,phonenumber,projectid);
    }
}
