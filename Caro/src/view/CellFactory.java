package view;

public class CellFactory {

	public Cell createCell(String type, int y, int x) {

		if (type.equals("Normal")) {
			return new NormalCell(y, x);
		} else if (type.equals("Dark")) {
			return new DarkCell(y, x);
		} else if (type.equals("Gray")) {
			return new GrayCell(y, x);
		} else {
			System.out.println("da vao day");
			return null;
		}
	}
}
