package lekan.cruelty;

import lekan.cruelty.Area.Cell;
import lekan.cruelty.Behavior.Cruelty;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lekan.cruelty.Random.*;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
public class Life {
	List<Person> people = new ArrayList<>();
	Area area;

	public enum OutputType {
		SHOW_ALL_LIST, SHOW_ALL_CRUELTY, SHOW_ALL_HEALTH,
		SHOW_ALIVE_LIST, SHOW_ALIVE_CRUELTY, SHOW_ALIVE_HEALTH,
		SHOW_FIELD
	}

	public Life(Area area) {
		this.area = area;
	}

	public void bringIntoWorld(Person person) {
		this.people.add(person);
		cell(area).addPerson(person);
	}

	public void doIt() {
		// move
		for (int i = 0; i < area.row(); i++) {
			for (int j = 0; j < area.column(); j++) {
				Cell cell = area.getCell(i, j);
				for (Person person : cell.getAlivePeople()) {
					// find new cell
					int dX = step();
					int dY = step();
					if (dX == 0 && dY == 0) {
						continue;
					}

					// refresh cell for person
					area.getCell(cell.getX() + dX, cell.getY() + dY)
							.addPerson(person);
					cell.removePerson(person);
				}
			}
		}

		// check field, if in one field 2 or mor people, they can start to kil each other
		for (Cell cell : area.findDenselyCell()) {
			for (Person person : cell.getAlivePeople()) {
				if (!wantKill(person)) continue;

				Person randomPerson = randomPerson(cell.getAlivePeople());
				if (randomPerson == null) continue;

				randomPerson.getBody()
						.setHealth(randomPerson.getBody().getHealth() - person.getBody().getStrong());
			}
		}
	}

	public boolean onlyOne() {
		return people.stream().filter(Person::isAlive).count() <= 1;
	}

	private boolean wantKill(Person person) {
		return person.getBehavior().getCruelty().ordinal() * Math.random() > 1;
	}

	public void dump(OutputType... outputType) {
		dump(System.out, outputType);
	}

	public void dump(PrintStream stream, OutputType... outputType) {
		List<Person> alives = people.stream().filter(Person::isAlive).collect(Collectors.toList());

		for (OutputType type : outputType) {
			switch (type) {
				case SHOW_ALL_CRUELTY:
					showCruelty(stream, people);
					break;
				case SHOW_ALL_LIST:
					showList(stream, people);
					break;
				case SHOW_ALL_HEALTH:
					showHealth(stream, people);
					break;

				case SHOW_ALIVE_CRUELTY:
					showCruelty(stream, alives);
					break;
				case SHOW_ALIVE_LIST:
					showList(stream, alives);
					break;
				case SHOW_ALIVE_HEALTH:
					showHealth(stream, alives);
					break;

				case SHOW_FIELD:
					showField(stream, area);
					break;
			}
		}
	}

	private static void showField(PrintStream stream, Area area) {
		for (int i = 0; i < area.row(); i++) {
			for (int j = 0; j < area.column(); j++) {
				stream.printf("|%4s|", area.getCell(i, j).toString());
			}
			stream.println();
		}
	}

	private static void showHealth(PrintStream stream, List<Person> people) {
		long healthyPeople = people.stream()
				.filter(person -> person.getBody().getMaxHealth() > person.getBody().getHealth())
				.count();
		stream.printf("Healthy people count: %5.2f%%", healthyPeople * 100.0 / people.size());
		stream.println();
	}

	private static void showCruelty(PrintStream stream, List<Person> people) {
		for (Cruelty cruelty : Cruelty.values()) {
			stream.printf(cruelty.name() + ": %5.2f%%",
					people.stream().filter(person -> person.getBehavior().getCruelty() == cruelty).count() * 100.0 / people.size());
			stream.println();
		}
	}

	private static void showList(PrintStream stream, List<Person> people) {
		stream.println("People count: " + people.size());
		people.forEach(stream::println);
	}
}
