package semant;

import symbol.ClassInfo;
import syntaxtree.Print;
import syntaxtree.Statement;
import syntaxtree.VisitorAdapter;

public class StatementHandler extends VisitorAdapter {
	
	private Env env;
	
	private ClassInfo info;

	private StatementHandler(Env e, ClassInfo i) {
		super();

		env = e;
		info = i;
	}
	
	public static void firstPass(Env env, ClassInfo info, Statement s) {
		StatementHandler statementHandler = new StatementHandler(env, info);
		
		s.accept(statementHandler);
		
	}
	
	@Override
	public void visit(Print node) {
		System.out.println("oi");
	}

}
