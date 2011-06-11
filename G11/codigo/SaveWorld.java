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

		// Verifica o numero correto de argumentos 1 ou 6
		if (args.length != 1 && args.length != 6) {
			System.out
					.println("Usage: java SaveWorld path_arquivo (randomLimit) (maxRemove) (maxAdd) (timeLimit) (localLimit)");
			return;
		}

		// Le a entrada
		Grasp grasp = SaveWorld.readInput(args);

		// Executa o GRASP
		StationList solution = grasp.execute();

		// Imprime a solucao
		printSolution(solution);

	}

	private static void printSolution(StationList solution) {
		System.out.println("Valor: " + solution.getTotalCost());
		System.out.println("Total: " + solution.getStations().size());

		for (Station s : solution.getStations()) {
			System.out.println("S_" + s.getNumber());
		}
	}

	public static Grasp readInput(String[] args) {
		try {
			// Abre o arquivo e le linha por linha
			FileInputStream fstream = new FileInputStream(new File(args[0]));
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// Pega o numero de pontos
			strLine = br.readLine();
			Integer pointsNumber = Integer.valueOf(strLine.substring(2,
					strLine.length()));

			// Pega o numero de torres
			strLine = br.readLine();
			Integer stationsNumber = Integer.valueOf(strLine.substring(2,
					strLine.length()));

			// Le cada uma das torres e seus pontos
			StationList worstSolution = new StationList();
			List<Station> stationList = new ArrayList<Station>();
			int i = 1;
			while ((strLine = br.readLine()) != null) {
				Station s = Station.readStation(strLine, i++);
				worstSolution.addStation(s);
				stationList.add(s);
			}

			// Fecha arquivo
			in.close();

			// Gera a instancia do GRASP com os argumentos padrao ou com os
			// argumentos de entrada (usado para testes)
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
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
