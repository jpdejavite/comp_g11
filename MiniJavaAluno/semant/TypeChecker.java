package semant;

import semant.firstpass.FirstPass;
import semant.secondpass.SecondPass;
import syntaxtree.Program;
import errors.ErrorEchoer;

public class TypeChecker {
	private TypeChecker() {
		super();
	}

	public static Env TypeCheck(ErrorEchoer err, Program p) {
		// primeira passada
		Env e = FirstPass.firstPass(err, p);

		// segunda passada
		SecondPass.secondPass(e, p);

		return e;
	}
}
