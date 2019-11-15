package cmsc420.meeshquest.part1;

public class Coord implements Comparable<Coord> {
	int x;
	int y;
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public int compareTo(Coord arg0) {
		// TODO Auto-generated method stub
		if(x == arg0.x) {
			return y - arg0.y;
		}
		else{
			return x - arg0.x;
		}
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coord)) return false;
        Coord key = (Coord) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
