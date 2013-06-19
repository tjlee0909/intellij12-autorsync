package boxautorsync;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.MessageType;

/**
 * This guy is responsible for performing the actual rsync.
 */
public class Rsync
{
    public void Execute()
    {
        Project project = this.getFirstOpenProject();
        BackgroundTask task = new BackgroundTask(project);
        task.queue();
    }

    /**
     * Just get me the first open project if there is one, and null otherwise.
     */
    private Project getFirstOpenProject()
    {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        return (projects.length > 0) ? projects[0] : null;
    }
}
