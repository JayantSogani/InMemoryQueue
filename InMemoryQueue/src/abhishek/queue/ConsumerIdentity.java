package abhishek.queue;


public class ConsumerIdentity
{
    private String consumerId;

    private RuleSet rules;

    private boolean isRulesRequired = false;

    private String expression;

    public String getConsumerId()
    {
        return consumerId;
    }

    public RuleSet getRules()
    {
        return rules;
    }

    public String getExpression()
    {
        return expression;
    }

    public boolean isRulesRequired()
    {
        return isRulesRequired;
    }

    private ConsumerIdentity(ConsumerBuilder builder)
    {
        this.consumerId = builder.consumerId;
        this.rules = builder.rules;
        this.isRulesRequired = builder.isRulesRequired;
        this.expression = builder.expression;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((consumerId == null) ? 0 : consumerId.hashCode());
        result = prime * result + ((expression == null) ? 0 : expression.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConsumerIdentity other = (ConsumerIdentity) obj;
        if (consumerId == null)
        {
            if (other.consumerId != null)
                return false;
        }
        else if (!consumerId.equals(other.consumerId))
            return false;
        if (expression == null)
        {
            if (other.expression != null)
                return false;
        }
        else if (!expression.equals(other.expression))
            return false;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public String toString()
    {
        return "ConsumerIdentity [consumerId=" + consumerId + ", expression=" + expression + "]";
    }

    public static class ConsumerBuilder
    {
        private String consumerId;

        private RuleSet rules;

        private boolean isRulesRequired = false;

        private String expression;

        public ConsumerBuilder(String consumerId)
        {
            this.consumerId = consumerId;
        }

        public ConsumerBuilder setRules(RuleSet rules)
        {
            this.rules = rules;
            return this;
        }

        public ConsumerBuilder setIsRulesRequired(boolean isRulesRequired)
        {
            this.isRulesRequired = isRulesRequired;
            return this;
        }

        public ConsumerBuilder setExpression(String expression)
        {
            this.expression = expression;
            return this;
        }

        public ConsumerIdentity build()
        {
            return new ConsumerIdentity(this);
        }

    }

}
