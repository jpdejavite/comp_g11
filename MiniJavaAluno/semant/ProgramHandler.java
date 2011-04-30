package semant;

import errors.ErrorEchoer;
import syntaxtree.Program;
import syntaxtree.VisitorAdapter;

public class ProgramHandler extends VisitorAdapter {

	private Env result;

	private ProgramHandler(ErrorEchoer err) {
		super();

		result = new Env(err);
	}

	static Env firstPass(ErrorEchoer err, Program p) {

		ProgramHandler h = new ProgramHandler(err);

		p.accept(h);

		return h.result;
	}

	public void visit(Program node) {
		// classe principal
		MainClassHandler.firstPass(result, node.mainClass);

		// TODO: class decl list
	}
}
