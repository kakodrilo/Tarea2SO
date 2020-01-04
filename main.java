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
    public static String CrearClase(String input) throws FileNotFoundException, IOException {
        String linea;
        String clase = "public class Funciones{ "; // nombre de la clase 

        /* se abre el archivo */
        File archivo = new File(input);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        /* se lee la cantidad de funciones del archivo */
        int n = Integer.parseInt(br.readLine());
        /* se extraen las funciones */
        for (int i = 0; i < n; i++) {
            linea = br.readLine();
            int j = linea.indexOf("(");
            String nombre = linea.substring(0, j); // nomre del método a crear
            String expresion = linea.substring(j+4); // expresión matemática a crear
            clase = clase + "public Long " + nombre + "(Long x){ return "+expresion +"; } ";
        }
        clase = clase + "}";
        br.close(); // se cierrra el archivo
        return clase;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        /* Escritura de archivo Funciones.java */
        String source = CrearClase("funciones.txt"); // see obtiene el string de la clase a crear
        File folder = new File("./");
        File sourceFile = new File(folder, "Funciones.java"); // se abre el archivo en donde se escribirá la clase
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8)); // se escribe la clase

        /* Compilación de Funciones.java */
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compilationResult = compiler.run(null, null, null, sourceFile.getPath());
        if (compilationResult == 0) {
            System.out.println("Funciones Ingresadas!");
        } else {
            System.out.println("Fallo al ingresar funciones :c");
        }

        /* Utilización de la clase creada */
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {folder.toURI().toURL() });
            Class<?> cls = Class.forName("Funciones", true, classLoader); // se obtiene la clase
            
            /* Interacción con terminal */
            Scanner Input = new Scanner (System.in);
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
                Method method = cls.getMethod(fun, Long.class); // se obtiene al método
                Object respuesta = method.invoke(cls.newInstance(), Long.parseLong(var)); // se ejecuta el método
                System.out.println("El resultado es: "+respuesta); // se muestra la respuesta
                System.out.println("Ingrese operacion:");
                linea = Input.nextLine();
            }
            Input.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error de programa");
        }

    }
}