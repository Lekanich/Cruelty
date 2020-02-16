package lekan.cruelty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import one.util.streamex.StreamEx;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
@RequiredArgsConstructor
final public class Area {
	private final Cell[][] field;

	@RequiredArgsConstructor
	@FieldDefaults(makeFinal = true)
	public static class Cell {
		@Getter
		int x;
		@Getter
		int y;
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

		public List<Person> getPeople() {
			return people;
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
		if (x == 0 || y == 0) {
			throw new IllegalArgumentException("empty field");
		}

		this.field = new Cell[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				field[i][j] = new Cell(i, j);
			}
		}
	}

	public int column() {
		return field[0].length;
	}

	public int row() {
		return field.length;
	}

	public Stream<Cell> cells() {
		return StreamEx.of(field)
				.flatArray(cells -> cells);
	}

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
}
