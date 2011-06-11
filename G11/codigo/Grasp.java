import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * a Classe Grasp traz todas as implementações dos algoritmos envolvendo o
 * algoritmo GRASP e busca local.
 */
public class Grasp {

	/** numero de pontos da entrada. */
	private Integer pointsNumber;

	/** numero de torres da entrada. */
	private Integer stationsNumber;

	/** lista de torres. */
	private List<Station> stationList;

	/** tempo do sistema logo apos a leitura da entrada */
	private long msBegin;

	/** variavel que diz se a busca local chegou a um minimo */
	private boolean isLocalMin;

	/** pior solução encontrada */
	private final StationList worstSolution;

	/** limite randomico. */
	private int RANDOM_LIMIT = 30;

	/** maximo de vizinhos para remocao */
	private int MAX_NEIGHBORS_REMOVE = 10;

	/** maximo de vizinhos para adicao */
	private int MAX_NEIGHBORS_ADD = 10;

	/** tempo maximo de execucao para o GRASP */
	private long TIME_LIMIT = 55000;

	/** tempo maximo de execucao da busca local */
	private long LOCAL_TIME_LIMIT = 55000;

	/**
	 * Instancia uma nova GRASP.
	 * 
	 * @param pointsNumber
	 *            the points number
	 * @param stationsNumber
	 *            the stations number
	 * @param stationList
	 *            the station list
	 * @param msBegin
	 *            the ms begin
	 * @param worstSolution
	 *            the worst solution
	 * @param randomLimit
	 *            the random limit
	 * @param maxRemove
	 *            the max remove
	 * @param maxAdd
	 *            the max add
	 * @param timeLimit
	 *            the time limit
	 * @param localLimit
	 *            the local limit
	 */
	public Grasp(Integer pointsNumber, Integer stationsNumber,
			List<Station> stationList, long msBegin, StationList worstSolution,
			int randomLimit, int maxRemove, int maxAdd, long timeLimit,
			long localLimit) {
		this.setPointsNumber(pointsNumber);
		this.setStationsNumber(stationsNumber);
		this.setStationList(stationList);
		this.msBegin = msBegin;
		this.worstSolution = worstSolution;
		this.RANDOM_LIMIT = randomLimit;
		this.MAX_NEIGHBORS_REMOVE = maxRemove;
		this.MAX_NEIGHBORS_ADD = maxAdd;
		this.TIME_LIMIT = timeLimit;
		this.LOCAL_TIME_LIMIT = localLimit;
	}

