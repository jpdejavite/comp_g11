package semant.secondpass;

import semant.Env;
import syntaxtree.ClassDecl;
import syntaxtree.Program;
import util.List;

/**
 * The Class SecondPass.
 */
public final class SecondPass {

	/**
	 * Instantiates a new second pass.
	 */
	private SecondPass() {
		super();
	}

	/**
	 * Do the second pass of the program. First search for cyclic inheritance in
	 * the program, and then call the main class and all class declarations
	 * second pass.
	 * 
	 * @param e
	 *            the environment
	 * @param p
	 *            the program
	 */
	public static void secondPass(Env e, Program p) {
		// search for cyclic inheritance
		InheritanceBuilder.secondPass(e, p);

		// Program second pass {
		// main class second pass
		MainClassHandler.secondPass(e, p.mainClass);

		// calls second pass for all class declarations
		List<ClassDecl> classDeclList = p.classList;
		while (classDeclList != null && classDeclList.head != null) {
			ClassDeclHandler.secondPass(e, classDeclList.head);
			classDeclList = classDeclList.tail;
		}
		// }
	}

}
