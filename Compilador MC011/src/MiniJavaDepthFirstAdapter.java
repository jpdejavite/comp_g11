import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import minijava.analysis.DepthFirstAdapter;
import minijava.node.AAndExp;
import minijava.node.AArrayAssignStatement;
import minijava.node.AArrayLengthExp;
import minijava.node.AArrayLookupExp;
import minijava.node.AAssignStatement;
import minijava.node.ABlockStatement;
import minijava.node.ABooleanTypeType;
import minijava.node.ACallExp;
import minijava.node.AClassDecl;
import minijava.node.AFalseExp;
import minijava.node.AFormal;
import minijava.node.AIdentifier;
import minijava.node.AIdentifierExpExp;
import minijava.node.AIdentifierTypeType;
import minijava.node.AIfStatement;
import minijava.node.AIntArrayTypeType;
import minijava.node.AIntegerLiteralExp;
import minijava.node.AIntegerTypeType;
import minijava.node.ALessThanExp;
import minijava.node.AMainClass;
import minijava.node.AMethodDecl;
import minijava.node.AMinusExp;
import minijava.node.ANewArrayExp;
import minijava.node.ANewObjectExp;
import minijava.node.ANotExp;
import minijava.node.APlusExp;
import minijava.node.APrintStatement;
import minijava.node.AProgram;
import minijava.node.AThisExp;
import minijava.node.ATimesExp;
import minijava.node.ATrueExp;
import minijava.node.AVarDecl;
import minijava.node.AWhileStatement;
import minijava.node.Node;
import minijava.node.PClassDecl;
import minijava.node.PExp;
import minijava.node.PFormal;
import minijava.node.PMethodDecl;
import minijava.node.PStatement;
import minijava.node.PVarDecl;
import minijava.node.Start;

public class MiniJavaDepthFirstAdapter extends DepthFirstAdapter {

	PrintStream out;

	private int ident;
	private boolean printSpace;

	public MiniJavaDepthFirstAdapter(PrintStream p) {
		super();
		out = p;
		ident = 0;
		printSpace = true;
	}

	public MiniJavaDepthFirstAdapter() {
		this(System.out);
	}

	private void beginNest() {
		ident += 4;
	}

	private void endNest() {
		ident -= 4;
	}

	private void print(String s) {
		if (printSpace)
			for (int i = 0; i < ident; i++)
				out.print(" ");
		out.print(s);

		printSpace = false;
	}

	private void println(String s) {
		if (printSpace)
			for (int i = 0; i < ident; i++)
				out.print(" ");
		out.println(s);

		printSpace = true;
	}

	public void inStart(Start node) {
		defaultIn(node);
	}

	public void outStart(Start node) {
		defaultOut(node);
	}

	public void defaultIn(Node node) {
		// Do nothing
	}

	public void defaultOut(Node node) {
		// Do nothing
	}

	@Override
	public void caseStart(Start node) {
		inStart(node);
		node.getPProgram().apply(this);
		node.getEOF().apply(this);
		outStart(node);
	}

	public void inAProgram(AProgram node) {
		defaultIn(node);
	}

	public void outAProgram(AProgram node) {
		defaultOut(node);
	}

	@Override
	public void caseAProgram(AProgram node) {
		inAProgram(node);
		if (node.getMainClass() != null) {
			node.getMainClass().apply(this);
		}
		{
			List<PClassDecl> copy = new ArrayList<PClassDecl>(node
					.getClassList());
			for (PClassDecl e : copy) {
				e.apply(this);
			}
		}
		outAProgram(node);
	}

	public void inAMainClass(AMainClass node) {

		defaultIn(node);
	}

	public void outAMainClass(AMainClass node) {
		defaultOut(node);
	}

