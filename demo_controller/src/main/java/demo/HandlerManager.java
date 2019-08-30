package demo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList) {
        for(Class<?> cls :classList){
            if(cls.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(cls);
            }
        }
    }

    /**
     * 创建MappingHandler
     * @param cls
     */
    private static void parseHandlerFromController(Class<?> cls) {
        //如果类包含RequestMapping注解
        String uriPrefix = "";
        if(cls.isAnnotationPresent(RequestMapping.class)){
            uriPrefix = cls.getDeclaredAnnotation(RequestMapping.class).value();
        }
        Method[] methods = cls.getDeclaredMethods();
        for(Method method : methods){
            if(!method.isAnnotationPresent(RequestMapping.class)){
                continue;
            }
            String uri = uriPrefix + method.getDeclaredAnnotation(RequestMapping.class).value();
            //处理请求参数
            List<String> paramNameList = new ArrayList<>();
            for (Parameter parameter : method.getParameters()){
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);

            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);
            HandlerManager.mappingHandlerList.add(mappingHandler);
        }
    }
}
