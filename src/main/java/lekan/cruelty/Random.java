package lekan.cruelty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import lekan.cruelty.Area.Cell;
import lekan.cruelty.Behavior.Cruelty;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
final public class Random {
	private static final java.util.Random RANDOM = new java.util.Random();

	public static String name() {
		return RandomStringUtils.randomAscii(5);
	}

	public static int step() {
		return RANDOM.nextInt(3) - 1;
	}

	public static Behavior behavior() {
		return new Behavior(cruelty());
	}

	public static Cruelty cruelty() {
		switch ((int) (Math.random() * 3)) {
			case 0:
				return Cruelty.LOW;
			case 1:
				return Cruelty.NORMAL;
			case 2:
				return Cruelty.HARD;
			default:
				return Cruelty.MANIAC;
		}
	}

	public static int health() {
		return RANDOM.nextInt(10) + 1;
	}

	public static int strong() {
		return RANDOM.nextInt(20) + 1;
	}

	public static Cell cell(Area area) {
		return area.field[RANDOM.nextInt(area.row())][RANDOM.nextInt(area.column())];
	}

	public static Person randomPerson(List<Person> persons, Person... except) {
		HashSet<Person> set = new HashSet<>(asList(except));
		List<Person> cleanPersons = persons.stream()
				.filter(person -> !set.contains(person))
				.collect(toList());

		if (cleanPersons.isEmpty()) return null;
		return randomPerson(cleanPersons);
	}

	public static Person randomPerson(List<Person> persons) {
		return persons.get(RANDOM.nextInt(persons.size()));
	}
}
