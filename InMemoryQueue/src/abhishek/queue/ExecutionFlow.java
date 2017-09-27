package abhishek.queue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ExecutionFlow
{
    ThreadPoolExecutor executor = null;

    public ExecutionFlow()
    {
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

        // Should be configured from outside may be property file
        executor = new ThreadPoolExecutor(
            5,
            10,
            5,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(3));

        executor.setRejectedExecutionHandler(rejectionHandler);
    }

    public void execute(Callable<String> task)
    {
        executor.submit(task);
    }

    /**
     * Gracefully shuts it down
     */
    public void shutDown()
    {
        executor.shutdown();
    }

    public void shutDownWithoutGraceFully()
    {
        executor.shutdownNow();
    }

    /**
     * Invokes all the consumer at one go
     */
    public List<Future<String>> invokeAll(CopyOnWriteArrayList<Consumer> consumers) throws InterruptedException
    {
        return executor.invokeAll(consumers);

    }
}
