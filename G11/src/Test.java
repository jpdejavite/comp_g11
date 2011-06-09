import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] a) throws IOException {
		PrintStream out = new PrintStream(new FileOutputStream(new File(
				"C:\\UNICAMP\\mc548\\trab\\instancia\\saidateste1")));
		System.setOut(out);
		String[] RANDOM_LIMIT = { "5", "50", "100" };
		String[] MAX_NEIGHBORS_REMOVE = { "5", "50", "100" };
		String[] MAX_NEIGHBORS_ADD = { "5", "50", "100" };
		String[] TIME_LIMIT = { "55000" };
		String[] LOCAL_TIME_LIMIT = { "15000", "25000", "55000" };
		String[] FILES = { "3000", "4000", "5000" };
		String prefix = "C:\\UNICAMP\\mc548\\trab\\instancia\\gigante";

		for (int filesIndex = 0; filesIndex < FILES.length; filesIndex++) {
			for (int localTimeLimitIndex = 0; localTimeLimitIndex < LOCAL_TIME_LIMIT.length; localTimeLimitIndex++) {
				for (int maxNeighborsAddIndex = 0; maxNeighborsAddIndex < MAX_NEIGHBORS_ADD.length; maxNeighborsAddIndex++) {
					for (int maxNeighborsRemoveIndex = 0; maxNeighborsRemoveIndex < MAX_NEIGHBORS_REMOVE.length; maxNeighborsRemoveIndex++) {
						for (int randomLimitIndex = 0; randomLimitIndex < RANDOM_LIMIT.length; randomLimitIndex++) {
							System.out
									.print(FILES[filesIndex]
											+ "\t"
											+ RANDOM_LIMIT[randomLimitIndex]
											+ "\t"
											+ MAX_NEIGHBORS_REMOVE[maxNeighborsRemoveIndex]
											+ "\t"
											+ MAX_NEIGHBORS_ADD[maxNeighborsAddIndex]
											+ "\t"
											+ TIME_LIMIT[0]
											+ "\t"
											+ LOCAL_TIME_LIMIT[localTimeLimitIndex]
											                   + "\t");
							String[] args = { prefix + FILES[filesIndex],
									RANDOM_LIMIT[randomLimitIndex], MAX_NEIGHBORS_REMOVE[maxNeighborsRemoveIndex],
									MAX_NEIGHBORS_ADD[maxNeighborsAddIndex], TIME_LIMIT[0],
									LOCAL_TIME_LIMIT[localTimeLimitIndex] };
							Main.main(args);
							System.out.println();
						}
					}
				}
			}
		}

	}

}
