package com.example.mlcircularprimes.circularprime;

/* 
 * 	2015-02-05 
 * 	FLORES GASTON - PRIMOS CIRCULARES
 * 
 * 	PrimeLib.java
 *  
 */

import java.util.HashSet;
import java.util.Set;

public final class PrimeLib {

	public static boolean isPrime(int number) {
		if (number == 0 || number == 1)
			return false;
		else if ((number == 2) || (number == 3) || (number == 5))
			return true;
		else {
			if ((number % 2 == 0))// || (number % 3 == 0) || (number % 5 == 0))
				return false;
			for (int i = 3; i * i <= number; i += 2) {
				if (number % i == 0)

					return false;
			}
			return true;
		}
	}

	public static boolean isCircularPrime(int prime, boolean primeList[]) {
		String primeString = Integer.toString(prime);

		for (int i = 0; i < primeString.length(); i++) {
			if (!primeList[Integer.parseInt(primeString.substring(i)
					+ primeString.substring(0, i))]) {
				return false;
			}
		}
		return true;
	}

	public static Set<Integer> getCircularNumbers(int number) {
		String numberString = Integer.toString(number);
		Set<Integer> rtn = new HashSet<Integer>();
		for (int i = 0; i < numberString.length(); i++) {
			rtn.add(Integer.parseInt(numberString.substring(i)
					+ numberString.substring(0, i)));
		}
		return rtn;
	}

	// Algoritmo: Criba de Eratóstenes
	public static void getPrimeList(int number, boolean[] primeList) {
		if (number >= 2)
			primeList[2] = true;
		for (int i = 3; i <= number; i += 2)
			primeList[i] = true;
		for (int i = 3; i * i <= number; i += 2) {
			if (primeList[i]) {
				for (int j = i * i; j <= number; j += i << 1)
					primeList[j] = false;
			}
		}
	}
}
