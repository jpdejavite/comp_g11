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

/**
 * The Class ClassDeclHandler.
 */
public class ClassDeclHandler extends VisitorAdapter {

	/** The environment. */
	private Env env;

	/**
	 * Instantiates a new class declaration handler.
	 * 
	 * @param e
	 *            the environment
	 */
	private ClassDeclHandler(Env e) {
		super();

		// saves the environment to change it
		env = e;
	}

	/**
	 * Do the second pass of the class declaration (simple and extends). Verify
	 * all methods signatures ,return Expression and their statements.
	 * 
	 * @param e
	 *            the environment
	 * @param c
	 *            the class declaration
	 */
	public static void secondPass(Env e, ClassDecl c) {
		ClassDeclHandler classDeclHandler = new ClassDeclHandler(e);

		c.accept(classDeclHandler);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.ClassDeclSimple)
	 */
	public void visit(ClassDeclSimple node) {

		// gets the class info stored in the environment
		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo classInfo = env.classes.get(className);

		// analyze all methods in this class
		List<MethodDecl> methodDeclList = node.methodList;
		while (methodDeclList != null && methodDeclList.head != null) {
			// gets the method info stored in the environment
			Symbol methodName = Symbol.symbol(methodDeclList.head.name.s);
			MethodInfo methodInfo = classInfo.methods.get(methodName);

			// analyze (second pass) all statements in this method
			List<Statement> statementList = methodDeclList.head.body;
			while (statementList != null && statementList.head != null) {
				StatementHandler.secondPass(env, classInfo, methodInfo,
						statementList.head);
				statementList = statementList.tail;
			}

			// get the return type form the return expression
			Type expReturnType = ExpHandler.secondPass(env, classInfo,
					methodInfo, methodDeclList.head.returnExp);

			// check the return expression type with the declared one
			if (!(expReturnType.isComparable(methodDeclList.head.returnType,
					env, Type.getTypeName(expReturnType), node.line, node.row))) {
				env.err
						.Error(
								node,
								new Object[] { "Tipo de retorno incompativel do metodo \'"
										+ methodDeclList.head.name.s
										+ "\' da classe \'"
										+ node.name.s
										+ "\'. Esperado: "
										+ methodDeclList.head.returnType
										+ ". Encontrado: " + expReturnType });
			}

			// iterate over the list
			methodDeclList = methodDeclList.tail;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.ClassDeclExtends)
	 */
	public void visit(ClassDeclExtends node) {
		// gets the class info stored in the environment
		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo classInfo = env.classes.get(className);

		// gets the method info stored in the environment
		List<MethodDecl> methodDeclList = node.methodList;
		while (methodDeclList != null && methodDeclList.head != null) {
			Symbol methodName = Symbol.symbol(methodDeclList.head.name.s);
			MethodInfo methodInfo = classInfo.methods.get(methodName);

			// analyze (second pass) all statements in this method
			List<Statement> statementList = methodDeclList.head.body;
			while (statementList != null && statementList.head != null) {
				StatementHandler.secondPass(env, classInfo, methodInfo,
						statementList.head);
				statementList = statementList.tail;
			}

			// get the return type form the return expression
			Type expReturnType = ExpHandler.secondPass(env, classInfo,
					methodInfo, methodDeclList.head.returnExp);

			// check the return expression type with the declared one
			if (!(expReturnType.isComparable(methodDeclList.head.returnType,
					env, Type.getTypeName(expReturnType), node.line, node.row))) {
				env.err
				.Error(
						node,
						new Object[] { "Tipo de retorno incompativel do metodo \'"
								+ methodDeclList.head.name.s
								+ "\' da classe \'"
								+ node.name.s
								+ "\'. Esperado: "
								+ methodDeclList.head.returnType
								+ ". Encontrado: " + expReturnType });
			}

			// iterate over the list
			methodDeclList = methodDeclList.tail;
		}
	}
}
