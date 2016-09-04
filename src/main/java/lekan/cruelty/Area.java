package lekan.cruelty;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
@RequiredArgsConstructor
final public class Area implements Iterable<Area.Cell> {
	Cell[][] field;

	final public class CellIterator implements Iterator<Cell> {
		int currentX = 0;
		int currentY = 0;

		@Override
		public boolean hasNext() {
			return currentX < field.length && currentY + 1 < field[currentY].length;
		}

		@Override
		public Cell next() {
			currentY++;
			if (currentY == field[currentX].length) {
				currentY = 0;
				currentX++;
			}
			if (currentX == field.length) {
				throw new IllegalAccessError("Next element not found");
			}

			return field[currentX][currentY];
		}

		@Override
		public void forEachRemaining(Consumer<? super Cell> action) {
			for (Cell[] aField : field) {
				for (Cell anAField : aField) {
					action.accept(anAField);
				}
			}
		}
	}

	@RequiredArgsConstructor
	@FieldDefaults(makeFinal = true)
	public static class Cell {
		@Getter int x;
		@Getter int y;
		List<Person> people = new LinkedList<>();

		public boolean isMoreThenOne() {
			return people.size() > 1;
		}

		public void addPerson(Person person) {
			people.add(person);
		}

		public void removePerson(Person person) {
			people.remove(person);
		}

		public List<Person> getAlivePeople() {
			return people.stream().filter(Person::isAlive).collect(Collectors.toList());
		}

		@Override
		public String toString() {
			return Integer.toString(people.size());
		}
	}

	public Area(int x, int y) {
		if(x == 0 || y == 0) {
			throw new IllegalArgumentException("empty field");
		}

		this.field = new Cell[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				field[i][j] = new Cell(i, j);
			}
		}
	}

	public int column() { return field[0].length; }

	public int row() { return field.length; }

	public List<Cell> findDenselyCell() {
		List<Cell> cells = new LinkedList<>();
		for (int i = 0; i < row(); i++) {
			for (int j = 0; j < column(); j++) {
				if (field[i][j].isMoreThenOne()) {
					cells.add(field[i][j]);
				}
			}
		}
		return cells;
	}

	public Cell getCell(int row, int column) {
		if (row >= row()) {
			row = row() - 1;
		}
		if (column >= column()) {
			column = column() - 1;
		}
		if (row < 0) {
			row = 0;
		}
		if (column < 0) {
			column = 0;
		}
		return field[row][column];
	}

	@Override
	public Iterator<Cell> iterator() {
		return new CellIterator();
	}
}
