package de.fh_muenster.projectxx.Interfaces;

import java.util.ArrayList;
import java.util.List;

import de.project.dto.discussion.DiscussionTO;

/**
 * Created by user on 16.06.15.
 */
public interface AsyncResponse {
    void processFinish(List<DiscussionTO> output);
}
