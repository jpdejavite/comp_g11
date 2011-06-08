import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Station {
	private int number;
	private BigDecimal cost;
	private List<Integer> points;
	private BigDecimal ponderation;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public void setPoints(List<Integer> points) {
		this.points = points;
	}

	public static Station readStation(String str, int number) {
		Station station = new Station();
		station.setNumber(number);
		String[] strList = str.split(" ");

		station.setCost(new BigDecimal(strList[1]));

		List<Integer> points = new ArrayList<Integer>();

		for (int i = 2; i < strList.length; i++) {
			points.add(Integer.valueOf(strList[i]));
		}
		station.setPoints(points);

		return station;

	}

	public void setPonderation(BigDecimal ponderation) {
		this.ponderation = ponderation;
	}

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
