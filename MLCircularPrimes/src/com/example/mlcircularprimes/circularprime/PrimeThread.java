package com.example.mlcircularprimes.circularprime;

/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * PrimeThread.java
 *  
 */

public class PrimeThread implements Runnable {
	public int from;
	public int to;
	public boolean[] primeList;

	public PrimeThread(int from, int to, boolean[] primeList) {
		super();
		this.from = from;
		this.to = to;
		this.primeList = primeList;
	}

	@Override
	public void run() {
		for (int i = from + 1; i < to; i++) {
			primeList[i] = PrimeLib.isPrime(i);
		}
	}

}

