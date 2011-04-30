package semant;

import syntaxtree.Program;
import errors.ErrorEchoer;

public class TypeChecker
{
    private TypeChecker()
    {
        super();
    }

    public static Env TypeCheck(ErrorEchoer err, Program p)
    {		
    	// primeira passada
    	Env e = FirstPass.firstPass(err,p);
    	
    	// TODO: Segunda passada
    	
        return null;
    }
}
