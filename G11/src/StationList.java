import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class StationList.
 */
public class StationList {
	
	/** The total cost. */
	private BigDecimal totalCost;
	
	/** The stations. */
	private List<Station> stations;
	
	public StationList() {
		super();
		this.totalCost = BigDecimal.valueOf(0);
		this.stations = new ArrayList<Station>();
	}
	
	/**
	 * Gets the total cost.
	 *
	 * @return the total cost
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	
	/**
	 * Sets the total cost.
	 *
	 * @param totalCost the new total cost
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	
	/**
	 * Gets the stations.
	 *
	 * @return the stations
	 */
	public List<Station> getStations() {
		return stations;
	}
	
	/**
	 * Sets the stations.
	 *
	 * @param stations the new stations
	 */
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	
	/**
	 * Adds the station.
	 *
	 * @param s the s
	 */
	public void addStation(Station s) {
		if(!stations.contains(s)) {
			stations.add(s);
			totalCost = totalCost.add(s.getCost());
		}
	}
	
	public void removeStation(Station s) {
		if(stations.remove(s)) {
			totalCost = totalCost.subtract(s.getCost());
		}
	}


}
