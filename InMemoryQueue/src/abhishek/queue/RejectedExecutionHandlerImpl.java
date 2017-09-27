package abhishek.queue;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler
{

    /** {@inheritDoc} */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
    {
        executor.submit(r);

    }

}
