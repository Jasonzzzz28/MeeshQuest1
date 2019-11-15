package cmsc420.meeshquest.part1;

public class city implements Comparable<city>{
		private String name;
		private int radius, x, y;
		private String color;

		public city(String name, int x, int y, int radius, String color) {
			this.name = name;
			this.radius = radius;
			this.color = color;
			this.x = x;
			this.y = y;
		}

		public String getName() {
			return name;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public String getColor() {
			return color;
		}

		public int getRadius() {
			return radius;
		}

		@Override
		public int compareTo(city arg0) {
			// TODO Auto-generated method stub
			return name.toLowerCase().compareTo(arg0.name.toLowerCase());
		}
}
