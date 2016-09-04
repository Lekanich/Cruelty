package lekan.cruelty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import lekan.cruelty.Area.Cell;
import lekan.cruelty.Behavior.Cruelty;


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
		return (int) (Math.round(Math.random() * 2) - 1);
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
		return (int) (Math.random() * 10) + 1;
	}

	public static int strong() {
		return (int) (Math.random() * 20) + 1;
	}

	public static Cell cell(Area area) {
		return area.field[(int) (area.row() * Math.random())][(int) (area.column() * Math.random())];
	}

	public static Person randomPerson(List<Person> persons, Person... except) {
		HashSet<Person> set = new HashSet<>(Arrays.asList(except));
		List<Person> cleanPersons = persons.stream()
				.filter(person -> !set.contains(except))
				.collect(Collectors.toList());
		return cleanPersons.get(RANDOM.nextInt(cleanPersons.size()));
	}
}
