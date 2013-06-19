package boxautorsync;

import com.intellij.ide.FrameStateListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

public class RsyncFrameStateListener implements FrameStateListener
{
    public RsyncFrameStateListener() { }

    public void onFrameActivated()
    {
        Project project = this.getFirstOpenProject();

        if (project == null || !Settings.IsRsyncEnabled(project))
        {
            return;
        }

        new Rsync().Execute();
    }

    public void onFrameDeactivated() { }

    /**
     * Just get me the first open project if there is one, and null otherwise.
     */
    private Project getFirstOpenProject()
    {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        return (projects.length > 0) ? projects[0] : null;
    }
}
