package lekan.cruelty;

import java.util.Formatter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
@Getter
@Setter
@FieldDefaults(makeFinal = true)
public class Person {
	private String name;
	private Behavior behavior;
	@NonFinal private int health;
	private int maxHealth;
	private int strong;

	public Person(String name, Behavior behavior, int health, int strong) {
		this.name = name;
		this.behavior = behavior;
		this.health = health;
		this.maxHealth = health;
		this.strong = strong;
	}

	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public String toString() {
		return new Formatter()
				.format("Alive: %5s Name: %5s Max health: %3s Health: %3s Cruelty: %10s",
						isAlive(), getName(), getMaxHealth(), getHealth(), getBehavior().getCruelty().name())
				.toString();
	}
}
