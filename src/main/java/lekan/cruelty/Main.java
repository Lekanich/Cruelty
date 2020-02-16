package lekan.cruelty;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
		long start = System.currentTimeMillis();
		start(1_000, 1_000, 25, 25);
		long finish = System.currentTimeMillis();

		log.info("Spent: {} s", TimeUnit.MILLISECONDS.toSeconds(finish - start));
	}

	private static void start(int humanNumber, int iterationNumber, int fieldWidth, int fieldHeight) {
		Life life = new Life(new Area(fieldWidth, fieldHeight));
		IntStream.range(0, humanNumber)
				.forEach(i -> life.bringIntoWorld(new Person(name(), behavior(), health(), strong())));

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
