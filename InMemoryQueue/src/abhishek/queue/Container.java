package abhishek.queue;

import org.json.JSONObject;
/*
 * 
 * Container class holds JSON Message with expression.
 * 
 */
public class Container
{
    private JSONObject message;

    private String expression;

    Container(JSONObject message, String expression)
    {
        this.message = message;
        this.expression = expression;
    }

    public JSONObject getMessage()
    {
        return message;
    }

    public String getExpression()
    {
        return expression;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
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
        Container other = (Container) obj;
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
        return "Container [expression=" + expression + "]";
    }

}
