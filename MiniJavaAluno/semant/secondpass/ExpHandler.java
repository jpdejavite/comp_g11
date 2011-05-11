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

/**
 * The Class ExpHandler.
 */
public class ExpHandler extends TypeVisitorAdapter {

	/** The environment. */
	private Env env;

	/** The class info. */
	private ClassInfo cinfo;

	/** The method info. */
	private MethodInfo minfo;

	/**
	 * Instantiates a new expression handler.
	 * 
	 * @param env
	 *            the environment
	 * @param c
	 *            the class info
	 * @param m
	 *            the method info
	 */
	public ExpHandler(Env env, ClassInfo c, MethodInfo m) {
		this.env = env;
		this.cinfo = c;
		this.minfo = m;
	}

	/**
	 * Do the second pass of the expression. Verify all types of expression
	 * semantic, mostly type verification, and return it type.
	 * 
	 * @param env
	 *            the environment
	 * @param c
	 *            the class info
	 * @param m
	 *            the method info
	 * @param e
	 *            the expression
	 * @return the type returned by the expression analyzed
	 */
	public static Type secondPass(Env env, ClassInfo c, MethodInfo m, Exp e) {
		ExpHandler h = new ExpHandler(env, c, m);

		return e.accept(h);
	}

	/**
	 * Search the variable info in the environment attributes, formal table and
	 * local table.
	 * 
	 * @param cinfo
	 *            the cinfo
	 * @param minfo
	 *            the minfo
	 * @param varName
	 *            the var name
	 * @return the variable
	 */
	static VarInfo getVariable(ClassInfo cinfo, MethodInfo minfo, Symbol varName) {
		// search in the attributes
		VarInfo varInfo = cinfo.attributes.get(varName);
		if (varInfo == null) {

			// search in the formal table if it exist
			if (minfo != null && minfo.formalsTable != null) {
				varInfo = minfo.formalsTable.get(varName);
			}

			if (varInfo == null) {
				// search in the local table if it exist
				if (minfo != null) {
					varInfo = minfo.localsTable.get(varName);
				}

			}
		}

		return varInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.And)
	 */
	public Type visit(And node) {
		// gets the left side type form the left side expression
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// check the left side expression type
		if (!(leftType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'",
					"Esperado: boolean", "Encontrado: " + leftType });
		}

