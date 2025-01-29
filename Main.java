public class Main
{
    public static void main(String args[]) //throws IOException
    {
        String[] tokens = {"x", "*", "2","+","x"};
        //String[] tokens = {"4","*","x","+","2"}; //f(3) = 36 - worked!
        //String[] tokens = {"4","/","2","+", "2", "^","(","x","^","2","+","1",")"}; //f(3) = 1026 - worked!
        System.out.print("f(x) = ");
        for(int i = 0; i < tokens.length; i++)
        {
            System.out.print(tokens[i] + " ");
        }
        System.out.println();

        MathParser parser = new MathParser(tokens);
        MathFunction fn = parser.parseExpression();
        Boolean jimmy = fn.isConstant(); //how does this work? lol
        System.out.println("f(x) being constant = " + jimmy);
        System.out.println("f(3.0) = " + fn.callFn(3.0));
        MathFunction dervy = fn.derv();
        System.out.println("fn = " + fn);
        System.out.println("f'(x) = " + dervy);
    }
}
//work on dervs 