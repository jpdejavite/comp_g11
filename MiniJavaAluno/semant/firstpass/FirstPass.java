package semant.firstpass;

import semant.Env;
import syntaxtree.Program;
import errors.ErrorEchoer;

public final class FirstPass {

	private FirstPass() {
		super();
	}

	/**
	 * Enviroment first pass. Calls the program firts pass and adds all
	 * declarations to the enviroment, if there is a conflict then adds an error
	 * 
	 * @param err
	 *            the err
	 * @param p
	 *            the p
	 * @return the env
	 */
	public static Env firstPass(ErrorEchoer err, Program p) {
		//Calls the program first pass
		return ProgramHandler.firstPass(err, p);
	}

}
