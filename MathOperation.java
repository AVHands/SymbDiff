
class MathFunction
{
    public double callFn(double x)
    {
        return 0.0/0.0;
    }
    public Boolean isConstant()
    {
        return true;
    }
    public MathFunction derv()
    {
        return new MathFunction();
    }
    public MathFunction simplify()
    {
        return new MathFunction();
    }
}

class BinaryOperation extends MathFunction
{
    MathFunction lefty;
    MathFunction righty;

    public BinaryOperation(MathFunction leftArg, MathFunction rightArg)
    {
        lefty = leftArg;
        righty = rightArg;
    }
    @Override
    public Boolean isConstant()
    {
        return lefty.isConstant() && righty.isConstant();
    }
}


class MinusOperation extends BinaryOperation
{
    public MinusOperation(MathFunction leftArg, MathFunction rightArg)
    {
        super(leftArg, rightArg);
    }
    @Override
    public double callFn(double x)
    {
        return lefty.callFn(x)-righty.callFn(x);
    }
    @Override
    public MathFunction derv()
    {
        return new MinusOperation(lefty.derv(), righty.derv());
    }
    @Override
    public MathFunction simplify()
    {
        MathFunction leftArg = lefty.simplify(); 
        MathFunction rightArg = righty.simplify();

        if(leftArg instanceof MathConstant leftArgC)
        {
            double leftVal = leftArgC.value; 
            
            if(rightArg instanceof MathConstant rightArgC)
            {
                double rightVal = rightArgC.value;
                return new MathConstant(leftVal-rightVal);
            }
            else
            {
                return new MinusOperation(leftArg, rightArg);
            }
        }
        else if(rightArg instanceof MathConstant rightArgC)
        {
            double rightVal = rightArgC.value; 
            if(rightVal == 0)
            {
                return leftArg;
            }
            else
            {
                return new MinusOperation(leftArg, rightArg);
            }
        }
        else
        {
            return new MinusOperation(leftArg, rightArg);
        }
    }
    @Override
    public String toString()
    {
        return lefty + " - " + righty;
    }
}
class PlusOperation extends BinaryOperation
{
    public PlusOperation(MathFunction leftArg, MathFunction rightArg)
    {
        super(leftArg, rightArg);
    }
    @Override
    public double callFn(double x)
    {
        return lefty.callFn(x)+righty.callFn(x);
    }
    @Override
    public MathFunction derv()
    {
        return new PlusOperation(lefty.derv(), righty.derv());
        //return lefty.derv()+righty.derv();
    }
    @Override
    public MathFunction simplify()
    {
        MathFunction leftArg = lefty.simplify(); 
        MathFunction rightArg = righty.simplify();

        if(leftArg instanceof MathConstant leftArgC)
        {
            double leftVal = leftArgC.value; 
            if(leftVal == 0)
            {
                return rightArg;
            }
            else if(rightArg instanceof MathConstant rightArgC)
            {
                double rightVal = rightArgC.value;
                return new MathConstant(leftVal+rightVal);
            }
            else
            {
                return new PlusOperation(leftArg, rightArg);
            }
        }
        else if(rightArg instanceof MathConstant rightArgC)
        {
            double rightVal = rightArgC.value; 
            if(rightVal == 0)
            {
                return leftArg;
            }
            else
            {
                return new PlusOperation(leftArg, rightArg);
            }
        }
        else
        {
            return new PlusOperation(leftArg, rightArg);
        }
    }
    @Override
    public String toString()
    {
        return lefty + " + " + righty; //return lefty + "+" + righty
    }
}

class MultOperation extends BinaryOperation
{
    public MultOperation(MathFunction leftArg, MathFunction rightArg)
    {
        super(leftArg, rightArg);
    }
    @Override
    public double callFn(double x)
    {
        return lefty.callFn(x)*righty.callFn(x);
    }
    @Override
    public MathFunction derv()
    {
        return new MultOperation(lefty.derv(), righty.derv());
    }
    @Override
    public String toString()
    {
        return lefty + " * " + righty;
    }
    @Override
    public MathFunction simplify()
    {
        MathFunction leftArg = lefty.simplify(); 
        MathFunction rightArg = righty.simplify();

        if(leftArg instanceof MathConstant leftArgC)
        {
            double leftVal = leftArgC.value; 
            if(leftVal == 0)
            {
                return leftArg;
            }
            else if(rightArg instanceof MathConstant rightArgC)
            {
                double rightVal = rightArgC.value;
                return new MathConstant(leftVal*rightVal);
            }
            else
            {
                return new MultOperation(leftArg, rightArg);
            }
        }
        else if(rightArg instanceof MathConstant rightArgC)
        {
            double rightVal = rightArgC.value; 
            if(rightVal == 0)
            {
                return rightArg;
            }
            else
            {
                return new MultOperation(leftArg, rightArg);
            }
        }
        else
        {
            return new MultOperation(leftArg, rightArg);
        }
    }
}

