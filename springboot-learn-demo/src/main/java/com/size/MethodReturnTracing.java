package com.size;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import java.lang.reflect.Field;
import com.sun.btrace.AnyType;

import static com.sun.btrace.BTraceUtils.Reflective;
import static com.sun.btrace.BTraceUtils.Reflective.classForName;
import static com.sun.btrace.BTraceUtils.Reflective.contextClassLoader;
import static com.sun.btrace.BTraceUtils.Strings.str;
import static com.sun.btrace.BTraceUtils.println;

@BTrace(unsafe = true)
public class MethodReturnTracing {
    @OnMethod(
            clazz="com.size.CountEmptyObjectSize",
            method="aa"
    )
    public static void getMap(@Self Object bm) {
        //如果map是实例变量
        Field mapField = Reflective.field("com.size.CountEmptyObjectSize", "map");
        println(Reflective.get(mapField,bm));
    }
    @OnMethod(clazz = "com.size.CountEmptyObjectSize", // 类的全限定名
            method = "aa", // 方法名
            location = @Location(Kind.ENTRY)) // 表示跟踪某个类的某个方法，位置为方法返回处
    public static void onMethodEntry(@Self Object self, int in, @Return AnyType result) { // @Return注解将上面被跟踪方法的返回值绑定到此探查方法的参数上

        println(BTraceUtils.Time.timestamp("yyyy-MM-dd HH:mm:ss")); // 打印时间

        println("method self: " + str(self));

        println("method params: " + in); // 打印入参

        println("method return: " + result.toString()); // 打印结果对象，因String.valueOf(obj)为外部方法，故需使用unsafe模式

        println("=========================="); // 强烈建议加上，否则会因为输出缓冲看不到最新打印结果
    }
    @OnMethod(clazz = "com.size.CountEmptyObjectSize", // 类的全限定名
            method = "aa", // 方法名
            location = @Location(Kind.RETURN)) // 表示跟踪某个类的某个方法，位置为方法返回处
    public static void onMethodReturn(@Self Object self, Object[] id, @Return AnyType result) { // @Return注解将上面被跟踪方法的返回值绑定到此探查方法的参数上

        println(BTraceUtils.Time.timestamp("yyyy-MM-dd HH:mm:ss")); // 打印时间

        println("method self: " + str(self));

        println("method params: " + id.length + id[1]); // 打印入参

        println("method return: " + result.toString()); // 打印结果对象，因String.valueOf(obj)为外部方法，故需使用unsafe模式

        println("=========================="); // 强烈建议加上，否则会因为输出缓冲看不到最新打印结果
    }
    //一分钟执行一次
//    @OnTimer(1000*60)
//    public static void timeGetMap(){
//        //如果map是静态变量
//        Class clazz = classForName("cn.freemethod.business.BusinessMap", contextClassLoader());
//        Field mapField = Reflective.field(clazz, "map");
//        println(Reflective.get(mapField));
//    }
}
