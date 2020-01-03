import java.io.*;
import java.lang.reflect.*;
import javax.tools.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.net.*;
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
            clase = clase + "public int " + nombre + "(int x){ return "+expresion +"; } ";
        }
        clase = clase + "}";
        br.close();
        return clase;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String c = CrearClase("funciones.txt");
        System.out.println(c);

        /*
        String source = "public class Solution{" + "public int add(){" + "return 1+1;" + "}" + "}";

        File folder = new File("./");
        File sourceFile = new File(folder, "Solution.java");

        try {
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sourceFile.getPath());
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.out.println("JDK required (running inside of JRE)");
        } else {
            System.out.println("you got it!");
        }

        int compilationResult = compiler.run(null, null, null, sourceFile.getPath());
        if (compilationResult == 0) {
            System.out.println("Compilation is successful");
        } else {
            System.out.println("Compilation Failed");
        }

        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {folder.toURI().toURL() });
            Class<?> cls = Class.forName("Solution", true, classLoader);
            Object instance = cls.newInstance();
            Method method = cls.getDeclaredMethod("add", null);
            System.out.println(method.invoke(instance, null));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }*/

    }
}