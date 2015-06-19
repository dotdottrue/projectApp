package de.fh_muenster.projectxx;

import java.io.Serializable;
/**
 * Diese klasse stellt ein Project dar
 * Veraltete Klasse
 * @author Niclas Christ
 * @author Dennis Russ
 * @version 1.0 Erstellt am 12.05.15
 */
public class Project implements Serializable {
    private String projectname;
    private String description;
    private long projectid;

    /**
     * Default konstruktor
     * @param projectname
     * @param description
     */
    public Project(String projectname, String description)
    {
        this.projectname = projectname;
        this.description = description;
    }

    /**
     * Get Describtion
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * get Projectname
     * @return
     */
    public String getProjectname() {
        return this.projectname;
    }

    /**
     * get ProjectId
     * @return
     */
    public long getProjectid() {
        return projectid;
    }

    /**
     * set ProjecID
     * @param projectid
     */
    public void setProjectid(long projectid) {
        this.projectid = projectid;
    }

    /**
     * Set Projectname
     * @param projectname
     */
    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    /**
     * set Describtion
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
