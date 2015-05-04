# Circular Primes

Este es un proyecto Android que resuelve la busqueda de numeros primeros circulares que existen por debajo del millon. La app esta compuesta por 3 pantallas:

**Pantalla 1**

[![P1](http://s22.postimg.org/etf9x3erl/Screenshot_2015_05_04_02_10_05.png)](http://s22.postimg.org/etf9x3erl/Screenshot_2015_05_04_02_10_05.png)

Esta pantalla nos permite verificar si el numero ingresado es un Primo Circular, por default dejo como ejemplo el primo circular 197.

**Pantalla 2**

[![P1](http://s9.postimg.org/9ccv3bowv/Screenshot_2015_05_04_02_16_47.png)](http://s9.postimg.org/9ccv3bowv/Screenshot_2015_05_04_02_16_47.png)

Esta pantalla es un poco mas completa, propongo 2 mecanismos para la busqueda de TODOS los Primos Circulares usando 2 algoritmos, el primero corresponde a un algoritmo "mejorado" de busqueda por fuerza bruta, mientras que el segundo es el ya conocido de la Criba de Eratosthenes ("Sieve of Eratosthenes"), es interesante analizar ambos ya que, si bien el primero utiliza threas, durante el test verficaremos que en la mayoria de los casos (por no decir todos) el segundo algortimo (Eratosthenes) es mucho mas rapido, le insume la casi la mitad del tiempo respecto al primero.
El spinner nos dejara seleccionar los valores 10, 100, 1000, 10000, 100000, 1000000 y 10000000.
Para el calculo con Threads, basicamente el proceso es separado en N subprocesos, N será igual a la cantidad de procesadores **N = Runtime.getRuntime().availableProcessors()**, con esto intento aprovechar de alguna forma todos los recursos disponibles. Sin embargo, al ejecutar ambos, concluiremos que el uso de Threads no tiene sentido para la resolucion de este problema ya que evidentemente el costo de los Threads es elevado y termina insumiendo mas tiempo que el algoritmo de Eratosthenes. Otro punto fundamental quizas, es que el problema corresponde a una complejidad espacial de O(n) y una complejidad temporal de O(n2), entonces el uso de Threads no nos mostraria una mejora significativa en el calculo.

**Pantall 3**

[![P1](http://s22.postimg.org/v9wx8b2pt/Screenshot_2015_05_04_02_15_34.png)](http://s22.postimg.org/v9wx8b2pt/Screenshot_2015_05_04_02_15_34.png)

Como extra les dejo un grafico para mostrar el tiempo que insumen ambos algoritmos para el calculo de los Primos Circulares hasta el millon.

** APK **
Les dejo aqui el archivo APK para que puedan testearlo en el dispositivo


** DEMO (Video) **
Aqui les dejo tambien un video explicando un poco el proyecto, proceso y ejecucion del mismo.





