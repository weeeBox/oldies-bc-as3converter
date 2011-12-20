package macromedia.asc.semantics;

import macromedia.asc.parser.MetaDataEvaluator;
import macromedia.asc.parser.MetaDataNode;

/**
 * Class to store metadata info, so that Slots don't have pointers back into the AST
 * @author Erik Tierney
 */
public class MetaData
{
    public String id;
    public Value[] values;

    public MetaData()
    {
        id = null;
        values = null;
    }

    public String getValue(String key)
    {
        for (int i = 0, length = count(); i < length; i++)
        {
            if (values[i] instanceof MetaDataEvaluator.KeyValuePair)
            {
                if (((MetaDataEvaluator.KeyValuePair) values[i]).key.equals(key))
                {
                    return ((MetaDataEvaluator.KeyValuePair) values[i]).obj;
                }
            }
        }
        return null;
    }

    public String getValue(int index)
    {
        if (index < 0 || index >= count())
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        else if (values[index] instanceof MetaDataEvaluator.KeylessValue)
        {
            return ((MetaDataEvaluator.KeylessValue) values[index]).obj;
        }
        else if (values[index] instanceof MetaDataEvaluator.KeyValuePair)
        {
            return ((MetaDataEvaluator.KeyValuePair) values[index]).obj;
        }
        else
        {
            return null;
        }
    }

    public int count()
    {
        return values != null ? values.length : 0;
    }

}
