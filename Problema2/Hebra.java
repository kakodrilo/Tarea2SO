
public class Hebra implements Runnable{

    int arr[];
    int l;
    int r;

    public Hebra(int arr[], int n, int m){
        this.arr = arr;
        this.l = n;
        this.r = m;
    }
    
    /*
    run: lo que contiene este método es lo ejecutado por la hebra.
    Implementa la parte recursiba del algoritmo mergesort, en la que se divide el problema en dos arreglos
    de mitad de tamaño a ordenar, y le asigna a dos hebras diferentes ordenar esos subarreglos. 
    Luego, llama al método merge para unir los dos subarreglos ahora ordenados. 
    */
    public void run(){
        if (l < r) 
        { 
            //Se encuentra el punto medio del arreglo para hacer la división:
            int m = (l+r)/2; 
            //Se crea una hebra para que se encargue de la primera mitad del arreglo:
            Hebra clase1 = new Hebra(arr,l,m);
            Thread hebra1 = new Thread(clase1);
            //Se crea otra hebra para que se encargue de la segunda mitad del arreglo:
            Hebra clase2 = new Hebra(arr , m+1, r);
            Thread hebra2 = new Thread(clase2);
            //Y se espera hasta que ambas hayan terminado para proceder al merge:
            try {
                hebra1.start();
                hebra2.start();
                hebra1.join();
                hebra2.join();

            } catch (Exception e) {
                System.out.println("Error en la hebra");
            }
            //Finalmente se hace el merge del resultado de las dos hebras:
            merge(arr, l, m, r); 
        } 
    }

    /*
    merge: junta dos arreglos ordenados, creando un solo arreglo ordenado. Para esto, compara elemento a elemento
    los dos arreglos ordenados, y va introduciento el valor menor. 
    ----
    Recibe un arreglo que contiene los dos arreglos ordenados (la mitad del arreglo corresponde al subarreglo 1 
    y la otra mitad al subarreglo 2), un entero l que indica la primera posición del subarreglo 1, un entero m que
    indica la última posición del subarreglo 1 (m+1 indica la primera posición del subarreglo 2) y un entero r
    que indica la útlima posición del subarreglo 2 dentro del arreglo. 
    ----
    No retorna nada, pues en el mismo espacio del arreglo recibido deja el arreglo ordenado. 
    */
    void merge(int arr[], int l, int m, int r) { 
        // Se calculan los dos tamaños de los arreglos:
        int t1 = m - l + 1; 
        int t2 = r - m;
        //Se generan los arreglos temporales en los que se copiarán ambos arreglos, para utilizar el espacio
        //para dejar el arreglo final:
        int L[] = new int [t1]; 
        int R[] = new int [t2]; 
        //Y se copia cada arreglo en los arreglos temporales:
        for (int i=0; i<t1; ++i){
            L[i] = arr[l + i];
        } 
        for (int j=0; j<t2; ++j){
            R[j] = arr[m + 1+ j]; 
        }
        
        //Se genera el arreglo final ordenado, tomando elemento a elemento del arreglo 1 y arreglo 2, e introduciendo
        //el menor de la comparación, para luego avanzar al siguiente:
        int i = 0, j = 0, k = l; //i y j son contadores para cada arreglo, y k es el contador para el arreglo final
        while (i < t1 && j < t2) 
        { 
            if (L[i] <= R[j]) { 
                arr[k] = L[i]; 
                i++; 
            } 
            else{ 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
        //En caso de que alguno de los arreglos se haya quedado sin elementos, se agregan todos los del otro arreglo 
        //que quedan:
        while (i < t1) { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
        while (j < t2) { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 

    /*
    imprimir: imprime un arreglo en la terminal.
    ----
    Recibe el arreglo a imprimir arr[]
    ----
    No retorna nada. 
    */
    static void imprimir(int arr[]) 
    { 
        int n = arr.length; 
        for (int i=0; i<n; ++i) 
            System.out.print(arr[i] + " "); 
        System.out.println(); 
    } 

    /*
    main: tiene un arreglo desordenado y genera una hebra para ordenando utilizando el algoritmo
    mergesort y más hebras. 
    */
    public static void main(String[] args) {
        int arreglo[] = {98,13,8,1,2,5,57,32,4,15,33,37,12,91,5,5,77,8,25,22,-8,56,-26,-200,156,277,11}; 
        //Se imprime el arreglo desordenado:
        System.out.println("Arreglo desordenado:"); 
        imprimir(arreglo); 
        
        //Se genera la hebra con el algortimo mergesort que ordenará el arreglo:
        Hebra hebra = new Hebra(arreglo,0,arreglo.length-1);
        Thread hbr = new Thread(hebra);
        try {
            hbr.start();
            hbr.join();
        } catch (Exception e) {
            System.out.println("Error en la hebra");
        }
        //Finalmente, se imprime el arreglo ordenado:
        System.out.println("\nArreglo ordenado"); 
        imprimir(arreglo); 
    }


}