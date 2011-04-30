/* Create an AST, then invoke our interpreter. */

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PushbackReader;

import minijava.lexer.Lexer;
import minijava.node.Start;
import minijava.parser.Parser;

public class Main {
	public static void main(String[] args) {
		
//		if(args.length != 1) {
//			System.out.println("Usage: Main filePath");
//		} else {
//			try {
//				File file = new File(args[0]);
//				
//				Lexer lexer = new Lexer(new PushbackReader(
//						new FileReader(file),
//						1024));
//				Parser parser = new Parser(lexer);
//				Start ast = parser.parse();
//				//MiniJavaDepthFirstAdapter interp = new MiniJavaDepthFirstAdapter();
//				//ast.apply(interp);
//				
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//		}
		
		
		File base = new File("");
		File testSmall = new File(base.getAbsolutePath() + File.separator
				+ "testes" + File.separator + "small");
		File testLarge = new File(base.getAbsolutePath() + File.separator
				+ "testes" + File.separator + "large");
		File outSmall = new File(base.getAbsolutePath() + File.separator
				+ "testes" + File.separator + "saida" + File.separator
				+ "small");
		File outLarge = new File(base.getAbsolutePath() + File.separator
				+ "testes" + File.separator + "saida" + File.separator
				+ "large");

		Main.runTests(testSmall, outSmall);
		Main.runTests(testLarge, outLarge);
		
		System.out.println("The End");

	}


	private static void runTests(File testFolder, File outFolder) {
		for (File f : testFolder.listFiles()) {
			if (f.isDirectory()) {
				for (File inFile : f.listFiles()) {
					if (inFile.isFile()) {
						String fileSuffix = null;
						try {
							fileSuffix = inFile.getAbsolutePath().substring(
									testFolder.getAbsolutePath().length(),
									inFile.getAbsolutePath().length());
							File outFile = new File(outFolder.getAbsolutePath()
									+ fileSuffix);
							outFile.createNewFile();
							Lexer lexer = new Lexer(new PushbackReader(
									new FileReader(inFile.getAbsolutePath()),
									1024));
							Parser parser = new Parser(lexer);
							Start ast = parser.parse();
							MiniJavaDepthFirstAdapter interp = new MiniJavaDepthFirstAdapter(
									new PrintStream(outFile));
							ast.apply(interp);
						} catch (Exception e) {
							System.out.println(e);
							System.out
									.println("Erro no arquivo: " + fileSuffix);
						}
					}
				}
			}
		}
	}
}
