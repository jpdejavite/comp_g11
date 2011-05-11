package semant.secondpass;

/**
 * The Class Node, gives the structure needed for cyclic inheritance detection.
 * All nodes have a class name (a real class name) and it's supper class (may be
 * null).
 */
public class Node {

	/** The class name. */
	private String className;

	/** The super class name. */
	private String superClassName;

	/**
	 * Instantiates a new node setting the class name and it's supper class.
	 * 
	 * @param className
	 *            the class name
	 * @param superClassName
	 *            the super class name
	 */
	public Node(String className, String superClassName) {
		this.setClassName(className);
		this.setSuperClassName(superClassName);
	}

	/**
	 * Sets the class name.
	 * 
	 * @param className
	 *            the new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Gets the class name.
	 * 
	 * @return the class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the super class name.
	 * 
	 * @param superClassName
	 *            the new super class name
	 */
	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	/**
	 * Gets the super class name.
	 * 
	 * @return the super class name
	 */
	public String getSuperClassName() {
		return superClassName;
	}
}
