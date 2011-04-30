package semant;

import symbol.ClassInfo;
import syntaxtree.Print;
import syntaxtree.VisitorAdapter;

public class PrintStatementHandler extends VisitorAdapter {
	
	private Env env;
	
	private ClassInfo info;

	private PrintStatementHandler(Env e, ClassInfo i) {
		super();

		env = e;
		info = i;
	}
	
	@Override
	public void visit(Print node) {
		System.out.println("oi");
	}

}
