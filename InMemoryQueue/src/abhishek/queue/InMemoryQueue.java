package abhishek.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * API Level design where one can register and unregister and start
 * 
 */
public class InMemoryQueue
{
    private ConsumerRegistery registery;

    private Producer producer;

    private ExecutionFlow executionFlow;

    private static InMemoryQueue queue;

    // Create Queue at first time with
    // ..default configurations
    // .. or read the configurations from property file
    private InMemoryQueue()
    {
        registery = new ConsumerRegisteryImpl();
        producer = new ProducerImpl();
        executionFlow = new ExecutionFlow();
    }

    public static InMemoryQueue getInMemoryQueue()
    {
        return (queue = new InMemoryQueue());
    }

    public void addConsumer(Consumer consumer)
    {
        if (null == queue)
            throw new RuntimeException("Please create In Memory Queue First");

        registery.doRegister(consumer, producer.throwQueue());
    }

    public void deleteConsumer(Consumer consumer)
    {
        if (null == queue)
            throw new RuntimeException("Please create In Memory Queue First");

        registery.unRegister(consumer);
    }

    public <T> void produceElements(T elements) throws InterruptedException
    {
        producer.doProduce(elements, executionFlow);
    }

    public void startConsuming() throws InterruptedException, ExecutionException
    {
        while (!isEmpty())
            registery.startConsuming(executionFlow, producer);
    }

    public void undoneQueue()
    {
        if (null == producer)
            return;
        BlockingQueue<Container> queue = producer.throwQueue();
        if (null == queue)
            return;
        queue.clear();
    }

    public void shutDownProcess()
    {
        try
        {
            undoneQueue();
            executionFlow.shutDown();
            await();
        }
        catch (InterruptedException e)
        {
            executionFlow.shutDown();
        }

    }

    public void await() throws InterruptedException
    {
        executionFlow.executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
    }

    public boolean isEmpty()
    {
        return producer.throwQueue().isEmpty();
    }
}
