package edu.asu.cse512;

import java.io.Serializable;

public class PointPair implements Serializable {

	private static final long serialVersionUID = 1L;
	public Point point1;
	public Point point2;

	public PointPair(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	public static double pointPairDistance(Point point1, Point point2) {
		double pointXDiffrence = Math.abs(point1.getxCoordinate() - point2.getxCoordinate());
		double pointYDiffrence = Math.abs(point1.getyCoordinate() - point2.getyCoordinate());
		return Math.hypot(pointXDiffrence, pointYDiffrence);

	}

	public String toString() {
		return point1.getxCoordinate() + "," + point1.getyCoordinate() + "\n" + point2.getxCoordinate() + ","
				+ point2.getyCoordinate();
	}

}