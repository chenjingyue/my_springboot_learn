package com.netty.extend.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.netty.extend.vo.MessageHead;
import com.netty.extend.vo.MessageVo;

import java.util.HashMap;
import java.util.Map;

public class KryoSerializer {

    private static Kryo kryo = KryoFactory.createKryo();

    public static <T> byte[] serialize(T object) {
        Output output = new Output(4096, 65536);
        kryo.writeObject(output, object);
        output.close();
        byte[] bytes = output.toBytes();
        return bytes;
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Input input = new Input(bytes);
        T object = kryo.readObject(input, clazz);
        input.close();
        return object;
    }


    public static void main(String[] args) {
        Kryo kryo =  KryoFactory.createKryo();

//        kryo.register(MessageVo.class);
//        kryo.register(MessageHead.class);

        MessageVo object = new MessageVo();
        object.setBody("bbbbbb");
        MessageHead head = new MessageHead();
        head.setId("woshi id");
        Map<String,Object> map = new HashMap<>();
        map.put("key","chen");
        head.setExtendAttr(map);
        object.setHead(head);

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(4096,65536);
        kryo.writeObject(output, object);
//        output.flush();
        output.close();

//        byte[] b = baos.toByteArray();
//        try {
//            baos.flush();
//            baos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        byte[] bytes = output.toBytes();
        String  aa = new String(bytes);
        System.out.println("--------------> " + bytes.length);
        System.out.println("--------------> " + aa);

        Input input = new Input(bytes);
        MessageVo object2 = kryo.readObject(input, MessageVo.class);
        input.close();
        System.out.println(object2);

    }

}
