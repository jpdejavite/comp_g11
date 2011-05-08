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
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.If;
import syntaxtree.IntArrayType;
import syntaxtree.IntegerType;
import syntaxtree.Print;
import syntaxtree.Statement;
import syntaxtree.Type;
import syntaxtree.VisitorAdapter;
import syntaxtree.While;
import util.List;

public class StatementHandler extends VisitorAdapter {

	private Env env;
	private ClassInfo cinfo;
	private MethodInfo minfo;

	public StatementHandler(Env env, ClassInfo c, MethodInfo m) {
		this.env = env;
		this.cinfo = c;
		this.minfo = m;
	}

	public static void secondPass(Env env, ClassInfo c, MethodInfo m,
			Statement s) {
		StatementHandler h = new StatementHandler(env, c, m);

		s.accept(h);
	}

	public void visit(Print node) {
		ExpHandler.secondPass(env, cinfo, minfo, node.exp);
	}

	public void visit(Block node) {

		// verifica a lista de declara��es
		List<Statement> statementList = node.body;
		while (statementList != null && statementList.head != null) {
			StatementHandler.secondPass(env, cinfo, minfo, statementList.head);
			statementList = statementList.tail;
		}

	}

	public void visit(If node) {
		// pega o tipo da express�o
		Type ifType = ExpHandler.secondPass(env, cinfo, minfo, node.condition);

		// verifica o tipo da express�o
		if (!(ifType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para condicao do \'while\'.",
					"Esperado: boolean", "Encontrado: " + ifType });
		}

		// verifica a declara��o se
		StatementHandler.secondPass(env, cinfo, minfo, node.thenClause);

		// verifica a declara��o senao
		if (node.elseClause != null) {
			StatementHandler.secondPass(env, cinfo, minfo, node.elseClause);
		}

	}

	public void visit(While node) {
		// pega o tipo da express�o
		Type condiditionType = ExpHandler.secondPass(env, cinfo, minfo,
				node.condition);

		// verifica o tipo da express�o
		if (!(condiditionType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para condicao do \'while\'.",
					"Esperado: boolean", "Encontrado: " + condiditionType });
		}

		// verifica a declara��o
		StatementHandler.secondPass(env, cinfo, minfo, node.body);

	}

	public void visit(Assign node) {
		// pega o tipo da express�o
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.exp);

		Symbol varName = Symbol.symbol(node.var.s);
		VarInfo varInfo = ExpHandler.getVariable(cinfo, minfo, varName);

		if (varInfo == null) {
			env.err.Error(node, new Object[] { "Identificador \'" + varName
					+ "\' nao definido no metodo atual." });
		}

		if (varInfo != null) {
			// verifica os tipos da atribui��o
			if (!(expType.isComparable(varInfo.type, env,
					Type.getTypeName(expType), node.line, node.row))) {
				env.err.Error(node,
						new Object[] { "Atribui��o com tipos diferentes \'"
								+ varInfo.type + "\' = \'" + expType });
			}
		}

	}

	public void visit(ArrayAssign node) {
		// pega o tipo do indice
		Type indexType = ExpHandler.secondPass(env, cinfo, minfo, node.index);

		// verifica os tipos do indice
		if (!(indexType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o �ndice \'", "Esperado: int",
					"Encontrado: " + indexType });
		}

		// pega o tipo da express�o
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.value);

		// verifica os tipos da expressao
		if (!(expType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a express�o \'", "Esperado: int",
					"Encontrado: " + expType });
		}

		Symbol varName = Symbol.symbol(node.var.s);
		VarInfo varInfo = ExpHandler.getVariable(cinfo, minfo, varName);

		if (varInfo == null) {
			env.err.Error(node, new Object[] { "Identificador \'" + varName
					+ "\' nao definido no metodo atual." });
		}

		
		if (varInfo != null) {
			// verifica os tipos da var�avel
			if (!(varInfo.type instanceof IntArrayType)) {
				env.err.Error(node, new Object[] {
						"Tipo invalido para a vari�vel \'",
						"Esperado: int array", "Encontrado: " + varInfo.type });
			}
		}

	}

}
