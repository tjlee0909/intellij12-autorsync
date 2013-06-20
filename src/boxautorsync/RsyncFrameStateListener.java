package boxautorsync;

import com.intellij.ide.DataManager;
import com.intellij.ide.FrameStateListener;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.impl.http.RemoteFileManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

import javax.swing.*;

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

    /**
     * In order to avoid having your unsaved changes on your local get wiped out
     * when the autorsync kicks in, we must save all unsaved files AND push these
     * changes to the remote.
     */
    public void onFrameDeactivated()
    {
        if (!Settings.IsRsyncEnabled(this.getFirstOpenProject()))
        {
            return;
        }

        FileDocumentManager.getInstance().saveAllDocuments();
        this.uploadToRemoteHost();
    }

    private void uploadToRemoteHost()
    {
        ActionManager.getInstance().getAction("PublishGroup.Upload").actionPerformed(
            new AnActionEvent(
                    null,
                    DataManager.getInstance().getDataContext(),
                    ActionPlaces.UNKNOWN,
                    new Presentation(),
                    ActionManager.getInstance(),
                    0
            )
        );
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
