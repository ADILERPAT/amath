package pl.edu.pw.iem.modrzejm.amath;

import org.nfunk.jep.*;
import org.lsmp.djep.matrixJep.*;
import org.lsmp.djep.matrixJep.nodeTypes.*;

public class Evaluator {
	private MatrixJep j;

	public Evaluator() {
		j = new MatrixJep();
		j.addStandardConstants();
		j.addStandardFunctions();
		j.addComplex();
		j.setAllowUndeclared(true);
		j.setImplicitMul(true);
		j.setAllowAssignment(true);
	}

	public String eval(String str) {
		try {
			Node node = j.parse(str);
			Node proc = j.preprocess(node);
			Node simp = j.simplify(proc);
			Object value = j.evaluate(simp);
			// j.print(simp);
			// return "\t dim "+((MatrixNodeI) simp).getDim();
			return "\tvalue " + value.toString();
		} catch (ParseException e) {
			System.out.println("Parse error " + e.getMessage());
		} catch (Exception e) {
			System.out.println("evaluation error " + e.getMessage());
		}
		return "";
	}
}