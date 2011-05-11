package semant.firstpass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.Symbol;
import symbol.VarInfo;
import syntaxtree.Formal;
import syntaxtree.MethodDecl;
import syntaxtree.VarDecl;
import syntaxtree.VisitorAdapter;
import util.List;

public class MethodDecllHandler extends VisitorAdapter {

	private Env env;

	private ClassInfo classInfo;

	public MethodDecllHandler(Env e, ClassInfo v) {
		super();

		env = e;
		classInfo = v;
	}

	/**
	 * First pass of methods.
	 * 
	 * @param env
	 *            the enviroment
	 * @param info
	 *            the Class info
	 * @param methodDecl
	 *            the method decl node
	 */
	public static void firstPass(Env env, ClassInfo info, MethodDecl methodDecl) {

		// Creates the handler and calls accept
		MethodDecllHandler methodDeclHandler = new MethodDecllHandler(env, info);

		methodDecl.accept(methodDeclHandler);
	}

	public void visit(MethodDecl node) {
		// Creates the method info
		Symbol methodName = Symbol.symbol(node.name.s);
		MethodInfo methodInfo = new MethodInfo(node.returnType, methodName,
				classInfo.name);

		// Adds the method to the class info. If already exists the adds an
		// error
		if (!classInfo.addMethod(methodInfo)) {
			MethodInfo old = classInfo.methods.get(methodName);

			env.err.Error(node.name, new Object[] {
					"Metodo \'" + methodName
							+ "\' redeclarado para a classe \'"
							+ classInfo.name + "\'",
					"Declaracao anterior aqui: [" + old.type.line + ","
							+ old.type.row + "]" });
		}

		// Goes through the param list
		List<Formal> formalList = node.formals;
		while (formalList != null && formalList.head != null) {

			// Creates the formal (param) info
			Symbol formalName = Symbol.symbol(formalList.head.name.s);
			VarInfo formalInfo = new VarInfo(formalList.head.type, formalName);

			// Adds the formal info to the method info. If already exists the
			// adds an error
			if (!methodInfo.addFormal(formalInfo)) {

				// Gets the formal info that was alredy there to get the
				// location
				VarInfo old = methodInfo.formalsTable.get(formalName);

				env.err.Error(node.name, new Object[] {
						"Parametro \'" + formalName
								+ "\' redeclarado para o metodo \'"
								+ methodInfo.name + "\' na classe \'"
								+ classInfo.name + "\'",
						"Declaracao anterior aqui: [" + old.type.line + ","
								+ old.type.row + "]" });
			}

			formalList = formalList.tail;
		}

		// Goes through the local list
		List<VarDecl> localList = node.locals;
		while (localList != null && localList.head != null) {

			// Creates the local info
			Symbol localName = Symbol.symbol(localList.head.name.s);
			VarInfo localInfo = new VarInfo(localList.head.type, localName);

			// Adds the local info to the method info. If already exists the
			// adds an error
			if (!methodInfo.addLocal(localInfo)) {

				// Gets the olf var info that was alredy there to get the
				// location
				// It can be in local table and formal table
				VarInfo old = methodInfo.localsTable.get(localName);
				if (old == null) {
					old = methodInfo.formalsTable.get(localName);
				}
				env.err.Error(node.name, new Object[] {
						"Variavel Local \'" + localName
								+ "\' redeclarado para o metodo \'"
								+ methodInfo.name + "\' na classe \'"
								+ classInfo.name + "\'",
						"Declaracao anterior aqui: [" + old.type.line + ","
								+ old.type.row + "]" });
			}

			localList = localList.tail;
		}

	}
}
