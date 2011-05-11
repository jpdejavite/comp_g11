package semant.secondpass;

import semant.Env;
import symbol.Symbol;
import syntaxtree.MainClass;
import syntaxtree.VisitorAdapter;

/**
 * The Class MainClassHandler.
 */
public class MainClassHandler extends VisitorAdapter {

	/** The environment. */
	private Env env;

	/**
	 * Instantiates a new main class handler.
	 * 
	 * @param e
	 *            the environment
	 */
	private MainClassHandler(Env e) {
		super();

		// saves the environment to change it
		env = e;
	}

	/**
	 * Do the second pass of the main class. Verify it statement.
	 * 
	 * @param e
	 *            the environment
	 * @param mainClass
	 *            the main class
	 */
	static void secondPass(Env e, MainClass mainClass) {

		MainClassHandler h = new MainClassHandler(e);

		mainClass.accept(h);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.MainClass)
	 */
	public void visit(MainClass node) {

		// get the class name to get the class info from the environment
		Symbol className = Symbol.symbol(node.className.s);

		// call the statement second pass
		StatementHandler.secondPass(env, env.classes.get(className), null,
				node.s);

	}
}
