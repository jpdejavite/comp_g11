package semant.firstpass;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;
import symbol.VarInfo;
import syntaxtree.IntegerType;
import syntaxtree.MainClass;
import syntaxtree.VisitorAdapter;

public class MainClassHandler extends VisitorAdapter {

	private Env env;

	private MainClassHandler(Env e) {
		super();

		env = e;
	}

	/**
	 * First pass of main class
	 *
	 * @param e the enviroment
	 * @param mainClass the main class
	 */
	static void firstPass(Env e, MainClass mainClass) {

		//Creates the handler and calls accept
		MainClassHandler h = new MainClassHandler(e);

		mainClass.accept(h);

	}

	public void visit(MainClass node) {

		// Creates main class info
		Symbol className = Symbol.symbol(node.className.s);
		ClassInfo info = new ClassInfo(className);

		// Adds the class to the enviroment, if already exists the add and error
		if (!env.classes.put(className, info)) {
			env.err.Error(node, new Object[] { "Classe " + className + " redefinida."});
		}

		// Creates a varInfo for the main arg
		// It was necessary to use type Integer, because there is no type String[]
		Symbol mainArgName = Symbol.symbol(node.mainArgName.s);
		VarInfo v = new VarInfo(new IntegerType(node.mainArgName.line,
				node.mainArgName.row), mainArgName);

		// Adds the atribute to the class, adds an error if already exists
		if (!info.addAttribute(v)) {

			VarInfo old = info.attributes.get(mainArgName);

			env.err.Error(node.mainArgName, new Object[] {
					"Atributo \'" + mainArgName
							+ "\' redeclarado para a classe \'" + info.name
							+ "\' Declaracao anterior aqui: [" + old.type.line + ","
							+ old.type.row + "]" });

		}

	}
}
