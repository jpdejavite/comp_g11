import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Usage: saveWorld path_arquivo");
			return;
		}
		
		long msBegin = System.currentTimeMillis();
		
		Grasp grasp = Main.readInput(args[0], msBegin);

		StationList solution = grasp.execute();

		printSolution(solution, msBegin);

	}

	private static void printSolution(StationList solution, long msBegin) {
		System.out.println("Valor: " + solution.getTotalCost());
		System.out.println("Total: " + solution.getStations().size());

		Collections.sort(solution.getStations(), new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				if (o1.getNumber() > o2.getNumber()) {
					return 1;
				} else if (o1.getNumber() < o2.getNumber()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		for (Station s : solution.getStations()) {
			System.out.println("S_" + s.getNumber());
		}
		System.out.println(System.currentTimeMillis() - msBegin);
	}

	public static Grasp readInput(String path, long msBegin) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(new File(path));
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			strLine = br.readLine();
			Integer pointsNumber = Integer.valueOf(strLine.substring(2, strLine
					.length()));

			strLine = br.readLine();
			Integer stationsNumber = Integer.valueOf(strLine.substring(2,
					strLine.length()));

			StationList worstSolution = new StationList();
			int i = 1;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				Station s = Station.readStation(strLine, i++);
				worstSolution.addStation(s);
			}

			// Close the input stream
			in.close();

			return new Grasp(pointsNumber, stationsNumber, worstSolution.getStations(), msBegin, worstSolution);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
