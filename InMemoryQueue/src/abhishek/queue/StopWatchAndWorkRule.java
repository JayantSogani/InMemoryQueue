
package abhishek.queue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class StopWatchAndWorkRule implements RuleSet, BusinessLogic, Cloneable
{
    private String[] processAfter;

    private boolean processBefore;

    public StopWatchAndWorkRule(boolean processBefore, int howMany)
    {
        this.processBefore = processBefore;
        cbr = new CyclicBarrier(howMany + 1);
    }

    public StopWatchAndWorkRule(boolean processBefore)
    {
        this.processBefore = processBefore;
    }

    private CyclicBarrier cbr;

    /** {@inheritDoc} */
    @Override
    public boolean applyRule(Container work, ConsumerIdentity consumerInfo)
    {
        try
        {
            if (processBefore)
            {
                doBusinessLogic(work, consumerInfo);
                cbr.await();
                return false;
            }
            if (!processBefore)
            {
                cbr.await();
                doBusinessLogic(work, consumerInfo);
                return true;
            }
        }
        catch (InterruptedException | BrokenBarrierException e)
        {
            System.out.println("Something went wrong restart the process using executor rejected handler..");
        }
        return false;
    }

    public void setProcessAfter(int howMany, String... whos)
    {
        processAfter = whos;
    }

    public String[] getProcessAfter()
    {
        return processAfter;
    }

    public boolean isProcessBefore()
    {
        return processBefore;
    }

    public CyclicBarrier getCbr()
    {
        return cbr;
    }

    public void setProcessAfter(String[] processAfter)
    {
        this.processAfter = processAfter;
    }

    public void setProcessBefore(boolean processBefore)
    {
        this.processBefore = processBefore;
    }

    public void setCbr(CyclicBarrier cbr)
    {
        this.cbr = cbr;
    }

    /** {@inheritDoc} */
    @Override
    public void doBusinessLogic(final Container work, ConsumerIdentity ci)
    {
        System.out.println("Business Work is done for Consumer :  [ " + ci.getConsumerId() + " ]");
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        StopWatchAndWorkRule rule = new StopWatchAndWorkRule(this.processBefore);
        rule.setCbr(this.cbr);
        rule.setProcessAfter(this.processAfter);
        return rule;
    }
}
