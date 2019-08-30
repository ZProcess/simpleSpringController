package demo;

import java.util.List;

public class MiniApplication {
    public static void run(Class<?> cls, int port){
        System.out.println("Hello Mini-Spring!");
        TomcatServer tomcatServer = new TomcatServer(port);
        try {
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            //处理请求
            HandlerManager.resolveMappingHandler(classList);
            tomcatServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MiniApplication.run(MiniApplication.class,6699);
    }
}