class DivOperation extends BinaryOperation
{
    public DivOperation(MathFunction leftArg, MathFunction rightArg)
    {
        super(leftArg, rightArg);
    }
    @Override
    public double callFn(double x)
    {
        return lefty.callFn(x)/righty.callFn(x);
    }
    @Override
    public MathFunction simplify()
    {
        MathFunction leftArg = lefty.simplify(); 
        MathFunction rightArg = righty.simplify();

        if(leftArg instanceof MathConstant leftArgC)
        {
            double leftVal = leftArgC.value; 
            if(leftVal == 0)
            {
                return leftArg;
            }
            else if(rightArg instanceof MathConstant rightArgC)
            {
                double rightVal = rightArgC.value;
                return new MathConstant(leftVal/rightVal);
            }
            else
            {
                return new DivOperation(leftArg, rightArg);
            }
        }
        else
        {
            return new DivOperation(leftArg, rightArg);
        }
    }
}

class ExpoOperation extends BinaryOperation 
{
    public ExpoOperation(MathFunction leftArg, MathFunction rightArg)
    {
        super(leftArg, rightArg);
    }
    @Override
    public double callFn(double x)
    {
        return Math.pow(lefty.callFn(x), righty.callFn(x));
    }
    @Override
    public MathFunction derv()
    {
        MathFunction rightArg = righty.simplify();

        if(rightArg instanceof MathConstant rightArgC)
        {
            double rightVal = rightArgC.value; 

            MathFunction newExp = new MathConstant(rightVal-1);
            MathFunction jim = new ExpoOperation(lefty, newExp);
            MathFunction jim2 = new MultOperation(jim, rightArgC);
            MathFunction jim3 = new MultOperation(jim2, lefty.derv());

            return jim3;
        }
        else
        {
            return super.derv();
        }
    }
    //for derv check if exp is constant
    @Override
    public MathFunction simplify() //could add more >-<
    {
        MathFunction leftArg = lefty.simplify(); 
        MathFunction rightArg = righty.simplify();

        if(leftArg instanceof MathConstant leftArgC)
        {
            double leftVal = leftArgC.value; 
            if(rightArg instanceof MathConstant rightArgC)
            {
                double rightVal = rightArgC.value;
                return new MathConstant(Math.pow(leftVal,rightVal));
            }
            else
            {
                return new ExpoOperation(leftArg, rightArg);
            }
        }
        else if(rightArg instanceof MathConstant rightArgC)
        {
            double rightVal = rightArgC.value; 
            if(rightVal == 0)
            {
                return new MathConstant(1);
            }
            else
            {
                return new ExpoOperation(leftArg, rightArg);
            }
        }
        else
        {
            return new ExpoOperation(leftArg, rightArg);
        }
    }

}

class MathConstant extends MathFunction
{
    double value;
    public MathConstant(double constantValue)
    {
        value = constantValue;
    }
    @Override
    public double callFn(double x)
    {
        return value;
    }
    @Override
    public Boolean isConstant()
    {  
        return true;
    }
    @Override
    public MathFunction derv()
    {
        return new MathConstant(0);
    }
    @Override
    public String toString()//trying to print out value as a string--value is a double--
    {
        return String.valueOf(value);
    }
    @Override
    public MathFunction simplify()
    {
        return this;
    }
}

class MathVariable extends MathFunction
{
    @Override
    public double callFn(double x)
    {
        return x;
    }
    @Override
    public Boolean isConstant()
    {  
        return false;
    }
    @Override
    public MathFunction derv()
    {
        return new MathConstant(1);
    }
    @Override
    public String toString()//trying to print out value as a string--value is a double--
    {
        return "x"; //I want to print the function 
    }
    @Override
    public MathFunction simplify()
    {
        return this;
    }
}

public class MathOperation 
{
    public static void main(String[] args) {
        
    }
    
}
