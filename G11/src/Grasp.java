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
	private final StationList worstSolution;

	private final static int K = 30;
	private final static int L = 5;

	public Grasp(Integer pointsNumber, Integer stationsNumber,
			List<Station> stationList, long msBegin, StationList worstSolution) {
		this.setPointsNumber(pointsNumber);
		this.setStationsNumber(stationsNumber);
		this.setStationList(stationList);
		this.msBegin = msBegin;
		this.worstSolution = worstSolution;
	}

	public StationList execute() {
		int i = 0, j = 0;
		StationList solution = new StationList();
		StationList starSolution = worstSolution;

		Collections.sort(stationList, new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				return o1.getPonderation().compareTo(o2.getPonderation());
			}
		});

		while (System.currentTimeMillis() - msBegin < 50000) {
			j++;
			solution = randomGreedySolution();
			solution = localSearch(solution);
			if (solution.getTotalCost().compareTo(starSolution.getTotalCost()) < 0) {
				i++;
				starSolution = solution;
			}
		}

		System.out.println("j: " + j);
		System.out.println("i: " + i);

		return starSolution;
	}

	private StationList localSearch(StationList solution) {
		isLocalMin = false;
		StationList starStationList = solution;
		long localBegin = System.currentTimeMillis();
		
		while (System.currentTimeMillis() - msBegin < 50000
				&& System.currentTimeMillis() - localBegin < 25000
				&& !isLocalMin) {
			//System.out.println(System.currentTimeMillis() - msBegin);
			StationList bestNeighbor = getBestNeighbor(starStationList);
			if (bestNeighbor.getTotalCost().compareTo(
					starStationList.getTotalCost()) < 0) {
				starStationList = bestNeighbor;
			//	System.out.println(bestNeighbor.getTotalCost());
			} else {
				isLocalMin = true;
			}

		}
		return starStationList;
	}

	private StationList getBestNeighbor(StationList solution) {
		StationList bestNeighbor = worstSolution;

		for (int i=stationList.size()-1, j=0; i>=0 && j<L ; i--) {
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
				for (Station t : stationList) {
					if (!solution.getStations().contains(t)) {
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

		return l.remove((int) (Math.random() * K % K));

	}

	private List<Station> buildRestrictCanditatesList(StationList solution) {

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
