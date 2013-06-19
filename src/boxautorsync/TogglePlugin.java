package boxautorsync;

import com.intellij.openapi.actionSystem.*;

public class TogglePlugin extends AnAction
{
	public void actionPerformed(AnActionEvent e)
	{
        Settings.ToggleRsyncEnabled(e.getProject());
		this.update(e);
	}

	public void update(AnActionEvent e)
	{
		Presentation presentation = e.getPresentation();
		presentation.setText(Settings.IsRsyncEnabled(e.getProject()) ? "Disable Box Files synchronization" : "Enable Box Files synchronization");
		super.update(e);
	}
}