		// gets the rigth side type form the rigth side expression
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// check the rigth side expression type
		if (!(rigthType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'",
					"Esperado: boolean", "Encontrado: " + rigthType });
		}

		// return the booelan type and store it on the node
		return node.type = new BooleanType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.Equal)
	 */
	public Type visit(Equal node) {
		// gets the left side type form the left side expression
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);
		// gets the rigth side type form the rigth side expression
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// check the rigth side expression type with the left side expression
		// type
		if (!(leftType.isComparable(rigthType, env, Type.getTypeName(leftType),
				node.line, node.row))
				&& !(rigthType.isComparable(leftType, env,
						Type.getTypeName(rigthType), node.line, node.row))) {
			env.err.Error(node,
					new Object[] { "Comparação de tipos diferentes \'"
							+ leftType + "\' == \'" + rigthType });
		}

		// return the booelan type and store it on the node
		return node.type = new BooleanType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.LessThan)
	 */
	public Type visit(LessThan node) {
		// gets the left side type form the left side expression
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// check the left side expression type
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// gets the rigth side type form the rigth side expression
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// check the rigth side expression type
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		// return the booelan type and store it on the node
		return node.type = new BooleanType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.Plus)
	 */
	public Type visit(Plus node) {
		// gets the left side type form the left side expression
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// check the left side expression type
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// gets the rigth side type form the rigth side expression
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// check the rigth side expression type
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		// return the integer type and store it on the node
		return node.type = new IntegerType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.Minus)
	 */
	public Type visit(Minus node) {
		// gets the left side type form the left side expression
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// check the left side expression type
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// gets the rigth side type form the rigth side expression
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// check the rigth side expression type
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		// return the integer type and store it on the node
		return node.type = new IntegerType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.Times)
	 */
	public Type visit(Times node) {
		// gets the left side type form the left side expression
		Type leftType = ExpHandler.secondPass(env, cinfo, minfo, node.lhs);

		// check the left side expression type
		if (!(leftType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado esquerdo \'", "Esperado: int",
					"Encontrado: " + leftType });
		}

		// gets the rigth side type form the rigth side expression
		Type rigthType = ExpHandler.secondPass(env, cinfo, minfo, node.rhs);

		// check the rigth expression type
		if (!(rigthType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o lado direito \'", "Esperado: int",
					"Encontrado: " + rigthType });
		}

		// return the integer type and store it on the node
		return node.type = new IntegerType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.ArrayLookup)
	 */
	public Type visit(ArrayLookup node) {
		// gets the index type form the index expression
		Type indexType = ExpHandler.secondPass(env, cinfo, minfo, node.index);

		// check the index expression type
		if (!(indexType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para o índice \'", "Esperado: int",
					"Encontrado: " + indexType });
		}

		// gets the array lookup type form the array lookup expression
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.array);

		// check the array lookup expression type
		if (!(expType instanceof IntArrayType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: int []",
					"Encontrado: " + expType });
		}

		// return the integer type and store it on the node
		return node.type = new IntegerType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.ArrayLength)
	 */
	public Type visit(ArrayLength node) {
		// gets the array lenth type form the array length expression
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.array);

		// check the array length expression type
		if (!(expType instanceof IntArrayType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: int []",
					"Encontrado: " + expType });
		}

		// return the type and store it on the node
		return node.type = new IntegerType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.Call)
	 */
	public Type visit(Call node) {
		// gets the object type form the object expression
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.object);

		// check the array length expression type
		if (!(expType instanceof IdentifierType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para fazer chamada", "Esperado: Classe",
					"Encontrado: " + expType });
		} else {
			IdentifierType classType = (IdentifierType) expType;

			// gets the class info from the environment
			Symbol className = Symbol.symbol(classType.name);
			ClassInfo classInfo = env.classes.get(className);

			// gets the method and check it existance
			MethodInfo methodInfo = classInfo.methods.get(Symbol
					.symbol(node.method.s));
			if (methodInfo == null) {
				env.err.Error(node, new Object[] { "Nao existe metodo \'"
						+ node.method + "\' na classe \'" + className + "\'" });
			} else {
				// check all method parameters
				List<VarInfo> formalList = methodInfo.formals;
				List<Exp> actualsList = node.actuals;

				// if one of the parameter list is null they are different
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

					// analyze all parameters expression and check their type
					// with the expected
					while (formalList != null && formalList.head != null
							&& actualsList != null && actualsList.head != null) {
						// analyze and get the parameter expression type
						Type actualType = ExpHandler.secondPass(env, cinfo,
								minfo, actualsList.head);

						// check the parameter expression type
						if (!(actualType.isComparable(formalList.head.type,
								env, Type.getTypeName(actualType), node.line,
								node.row))) {
							env.err.Error(node, new Object[] {
									"Tipo invalido para parametro do metodo "
											+ methodInfo.name,
									"Esperado: " + formalList.head.type,
									"Encontrado: " + actualType });
						}

						// iterate the lists
						actualsList = actualsList.tail;
						formalList = formalList.tail;
					}
				}
				return node.type = methodInfo.type;
			}
		}

		// return the integer type and store it on the node
		return node.type = new IntegerType(node.line, node.row);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.IntegerLiteral)
	 */
	public Type visit(IntegerLiteral node) {
		// return the integer type and store it on the node
		return node.type = new IntegerType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.True)
	 */
	public Type visit(True node) {
		// return the boolean type and store it on the node
		return node.type = new BooleanType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.False)
	 */
	public Type visit(False node) {
		// return the boolean type and store it on the node
		return node.type = new BooleanType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.IdentifierExp)
	 */
	public Type visit(IdentifierExp node) {
		// gets the variable info form the environment
		Symbol varName = Symbol.symbol(node.name.s);
		VarInfo varInfo = ExpHandler.getVariable(cinfo, minfo, varName);

		// check the variable expression type
		if (varInfo == null) {
			env.err.Error(node, new Object[] { "Identificador \'" + varName
					+ "\' nao definido no metodo atual." });
			return node.type = new IntegerType(node.line, node.row);
		}

		// return the variable type and store it on the node
		return node.type = varInfo.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.This)
	 */
	public Type visit(This node) {
		// check ifit's the main method, because it cannot be used in the main
		if (minfo == null) {
			env.err.Error(
					node,
					new Object[] { "this nao pode ser usado em um contexto estatico." });
		}

		// return the identifier type and store it on the node
		return node.type = new IdentifierType(node.line, node.row,
				cinfo.name.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.NewArray)
	 */
	public Type visit(NewArray node) {
		// gets the new array type form the new array expression
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.size);

		// check the new array expression type
		if (!(expType instanceof IntegerType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: int",
					"Encontrado: " + expType });
		}

		// return the int array type and store it on the node
		return node.type = new IntArrayType(node.line, node.row);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.NewObject)
	 */
	public Type visit(NewObject node) {
		// gets the variable info form the environment
		Symbol className = Symbol.symbol(node.className.s);
		ClassInfo classInfo = env.classes.get(className);

		// check the variable existence
		if (classInfo == null) {
			env.err.Error(node, new Object[] { "Classe \'" + className
					+ "\' nao definido no programa." });
			return node.type = new IntegerType(node.line, node.row);
		}

		// return the identifier type and store it on the node
		return node.type = new IdentifierType(node.line, node.row,
				node.className.s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see syntaxtree.TypeVisitorAdapter#visit(syntaxtree.Not)
	 */
	public Type visit(Not node) {
		// gets the not expression type form the not expression expression
		Type expType = ExpHandler.secondPass(env, cinfo, minfo, node.exp);

		// check the variable expression type
		if (!(expType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a expressão \'", "Esperado: boolean",
					"Encontrado: " + expType });
		}

		// return the boolean type and store it on the node
		return node.type = new BooleanType(node.line, node.row);
	}

}
