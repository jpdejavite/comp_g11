package semant.secondpass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.Symbol;
import symbol.VarInfo;
import syntaxtree.ArrayAssign;
import syntaxtree.Assign;
import syntaxtree.Block;
import syntaxtree.BooleanType;
import syntaxtree.If;
import syntaxtree.IntArrayType;
import syntaxtree.IntegerType;
import syntaxtree.Print;
import syntaxtree.Statement;
import syntaxtree.Type;
import syntaxtree.VisitorAdapter;
import syntaxtree.While;
import util.List;

/**
 * The Class StatementHandler.
 */
public class StatementHandler extends VisitorAdapter {

	/** The environment. */
	private Env env;

	/** The class info. */
	private ClassInfo cinfo;

	/** The method info. */
	private MethodInfo minfo;

	/**
	 * Instantiates a new statement handler.
	 * 
	 * @param env
	 *            the environment
	 * @param c
	 *            the class info
	 * @param m
	 *            the method info
	 */
	public StatementHandler(Env env, ClassInfo c, MethodInfo m) {
		this.env = env;
		this.cinfo = c;
		this.minfo = m;
	}

	/**
	 * Do the second pass of the statement. Verify all types of statement
	 * semantic, mostly type verification.
	 * 
	 * @param env
	 *            the environment
	 * @param c
	 *            the class info
	 * @param m
	 *            the method info
	 * @param s
	 *            the statement
	 */
	public static void secondPass(Env env, ClassInfo c, MethodInfo m,
			Statement s) {
		StatementHandler h = new StatementHandler(env, c, m);

		s.accept(h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.Print)
	 */
	public void visit(Print node) {
		// analyze the expression
		ExpHandler.secondPass(env, cinfo, minfo, node.exp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.Block)
	 */
	public void visit(Block node) {

		// analyze all statements in this block statement
		List<Statement> statementList = node.body;
		while (statementList != null && statementList.head != null) {
			StatementHandler.secondPass(env, cinfo, minfo, statementList.head);
			statementList = statementList.tail;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.If)
	 */
	public void visit(If node) {
		// gets the if type form the if expression
		Type ifType = ExpHandler.secondPass(env, cinfo, minfo, node.condition);

		// check the if expression type
		if (!(ifType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para condicao do \'while\'.",
					"Esperado: boolean", "Encontrado: " + ifType });
		}

		// analyze the then statement
		StatementHandler.secondPass(env, cinfo, minfo, node.thenClause);

		// analyze the else clause, if it exists
		if (node.elseClause != null) {
			StatementHandler.secondPass(env, cinfo, minfo, node.elseClause);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.While)
	 */
	public void visit(While node) {
		// gets the while type form the while expression
		Type condiditionType = ExpHandler.secondPass(env, cinfo, minfo,
				node.condition);

		// check the while expression type
		if (!(condiditionType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para condicao do \'while\'.",
					"Esperado: boolean", "Encontrado: " + condiditionType });
		}

		// analyze the while statement
		StatementHandler.secondPass(env, cinfo, minfo, node.body);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.Assign)
	 */
	public void visit(Assign node) {
		// gets the assign type form the assign expression
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.exp);

		// gets the assign variable info form the environment
		Symbol varName = Symbol.symbol(node.var.s);
		VarInfo varInfo = ExpHandler.getVariable(cinfo, minfo, varName);

		// check the variable existence
		if (varInfo == null) {
			env.err.Error(node, new Object[] { "Identificador \'" + varName
					+ "\' nao definido no metodo atual." });
		}

		// if the variable exist
		if (varInfo != null) {
			// check the assign expression type with the variable type
			if (!(expType.isComparable(varInfo.type, env,
					Type.getTypeName(expType), node.line, node.row))) {
				env.err.Error(node,
						new Object[] { "Atribuição com tipos diferentes \'"
								+ varInfo.type + "\' = \'" + expType });
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.VisitorAdapter#visit(syntaxtree.ArrayAssign)
	 */
	public void visit(ArrayAssign node) {
		// gets the index type form the index expression
		Type indexType = ExpHandler.secondPass(env, cinfo, minfo, node.index);

		// check the index type
		if (!(indexType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o índice \'", "Esperado: int",
					"Encontrado: " + indexType });
		}

		// gets the assign type form the assign expression
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.value);

		// check the assign type
		if (!(expType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: int",
					"Encontrado: " + expType });
		}

		// gets the assign array variable info form the environment
		Symbol varName = Symbol.symbol(node.var.s);
		VarInfo varInfo = ExpHandler.getVariable(cinfo, minfo, varName);

		// check the variable existence
		if (varInfo == null) {
			env.err.Error(node, new Object[] { "Identificador \'" + varName
					+ "\' nao definido no metodo atual." });
		}

		// if the variable exist
		if (varInfo != null) {
			// check the assign array type
			if (!(varInfo.type instanceof IntArrayType)) {
				env.err.Error(node, new Object[] {
						"Tipo invalido para a variável \'",
						"Esperado: int array", "Encontrado: " + varInfo.type });
			}
		}

	}

}