	/**
	 * Execute.
	 * 
	 * @return the station list
	 */
	public StationList execute() {
		// starSolution guarda a melhor solucao ate o momento
		StationList solution = new StationList();
		StationList starSolution = worstSolution;

		Collections.sort(stationList, new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				return o1.getPonderation().compareTo(o2.getPonderation());
			}
		});

		// Executa o GRASP usando 55s como criterio de parada
		while (System.currentTimeMillis() - msBegin < 50000) {
			solution = randomGreedySolution();
			solution = localSearch(solution);
			if (solution.getTotalCost().compareTo(starSolution.getTotalCost()) < 0) {
				starSolution = solution;
			}
		}

		return starSolution;
	}

	/**
	 * Local search.
	 * 
	 * @param solution
	 *            the solution
	 * @return the station list
	 */
	private StationList localSearch(StationList solution) {
		isLocalMin = false;
		StationList starStationList = solution;
		long localBegin = System.currentTimeMillis();

		// Executa a busca local, tendo 3 criterios de paradas:
		// Tempo de execucao global do programa < TIME_LIMIT = 55s
		// Tempo de execucao da busca local < LOCAL_TIME_LIMIT = 55s
		// Nao chegou a um minimo local
		while (System.currentTimeMillis() - msBegin < TIME_LIMIT
				&& System.currentTimeMillis() - localBegin < LOCAL_TIME_LIMIT
				&& !isLocalMin) {

			// Seleciona o melhor vizinho e verifica se o vizinho eh melhor que
			// a melhor solucao atual
			StationList bestNeighbor = getBestNeighbor(starStationList);
			if (bestNeighbor.getTotalCost().compareTo(
					starStationList.getTotalCost()) < 0) {
				starStationList = bestNeighbor;
			} else {
				isLocalMin = true;
			}

		}
		return starStationList;
	}

	/**
	 * Gets the best neighbor.
	 * 
	 * @param solution
	 *            the solution
	 * @return the best neighbor
	 */
	private StationList getBestNeighbor(StationList solution) {
		// Guarda o melhor vizinho
		StationList bestNeighbor = worstSolution;

		// Pega a solucao atual e retira um de seus elementos por vez, ate ter
		// retirado MAX_NEIGHBORS_REMOVE. Apos isso insere elementos que nao
		// estao na solucao, um por vez, ate ter inserido MAX_NEIGHBORS_ADD
		for (int i = stationList.size() - 1, j = 0; i >= 0
				&& j < MAX_NEIGHBORS_REMOVE; i--) {
			Station s = stationList.get(i);
			if (solution.getStations().contains(s)) {
				j++;
				StationList firstNeighbor = solution;
				firstNeighbor.removeStation(s);
				if (isViable(firstNeighbor)
						&& firstNeighbor.getTotalCost().compareTo(
								bestNeighbor.getTotalCost()) < 0) {
					bestNeighbor = createCopySolutionList(firstNeighbor);
				}
				firstNeighbor.addStation(s);
				for (int k = 0, l = 0; k < stationList.size()
						&& l < MAX_NEIGHBORS_ADD; k++) {
					Station t = stationList.get(k);
					if (!solution.getStations().contains(t)) {
						l++;
						StationList neighbor = solution;
						neighbor.removeStation(s);
						neighbor.addStation(t);
						if (isViable(neighbor)
								&& neighbor.getTotalCost().compareTo(
										bestNeighbor.getTotalCost()) < 0) {
							bestNeighbor = createCopySolutionList(neighbor);
						}
						neighbor.removeStation(t);
						neighbor.addStation(s);
					}
				}
			}
		}
		return bestNeighbor;
	}

	/**
	 * Creates the copy solution list.
	 * 
	 * @param solution
	 *            the solution
	 * @return the station list
	 */
	private StationList createCopySolutionList(StationList solution) {
		// Copia a solucao
		StationList starStationList = new StationList();
		starStationList.getStations().addAll(solution.getStations());
		starStationList.setTotalCost(solution.getTotalCost());
		return starStationList;
	}

	/**
	 * Random greedy solution.
	 * 
	 * @return the station list
	 */
	private StationList randomGreedySolution() {
		// Gera uma solucao gulosa aleatoria
		StationList solution = new StationList();

		// Copia a lista de torres inicial (ja ordenada por valor de ponderacao)
		List<Station> l = buildRestrictCanditatesList(solution);

		// Adiciona elementos ate a solucao ser viavel
		while (!isViable(solution)) {

			// Seleciona um element
			Station e = randomGreegyElement(l);
			solution.addStation(e);
		}

		return solution;
	}

	/**
	 * Random greegy element.
	 * 
	 * @param l
	 *            the l
	 * @return the station
	 */
	private Station randomGreegyElement(List<Station> l) {
		// Retira da lista L (ordenada por valor de ponderacao) um dos
		// RANDOM_LIMIT primeiros elementos
		return l.remove((int) ((Math.random() * RANDOM_LIMIT % RANDOM_LIMIT) % l
				.size()));

	}

	/**
	 * Builds the restrict canditates list.
	 * 
	 * @param solution
	 *            the solution
	 * @return the list
	 */
	private List<Station> buildRestrictCanditatesList(StationList solution) {
		// Copia a lista de torres inicial, que ja esta ordenada por valor de
		// ponderacao
		List<Station> stations = new ArrayList<Station>();

		stations.addAll(stationList);

		return stations;
	}

	/**
	 * Checks if is viable.
	 * 
	 * @param solution
	 *            the solution
	 * @return true, if is viable
	 */
	private boolean isViable(StationList solution) {
		// Verifica se a solucao eh viavel, adicionando todos os pontos de todas
		// as torres a um conjunto e verificando se o numero de pontos eh do
		// tamanho correto
		Set<Integer> points = new HashSet<Integer>();
		for (Station s : solution.getStations()) {
			for (Integer i : s.getPoints()) {
				points.add(i);
			}
		}
		if (points.size() < pointsNumber) {
			return false;
		}
		return true;
	}

	/**
	 * Sets the points number.
	 * 
	 * @param pointsNumber
	 *            the new points number
	 */
	public void setPointsNumber(Integer pointsNumber) {
		this.pointsNumber = pointsNumber;
	}

	/**
	 * Gets the points number.
	 * 
	 * @return the points number
	 */
	public Integer getPointsNumber() {
		return pointsNumber;
	}

	/**
	 * Sets the stations number.
	 * 
	 * @param stationsNumber
	 *            the new stations number
	 */
	public void setStationsNumber(Integer stationsNumber) {
		this.stationsNumber = stationsNumber;
	}

	/**
	 * Gets the stations number.
	 * 
	 * @return the stations number
	 */
	public Integer getStationsNumber() {
		return stationsNumber;
	}

	/**
	 * Sets the station list.
	 * 
	 * @param stationList
	 *            the new station list
	 */
	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}

	/**
	 * Gets the station list.
	 * 
	 * @return the station list
	 */
	public List<Station> getStationList() {
		return stationList;
	}

}
