package rpn;
import java.util.function.*;

/**
 *
 * @author Eynar Roshev <eynaroshev@gmail.com>
 */
public class Word {
    private String identifier;
    private int arity;
    private BiFunction function;
    
    public Word(String identifier, int arity, BiFunction function) {
        this.identifier = identifier;
        this.arity = arity;
        this.function = function;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getArity() {
        return arity;
    }

    public void setArity(int arity) {
        this.arity = arity;
    }

    public BiFunction getFunction() {
        return function;
    }

    public void setFunction(BiFunction function) {
        this.function = function;
    }
}
