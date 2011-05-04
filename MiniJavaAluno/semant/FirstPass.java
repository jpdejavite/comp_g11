package semant;

import semant.firstpass.ProgramHandler;
import syntaxtree.Program;
import errors.ErrorEchoer;

public final class FirstPass {

	private FirstPass() {
		super();
	}

	public static Env firstPass(ErrorEchoer err, Program p) {
		return ProgramHandler.firstPass(err, p);
	}

}
