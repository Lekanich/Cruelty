package lekan.cruelty;

import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.List;

import static lekan.cruelty.Life.OutputType.*;
import static lekan.cruelty.Random.behavior;
import static lekan.cruelty.Random.health;
import static lekan.cruelty.Random.name;
import static lekan.cruelty.Random.strong;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
@Log4j2
public class Main {

	public static void main(String[] args) {
		start(10000, 1_000_000, 25, 25);
	}

	private static void start(int humanNumber, int iterationNumber, int fieldWidth, int fieldHeight) {
		List<Person> persons = new LinkedList<>();
		for (int i = 0; i < humanNumber; i++) {
			persons.add(new Person(name(), behavior(), health(), strong()));
		}

		Life life = new Life(persons, new Area(fieldWidth, fieldHeight));
		life.dump(SHOW_ALL_CRUELTY);
		log.info("=====================================================================");
		life.dump(SHOW_FIELD);

		int i = 0;
		for (; i < iterationNumber; i++) {
			if (life.onlyOne()) {
				break;
			}

			life.doIt();
		}

		log.info("Completed {} iterations.", i);

//		life.dump(SHOW_COMPLETED_STEPS, SHOW_ALIVE_CRUELTY, SHOW_ALIVE_LIST, SHOW_ALIVE_HEALTH);
		life.dump(SHOW_COMPLETED_STEPS, SHOW_ALIVE_CRUELTY, SHOW_FIELD);
	}
}
