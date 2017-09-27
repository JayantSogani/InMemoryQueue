package abhishek.queue;

import java.util.concurrent.BlockingQueue;


public interface Producer
{
    public <T> void doProduce(T message, ExecutionFlow flow) throws InterruptedException;

    public BlockingQueue<Container> throwQueue();
}
