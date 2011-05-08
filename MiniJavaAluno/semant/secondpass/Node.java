package semant.secondpass;

public class Node {
	private String className;
	private String superClassName;

	public Node(String className, String superClassName) {
		this.setClassName(className);
		this.setSuperClassName(superClassName);
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	public String getSuperClassName() {
		return superClassName;
	}
}
