package semant.secondpass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.Symbol;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.MethodDecl;
import syntaxtree.Statement;
import syntaxtree.Type;
import syntaxtree.VisitorAdapter;
import util.List;

public class ClassDeclHandler extends VisitorAdapter {

	private Env env;

	private ClassDeclHandler(Env e) {
		super();

		env = e;
	}

	public static void secondPass(Env e, ClassDecl c) {
		ClassDeclHandler classDeclHandler = new ClassDeclHandler(e);

		c.accept(classDeclHandler);

	}

	public void visit(ClassDeclSimple node) {

		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo classInfo = env.classes.get(className);

		// lista de métodos da classes
		List<MethodDecl> methodDeclList = node.methodList;
		while (methodDeclList != null && methodDeclList.head != null) {
			Symbol methodName = Symbol.symbol(methodDeclList.head.name.s);
			MethodInfo methodInfo = classInfo.methods.get(methodName);

			// verifica a lista de declarações
			List<Statement> statementList = methodDeclList.head.body;
			while (statementList != null && statementList.head != null) {
				StatementHandler.secondPass(env, classInfo, methodInfo,
						statementList.head);
				statementList = statementList.tail;
			}

			// verifica o retorno do método
			Type expReturnType = ExpHandler.secondPass(env, classInfo,
					methodInfo, methodDeclList.head.returnExp);

			if (!(expReturnType.isComparable(methodDeclList.head.returnType,
					env, Type.getTypeName(expReturnType), node.line, node.row))) {
				env.err.Error(node, new Object[] {
						"Tipo de retorno incompatível",
						"Esperado: " + methodDeclList.head.returnType,
						"Encontrado: " + expReturnType });
			}

			methodDeclList = methodDeclList.tail;
		}

	}

	public void visit(ClassDeclExtends node) {
		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo classInfo = env.classes.get(className);

		// lista de métodos da classes
		List<MethodDecl> methodDeclList = node.methodList;
		while (methodDeclList != null && methodDeclList.head != null) {
			Symbol methodName = Symbol.symbol(methodDeclList.head.name.s);
			MethodInfo methodInfo = classInfo.methods.get(methodName);

			// verifica a lista de declarações
			List<Statement> statementList = methodDeclList.head.body;
			while (statementList != null && statementList.head != null) {
				StatementHandler.secondPass(env, classInfo, methodInfo,
						statementList.head);
				statementList = statementList.tail;
			}

			// verifica o retorno do método
			Type expReturnType = ExpHandler.secondPass(env, classInfo,
					methodInfo, methodDeclList.head.returnExp);

			if (!(expReturnType.isComparable(methodDeclList.head.returnType,
					env, Type.getTypeName(expReturnType), node.line, node.row))) {
				env.err.Error(node, new Object[] {
						"Tipo de retorno incompatível",
						"Esperado: " + methodDeclList.head.returnType,
						"Encontrado: " + expReturnType });
			}

			methodDeclList = methodDeclList.tail;
		}
	}
}
