package semant.firstpass;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;
import symbol.VarInfo;
import syntaxtree.VarDecl;
import syntaxtree.VisitorAdapter;

public class VarDeclHandler extends VisitorAdapter {

	private Env env;

	private ClassInfo info;

	public VarDeclHandler(Env e, ClassInfo v) {
		super();

		env = e;
		info = v;
	}

	/**
	 * First pass of variables.
	 * 
	 * @param env
	 *            the enviroment
	 * @param info
	 *            the class info
	 * @param varDecl
	 *            the var declaration node
	 */
	public static void firstPass(Env env, ClassInfo info, VarDecl varDecl) {

		// Creates the handler and calls accept
		VarDeclHandler varDeclHandler = new VarDeclHandler(env, info);

		varDecl.accept(varDeclHandler);
	}

	public void visit(VarDecl node) {

		// Creates the variable info
		Symbol name = Symbol.symbol(node.name.s);
		VarInfo v = new VarInfo(node.type, name);

		// Adds the variable to the class info. If already exists then adds an
		// error.
		if (!info.addAttribute(v)) {

			VarInfo old = info.attributes.get(name);

			env.err.Error(node.name, new Object[] {
					"Atributo \'" + name + "\' redeclarado para a classe \'"
							+ info.name + "\'",
					"Declaracao anterior aqui: [" + old.type.line + ","
							+ old.type.row + "]" });

		}
	}

}
