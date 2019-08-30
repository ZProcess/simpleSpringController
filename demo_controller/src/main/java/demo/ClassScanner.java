package demo;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName)
            throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(path);
        return getClasses(new File(url.getFile().replaceAll("%20", " ")), packageName);
    }
    private static List<Class<?>> getClasses(File dir, String pk)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!dir.exists()) {
            return classes;
        }
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                classes.addAll(getClasses(f, pk + "." + f.getName()));
            }
            String name = f.getName();
            if (name.endsWith(".class")) {
                classes.add(Class.forName(pk + "."
                        + name.substring(0, name.length() - 6)));
            }
        }
        return classes;
    }


    /**
     * 获取Jar包中的类
     * @param jarFilePath Jar包路径
     * @param path 类的相对路径
     * @return
     */
    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path)
            throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();
            if(entryName.startsWith(path) && entryName.endsWith(".class")){
                String classFullName = entryName.replace("/", ".")
                        .substring(0, entryName.length() - 6);
                classList.add(Class.forName(classFullName));
            }
        }
        return classList;
    }
}
