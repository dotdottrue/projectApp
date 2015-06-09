package de.fh_muenster.projectxx;

import java.io.Serializable;
/**
 * Created by user on 12.05.15.
 */
public class Project implements Serializable {
    private String projectname;
    private String description;
    private long projectid;
    //private String Projectstatus enum{"IDEAL","DELAYED","OUTOFTIME","FINISHED"};
    //Project members
    //calendar (Meilensteine,Termine)

    //
    public Project(String projectname, String description)
    {
        this.projectname = projectname;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getProjectname() {
        return this.projectname;
    }

    public long getProjectid() {
        return projectid;
    }

    public void setProjectid(long projectid) {
        this.projectid = projectid;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
