package semant.secondpass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.Symbol;
import symbol.VarInfo;
import syntaxtree.And;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.BooleanType;
import syntaxtree.Call;
import syntaxtree.Equal;
import syntaxtree.Exp;
import syntaxtree.False;
import syntaxtree.IdentifierExp;
import syntaxtree.IdentifierType;
import syntaxtree.IntArrayType;
import syntaxtree.IntegerLiteral;
import syntaxtree.IntegerType;
import syntaxtree.LessThan;
import syntaxtree.Minus;
import syntaxtree.NewArray;
import syntaxtree.NewObject;
import syntaxtree.Not;
import syntaxtree.Plus;
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.Type;
import syntaxtree.TypeVisitorAdapter;
import util.List;

public class ExpHandler extends TypeVisitorAdapter {

	private Env env;
	private ClassInfo cinfo;
	private MethodInfo minfo;

	public ExpHandler(Env env, ClassInfo c, MethodInfo m) {
		this.env = env;
		this.cinfo = c;
		this.minfo = m;
	}

	public static Type secondPass(Env env, ClassInfo c, MethodInfo m, Exp s) {
		ExpHandler h = new ExpHandler(env, c, m);

		return s.accept(h);
	}

	static VarInfo getVariable(ClassInfo cinfo, MethodInfo minfo, Symbol varName) {
		VarInfo varInfo = cinfo.attributes.get(varName);
		if (varInfo == null) {

			if (minfo != null && minfo.formalsTable != null) {
				varInfo = minfo.formalsTable.get(varName);
			}

			if (varInfo == null) {

				if (minfo != null) {
					varInfo = minfo.localsTable.get(varName);
				}

			}
		}

		return varInfo;
	}

	public Type visit(And node) {
		// pega o tipo da expressao do lado esquerdo
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// verifica o tipo da expressao do lado esquerdo
		if (!(leftType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'",
					"Esperado: boolean", "Encontrado: " + leftType });
		}

