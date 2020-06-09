package com.example.my_springboot_learn.proxy;

import org.apache.commons.io.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ProxyFactory {

    private static final String line = "\n";
    private static final String semicolonLine = ";" + line;
    private static final String tab = "\t";
    private static final String doubleTab = "\t\t";
    private static final String filedName = "target";
    private static final String packageName = "com.demo.proxy";
    private static final String className = "$Proxy";
    private static final String basePath = "F:\\GitCode\\test";
    private static final String parentPath = basePath + File.separator +
            packageName.replace(".", File.separator);
    private static final String filePath = parentPath + File.separator + className + ".java";


    public static Object getProxyObject(Object target) {
        Class clazz;
        Class<?>[] interfaces = target.getClass().getInterfaces();
        if (interfaces.length > 0) {
            clazz = interfaces[0];
        } else {
            throw new RuntimeException("没有找到接口");
        }
        String classContent = getClassContent(clazz);
//        System.out.println(classContent);
        try {
            File file = generateFile(classContent);
            Class c = compileJavaFile(file);
            Constructor constructor = c.getConstructor(clazz);
            return constructor.newInstance(target);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static String getClassContent(Class clazz) {
        String content = "";
        String interfaceName = clazz.getSimpleName();
        String packageContent = getPackageContent();
        String importContent = getImportContent(clazz);
        String classNameContent = getClassNameContent(interfaceName);
        String filed = getFiled(interfaceName);
        String construct = getConstructContent(interfaceName);
        String methodContents = geMethodContent(clazz);

        content += packageContent + importContent + classNameContent +
                filed + construct + methodContents + "}";
        return content;
    }

    private static String getFiled(String simpleName) {
        return tab + "private " + simpleName + " " + filedName + semicolonLine;
    }

    private static String getClassNameContent(String interfaceName) {
        return "public class " + className + " implements " + interfaceName + " {" + line;
    }

    private static String getConstructContent(String interfaceName) {
        return tab + "public " + className + "(" + interfaceName + " " + filedName + ") {" + line +
                doubleTab + "this." + filedName + " = " + filedName + semicolonLine +
                tab + "}" + line;
    }

    private static String getPackageContent() {
        return "package " + packageName + semicolonLine;
    }

    private static String getImportContent(Class clazz) {
        return "import " + clazz.getName() + semicolonLine;
    }

    private static String geMethodContent(Class clazz) {
        String methodContents = "";
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String parameterNames = "";
            String parameter = "";
            int index = 0;
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length > 0) {
                for (Class<?> parameterType : parameterTypes) {
                    parameterNames = parameterType.getSimpleName() + " arg" + index + ",";
                    parameter = "arg" + index + ",";
                }
                parameterNames = parameterNames.substring(0, parameterNames.length() - 1);
                parameter = parameter.substring(0, parameter.length() - 1);
            }
            String methodContent = tab + "public " + method.getReturnType().getSimpleName() + " "
                    + method.getName() + "(" + parameterNames + ") {" + line
                    + doubleTab + "return " + filedName + "." + method.getName() + "(" + parameter + ")" + semicolonLine
                    + tab + "}";
            methodContents += methodContent + line;
        }
        return methodContents;
    }

    private static String geMethodContent1(Class clazz, InvocationHandler handler) {
        String methodContents = "";
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String parameterNames = "";
            String parameter = "";
            String parameterTypeStr = "";
            int index = 0;
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length > 0) {
                for (Class<?> parameterType : parameterTypes) {
                    parameterNames = parameterType.getSimpleName() + " arg" + index + ",";
                    parameter = "arg" + index + ",";
                    parameterTypeStr = parameterType.getSimpleName() + ".class" + ",";
                }
                parameterNames = parameterNames.substring(0, parameterNames.length() - 1);
                parameter = parameter.substring(0, parameter.length() - 1);
                parameterTypeStr = parameterTypeStr.substring(0, parameterTypeStr.length() - 1);
            }
            if ("".equals(parameter)) {
                parameter = "null";
            } else {
                parameter = "new Object[] {" + parameter + "}";
            }
            if ("".equals(parameterTypeStr)) {
                parameterTypeStr = "";
            } else {
                parameterTypeStr = "," + parameterTypeStr;
            }
            String methodContent = tab + "public " + method.getReturnType().getSimpleName() + " "
                    + method.getName() + "(" + parameterNames + ") {" + line
                    + doubleTab + method.getReturnType().getSimpleName() + " result = null;" + line
                    + doubleTab + "try {" + line
                    + "Method method = " + clazz.getSimpleName() + ".class.getMethod" + "( \"" + method.getName() + "\"" + parameterTypeStr + ")" + semicolonLine
                    + doubleTab + "result = (" + method.getReturnType().getSimpleName() + ") "
                    + filedName + "." + handler.getClass().getMethods()[0].getName() + "(this,method," + parameter + ")" + semicolonLine
                    + "} catch (Throwable throwable) {\n" +
                    "            throwable.printStackTrace();\n" +
                    "        }\n"
                    + doubleTab + "return result;" + line
                    + tab + "}";
            methodContents += methodContent + line;
        }
        return methodContents;
    }

    public static File generateFile(String content) throws IOException {
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, content);
        return file;
    }

    public static Class compileJavaFile(File file) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null,null, null);
        Iterable units = fileMgr.getJavaFileObjects(file.getAbsolutePath());
        JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null,
                units);
        t.call();
        fileMgr.close();


        // load into memory and create an instance
        URL[] urls = new URL[]{new URL("file:/" + basePath + File.separator)};
        URLClassLoader ul = new URLClassLoader(urls);
        Class<?> aClass = ul.loadClass(packageName + "." + className);
        return aClass;


    }

    public static Object getProxyObject(Class<?>[] interfaces, InvocationHandler handler) {
        /**
         * ClassLoader loader,
         * Class<?>[] interfaces,
         * InvocationHandler h
         */
        String content = "";
        // 目标对象的接口名
        String interfaceName = handler.getClass().getSimpleName();
        String packageContent = getPackageContent();
        String interfaceNames = "";
        String importContent = "";
        String methodContents = "";
        importContent += getImportContent(Method.class);
        for (Class<?> anInterface : interfaces) {
            interfaceNames += anInterface.getSimpleName() + ",";
            importContent += getImportContent(anInterface);
            methodContents += geMethodContent1(anInterface, handler);
        }
        importContent += getImportContent(handler.getClass());
        interfaceNames = interfaceNames.substring(0, interfaceNames.length() - 1);
        String classNameContent = getClassNameContent(interfaceNames);
        String filed = getFiled(interfaceName);
        String construct = getConstructContent(interfaceName);
        content += packageContent + importContent + classNameContent +
                filed + construct + methodContents + "}";
//        System.out.println(content);

        try {
            File file = generateFile(content);
            Class c = compileJavaFile(file);
            Constructor constructor = c.getConstructor(handler.getClass());
            return constructor.newInstance(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
//        TestProxy target = new TestProxy();
//        ITestProxy proxyObject = (ITestProxy) getProxyObject(target);
//        String query = proxyObject.query("ggg");
//        System.out.println(query);


//        String str = "com.demo.proxy";
//        String result = str.replaceAll("\\.", "\\" + File.separator);
//        System.out.println(result);
        //            packageName.replaceAll("\\.", File.separator);

        Class<?>[] interfaces = new Class[]{ITestProxy.class};
        ITestProxy proxyObject = (ITestProxy) getProxyObject(interfaces, new MyInvocationHandler());
//        System.out.println(proxyObject);
        System.out.println(proxyObject.query("hhh"));
    }

}
