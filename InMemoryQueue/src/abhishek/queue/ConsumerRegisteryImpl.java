package abhishek.queue;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class ConsumerRegisteryImpl implements ConsumerRegistery
{
    private final CopyOnWriteArrayList<Consumer> consumers = new CopyOnWriteArrayList<Consumer>();

    /** {@inheritDoc} */
    @Override
    public void doRegister(Consumer consumer, BlockingQueue<Container> queue)
    {
        consumers.add(consumer);
        consumer.attachWork(queue);
    }

    /** {@inheritDoc} */
    @Override
    public void unRegister(Consumer consumer)
    {
        consumers.remove(consumer);
    }

    @Override
    public void startConsuming(ExecutionFlow flow, Producer producer) throws InterruptedException, ExecutionException
    {
        List<Future<String>> invokeAll = flow.invokeAll(consumers);
        for (Future<String> result : invokeAll)
        {
            String state = result.get();
            if (result.isDone())
            {
                System.out.println("Task is computed with state " + state);
            }
            else
            {
                // Submit that task again
                // flow.execute(task);
                System.out.println("Task is Not Computed with state " + state);
            }
        }

    }

}
