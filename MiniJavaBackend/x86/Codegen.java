package x86;

import tree.CJUMP;
import tree.EXPSTM;
import tree.JUMP;
import tree.LABEL;
import tree.MOVE;
import tree.NAME;
import tree.SEQ;
import tree.Stm;
import util.Instructions;
import util.List;
import assem.Instr;
import assem.OPER;

public class Codegen {
	Frame frame;

	public Codegen(Frame f) {
		frame = f;
	}

	private List<Instr> ilist = null;
	private List<Instr> last = null;

	private void emit(Instr inst) {
		if (last != null)
			last = last.tail = new List<Instr>(inst, null);
		else
			last = ilist = new List<Instr>(inst, null);
	}
	
	private void munchStm(Stm stm) {
		// test all possible stm
		if (stm instanceof SEQ) {
			munchSeq((SEQ) stm);
		} else if (stm instanceof MOVE) {
			munchMove((MOVE) stm);
		} else if (stm instanceof LABEL) {
			munchLabel((LABEL) stm);
		} else if (stm instanceof JUMP) {
			munchJump((JUMP) stm);
		} else if (stm instanceof EXPSTM) {
			munchExpStm((EXPSTM) stm);
		} else if (stm instanceof CJUMP) {
			munchCJump((CJUMP) stm);
		} else {
			// haven't found any match, then error
			System.err.print("Statement doesn't match any type");
		}

	}

	private void munchCJump(CJUMP stm) {
		// TODO Auto-generated method stub

	}

	private void munchExpStm(EXPSTM stm) {
		// TODO Auto-generated method stub

	}

	private void munchJump(JUMP stm) {
	}
	
	// only emits the instruction
	private void munchLabel(LABEL stm) {
		// format assemb string
		String assemb = String.format(Instructions.LABEL, stm.label.toString());
		
		// creates new instruction
		emit(new assem.LABEL(assemb, stm.label));
	}

	private void munchMove(MOVE stm) {
		// TODO Auto-generated method stub

	}

	// for a sequence there are two stm inside, so it calls munch stm for each
	// one
	private void munchSeq(SEQ seq) {
		// calls munchStm for each stm on seq
		munchStm(seq.left);
		munchStm(seq.right);

	}

	/*-------------------------------------------------------------*
	 *                              MAIN                           *
	 *-------------------------------------------------------------*/
	List<Instr> codegen(Stm s) {
		List<Instr> l;
		munchStm(s);
		l = ilist;
		ilist = last = null;
		return l;
	}

	List<Instr> codegen(List<Stm> body) {
		List<Instr> l = null, t = null;

		for (; body != null; body = body.tail) {
			munchStm(body.head);
			if (l == null)
				l = ilist;
			else
				t.tail = ilist;
			t = last;
			ilist = last = null;
		}
		return l;
	}
}
