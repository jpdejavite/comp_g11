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

	/**
	 * Second pass for Inheritance.
	 * 
	 * @param env
	 *            the enviroment
	 * @param p
	 *            the program
	 */
	public static void secondPass(Env env, Program p) {
		// Variable to get the result of cycle Inheritance
		boolean haveCycle = false;

		// Global variable of class list
		globalDeclList = p.classList;

		// Iterates through the class list and adds the class name and its super
		// the to arraylist
		List<ClassDecl> classDeclList = p.classList;
		classList = new ArrayList<Node>();
		while (classDeclList != null && classDeclList.head != null) {
			classList.add(InheritanceBuilder.buildNode(classDeclList.head));

			classDeclList = classDeclList.tail;
		}
		// Finally adds the main class to the array list
		classList.add(new Node(p.mainClass.className.s, null));

		// pIterates through the class arraylist and calls the method to detect
		// cycle for each class. If founds it, then breaks
		for (Node node : classList) {
			if (InheritanceBuilder.isCycle(env, node, new ArrayList<String>())) {
				haveCycle = true;
				break;
			}
		}

		// If have found any cycle then adds an error
		if (haveCycle) {
			env.err.Error(p, new Object[] { "Heran�a c�clica detectada" });
		} else {
			// If there is no cycle then goes through the original class list
			// and calls the ste base method for each one (when there is the
			// need - ClassDeclExtends)
			classDeclList = p.classList;
			while (classDeclList != null && classDeclList.head != null) {
				InheritanceBuilder.setBase(classDeclList.head, env);

				classDeclList = classDeclList.tail;
			}
		}
	}

	private static void setBase(ClassDecl classDecl, Env env) {

		if (classDecl instanceof ClassDeclExtends) {
			// Creates the super class info
			Symbol superClass = Symbol
					.symbol(((ClassDeclExtends) classDecl).superClass.s);
			ClassInfo superInfo = env.classes.get(superClass);

			// If it doesnt exists then adds an error
			if (superInfo == null) {
				env.err.Error(((ClassDeclExtends) classDecl),
						new Object[] { "Classe pai \'" + superClass
								+ "\' nao encontrada," });
			} else {
				// Applies set base to the super class before aplying the the
				// given class
				InheritanceBuilder.setBase(InheritanceBuilder.getClassDeclList(
						superInfo, globalDeclList), env);

				// If the given class hasnt been apllied the base, then apllies
				if (env.classes.get(Symbol
						.symbol(((ClassDeclExtends) classDecl).name.s)).base == null) {
					env.classes
							.get(
									Symbol
											.symbol(((ClassDeclExtends) classDecl).name.s))
							.setBase(superInfo, env,
									((ClassDeclExtends) classDecl));
				}
			}
		}

	}

	/**
	 * Gets the class declaration list.
	 * 
	 * @param classInfo
	 *            the class info
	 * @param classDeclList
	 *            the class decl list
	 * @return the class decl list
	 */
	private static ClassDecl getClassDeclList(ClassInfo classInfo,
			List<ClassDecl> classDeclList) {
		List<ClassDecl> cList = classDeclList;
		// Goes through the class list searching for the class name
		while (cList != null && cList.head != null) {
			if (cList.head.name.s.equals(classInfo.name.toString())) {
				return cList.head;
			}

			cList = cList.tail;
		}
		return null;

	}

	/**
	 * Builds an new node, given the class declaration type
	 * 
	 * @param node
	 *            the head
	 * @return the node
	 */
	private static Node buildNode(ClassDecl node) {
		// If the node is ClassDeclExtends then adds the superclass name, if not
		// then puts null
		if (node instanceof ClassDeclExtends) {
			return new Node(((ClassDeclExtends) node).name.s,
					((ClassDeclExtends) node).superClass.s);
		} else {
			return new Node(node.name.s, null);

		}
	}

	/**
	 * Checks if there is an cycle in the given node list
	 * 
	 * @param e
	 *            the enviroment
	 * @param node
	 *            the node
	 * @param nodeList
	 *            the classes array list
	 * @return true, if is cycle
	 */
	private static boolean isCycle(Env e, Node node, ArrayList<String> nodeList) {
		
		// Adds the given node on the list
		nodeList.add(node.getClassName());

		// If there is a super class, then searches for a cycle
		if (node.getSuperClassName() != null) {

			// If the super class it is on the list, then there is a cycle
			if (nodeList.contains(node.getSuperClassName())) {
				return true;
			} else {
				// If not, then looks for a cycle starting on the superclass
				return InheritanceBuilder.isCycle(e, InheritanceBuilder
						.getNode(node.getSuperClassName()), nodeList);
			}
		}
		return false;

	}

	/**
	 * Gets the node with the given class name on the class array list.
	 * 
	 * @param className
	 *            the class name
	 * @return the node
	 */
	private static Node getNode(String className) {
		// Goes through the class list searching for the class name
		for (Node node : classList) {
			if (node.getClassName().equals(className)) {
				return node;
			}
		}

		return null;
	}
}
