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

	public static void firstPass(Env env, ClassInfo info, MethodDecl methodDecl) {
		MethodDecllHandler methodDeclHandler = new MethodDecllHandler(env, info);

		methodDecl.accept(methodDeclHandler);
	}

	public void visit(MethodDecl node) {
		// adiciona a declaração do método na classe
		Symbol methodName = Symbol.symbol(node.name.s);
		MethodInfo methodInfo = new MethodInfo(node.returnType, methodName,
				classInfo.name);

		if (!classInfo.addMethod(methodInfo)) {
			MethodInfo old = classInfo.methods.get(methodName);

			env.err.Error(node.name, new Object[] {
					"Metodo \'" + methodName
							+ "\' redeclarado para a classe \'"
							+ classInfo.name + "\'",
					"Declaracao anterior aqui: [" + old.type.line + ","
							+ old.type.row + "]" });
		}

		// adiciona todas os parâmetros no método
		List<Formal> formalList = node.formals;
		while (formalList != null && formalList.head != null) {
			// cria a variavel formal
			Symbol formalName = Symbol.symbol(formalList.head.name.s);
			VarInfo formalInfo = new VarInfo(formalList.head.type, formalName);

			if (!methodInfo.addFormal(formalInfo)) {
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

		// adiciona todas as declarações no método
		List<VarDecl> localList = node.locals;
		while (localList != null && localList.head != null) {
			// cria a variavel local
			Symbol localName = Symbol.symbol(localList.head.name.s);
			VarInfo localInfo = new VarInfo(localList.head.type, localName);
			
			if (!methodInfo.addLocal(localInfo)) {
				VarInfo old = methodInfo.localsTable.get(localName);
				if(old == null) {
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