	@Override
	public void caseAMainClass(AMainClass node) {
		inAMainClass(node);
		print("class ");
		if (node.getClassName() != null) {
			node.getClassName().apply(this);
		}
		println("");
		println("{");
		beginNest();

		print("public static void main(String[] ");
		if (node.getMainArgName() != null) {
			node.getMainArgName().apply(this);
		}
		println(")");
		println("{");
		beginNest();
		if (node.getS() != null) {
			node.getS().apply(this);
		}
		endNest();
		println("}");
		endNest();
		println("}");
		outAMainClass(node);
	}

	

	public void inAClassDecl(AClassDecl node) {
		defaultIn(node);
	}

	public void outAClassDecl(AClassDecl node) {
		defaultOut(node);
	}

	@Override
	public void caseAClassDecl(AClassDecl node) {
		print("class ");
		inAClassDecl(node);
		if (node.getName() != null) {
			node.getName().apply(this);
		}

		

		if (node.getSuperClass() != null) {
			print(" extends ");
			node.getSuperClass().apply(this);
		}

		println("");
		println("{");
		beginNest();

		{
			List<PVarDecl> copy = new ArrayList<PVarDecl>(node.getVarList());
			for (PVarDecl e : copy) {
				e.apply(this);
			}
		}
		{
			List<PMethodDecl> copy = new ArrayList<PMethodDecl>(node
					.getMethodList());
			for (PMethodDecl e : copy) {
				e.apply(this);
			}
		}
		outAClassDecl(node);

		endNest();
		println("}");
	}

	public void inAVarDecl(AVarDecl node) {
		defaultIn(node);
	}

	public void outAVarDecl(AVarDecl node) {
		defaultOut(node);
	}

	@Override
	public void caseAVarDecl(AVarDecl node) {
		inAVarDecl(node);
		if (node.getType() != null) {
			node.getType().apply(this);
		}

		print(" ");

		if (node.getName() != null) {
			node.getName().apply(this);
		}
		outAVarDecl(node);

		println(";");
	}

	public void inAMethodDecl(AMethodDecl node) {
		defaultIn(node);
	}

	public void outAMethodDecl(AMethodDecl node) {
		defaultOut(node);
	}

	@Override
	public void caseAMethodDecl(AMethodDecl node) {
		print("public ");

		inAMethodDecl(node);
		if (node.getReturnType() != null) {
			node.getReturnType().apply(this);
		}

		print(" ");

		if (node.getName() != null) {
			node.getName().apply(this);
		}

		print("(");

		{
			List<PFormal> copy = new ArrayList<PFormal>(node.getFormals());
			for (PFormal e : copy) {
				e.apply(this);

				if (copy.indexOf(e) != copy.size() - 1) {
					print(", ");
				}

			}
		}

		println(")");

		println("{");
		beginNest();

		{
			List<PVarDecl> copy = new ArrayList<PVarDecl>(node.getLocals());
			for (PVarDecl e : copy) {
				e.apply(this);
			}
		}
		{
			List<PStatement> copy = new ArrayList<PStatement>(node.getBody());
			for (PStatement e : copy) {
				e.apply(this);
			}
		}

		print("return ");

		if (node.getReturnExp() != null) {
			node.getReturnExp().apply(this);
		}
		outAMethodDecl(node);

		println(";");

		endNest();
		println("}");
	}

	public void inAFormal(AFormal node) {
		defaultIn(node);
	}

	public void outAFormal(AFormal node) {
		defaultOut(node);
	}

	@Override
	public void caseAFormal(AFormal node) {
		inAFormal(node);
		if (node.getType() != null) {
			node.getType().apply(this);
		}

		print(" ");

		if (node.getName() != null) {
			node.getName().apply(this);
		}
		outAFormal(node);
	}

	public void inAIntArrayTypeType(AIntArrayTypeType node) {
		defaultIn(node);
	}

	public void outAIntArrayTypeType(AIntArrayTypeType node) {
		defaultOut(node);
	}

	@Override
	public void caseAIntArrayTypeType(AIntArrayTypeType node) {
		inAIntArrayTypeType(node);

		print("int[]");

		outAIntArrayTypeType(node);
	}

