package lekan.cruelty;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import static lekan.cruelty.Life.OutputType.SHOW_ALIVE_CRUELTY;
import static lekan.cruelty.Life.OutputType.SHOW_ALIVE_HEALTH;
import static lekan.cruelty.Life.OutputType.SHOW_ALIVE_LIST;
import static lekan.cruelty.Life.OutputType.SHOW_ALL_CRUELTY;
import static lekan.cruelty.Random.behavior;
import static lekan.cruelty.Random.health;
import static lekan.cruelty.Random.name;
import static lekan.cruelty.Random.strong;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
public class Main {

	public static void main(String[] args) {
		List<Person> persons = new LinkedList<>();
		for (int i = 0; i < 10000; i++) {
			persons.add(new Person(name(), behavior(), health(), strong()));
		}

		Life life = new Life(persons, new Area(3, 3));
		life.dump(SHOW_ALL_CRUELTY);
		System.out.println("=====================================================================");

		for (int i = 0; i < 1_000_000; i++) {
			if (life.onlyOne()) break;

			life.doIt();
		}

		life.dump(SHOW_ALIVE_CRUELTY, SHOW_ALIVE_LIST, SHOW_ALIVE_HEALTH);
	}
}
