package de.fh_muenster.projectxx.soap;


import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import de.fh_muenster.projectxx.Project;

/**
 * Created by user on 08.06.15.
 */
public class ProjectService {
    private Project project;
    private Long userid;

    public ProjectService(Long userid){

        this.userid = userid;
    }


    public ArrayList<Project> getProjectList() throws SoapFault {
        String method = "giveProjects";


        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,this.userid);
        ArrayList<Project> projects = (ArrayList<Project>)result.getPrimitiveProperty("List");
        return projects;

    }

    public void addProject(Project project) throws SoapFault {
        String method = "addProject";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,this.userid,project);

    }

    public void updateProject(Project project) throws SoapFault {
        String method ="updateProject";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,project);
    }
}
