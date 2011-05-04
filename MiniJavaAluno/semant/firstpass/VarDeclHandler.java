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

	public static void firstPass(Env env, ClassInfo info, VarDecl varDecl) {
		VarDeclHandler varDeclHandler = new VarDeclHandler(env, info);

		varDecl.accept(varDeclHandler);
	}

	public void visit(VarDecl node) {
		// adiciona a declaração na classe
		Symbol name = Symbol.symbol(node.name.s);
		VarInfo v = new VarInfo(node.type, name);

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
