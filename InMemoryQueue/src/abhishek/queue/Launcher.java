package abhishek.queue;

import java.util.concurrent.ExecutionException;

import org.json.JSONObject;


public class Launcher
{

    public static void main(String... args) throws InterruptedException, CloneNotSupportedException, ExecutionException
    {
        InMemoryQueue queue = InMemoryQueue.getInMemoryQueue();
        
        Launcher launch = new Launcher();
        launch.noExpressionAttached(queue);

        // start consuming
        
        queue.startConsuming();

        // Shut Down process starts
        queue.shutDownProcess();

    }

    /**
     * One expression to one consumer and its own specific rule to handle the queue
     * Submitting the task in the Queue where listeners are listening
     * Scenario : two task with expression of "abc" and "def" Where "def" is been pulled by
     * consumer 4 with strategy to remove the task as soon its finished.
     * where as task expression "abc" will be executed first by consumer1, consumer2 then
     * only it will get picked by consumer3
     * One can register his own rule to work.
     * @throws CloneNotSupportedException
     */
    public void noExpressionAttached(InMemoryQueue queue) throws InterruptedException, CloneNotSupportedException
    {

    	JSONObject jsonMessage=new JSONObject();
        for(int i=1;i<=4;i++)
        	jsonMessage.put("msg"+i, i);
        
        System.out.println(jsonMessage);
        
        
        StopWatchAndWorkRule rule1 = new StopWatchAndWorkRule(false, 2);   
        StopWatchAndWorkRule rule2 = (StopWatchAndWorkRule) rule1.clone();
        rule2.setProcessBefore(true);

        StopWatchAndWorkRule rule3 = (StopWatchAndWorkRule) rule1.clone();
        rule3.setProcessBefore(true);

        PollRule pollRule = new PollRule();
        //poll rule is an extra rule I added for consumer 4 
        ConsumerIdentity consumer4 = new ConsumerIdentity.ConsumerBuilder("consumer4")
            .setExpression("def")
            .setIsRulesRequired(true)
            .setRules(pollRule)
            .build();

        ConsumerIdentity consumer1 = new ConsumerIdentity.ConsumerBuilder("consumer1")
            .setExpression("abc")
            .setIsRulesRequired(true)
            .setRules(rule2)
            .build();

        ConsumerIdentity consumer2 = new ConsumerIdentity.ConsumerBuilder("consumer2")
            .setExpression("abc")
            .setIsRulesRequired(true)
            .setRules(rule3)
            .build();

        ConsumerIdentity consumer3 = new ConsumerIdentity.ConsumerBuilder("consumer3")
            .setExpression("abc")
            .setIsRulesRequired(true)
            .setRules(rule1)
            .build();

        // 1st element produce create container as per requirement
        queue.produceElements(new Container(jsonMessage, "abc"));
        // 2nd element produce create container as per requirement
        queue.produceElements(new Container(jsonMessage, "abc"));

        queue.produceElements(new Container(jsonMessage, "def"));

        queue.addConsumer(new ConsumerWork(consumer1));
        queue.addConsumer(new ConsumerWork(consumer2));
        queue.addConsumer(new ConsumerWork(consumer3));
        queue.addConsumer(new ConsumerWork(consumer4));

    }

}
