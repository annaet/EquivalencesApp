package treeBuilder;

import java.util.ArrayList;
import java.util.SortedSet;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import parser.ExprLexer;
import parser.ExprParser;
import parser.ExprWalker;
import treeManipulation.RuleApplicator;
import treeManipulation.TruthTable;

public class Compiler {
	public static void main(String args[]) {
		Compiler compiler = new Compiler();
//		String s = "(r&q)&p";
		String s = "a<->b";
		FormationTree tree = compiler.compile(s);
		System.out.println(tree.toString() + "\n");
		
		TruthTable tt = new TruthTable(compiler);
		ArrayList<Integer> vals = tt.getTruthValues(tree);
		
		for (int i = 0; i < vals.size(); ++i) {
			System.out.print(vals.get(i));
		}
		System.out.println("\n");
		
		Node node = tree.findNode(0, 0);
		
//		TreeIterator it = new TreeIterator(node);
//		while (it.hasNext()) {
//			System.out.println(it.next().getValue());
//		}
//		
//		RuleSelector rs = new RuleSelector();
//		BitSet bs = rs.getApplicableRules(tree, node);
//		
//		RuleApplicator ra = new RuleApplicator();
//		ra.applyDistributivityAndRight(tree, (BinaryOperator) node);
//		ra.applyRandomRule(bs, tree, (BinaryOperator) node);
//		ra.applyRuleFromBitSet(bs, 1, tree, (BinaryOperator) node); 
		System.out.println(tree.toTreeString());
	}
	
	public FormationTree compile(String expr) {
		CharStream input = new ANTLRInputStream(expr);
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		
		FormationTree tree = new FormationTree();
		
		ParseTree parseTree = parser.prog();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ExprWalker(tree), parseTree);

        return tree;
	}
}