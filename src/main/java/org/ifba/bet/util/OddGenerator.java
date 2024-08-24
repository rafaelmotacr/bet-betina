package org.ifba.bet.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class OddGenerator {

	private static double generateProbability() {
		// Média de 0.3 a 0.5 com uma pequena variação
		Random rand = new Random();
		return 0.3 + (0.2 * rand.nextDouble());
	}

	// Calcula as odds a partir da probabilidade
	@SuppressWarnings("unused")
	public static double calculateOdds() {
		double probability = generateProbability();
		double odds = 1 / probability;

		// Arredondar o valor para duas casas decimais
		BigDecimal bigDecimal = new BigDecimal(odds);
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

		return bigDecimal.doubleValue();
	}
}