	public void inABooleanTypeType(ABooleanTypeType node) {
		defaultIn(node);
	}

	public void outABooleanTypeType(ABooleanTypeType node) {
		defaultOut(node);
	}

	@Override
	public void caseABooleanTypeType(ABooleanTypeType node) {
		inABooleanTypeType(node);

		print("boolean");

		outABooleanTypeType(node);
	}

	public void inAIntegerTypeType(AIntegerTypeType node) {
		defaultIn(node);
	}

	public void outAIntegerTypeType(AIntegerTypeType node) {
		defaultOut(node);
	}

	@Override
	public void caseAIntegerTypeType(AIntegerTypeType node) {
		inAIntegerTypeType(node);

		print("int");

		outAIntegerTypeType(node);
	}

	public void inAIdentifierTypeType(AIdentifierTypeType node) {
		defaultIn(node);
	}

	public void outAIdentifierTypeType(AIdentifierTypeType node) {
		defaultOut(node);
	}

	@Override
	public void caseAIdentifierTypeType(AIdentifierTypeType node) {

		//print(node.getName().toString());

		inAIdentifierTypeType(node);
		if (node.getName() != null) {
			node.getName().apply(this);
		}
		outAIdentifierTypeType(node);
	}

	public void inABlockStatement(ABlockStatement node) {
		defaultIn(node);
	}

	public void outABlockStatement(ABlockStatement node) {
		defaultOut(node);
	}

	@Override
	public void caseABlockStatement(ABlockStatement node) {
		println("{");
		beginNest();

		inABlockStatement(node);
		{
			List<PStatement> copy = new ArrayList<PStatement>(node.getBody());
			for (PStatement e : copy) {
				e.apply(this);
			}
		}
		outABlockStatement(node);

		endNest();
		println("}");
	}

	public void inAIfStatement(AIfStatement node) {
		defaultIn(node);
	}

	public void outAIfStatement(AIfStatement node) {
		defaultOut(node);
	}

	@Override
	public void caseAIfStatement(AIfStatement node) {
		print("if ( ");

		inAIfStatement(node);
		if (node.getCondition() != null) {
			node.getCondition().apply(this);
		}

		println(")");
		beginNest();

		if (node.getThenClause() != null) {
			node.getThenClause().apply(this);
		}

		endNest();

		if (node.getElseClause() != null) {
			println("else");
			beginNest();
			node.getElseClause().apply(this);
			endNest();
		}
		outAIfStatement(node);
	}

	public void inAWhileStatement(AWhileStatement node) {
		defaultIn(node);
	}

	public void outAWhileStatement(AWhileStatement node) {
		defaultOut(node);
	}

	@Override
	public void caseAWhileStatement(AWhileStatement node) {
		print("while ( ");

		inAWhileStatement(node);
		if (node.getCondition() != null) {
			node.getCondition().apply(this);
		}

		println(")");
		beginNest();

		if (node.getBody() != null) {
			node.getBody().apply(this);
		}
		outAWhileStatement(node);

		endNest();
	}

	public void inAPrintStatement(APrintStatement node) {
		defaultIn(node);
	}

	public void outAPrintStatement(APrintStatement node) {
		defaultOut(node);
	}

	@Override
	public void caseAPrintStatement(APrintStatement node) {
		print("System.out.println( ");

		inAPrintStatement(node);
		if (node.getExp() != null) {
			node.getExp().apply(this);
		}
		outAPrintStatement(node);

		println(");");
	}

	public void inAAssignStatement(AAssignStatement node) {
		defaultIn(node);
	}

	public void outAAssignStatement(AAssignStatement node) {
		defaultOut(node);
	}

	@Override
	public void caseAAssignStatement(AAssignStatement node) {
		inAAssignStatement(node);
		if (node.getVar() != null) {
			node.getVar().apply(this);
		}

		print(" = ");

		if (node.getExp() != null) {
			node.getExp().apply(this);
		}
		outAAssignStatement(node);

		println(";");
	}

