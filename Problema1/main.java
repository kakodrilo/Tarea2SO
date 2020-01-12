import java.io.*;
import java.lang.reflect.*;
import javax.tools.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.net.*;
import java.util.Scanner;
import java.lang.*;


public class main {

    /*
    CrearClase: crea un string que contiene a la clase Funciones, todos los métodos de esta son
    las funciones entregadas por un archivo.
    ----
    Recibe un String con el nombre del archivo donde se encuentran las funciones.
    ----
    Retorna el string con la clase.
    */
    public static String CrearClase(final String input) throws FileNotFoundException, IOException {
        String linea;
        String clase = "public class Funciones{ \n"; // nombre de la clase 

        /* se abre el archivo */
        final File archivo = new File(input);
        final FileReader fr = new FileReader(archivo);
        final BufferedReader br = new BufferedReader(fr);
        /* se lee la cantidad de funciones del archivo */
        final int n = Integer.parseInt(br.readLine());
        /* se extraen las funciones */
        for (int i = 0; i < n; i++) {
            linea = br.readLine();
            final int j = linea.indexOf("(");
            final String nombre = linea.substring(0, j); // nombre del método a crear
            final String expresion = linea.substring(j+4); // expresión matemática a crear
            /* 
            Por cada iteración se crea una clase que implementa Runnable, en su run() tiene
            la expresión extraida del archivo.
            Además se crea un método que genera una instancia de esta clase y ejecuta la hebra.
            */

            /* clase que implementa Runnable y define el run() */
            clase = clase + "public class "+nombre+" implements Runnable{\n"+
                            "Long x;\nLong r;\n"+
                            "public "+nombre+"(Long variable){\n"+
                            "this.x = variable;}\n"+
                            "public void run(){\n"+
                            "try{\n"+
                            "this.r = "+expresion+";}\n"+
                            "catch (Exception e){\n"+
                            "System.out.println(\"Problema en "+nombre+"\");}}\n"+
                            "public Long getr(){\n"+
                            "return this.r;}\n"+
                            "}\n";
            /* Método que ejecuta la hebra */
            clase = clase + "public Long " + nombre + "(Long variable) throws InterruptedException{\n"+
                             nombre+" clase = new "+nombre+"(variable);\n"+
                            "Thread hebra = new Thread(clase);\n"+
                            "hebra.start();\n"+
                            "hebra.join();\n"+
                            "return clase.getr();}\n";
        }
        clase = clase + "}";
        br.close(); // se cierrra el archivo
        return clase;
    }

    public static void main(final String[] args) throws FileNotFoundException, IOException {

        /* Escritura de archivo Funciones.java */
        final String source = CrearClase("funciones.txt"); // se obtiene el string de la clase a crear
        final File folder = new File("./");
        final File sourceFile = new File(folder, "Funciones.java"); // se abre el archivo en donde se escribirá la clase
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8)); // se escribe la clase

        /* Compilación de Funciones.java */
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final int compilationResult = compiler.run(null, null, null, sourceFile.getPath());
        if (compilationResult == 0) {
            System.out.println("Funciones Ingresadas!");
        } else {
            System.out.println("Fallo al ingresar funciones :c");
        }

        /* Utilización de la clase creada */
        try {
            final URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {folder.toURI().toURL() });
            final Class<?> cls = Class.forName("Funciones", true, classLoader); // se obtiene la clase
            
            /* Interacción con terminal */
            final Scanner Input = new Scanner (System.in);
            String linea;
            String fun;
            String var;
            int j;
            System.out.println("Ingrese operacion:");
            linea = Input.nextLine();
            while (!linea.equals("salir")) {
                j = linea.indexOf("(");
                fun = linea.substring(0, j); // se extrae el nombre del metodo a utiizar
                var = linea.substring(j+1, linea.length()-1); // se extrae el valor de la variable

                /* Ejecución del método solicitado */
                final Method method = cls.getMethod(fun, Long.class); // se obtiene al método
                final Object respuesta = method.invoke(cls.newInstance(), Long.parseLong(var)); // se ejecuta el método
                System.out.println("El resultado es: "+respuesta); // se muestra la respuesta
                System.out.println("Ingrese operacion:");
                linea = Input.nextLine();
            }
            Input.close();
            
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Error de programa");
        }

    }
}