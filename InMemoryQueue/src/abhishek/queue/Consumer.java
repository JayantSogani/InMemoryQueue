package abhishek.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;


public interface Consumer extends Callable<String>
{
    public void attachWork(BlockingQueue<Container> containers);
}
