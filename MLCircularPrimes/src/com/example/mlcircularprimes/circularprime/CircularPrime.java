package com.example.mlcircularprimes.circularprime;


/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * CircularPrime.java
 * Calculo de primos circulares 0 <= N , usando X threads (X = cantidad de procesadores).
 * Basicamente se particiona la operacion en X subprocesos y cada uno es ejecutado en un thread. La idea es aprovechar al maximo cada procesador
 * sin sobrecargarlo (aunque el uso de los mismos dependerá de su implementacion y el consumo de recursos por parte de otras
 * aplicaciones), tambien se evita el uso de estructuras/bloques sincronized.
 *  
 */

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.util.SparseArray;

public class CircularPrime {
	// Cantidad de procesadores/Proceso en N threads (N = PROCESSORS)
	private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
	private long processTime;
	private int totalProcessors = PROCESSORS;
	private SparseArray<List<Object>> circularPrimesResult = new SparseArray<List<Object>>();

	public void solveCircularPrimesUsingThreads(int limit) throws InterruptedException {
		ExecutorService execServ = Executors.newFixedThreadPool(PROCESSORS);
		// Creo las Tasks segun la cantidad de procesadores
		/*
		 * No me queda otra que separar el thread que chequea si es primo y el que 
		 * chequea si es primo circular, porque al separar el proceso en N subprocesos,
		 * puede pasar que el chequeo de un primo circular requiera que el proceso de chequeo
		 * de primos se haya realizado previamente.
		 */
		PrimeThread primeThreads[] = new PrimeThread[PROCESSORS];
		CircularPrimeThread circularPrimeThreads[] = new CircularPrimeThread[PROCESSORS];
		boolean circularPrimes[] = new boolean[limit];
		int range = limit / PROCESSORS;
		int from = 0;
		int to = 0;
		/*
		 * Creo X Threads (con X = PROCESSORS) Se crearan X Threads con grupos
		 * de LIMIT/PROCESSORS numeros. Por ejemplo, si LIMIT = 150 y PROCESSORS
		 * = 4 entonces habrá grupos con un minimo de 150/4 = 37 numeros (a
		 * excepcion del ultimo grupo que puede tener un poco mas) Thread1
		 * procesará de 0 a 37 Thread2 procesará de 37 a 74 Thread3 procesará de
		 * 74 a 111 Thread4 procesará de 111 a 150
		 */
		for (int i = 0; i < PROCESSORS; i++) {
			from = to;
			if (i == PROCESSORS - 1) {
				to = limit;
			} else {
				to += range;
			}
			primeThreads[i] = new PrimeThread(from, to, circularPrimes);
			circularPrimeThreads[i] = new CircularPrimeThread(from, to, circularPrimes);
		}
		// Ejecuto los threads para obtener los primos
		long tempProcessTime = new Date().getTime();
		for (int i = 0; i < PROCESSORS; i++) {
			execServ.execute(primeThreads[i]);
		}
		execServ.shutdown();
		execServ.awaitTermination(1, TimeUnit.MINUTES);

		// Ejecuto los threads para obtener los primos circulares
		execServ = Executors.newFixedThreadPool(PROCESSORS);
		for (int i = 0; i < PROCESSORS; i++) {
			execServ.execute(circularPrimeThreads[i]);
		}
		execServ.shutdown();
		execServ.awaitTermination(1, TimeUnit.MINUTES);
		
		// Genero los resultados
		this.processTime = (new Date().getTime() - tempProcessTime);
		for (int i = 0; i < circularPrimes.length; i++) {
			if (circularPrimes[i]) {
				this.circularPrimesResult.put(i, Arrays.asList(PrimeLib.getCircularNumbers(i).toArray()));
			}
		}
	}

	// Algoritmo: Criba de Eratóstenes
	public void solveCircularPrimesUsingSieveEratosthenes(int limit) {
		boolean[] circularPrimes = new boolean[limit];
		long tempProcessTime = new Date().getTime();
		PrimeLib.getPrimeList(limit, circularPrimes);
		for (int i = 0; i < circularPrimes.length; i++) {
			if (circularPrimes[i]) {
				circularPrimes[i] = PrimeLib.isCircularPrime(i, circularPrimes);
			}
		}
		// Genero los resultados
		this.processTime = (new Date().getTime() - tempProcessTime);
		for (int i = 0; i < circularPrimes.length; i++) {
			if (circularPrimes[i]) {
				this.circularPrimesResult.put(i, Arrays.asList(PrimeLib.getCircularNumbers(i).toArray()));
			}
		}
	}

	public long getProcessTime() {
		return processTime;
	}

	public int getTotalProcessors() {
		return totalProcessors;
	}

	public SparseArray<List<Object>> getCircularPrimesResult() {
		return circularPrimesResult;
	}
}

