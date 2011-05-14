package errors;

import syntaxtree.Absyn;

public interface ErrorEchoer
{
	public void Println(Object[] msg);
	public void Print(Object[] msg);
	public void Error(Absyn obj, Object[] msg);
	public void Warning(Absyn obj, Object[] msg);
	public int ErrorCount();
	public int WarningCount();
	public void Reset();
}