		// pega o tipo da expressao do lado direito
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// verifica o tipo da expressao do lado direito
		if (!(rigthType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'",
					"Esperado: boolean", "Encontrado: " + rigthType });
		}

		return node.type = new BooleanType(node.line, node.row);
	}

	public Type visit(Equal node) {
		// pega o tipo da expressao do lado esquerdo
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);
		// pega o tipo da expressao do lado direito
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// verifica o tipo da comparação
		if (!(leftType.isComparable(rigthType, env, Type.getTypeName(leftType),
				node.line, node.row))
				&& !(rigthType.isComparable(leftType, env,
						Type.getTypeName(rigthType), node.line, node.row))) {
			env.err.Error(node,
					new Object[] { "Comparação de tipos diferentes \'"
							+ leftType + "\' == \'" + rigthType });
		}

		return node.type = new BooleanType(node.line, node.row);
	}

	public Type visit(LessThan node) {
		// pega o tipo da expressao do lado esquerdo
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// verifica o tipo da expressao do lado esquerdo
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// pega o tipo da expressao do lado direito
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// verifica o tipo da expressao do lado direito
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		return node.type = new BooleanType(node.line, node.row);
	}

	public Type visit(Plus node) {
		// pega o tipo da expressao do lado esquerdo
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// verifica o tipo da expressao do lado esquerdo
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// pega o tipo da expressao do lado direito
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// verifica o tipo da expressao do lado direito
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		return node.type = new IntegerType(node.line, node.row);
	}

	public Type visit(Minus node) {
		// pega o tipo da expressao do lado esquerdo
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// verifica o tipo da expressao do lado esquerdo
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// pega o tipo da expressao do lado direito
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// verifica o tipo da expressao do lado direito
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		return node.type = new IntegerType(node.line, node.row);
	}

	public Type visit(Times node) {
		// pega o tipo da expressao do lado esquerdo
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// verifica o tipo da expressao do lado esquerdo
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// pega o tipo da expressao do lado direito
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// verifica o tipo da expressao do lado direito
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		return node.type = new IntegerType(node.line, node.row);
	}

	public Type visit(ArrayLookup node) {
		// pega o tipo do indice
		Type indexType = ExpHandler.secondPass(env, cinfo, minfo, node.index);

		// verifica os tipos do indice
		if (!(indexType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o índice \'", "Esperado: int",
					"Encontrado: " + indexType });
		}

		// pega o tipo da expressão
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.array);

		// verifica os tipos da expressao
		if (!(expType instanceof IntArrayType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: int []",
					"Encontrado: " + expType });
		}

		return node.type = new IntegerType(node.line, node.row);
	}

	public Type visit(ArrayLength node) {
		// pega o tipo da expressão
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.array);

		// verifica os tipos da expressao
		if (!(expType instanceof IntArrayType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: int []",
					"Encontrado: " + expType });
		}

		return node.type = new IntegerType(node.line, node.row);
	}

	public Type visit(Call node) {
		// pega o tipo da expressao
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.object);

		// verifica o tipo da expressao
		if (!(expType instanceof IdentifierType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para fazer chamada", "Esperado: Classe",
					"Encontrado: " + expType });
		} else {
			// verifica se existe o metodo com o mesmo nome na classe
			IdentifierType classType = (IdentifierType) expType;
			Symbol className = Symbol.symbol(classType.name);
			ClassInfo classInfo = env.classes.get(className);
			MethodInfo methodInfo = classInfo.methods.get(Symbol
					.symbol(node.method.s));
			if (methodInfo == null) {
				env.err.Error(node, new Object[] { "Nao existe metodo \'"
						+ node.method + "\' na classe \'" + className + "\'" });
			} else {
				// verifica os parametos do metodo

				List<VarInfo> formalList = methodInfo.formals;
				List<Exp> actualsList = node.actuals;
				if (formalList == null && actualsList != null) {
					env.err.Error(node, new Object[] {
							"Numero errado de parametros do metodo "
									+ methodInfo.name, "Esperado: " + 0,
							"Encontrado: " + actualsList.size() });
				} else if (formalList != null && actualsList == null) {
					env.err.Error(node, new Object[] {
							"Numero errado de parametros do metodo "
									+ methodInfo.name,
							"Esperado: " + formalList.size(),
							"Encontrado: " + 0 });
				} else if (actualsList != null && actualsList != null
						&& actualsList.size() != formalList.size()) {
					env.err.Error(node, new Object[] {
							"Numero errado de parametros do metodo "
									+ methodInfo.name,
							"Esperado: " + formalList.size(),
							"Encontrado: " + actualsList.size() });
				} else {
					while (formalList != null && formalList.head != null
							&& actualsList != null && actualsList.head != null) {
						Type actualType = ExpHandler.secondPass(env, cinfo,
								minfo, actualsList.head);
						if (!(actualType.isComparable(formalList.head.type,
								env, Type.getTypeName(actualType), node.line,
								node.row))) {
							env.err.Error(node, new Object[] {
									"Tipo invalido para parametro do metodo "
											+ methodInfo.name,
									"Esperado: " + formalList.head.type,
									"Encontrado: " + actualType });
						}
						actualsList = actualsList.tail;
						formalList = formalList.tail;
					}
				}
				return node.type = methodInfo.type;
			}
		}
		return node.type = new IntegerType(node.line, node.row);

	}

	public Type visit(IntegerLiteral node) {
		return node.type = new IntegerType(node.line, node.row);
	}

	public Type visit(True node) {
		return node.type = new BooleanType(node.line, node.row);
	}

	public Type visit(False node) {
		return node.type = new BooleanType(node.line, node.row);
	}

	public Type visit(IdentifierExp node) {
		Symbol varName = Symbol.symbol(node.name.s);
		VarInfo varInfo = ExpHandler.getVariable(cinfo, minfo, varName);

		if (varInfo == null) {
			env.err.Error(node, new Object[] { "Identificador \'" + varName
					+ "\' nao definido no metodo atual." });
			return node.type = new IntegerType(node.line, node.row);
		}

		return node.type = varInfo.type;
	}

	public Type visit(This node) {
		// Verifica se eh o main entao nao pode usar this
		if (minfo == null) {
			env.err.Error(
					node,
					new Object[] { "this nao pode ser usado em um contexto estatico." });
		}
		return node.type = new IdentifierType(node.line, node.row,
				cinfo.name.toString());
	}

	public Type visit(NewArray node) {
		// pega o tipo da expressão
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.size);

		// verifica os tipos da expressao
		if (!(expType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: int",
					"Encontrado: " + expType });
		}

		return node.type = new IntArrayType(node.line, node.row);
	}

	public Type visit(NewObject node) {
		Symbol className = Symbol.symbol(node.className.s);
		ClassInfo classInfo = env.classes.get(className);

		if (classInfo == null) {
			env.err.Error(node, new Object[] { "Classe \'" + className
					+ "\' nao definido no programa." });
			return node.type = new IntegerType(node.line, node.row);
		}

		return node.type = new IdentifierType(node.line, node.row,
				node.className.s);
	}

	public Type visit(Not node) {
		// pega o tipo da expressão
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.exp);

		// verifica os tipos da expressao
		if (!(expType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: boolean",
					"Encontrado: " + expType });
		}

		return node.type = new BooleanType(node.line, node.row);
	}

}
