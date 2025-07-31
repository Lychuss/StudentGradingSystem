package model;

import java.util.List;

public class Transmutation {
	
	private double max, min, grades;
	public static List<Transmutation> transmutations = List.of(
		    new Transmutation(100.0, 100.0, 100),
		    new Transmutation(98.40, 99.99, 99),
		    new Transmutation(96.80, 98.39, 98),
		    new Transmutation(95.20, 96.79, 97),
		    new Transmutation(93.60, 95.19, 96),
		    new Transmutation(92.00, 93.59, 95),
		    new Transmutation(90.40, 91.99, 94),
		    new Transmutation(88.80, 90.39, 93),
		    new Transmutation(87.20, 88.79, 92),
		    new Transmutation(85.60, 87.19, 91),
		    new Transmutation(84.00, 85.59, 90),
		    new Transmutation(82.40, 83.99, 89),
		    new Transmutation(80.80, 82.39, 88),
		    new Transmutation(79.20, 80.79, 87),
		    new Transmutation(77.60, 79.19, 86),
		    new Transmutation(76.00, 77.59, 85),
		    new Transmutation(74.40, 75.99, 84),
		    new Transmutation(72.80, 74.39, 83),
		    new Transmutation(71.20, 72.79, 82),
		    new Transmutation(69.60, 71.19, 81),
		    new Transmutation(68.00, 69.59, 80),
		    new Transmutation(66.40, 67.99, 79),
		    new Transmutation(64.80, 66.39, 78),
		    new Transmutation(63.20, 64.79, 77),
		    new Transmutation(61.60, 63.19, 76),
		    new Transmutation(60.00, 61.59, 75),
		    new Transmutation(56.00, 59.99, 74),
		    new Transmutation(52.00, 55.99, 73),
		    new Transmutation(48.00, 51.99, 72),
		    new Transmutation(44.00, 47.99, 71),
		    new Transmutation(40.00, 43.99, 70),
		    new Transmutation(36.00, 39.99, 69),
		    new Transmutation(32.00, 35.99, 68),
		    new Transmutation(28.00, 31.99, 67),
		    new Transmutation(24.00, 27.99, 66),
		    new Transmutation(20.00, 23.99, 65),
		    new Transmutation(16.00, 19.99, 64),
		    new Transmutation(12.00, 15.99, 63),
		    new Transmutation(8.00, 11.99, 62),
		    new Transmutation(4.00, 7.99, 61),
		    new Transmutation(0.00, 3.99, 60)
		);
	
	public Transmutation(double min, double max, double grades) {
		this.max = max;
		this.min = min;
		this.grades = grades;
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getGrades() {
		return grades;
	}
}
