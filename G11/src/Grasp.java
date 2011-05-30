import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grasp {

	private Integer pointsNumber;
	private Integer stationsNumber;
	private List<Station> stationList;
	private long msBegin;
	private boolean isLocalMin;

	private final static int K = 5;

	public Grasp(Integer pointsNumber, Integer stationsNumber,
			List<Station> stationList) {
		this.setPointsNumber(pointsNumber);
		this.setStationsNumber(stationsNumber);
		this.setStationList(stationList);
	}

	public StationList execute() {
		StationList solution = new StationList();
		StationList starSolution = new StationList();
		Station station = new Station();
		station.setCost(BigDecimal.valueOf(Integer.MAX_VALUE));
		starSolution.addStation(station);

		msBegin = System.currentTimeMillis();

		while (System.currentTimeMillis() - msBegin < 50000) {
			solution = randomGreedySolution();
			solution = localSearch(solution);
			if (solution.getTotalCost().compareTo(starSolution.getTotalCost()) < 0) {
				starSolution = solution;
			}
		}

		return starSolution;
	}

	private StationList localSearch(StationList solution) {
		isLocalMin = false;
		StationList starStationList = createCopySolutionList(solution);
		StationList copyStationList = createCopySolutionList(solution);

		while (System.currentTimeMillis() - msBegin < 50000 && !isLocalMin) {
			Station changeStation = null;
			Integer changeIndex = null;

			// percorre todos os elementos da solucao
			for (Station station : solution.getStations()) {
				// remove fom aux list
				removeFromStationList(station, copyStationList);

				// se for valida atualiza a solucao otima
				if (isViable(copyStationList)) {
					if (copyStationList.getTotalCost().compareTo(
							starStationList.getTotalCost()) < 0) {
						changeStation = null;
						changeIndex = solution.getStations().indexOf(station);
						starStationList = createCopySolutionList(copyStationList);
					}
				}

				// adiciona todos os vizinhos

			}

		}
		return solution;
	}

	private void removeFromStationList(Station station,
			StationList copyStationList) {
		copyStationList.getStations().remove(station);
		copyStationList.setTotalCost(copyStationList.getTotalCost().subtract(
				station.getCost()));
	}

	private StationList createCopySolutionList(StationList solution) {
		StationList starStationList = new StationList();
		starStationList.getStations().addAll(solution.getStations());
		starStationList.setTotalCost(solution.getTotalCost());
		return starStationList;
	}

	private StationList randomGreedySolution() {
		StationList solution = new StationList();

		List<Station> l = buildRestrictCanditatesList(solution);

		while (!isViable(solution)) {

			Station e = randomGreegyElement(l);
			solution.addStation(e);
		}

		return solution;
	}

	private Station randomGreegyElement(List<Station> l) {
		List<Station> stations = new ArrayList<Station>();

		for (Station s : l) {
			stations.add(s);
			if (stations.size() >= K) {
				break;
			}
		}

		Collections.shuffle(stations);
		l.remove(stations.get(0));
		return stations.get(0);

	}

	private List<Station> buildRestrictCanditatesList(StationList solution) {

		Collections.sort(stationList, new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				return o1.getPonderation().compareTo(o2.getPonderation());
			}
		});

		List<Station> stations = new ArrayList<Station>();

		stations.addAll(stationList);

		return stations;
	}

	private boolean isViable(StationList solution) {
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

	public void setPointsNumber(Integer pointsNumber) {
		this.pointsNumber = pointsNumber;
	}

	public Integer getPointsNumber() {
		return pointsNumber;
	}

	public void setStationsNumber(Integer stationsNumber) {
		this.stationsNumber = stationsNumber;
	}

	public Integer getStationsNumber() {
		return stationsNumber;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}

	public List<Station> getStationList() {
		return stationList;
	}

}
