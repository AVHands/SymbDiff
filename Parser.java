
class MathParser {

    int nextTokenIndex;
    String[] tokens;

    public MathParser(String[] inputTokens) {
        nextTokenIndex = 0;
        tokens = inputTokens;
    }

    MathFunction parseMonoTerminal() {
        // NOTE: we assume the specified token is either:
        // 1. "x"
        // 2. a number that can be parsed
        // Gets the next singular terminal (i.e., "x" or a number)

        String token = tokens[nextTokenIndex];
        nextTokenIndex++;

        if (token.equals("x")) // "x"
        {
            MathFunction fn = new MathVariable();
            return fn; //ahh it is an x!
        } else if (token.equals("(")) {
            MathFunction fn = parseExpression();
            return fn;
        } else {
            MathFunction fn = new MathConstant(Double.parseDouble(token));
            return fn;
        }
    }

    MathFunction parseExpTerm() 
    {
        MathFunction firstTerm = parseMonoTerminal();
        if (nextTokenIndex < tokens.length) 
        {
            String token = tokens[nextTokenIndex]; //

            if (token.equals("^")) 
            {
                MathFunction base = firstTerm;
                nextTokenIndex++;
                MathFunction exponent = parseExpTerm();
                MathFunction power = new ExpoOperation(base, exponent);
                firstTerm = power;
            }
        }
        return firstTerm;
    }

    MathFunction parseTerm() {
        // NOTE: we assume the specified token is either:
        // "+", "-", "*", "x", or a number we can parse.

        MathFunction runningTerm = parseExpTerm();

        // we only scan for operators.
        while (nextTokenIndex < tokens.length - 1) {
            String token = tokens[nextTokenIndex];

            if (token.equals("*")) // "*"
            {
                MathFunction leftArg = runningTerm;
                nextTokenIndex++;
                MathFunction rightArg = parseExpTerm();
                MathFunction product = new MultOperation(leftArg, rightArg);
                runningTerm = product;
            } 
            else if(token.equals("/"))
            {
                MathFunction leftArg = runningTerm;
                nextTokenIndex++;
                MathFunction rightArg = parseExpTerm();
                MathFunction product = new DivOperation(leftArg, rightArg);
                runningTerm = product;
            }
            else //if token[0] == '+' || '-'
            {
                // we can't get anything but a + or -
                // since we increment by 2s.
                break;
            }
        }
        return runningTerm;
    }

    public MathFunction parseExpression() {
        MathFunction runningExpr = parseTerm();

        while (nextTokenIndex < tokens.length - 1) 
        {
            // can only be "+" or "-"
            String token = tokens[nextTokenIndex];
            nextTokenIndex++;
            MathFunction rightArg = parseTerm();

            if (token.equals("+")) 
            {
                MathFunction fn = new PlusOperation(runningExpr, rightArg);
                runningExpr = fn;
            } else if (token.equals(")")) 
            {
                break;
            } 
            else // if token[0] == '-'
            {
                MathFunction fn = new MinusOperation(runningExpr, rightArg);
                runningExpr = fn;
            }
        }
        return runningExpr;
    }
}

public class Parser 
{
    public static void main(String[] args) {

    }

}
