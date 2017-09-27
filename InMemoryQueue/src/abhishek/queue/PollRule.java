package abhishek.queue;

/**
 * Always Poll
 * 
 */
public class PollRule implements RuleSet, BusinessLogic, Cloneable
{

    /** {@inheritDoc} */
    @Override
    public void doBusinessLogic(Container work, ConsumerIdentity consumerInfo)
    {
        System.out.println("Business Work is done for Consumer Expresion :  [ " + consumerInfo.getConsumerId() + " ]");
    }

    /** {@inheritDoc} */
    @Override
    public boolean applyRule(Container peek, ConsumerIdentity consumerInfo)
    {
        doBusinessLogic(peek, consumerInfo);
        return true;
    }

}
