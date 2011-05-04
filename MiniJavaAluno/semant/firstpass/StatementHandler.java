package semant.firstpass;

import semant.Env;
import symbol.ClassInfo;
import syntaxtree.VisitorAdapter;

public class StatementHandler extends VisitorAdapter {

	private Env env;

	private ClassInfo info;

	private StatementHandler(Env e, ClassInfo i) {
		super();

		env = e;
		info = i;
	}

}
