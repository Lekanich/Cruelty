package lekan.cruelty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Body {
	private int health;
	private final int maxHealth;
	private final int strong;

	public Body(int health, int strong) {
		this.health = health;
		this.maxHealth = health;
		this.strong = strong;
	}

	@Override
	public String toString() {
		return "Body " +
				"health=" + health +
				", maxHealth=" + maxHealth +
				", strong=" + strong;
	}
}