package lekan.cruelty;

import java.io.PrintStream;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import lekan.cruelty.Area.Cell;
import lekan.cruelty.Behavior.Cruelty;
import static lekan.cruelty.Random.randomPerson;
import static lekan.cruelty.Random.cell;
import static lekan.cruelty.Random.step;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
public class Life {
	List<Person> people;
	Area area;
	long stepStart = 0;
	long stepComplete = 0;

	public enum OutputType {
		SHOW_ALL_LIST, SHOW_ALL_CRUELTY, SHOW_ALL_HEALTH,
		SHOW_ALIVE_LIST, SHOW_ALIVE_CRUELTY, SHOW_ALIVE_HEALTH,
		SHOW_COMPLETED_STEPS
	}

	public Life(List<Person> people, Area area) {
		this.people = people;
		this.area = area;

		for (Person person : people) {
			cell(area).addPerson(person);
		}
	}

	public void doIt() {
	// move
		Stack<Person> stack = new Stack<>();
		for (Cell cell : area) {
			for (Person person : cell.getAlivePeople()) {
				if (!person.isAlive()) continue;

			// find new cell
				stepStart++;
				Cell newCell = area.getCell(cell.getX() + step(), cell.getY() + step());
				if (newCell == cell) continue;

			// refresh cell for person
				stepComplete++;
				newCell.addPerson(person);
				stack.push(person);
			}

			while (!stack.isEmpty()) {
				cell.removePerson(stack.pop());
			}
		}

	// check field, if in one field 2 or mor people, they can start to kil each other
		for (Cell cell : area.findDenselyCell()) {
			for (Person person : cell.getPeople()) {
				if (!person.isAlive()) continue;

				if (!wantKill(person)) continue;

				Person randomPerson = randomPerson(cell.getAlivePeople());
				if (randomPerson == null) continue;

				randomPerson.setHealth(randomPerson.getHealth() - person.getStrong());
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

				case SHOW_COMPLETED_STEPS:
					showCompleteSteps(stream);
					break;
			}
		}
	}

	private static void showHealth(PrintStream stream, List<Person> people) {
		long healthyPeople = people.stream().filter(person -> person.getMaxHealth() > person.getHealth()).count();
		stream.printf("Healthy people count: %6.3f%%", healthyPeople * 100.0 / people.size());
		stream.println();
	}

	private static void showCruelty(PrintStream stream, List<Person> people) {
		for (Cruelty cruelty : Cruelty.values()) {
			stream.printf(cruelty.name() + ": %6.3f%%",
					people.stream().filter(person -> person.getBehavior().getCruelty() == cruelty).count() * 100.0 / people.size());
			stream.println();
		}
	}

	private static void showList(PrintStream stream, List<Person> people) {
		stream.println("People count: " + people.size());
		people.forEach(stream::println);
	}

	private void showCompleteSteps(PrintStream stream) {
		stream.printf("Completed steps: %6.3f", stepComplete * 100.0 / stepStart);
		stream.println();
	}
}
