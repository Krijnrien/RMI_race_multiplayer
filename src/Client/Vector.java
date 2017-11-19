package Client;

public class Vector {

	private double x, y, angle, radius;
	private static byte Polar = 1;

	Vector(double scalar1, double scalar2, byte coordianteSystem) {
		this.angle = 0;
		this.y = 0;
		this.x = 0;
		this.radius = 0;
		byte cartesian = 0;
		if(coordianteSystem == cartesian) {
			this.x = scalar1;
			this.y = scalar2;
			refreshPolar();
		} else {
			this.radius = scalar1;
			this.angle = scalar2;
			refreshCartesian();
		}
	}

	public static byte getPolar() {
		return Polar;
	}

	public static void setPolar(byte polar) {
		Polar = polar;
	}

	double getX() {
		return x;
	}


	double getY() {
		return y;
	}


	private void refreshPolar() {
		radius = Math.sqrt(x * x + y * y);
		angle = Math.atan2(y, x);
	}

	private void refreshCartesian() {
		x = radius * Math.cos(angle);
		y = radius * Math.sin(angle);
	}

}
