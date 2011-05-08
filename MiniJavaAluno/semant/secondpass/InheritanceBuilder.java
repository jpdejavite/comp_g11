package semant.secondpass;

import java.util.ArrayList;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.Program;
import util.List;

public class InheritanceBuilder {

	private static ArrayList<Node> classList;

	private static List<ClassDecl> globalDeclList;

	public static void secondPass(Env env, Program p) {
		boolean haveCycle = false;
		globalDeclList = p.classList;

		// cria a lista de classes
		List<ClassDecl> classDeclList = p.classList;
		classList = new ArrayList<Node>();
		while (classDeclList != null && classDeclList.head != null) {
			classList.add(InheritanceBuilder.buildNode(classDeclList.head));

			classDeclList = classDeclList.tail;
		}
		classList.add(new Node(p.mainClass.className.s, null));

		// percorre a lista de classe procurando por ciclos
		for (Node node : classList) {
			if (InheritanceBuilder.isCycle(env, node, new ArrayList<String>())) {
				haveCycle = true;
				break;
			}
		}

		// se tiver encontrado algum ciclo
		if (haveCycle) {
			env.err.Error(p, new Object[] { "Herança cíclica detectada" });
		} else {
			classDeclList = p.classList;
			while (classDeclList != null && classDeclList.head != null) {
				InheritanceBuilder.setBase(classDeclList.head, env);

				classDeclList = classDeclList.tail;
			}
		}
	}

	private static void setBase(ClassDecl classDecl, Env env) {

		if (classDecl instanceof ClassDeclExtends) {
			// Seta a classe pai
			Symbol superClass = Symbol
					.symbol(((ClassDeclExtends) classDecl).superClass.s);
			ClassInfo superInfo = env.classes.get(superClass);
			if (superInfo == null) {
				env.err.Error(((ClassDeclExtends) classDecl),
						new Object[] { "Classe pai \'" + superClass
								+ "\' nao encontrada," });
			} else {
				InheritanceBuilder.setBase(InheritanceBuilder.getClassDeclList(
						superInfo, globalDeclList), env);
				if (env.classes.get(Symbol
						.symbol(((ClassDeclExtends) classDecl).name.s)).base == null) {
					env.classes
							.get(Symbol
									.symbol(((ClassDeclExtends) classDecl).name.s))
							.setBase(superInfo, env,
									((ClassDeclExtends) classDecl));
				}
			}
		}

	}

	private static ClassDecl getClassDeclList(ClassInfo superInfo,
			List<ClassDecl> classDeclList) {
		List<ClassDecl> cList = classDeclList;

		while (cList != null && cList.head != null) {
			if (cList.head.name.s.equals(superInfo.name.toString())) {
				return cList.head;
			}

			cList = cList.tail;
		}
		return null;

	}

	/**
	 * Retorna um node dependendo do tipo da ClassDecl.
	 * 
	 * @param head
	 *            the head
	 * @return the node
	 */
	private static Node buildNode(ClassDecl head) {
		if (head instanceof ClassDeclExtends) {
			return new Node(((ClassDeclExtends) head).name.s,
					((ClassDeclExtends) head).superClass.s);
		} else {
			return new Node(head.name.s, null);

		}
	}

	/**
	 * Checa se a lista de nós (nodeList) contem um ciclo.
	 * 
	 * @param e
	 *            the e
	 * @param node
	 *            the head
	 * @param nodeList
	 *            the array list
	 * @return true, if is cycle
	 */
	private static boolean isCycle(Env e, Node node, ArrayList<String> nodeList) {
		// adiciona o nó na lista
		nodeList.add(node.getClassName());

		// apenas procura ciclos se a classe tiver um pai
		if (node.getSuperClassName() != null) {

			// se o nó já estiver na lista achamos um ciclo
			if (nodeList.contains(node.getSuperClassName())) {
				return true;
			} else {
				// procura um ciclo partindo da classe pai
				return InheritanceBuilder.isCycle(e,
						InheritanceBuilder.getNode(node.getSuperClassName()),
						nodeList);
			}
		}
		return false;

	}

	private static Node getNode(String className) {
		// percorre a lista de classe procurando pela classe
		for (Node node : classList) {
			if (node.getClassName().equals(className)) {
				return node;
			}
		}

		return null;
	}
}
