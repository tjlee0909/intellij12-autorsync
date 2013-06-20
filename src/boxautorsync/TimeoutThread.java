package boxautorsync;

public class TimeoutThread
{
    private String _command;
    private long _timeout;

    public TimeoutThread(String command, long timeout)
    {
        this._command = command;
        this._timeout = timeout;
    }

    public boolean Execute()
    {
        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(this._command);
            Worker worker = new Worker(process);
            worker.start();

            try
            {
                worker.join(this._timeout);

                if (worker.exit != null)
                {
                    return true;
                }
            }
            catch(InterruptedException ex)
            {
                worker.interrupt();
                Thread.currentThread().interrupt();
            }
            finally
            {
                process.destroy();
            }
        }
        catch (Exception e) { }

        return false;
    }

    private class Worker extends Thread
    {
        private final Process process;
        private Integer exit;

        private Worker(Process process)
        {
            this.process = process;
        }

        public void run()
        {
            try
            {
                exit = process.waitFor();
            }
            catch (InterruptedException ignore)
            {
                return;
            }
        }
    }
}