	public void inAArrayAssignStatement(AArrayAssignStatement node) {
		defaultIn(node);
	}

	public void outAArrayAssignStatement(AArrayAssignStatement node) {
		defaultOut(node);
	}

	@Override
	public void caseAArrayAssignStatement(AArrayAssignStatement node) {
		inAArrayAssignStatement(node);
		if (node.getVar() != null) {
			node.getVar().apply(this);
		}

		print("[");

		if (node.getIndex() != null) {
			node.getIndex().apply(this);
		}

		print("] = ");

		if (node.getValue() != null) {
			node.getValue().apply(this);
		}
		outAArrayAssignStatement(node);

		println(";");
	}

	public void inAAndExp(AAndExp node) {
		defaultIn(node);
	}

	public void outAAndExp(AAndExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAAndExp(AAndExp node) {
		inAAndExp(node);
		if (node.getLhs() != null) {
			node.getLhs().apply(this);
		}

		print(" && ");

		if (node.getRhs() != null) {
			node.getRhs().apply(this);
		}
		outAAndExp(node);
	}

	public void inALessThanExp(ALessThanExp node) {
		defaultIn(node);
	}

	public void outALessThanExp(ALessThanExp node) {
		defaultOut(node);
	}

	@Override
	public void caseALessThanExp(ALessThanExp node) {
		inALessThanExp(node);
		if (node.getLhs() != null) {
			node.getLhs().apply(this);
		}

		print(" < ");

		if (node.getRhs() != null) {
			node.getRhs().apply(this);
		}
		outALessThanExp(node);
	}

	public void inAPlusExp(APlusExp node) {
		defaultIn(node);
	}

	public void outAPlusExp(APlusExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAPlusExp(APlusExp node) {
		inAPlusExp(node);
		if (node.getLhs() != null) {
			node.getLhs().apply(this);
		}

		print(" + ");

		if (node.getRhs() != null) {
			node.getRhs().apply(this);
		}
		outAPlusExp(node);
	}

	public void inAMinusExp(AMinusExp node) {
		defaultIn(node);
	}

	public void outAMinusExp(AMinusExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAMinusExp(AMinusExp node) {
		inAMinusExp(node);
		if (node.getLhs() != null) {
			node.getLhs().apply(this);
		}

		print(" - ");

		if (node.getRhs() != null) {
			node.getRhs().apply(this);
		}
		outAMinusExp(node);
	}

	public void inATimesExp(ATimesExp node) {
		defaultIn(node);
	}

	public void outATimesExp(ATimesExp node) {
		defaultOut(node);
	}

	@Override
	public void caseATimesExp(ATimesExp node) {
		inATimesExp(node);
		if (node.getLhs() != null) {
			node.getLhs().apply(this);
		}

		print(" * ");

		if (node.getRhs() != null) {
			node.getRhs().apply(this);
		}
		outATimesExp(node);
	}

	public void inAArrayLookupExp(AArrayLookupExp node) {
		defaultIn(node);
	}

	public void outAArrayLookupExp(AArrayLookupExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAArrayLookupExp(AArrayLookupExp node) {
		inAArrayLookupExp(node);
		if (node.getArray() != null) {
			node.getArray().apply(this);
		}

		print("[");

		if (node.getIndex() != null) {
			node.getIndex().apply(this);
		}
		outAArrayLookupExp(node);

		print("]");
	}

	public void inAArrayLengthExp(AArrayLengthExp node) {
		defaultIn(node);
	}

	public void outAArrayLengthExp(AArrayLengthExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAArrayLengthExp(AArrayLengthExp node) {
		inAArrayLengthExp(node);
		if (node.getArray() != null) {
			node.getArray().apply(this);
		}
		outAArrayLengthExp(node);

		print(".length");
	}

	public void inACallExp(ACallExp node) {
		defaultIn(node);
	}

	public void outACallExp(ACallExp node) {
		defaultOut(node);
	}

	@Override
	public void caseACallExp(ACallExp node) {
		inACallExp(node);
		if (node.getObject() != null) {
			node.getObject().apply(this);
		}

		print(".");

		if (node.getMethod() != null) {
			node.getMethod().apply(this);
		}

		print("(");

		{
			List<PExp> copy = new ArrayList<PExp>(node.getActuals());
			for (PExp e : copy) {
				e.apply(this);

				if (copy.indexOf(e) != copy.size() - 1) {
					print(", ");
				}
			}
		}
		outACallExp(node);

		print(")");
	}

	public void inAIntegerLiteralExp(AIntegerLiteralExp node) {
		defaultIn(node);
	}

	public void outAIntegerLiteralExp(AIntegerLiteralExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAIntegerLiteralExp(AIntegerLiteralExp node) {
		print(node.getValue().toString());

		inAIntegerLiteralExp(node);
		if (node.getValue() != null) {
			node.getValue().apply(this);
		}
		outAIntegerLiteralExp(node);
	}

	public void inATrueExp(ATrueExp node) {
		defaultIn(node);
	}

	public void outATrueExp(ATrueExp node) {
		defaultOut(node);
	}

	@Override
	public void caseATrueExp(ATrueExp node) {
		inATrueExp(node);

		print("true");

		outATrueExp(node);
	}

	public void inAFalseExp(AFalseExp node) {
		defaultIn(node);
	}

	public void outAFalseExp(AFalseExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAFalseExp(AFalseExp node) {
		inAFalseExp(node);

		print("false");

		outAFalseExp(node);
	}

	public void inAThisExp(AThisExp node) {
		defaultIn(node);
	}

	public void outAThisExp(AThisExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAThisExp(AThisExp node) {
		inAThisExp(node);

		print("this");

		outAThisExp(node);
	}

	public void inANewArrayExp(ANewArrayExp node) {
		defaultIn(node);
	}

	public void outANewArrayExp(ANewArrayExp node) {
		defaultOut(node);
	}

	@Override
	public void caseANewArrayExp(ANewArrayExp node) {
		print("new int[");

		inANewArrayExp(node);
		if (node.getSize() != null) {
			node.getSize().apply(this);
		}
		outANewArrayExp(node);

		print("]");
	}

	public void inANewObjectExp(ANewObjectExp node) {
		defaultIn(node);
	}

	public void outANewObjectExp(ANewObjectExp node) {
		defaultOut(node);
	}

	@Override
	public void caseANewObjectExp(ANewObjectExp node) {
		print("new ");

		inANewObjectExp(node);
		if (node.getClassname() != null) {
			node.getClassname().apply(this);
		}
		outANewObjectExp(node);

		print("()");
	}

	public void inANotExp(ANotExp node) {
		defaultIn(node);
	}

	public void outANotExp(ANotExp node) {
		defaultOut(node);
	}

	@Override
	public void caseANotExp(ANotExp node) {
		print("!");

		inANotExp(node);
		if (node.getExp() != null) {
			node.getExp().apply(this);
		}
		outANotExp(node);
	}

	public void inAIdentifierExpExp(AIdentifierExpExp node) {
		defaultIn(node);
	}

	public void outAIdentifierExpExp(AIdentifierExpExp node) {
		defaultOut(node);
	}

	@Override
	public void caseAIdentifierExpExp(AIdentifierExpExp node) {
		inAIdentifierExpExp(node);
		if (node.getName() != null) {
			node.getName().apply(this);
		}
		outAIdentifierExpExp(node);
	}

	public void inAIdentifier(AIdentifier node) {
		defaultIn(node);
	}

	public void outAIdentifier(AIdentifier node) {
		defaultOut(node);
	}

	@Override
	public void caseAIdentifier(AIdentifier node) {
		print(node.getS().toString());
		inAIdentifier(node);
		if (node.getS() != null) {
			node.getS().apply(this);
		}
		outAIdentifier(node);
	}
}
