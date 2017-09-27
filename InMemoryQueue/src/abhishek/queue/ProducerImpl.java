package abhishek.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ProducerImpl implements Producer
{
    private BlockingQueue<Container> message;

    /**
     * 1. Create Message queue {@inheritDoc}
     * @throws InterruptedException
     */
    @Override
    public <T> void doProduce(T container, ExecutionFlow flow) throws InterruptedException
    {
        checkMessageQueue();
        message.put((Container) container);

    }

    private void checkMessageQueue()
    {
        if (null == message || message.isEmpty())
        {
            message = new ArrayBlockingQueue<Container>(5);
        }

    }

    /**
     * User Defined capacity to fill message in queue,
     */
    public void createQueue(int capacity)
    {
        message = new ArrayBlockingQueue<Container>(capacity);

    }

    public BlockingQueue<Container> throwQueue()
    {
        return message;
    }

}
