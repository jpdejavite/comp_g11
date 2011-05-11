package semant.firstpass;

import semant.Env;
import syntaxtree.ClassDecl;
import syntaxtree.Program;
import syntaxtree.VisitorAdapter;
import util.List;
import errors.ErrorEchoer;

public class ProgramHandler extends VisitorAdapter {

	private Env result;

	private ProgramHandler(ErrorEchoer err) {
		super();

		result = new Env(err);
	}

	/**
	 * First pass of the Program.
	 * 
	 * @param err
	 *            the err
	 * @param p
	 *            the p
	 * @return the env
	 */
	public static Env firstPass(ErrorEchoer err, Program p) {

		// Creates a program handler and calls the accept that will call visit
		ProgramHandler h = new ProgramHandler(err);

		p.accept(h);

		return h.result;
	}

	public void visit(Program node) {
		// Calls main class first pass
		MainClassHandler.firstPass(result, node.mainClass);

		// Iterates the class list and call the class first pass for each one
		List<ClassDecl> classDeclList = node.classList;
		while (classDeclList != null && classDeclList.head != null) {
			ClassDeclHandler.firstPass(result, classDeclList.head);
			classDeclList = classDeclList.tail;
		}
	}
}
