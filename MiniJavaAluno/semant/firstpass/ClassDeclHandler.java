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

	public static void firstPass(Env e, ClassDecl c) {
		ClassDeclHandler classDeclHandler = new ClassDeclHandler(e);

		c.accept(classDeclHandler);

	}

	public void visit(ClassDeclSimple node) {
		// Cria a classe
		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo info = new ClassInfo(className);

		// Verifica se ja existe a classe com esse nome
		if (!env.classes.put(className, info)) {
			env.err.Error(node, new Object[] { "Nome de classe redefinido.",
					"Simbolo: " + className });
		}

		// adiciona todas as declarações da classe
		List<VarDecl> varDeclList = node.varList;
		while (varDeclList != null && varDeclList.head != null) {
			VarDeclHandler.firstPass(env, info, varDeclList.head);
			varDeclList = varDeclList.tail;
		}

		// adiciona todas as declarações de métodos da classe
		List<MethodDecl> methodDecl = node.methodList;
		while (methodDecl != null && methodDecl.head != null) {
			MethodDecllHandler.firstPass(env, info, methodDecl.head);
			methodDecl = methodDecl.tail;
		}
	}

	public void visit(ClassDeclExtends node) {
		// Cria a classe
		Symbol className = Symbol.symbol(node.name.s);
		ClassInfo info = new ClassInfo(className);

		// Verifica se ja existe a classe com esse nome
		if (!env.classes.put(className, info)) {
			env.err.Error(node, new Object[] { "Nome de classe redefinido.",
					"Simbolo: " + className });
		}
		
		// Adiciona todas as declarações da classe
		List<VarDecl> varDeclList = node.varList;
		while (varDeclList != null && varDeclList.head != null) {
			VarDeclHandler.firstPass(env, info, varDeclList.head);
			varDeclList = varDeclList.tail;
		}

		// adiciona todas as declarações de métodos da classe
		List<MethodDecl> methodDecl = node.methodList;
		while (methodDecl != null && methodDecl.head != null) {
			MethodDecllHandler.firstPass(env, info, methodDecl.head);
			methodDecl = methodDecl.tail;
		}
	}
}
