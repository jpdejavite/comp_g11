package semant.firstpass;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.MethodDecl;
import syntaxtree.VarDecl;
import syntaxtree.VisitorAdapter;
import util.List;

public class ClassDeclHandler extends VisitorAdapter {

	private Env env;

	private ClassDeclHandler(Env e) {
		super();

		env = e;
	}

	/**
	 * First pass of the class.
	 * 
	 * @param e
	 *            the enviroment
	 * @param c
	 *            the class declaration node
	 */
	public static void firstPass(Env e, ClassDecl c) {
		
		//Creates the handler and calls accept
		ClassDeclHandler classDeclHandler = new ClassDeclHandler(e);

		c.accept(classDeclHandler);

	}

	public void visit(ClassDeclSimple node) {
		// Creates the class info
		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo info = new ClassInfo(className);

		// Adds the class to the enviroment, if already exists a class with taht
		// name adds an error
		if (!env.classes.put(className, info)) {
			env.err.Error(node, new Object[] { "Nome de classe redefinido.",
					"Simbolo: " + className });
		}

		// Iterates through the variable declations and calls the first pass for
		// each one
		List<VarDecl> varDeclList = node.varList;
		while (varDeclList != null && varDeclList.head != null) {
			VarDeclHandler.firstPass(env, info, varDeclList.head);
			varDeclList = varDeclList.tail;
		}

		// Iterates through the method declations and calls the first pass for
		// each one
		List<MethodDecl> methodDecl = node.methodList;
		while (methodDecl != null && methodDecl.head != null) {
			MethodDecllHandler.firstPass(env, info, methodDecl.head);
			methodDecl = methodDecl.tail;
		}
	}

	public void visit(ClassDeclExtends node) {
		// Creates the class info
		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo info = new ClassInfo(className);

		// Adds the class to the enviroment, if already exists a class with taht
		// name adds an error
		if (!env.classes.put(className, info)) {
			env.err.Error(node, new Object[] { "Nome de classe redefinido.",
					"Simbolo: " + className });
		}

		// Iterates through the variable declations and calls the first pass for
		// each one
		List<VarDecl> varDeclList = node.varList;
		while (varDeclList != null && varDeclList.head != null) {
			VarDeclHandler.firstPass(env, info, varDeclList.head);
			varDeclList = varDeclList.tail;
		}

		// Iterates through the method declations and calls the first pass for
		// each one
		List<MethodDecl> methodDecl = node.methodList;
		while (methodDecl != null && methodDecl.head != null) {
			MethodDecllHandler.firstPass(env, info, methodDecl.head);
			methodDecl = methodDecl.tail;
		}
	}
}
