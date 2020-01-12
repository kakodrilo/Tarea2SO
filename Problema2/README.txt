Tarea2SO

Tarea 2 de Sistemas Operativos 2019-2

> Joaquín Castillo Tapia - 201773520-1
> María Paz Morales LLopis - 201773505-8

** Pregunta 2

*** Ejecución:
Abrir la terminal en donde están el archivo Hebra.java y makefile.
Para compilar y ejecutar el programa ejecutar en la terminal el siguiente comando:

                                $ make

> Esto generará el siguiente archivo: Hebra.class 

Para borrar los archivos creados se debe ejecutar en la terminal:

                                $ make clear

**Beneficio de Threads:
- En este problema es muy beneficioso usar threads ya que se está utilizando el algortimo de ordenamiento mergesort;
al ser recursivo y trabajar con dividir y conquistar, permite entregar a cada hebra un subproblema a resolver. Y estas
hebras harán lo mismo recursivamente. Por lo que se ahorra tiempo, ya que la ejecución de una hebra no depende de la otra,
y una hebra sólo debe esperar a las que creó para poder hacer le merge de las soluciones. 

** Consideraciones:
- El arreglo está escrito directamente en el main del archivo Hebras.java. Si se quiere probar con otro arreglo, 
se debe cambiar desde ahí.