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

	public static Env firstPass(ErrorEchoer err, Program p) {

		ProgramHandler h = new ProgramHandler(err);

		p.accept(h);

		return h.result;
	}

	public void visit(Program node) {
		// classe principal
		MainClassHandler.firstPass(result, node.mainClass);

		// lista de declarações de classes
		List<ClassDecl> classDeclList = node.classList;
		while (classDeclList != null && classDeclList.head != null) {
			ClassDeclHandler.firstPass(result, classDeclList.head);
			classDeclList = classDeclList.tail;
		}
	}
}
