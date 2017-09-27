package abhishek.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;


public interface ConsumerRegistery
{
    public void doRegister(Consumer consumer, BlockingQueue<Container> queue);

    public void unRegister(Consumer consumer);

    public void startConsuming(ExecutionFlow flow, Producer producer) throws InterruptedException, ExecutionException;
}
