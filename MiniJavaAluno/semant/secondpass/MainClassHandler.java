package semant.secondpass;

import semant.Env;
import symbol.Symbol;
import syntaxtree.MainClass;
import syntaxtree.VisitorAdapter;

public class MainClassHandler extends VisitorAdapter {

	private Env env;

	private MainClassHandler(Env e) {
		super();

		env = e;
	}

	static void secondPass(Env e, MainClass mainClass) {

		MainClassHandler h = new MainClassHandler(e);

		mainClass.accept(h);

	}

	public void visit(MainClass node) {

		Symbol className = Symbol.symbol(node.className.s);
		
		// verifica a declaracao
		StatementHandler.secondPass(env, env.classes.get(className), null, node.s);
		
	}
}
