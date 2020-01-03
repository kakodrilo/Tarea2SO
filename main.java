import java.io.*;
import java.lang.reflect.*;
import javax.tools.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.net.*;
import java.util.Scanner;
import java.lang.*;


public class main {

    public static String CrearClase(String input) throws FileNotFoundException, IOException {
        String linea = "";
        
        String clase = "public class Funciones{ ";
        File archivo = new File(input);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            linea = br.readLine();
            int j = linea.indexOf("(");
            String nombre = linea.substring(0, j);
            String expresion = linea.substring(j+4);
            clase = clase + "public Long " + nombre + "(Long x){ return "+expresion +"; } ";
        }
        clase = clase + "}";
        br.close();
        return clase;
    }

    public static void IngresarFunciones(){
        
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {

        String source = CrearClase("funciones.txt");

        File folder = new File("./");
        File sourceFile = new File(folder, "Funciones.java");

        
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        int compilationResult = compiler.run(null, null, null, sourceFile.getPath());
        if (compilationResult == 0) {
            System.out.println("Funciones Ingresadas!");
        } else {
            System.out.println("Fallo al ingresar funciones :c");
        }
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {folder.toURI().toURL() });
            Class<?> cls = Class.forName("Funciones", true, classLoader);
            
            Scanner Input = new Scanner (System.in);
            String linea;
            String fun;
            String var;
            int j;
            System.out.println("Ingrese operacion:");
            linea = Input.nextLine();
            while (!linea.equals("salir")) {
                j = linea.indexOf("(");
                fun = linea.substring(0, j);
                var = linea.substring(j+1, linea.length()-1);
                Method method = cls.getMethod(fun, Long.class);
                Object respuesta = method.invoke(cls.newInstance(), Long.parseLong(var) );
                System.out.println("El resultado es: "+respuesta);
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