package lekan.cruelty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


/**
 * @author Aleksander Zhelezniak
 * @version 0.1.0
 * @since 0.1.0
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class Behavior {
	private Cruelty cruelty;

	public enum Cruelty {
		LOW, NORMAL, HARD, MANIAC
	}
}
