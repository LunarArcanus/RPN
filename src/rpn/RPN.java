package rpn;
import java.util.*;
import java.util.function.*;

/**
 *
 * @author Eynar Roshev <eynaroshev@gmail.com>
 */
public class RPN {
    
    private Stack<Float> stack;
    
    private HashMap<String, Word> dictionary;
    
    public Stack getStack() {
        return stack;
    }
    
    public void printStack() {
        double check;
        System.out.print("Stack: ");
        // If the number is whole, then print that number without the
        // fractional part, otherwise print the entire float
        for (Float i: stack) {
            check = i - Math.floor(i);
            if (check == 0) {
                System.out.print(Math.round(i) + " ");
            }
            else {
                System.out.print(i + " "); 
            }
        }
        System.out.println();
    }
    
    private String[] tokenize(String program) {
        String[] tokens = program.split(" ");
        return tokens;
    }
    
    private void evaluate(String[] tokens) throws UndefinedWordException {
        int i;
        float numToken;
        float result;
        String token;
        Word word;
        float[] args;
        BiFunction<Float, Float, Float> function;
        for (i = 0; i < tokens.length; i++) {
            token = tokens[i];
            // There is a word defined for this token
            if (dictionary.containsKey(token)) {
                word = dictionary.get(token);
                args = getArguments(word);
                function = word.getFunction();
                result = function.apply(args[0], args[1]);
                stack.push(result);
            }
            else {
                try {
                    // Check if the token is a number. If so,
                    // push it onto the stack
                    numToken = Float.parseFloat(token);
                    stack.push(numToken);
                }
                catch (NumberFormatException e) {
                    // Token is neither a number nor a defined word.
                    // Raise an error
                    throw new UndefinedWordException("Undefined word " + token);
                }
            }
        }
    }
    
    private float[] getArguments(Word word) {
        float[] args;
        float arg;
        
        int arity = word.getArity();
        args = new float[arity];
        int i = 0;
        
        while (i < arity) {
            arg = stack.pop();
            args[i] = arg;
            i++;
        }
        return args;
    }
    
    public RPN() {
        stack = new Stack<>();
        dictionary = new HashMap<>();
        BiFunction<Float, Float, Float> plus = (x, y) -> x+y;
        BiFunction<Float, Float, Float> minus = (x, y) -> x-y;
        BiFunction<Float, Float, Float> division = (x, y) -> x/y;
        BiFunction<Float, Float, Float> multiplication = (x, y) -> x*y;
        Word plusWord = new Word("+", 2, plus);
        Word minusWord = new Word("-", 2, minus);
        Word divisionWord = new Word("/", 2, division);
        Word multiplicationWord = new Word("*", 2, multiplication);
        dictionary.put("+", plusWord);
        dictionary.put("-", minusWord);
        dictionary.put("/", divisionWord);
        dictionary.put("*", multiplicationWord);
    }
    
    public static void main(String[] args) throws UndefinedWordException {
        RPN f = new RPN();
        String[] tokens = f.tokenize("3 2 + 5 * 15 + 10 10 * - 80 - 4 6 * - 16 /");
        f.evaluate(tokens);
        f.printStack();
    }  
}
