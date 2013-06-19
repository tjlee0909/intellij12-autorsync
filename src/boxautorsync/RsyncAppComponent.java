package boxautorsync;

import com.intellij.ide.FrameStateListener;
import com.intellij.ide.FrameStateManager;
import com.intellij.openapi.components.ApplicationComponent;

public class RsyncAppComponent implements ApplicationComponent
{
    private FrameStateListener _frameStateListener;

    public void initComponent()
    {
        FrameStateManager.getInstance().addListener(this._getRsyncFrameStateListnener());
    }

    public void disposeComponent()
    {
        FrameStateManager.getInstance().removeListener(this._getRsyncFrameStateListnener());
    }

    public String getComponentName()
    {
        return "BoxRsyncAppComponent";
    }

    private FrameStateListener _getRsyncFrameStateListnener()
    {
        if (this._frameStateListener == null)
        {
            this._frameStateListener = new RsyncFrameStateListener();
        }

        return this._frameStateListener;
    }
}
