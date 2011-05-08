package semant.secondpass;

import semant.Env;
import syntaxtree.ClassDecl;
import syntaxtree.Program;
import util.List;

public final class SecondPass {

	private SecondPass() {
		super();
	}

	public static void secondPass(Env e, Program p) {
		// InheritanceBuilder verifica herancas circulares
		InheritanceBuilder.secondPass(e, p);

		// verificação do programa
		// verificacao da classe main
		MainClassHandler.secondPass(e, p.mainClass);

		// lista de declarações de classes
		List<ClassDecl> classDeclList = p.classList;
		while (classDeclList != null && classDeclList.head != null) {
			ClassDeclHandler.secondPass(e, classDeclList.head);
			classDeclList = classDeclList.tail;
		}
	}

}
