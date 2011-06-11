import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Station.
 */
public class Station {
	
	/** Numero da torre */
	private int number;
	
	/** Custo da torre */
	private BigDecimal cost;
	
	/** Lista de pontos */
	private List<Integer> points;
	
	/** O Valor da ponderacao = custo / (num pontos). */
	private BigDecimal ponderation;

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	public List<Integer> getPoints() {
		return points;
	}

	/**
	 * Sets the points.
	 *
	 * @param points the new points
	 */
	public void setPoints(List<Integer> points) {
		this.points = points;
	}

	/**
	 * Read station.
	 *
	 * @param str the str
	 * @param number the number
	 * @return the station
	 */
	public static Station readStation(String str, int number) {
		// Le uma torre da entrada
		Station station = new Station();
		
		// Pega o numero da torre
		station.setNumber(number);
		String[] strList = str.split(" ");

		// Pega o custo
		station.setCost(new BigDecimal(strList[1]));

		List<Integer> points = new ArrayList<Integer>();
		
		// Le a lista de pontos
		for (int i = 2; i < strList.length; i++) {
			points.add(Integer.valueOf(strList[i]));
		}
		station.setPoints(points);

		return station;

	}

	/**
	 * Sets the ponderation.
	 *
	 * @param ponderation the new ponderation
	 */
	public void setPonderation(BigDecimal ponderation) {
		this.ponderation = ponderation;
	}

	/**
	 * Gets the ponderation.
	 *
	 * @return the ponderation
	 */
	public BigDecimal getPonderation() {
		if (ponderation == null) {
			if (points.size() == 0) {
				ponderation = BigDecimal.valueOf(Integer.MAX_VALUE);
			} else {
				ponderation = cost.divide(BigDecimal.valueOf(points.size()), 10, RoundingMode.DOWN);
			}
		}
		return ponderation;
	}

}
