package de.fh_muenster.projectxx;

import java.io.Serializable;
/**
 * Created by user on 12.05.15.
 */
public class Project implements Serializable {
    private String projectname;
    private String description;
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
}
