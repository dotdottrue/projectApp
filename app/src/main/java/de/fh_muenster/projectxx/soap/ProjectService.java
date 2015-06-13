package de.fh_muenster.projectxx.soap;


import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import de.fh_muenster.projectxx.Project;

/**
 * Created by user on 08.06.15.
 */
public class ProjectService {



    public static ArrayList<Project> getProjectList(String userid) throws SoapFault {
        String method = "giveProjects";


        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,userid);
        ArrayList<Project> projects = (ArrayList<Project>)result.getPrimitiveProperty("List");
        return projects;

    }

    public static void addProject(Project project, String userid) throws SoapFault {
        String method = "createProject";
        String title = project.getProjectname();
        String desc = project.getDescription();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,userid,title,desc);
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

    public static void deleteProject(Project project) throws SoapFault {
        String method = "deleteProject";
        long id = project.getProjectid();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,id);
    }
}
