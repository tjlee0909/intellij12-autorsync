package boxautorsync;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

public class BackgroundTask extends Task.Backgroundable
{
    private final String RUNSYNC_COMMAND_PATH = "/box/www/runsync.command";
    private Project _project;

    public BackgroundTask(Project project)
    {
        super(project, "Background Task",false);
        this._project = project;
    }

    public void run(ProgressIndicator progressIndicator)
    {
        progressIndicator.setFraction(0.1);
        this.showMessage("Launching Rsync...", MessageType.INFO, this._project);
        progressIndicator.setFraction(0.2);

        try
        {
            progressIndicator.setFraction(0.3);
            Runtime.getRuntime().exec(RUNSYNC_COMMAND_PATH).waitFor();
        }
        catch (Exception e) { }

        progressIndicator.setFraction(1.0);
    }

    public void onSuccess()
    {
        VirtualFileManager.getInstance().syncRefresh();
        this.showMessage("Rsync succeeded.", MessageType.INFO, this._project);
    }

    private void showMessage(String message, MessageType messageType, Project project)
    {
        if (project == null)
        {
            return;
        }

        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);

        JBPopupFactory.getInstance().
                createHtmlTextBalloonBuilder(message, messageType, null).
                setFadeoutTime(1000).
                createBalloon().
                show(RelativePoint.getNorthWestOf(statusBar.getComponent()), Balloon.Position.atRight);
    }
}
