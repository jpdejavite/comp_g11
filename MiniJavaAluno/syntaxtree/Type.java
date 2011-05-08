package syntaxtree;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;

public abstract class Type extends Absyn {
	public boolean isComparable(Type t, Env env, Symbol className, int line,
			int row) {
		if (className == null || (!(t instanceof IdentifierType) || !(this instanceof IdentifierType))) {
			return t.getClass() == this.getClass();
		} else {

			if (((IdentifierType) t).name.equals(((IdentifierType) this).name)) {
				return true;
			} else {
				ClassInfo classInfo = env.classes.get(className);

				if (classInfo == null || classInfo.base == null) {
					return false;
				} else {

					return new IdentifierType(line, row,
							classInfo.base.name.toString()).isComparable(t,
							env, classInfo.base.name, line, row);
				}
			}
		}
	}

	public Type(int l, int r) {
		super(l, r);
	}

	public static Symbol getTypeName(Type type) {
		if (type instanceof IdentifierType) {
			return Symbol.symbol(((IdentifierType) type).name);
		}
		return null;
	}
}
