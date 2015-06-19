package de.fh_muenster.projectxx.soap;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.List;
import de.fh_muenster.projectxx.Project;
import de.project.dto.project.ProjectTO;
import de.project.enumerations.ProjectStatus;

/**
 * Diese Klasse stellt alle Service zum Thema Projecte bereit und bereitet die Soap verbindung vor
 * @author Dennis Russ
 * @version 1.0 Erstellt am 08.06.15
 */
public class ProjectService {

    /**
     * Diese Methode ruft die Projektliste zu meinem Gerät beim Server ab
     * @param userid    eigene Rufnummer
     * @return  ArrayList von Typ ProjectTo
     * @throws SoapFault
     */
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
                String status = object.getProperty("projectStatus").toString();
                switch(status) {
                    case "INTIME":
                        project.setProjectStatus(ProjectStatus.INTIME);
                        break;
                    case "DELAYED":
                        project.setProjectStatus(ProjectStatus.DELAYED);
                        break;
                    case "OUTOFTIME":
                        project.setProjectStatus(ProjectStatus.OUTOFTIME);
                        break;
                    default:
                        project.setProjectStatus(ProjectStatus.FINISHED);
                }

                String id = object.getProperty("id").toString();
                Long lid = Long.valueOf(id).longValue();
                long test = lid;
                project.setId(test);
                projecttoList.add(project);
            }
            return projecttoList;
        }
        catch (NullPointerException e){
            System.out.println("Es ist ein Fehler aufgetreten! " + e);
            return null;
        }


    }

    /**
     * Diese Methode erzeugt ein neues Projekt
     * @param project   Neu erzeugtes Projekt
     * @param userid    meine Telefonnummer
     * @throws SoapFault
     */
    public static void addProject(Project project, String userid) throws SoapFault {
        String method = "createProject";
        String title = project.getProjectname();
        String desc = project.getDescription();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,userid,title,desc);
    }

    /**
     * Diese Methode teilt dem Server änderungen an den Projektdaten mit
     * @param project   geändertes Projekt
     * @throws SoapFault
     */
    public static void updateProject(ProjectTO project) throws SoapFault {
        String method ="updateProject";
        String title = project.getProjectName();
        String desc = project.getDescription();
        long id = project.getId();
        String status = project.getProjectStatus().toString();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,id,title,desc,status);
    }

    /**
     * Diese Methode entfernt mich selbst aus dem Projekt (Projekt verlassen)
     * @param project   Aktuelles Projekt
     * @param userid    Meine Telefonnummer
     * @throws SoapFault
     */
    public static void removeMember(ProjectTO project,String userid) throws SoapFault {
        String method = "removeProjectMember";
        long id = project.getId();
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,id, userid);
    }

    /**
     * Diese Methode fügt einen Kintakt zum Projekt hinzu
     * @param phonenumber   Telefonnummer des Kontakts
     * @param projectid     Projeltid des Aktuellen Projektes
     * @throws SoapFault
     */
    public static void addUserToProject(String phonenumber, long projectid) throws SoapFault {
        String method = "addUserToProject";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,phonenumber,projectid);
    }
}
