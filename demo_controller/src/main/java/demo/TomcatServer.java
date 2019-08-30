package demo;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;


public class TomcatServer {
    private Tomcat tomcat;
    private int port;

    public TomcatServer() {
    }

    public TomcatServer(int port){
        this.port = port;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        Connector connector = new Connector();
        tomcat.setHostname("localhost");//设置主机名
        connector.setPort(this.port);
        connector.setURIEncoding("UTF-8");
        tomcat.getService().addConnector(connector);
        tomcat.setBaseDir(".");

        //注入servlet
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        //自己定义的servlet（理解成和web.xml中的配置）
        DispatcherServlet servlet = new DispatcherServlet();
        tomcat.addServlet(context, "dispatcherServlet", servlet)
                .setAsyncSupported(true);
        //名称和上面的一致
        context.addServletMappingDecoded("/", "dispatcherServlet");

        //context添加到host中
        tomcat.getHost().addChild(context);
        tomcat.start();

        //防止一启动就关闭
        tomcat.getServer().await();
    }
}
