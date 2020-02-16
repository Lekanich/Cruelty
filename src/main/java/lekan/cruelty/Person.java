package lekan.cruelty;

import java.util.Formatter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
@Getter
@Setter
public class Person {
	private final String name;
	private final Behavior behavior;
	private final Body body;

	public Person(String name, Behavior behavior, int health, int strong) {
		this.name = name;
		this.behavior = behavior;
		this.body = new Body(health, strong);
	}

	public boolean isAlive() {
		return body.getHealth() > 0;
	}

	@Override
	public String toString() {
		return new Formatter()
				.format("Alive: %5s Name: %5s %3s Cruelty: %10s",
						isAlive(), getName(), getBody().toString(), getBehavior().getCruelty().name())
				.toString();
	}
}
