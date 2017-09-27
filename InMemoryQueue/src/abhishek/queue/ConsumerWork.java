package abhishek.queue;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;


public class ConsumerWork implements Consumer, PostProcess
{

    private final ConsumerIdentity consumerInfo;

    private BlockingQueue<Container> containers;

    public ConsumerWork(ConsumerIdentity ci)
    {
        this.consumerInfo = ci;
    }

    /** {@inheritDoc} */
    @Override
    public String call()
    {
        System.out.println("***Executing the consumer work for : " + consumerInfo.getConsumerId());
        try
        {
            if (null == containers)
            {
                System.out.println("No Work container is produced for the the consumer to consume : "
                    + consumerInfo.getConsumerId());
                return State.ENDED.toString();
            }
            // Container peek = containers.peek();

            Iterator<Container> containerIterator = containers.iterator();
            // Want to only execute only one task at a time. not all as it should come via another worker
            // though this can be triggered here also by removing the taskCompleted variable.
            boolean taskIsNotCompleted = true;

            while (taskIsNotCompleted && !containers.isEmpty())
            {
                Container peek = containerIterator.next();
                if (isExpressionMatched(peek))
                {
                    applyRules(peek);
                    doPostProcessLogic(peek);
                    taskIsNotCompleted = false;
                }

                taskIsNotCompleted = !taskIsNotCompleted ? false : containerIterator.hasNext() ? true : false;
            }

            return State.SUCCESS.toString();
        }
        catch (Exception exp)
        {
            return State.FAIL.toString();
        }

    }

    private void applyRules(Container peek)
    {
        RuleSet rules = consumerInfo.getRules();

        if (rules.applyRule(peek, consumerInfo))
            containers.remove(peek);

    }

    /**
     * If expression does not match to the consumer it bypass the process
     * meaning it does it not consume anything
     * if No rules
     */
    private boolean isExpressionMatched(Container peek)
    {
        if (!peek.getExpression().equals(consumerInfo.getExpression()))
            return false;

        if (consumerInfo.isRulesRequired() && null == consumerInfo.getRules())
        {
            System.out.println("No Rule is defined , do the peek from the queue please remove the element");
            return false;
        }
        System.out.println("Expression [" + peek.getExpression() + "]" + " is matched for : " +consumerInfo.getConsumerId());

        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void attachWork(BlockingQueue<Container> containers)
    {
        this.containers = containers;
    }

    /** {@inheritDoc} */
    @Override
    public void doPostProcessLogic(Container work)
    {
        System.out.println("Post Process Logic for : " + consumerInfo.getConsumerId());

    }
}
