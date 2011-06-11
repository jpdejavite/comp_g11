import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SaveWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 1 && args.length != 6) {
			System.out
					.println("Usage: saveWorld path_arquivo (randomLimit) (maxRemove) (maxAdd) (timeLimit) (localLimit)");
			return;
		}

		Grasp grasp = SaveWorld.readInput(args);

		StationList solution = grasp.execute();

		printSolution(solution);

	}

	private static void printSolution(StationList solution) {
		System.out.print(solution.getTotalCost());
		// TODO System.out.println("Valor: " + solution.getTotalCost());
		// TODO System.out.println("Total: " + solution.getStations().size());

		// Collections.sort(solution.getStations(), new Comparator<Station>() {
		// @Override
		// public int compare(Station o1, Station o2) {
		// if (o1.getNumber() > o2.getNumber()) {
		// return 1;
		// } else if (o1.getNumber() < o2.getNumber()) {
		// return -1;
		// } else {
		// return 0;
		// }
		// }
		// });
		// TODO
		// for (Station s : solution.getStations()) {
		// System.out.println("S_" + s.getNumber());
		// }
		// System.out.println(System.currentTimeMillis() - msBegin);
	}

	public static Grasp readInput(String[] args) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(new File(args[0]));
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
			List<Station> stationList = new ArrayList<Station>();
			int i = 1;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				Station s = Station.readStation(strLine, i++);
				worstSolution.addStation(s);
				stationList.add(s);
			}

			// Close the input stream
			in.close();
			if (args.length < 6) {
				return new Grasp(pointsNumber, stationsNumber, stationList,
						System.currentTimeMillis(), worstSolution, 50, 100, 5,
						55000, 55000);
			} else {
				return new Grasp(pointsNumber, stationsNumber, stationList,
						System.currentTimeMillis(), worstSolution, Integer
								.valueOf(args[1]).intValue(), Integer.valueOf(
								args[2]).intValue(), Integer.valueOf(args[3])
								.intValue(), Integer.valueOf(args[4])
								.longValue(), Integer.valueOf(args[5])
								.longValue());
			}
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
