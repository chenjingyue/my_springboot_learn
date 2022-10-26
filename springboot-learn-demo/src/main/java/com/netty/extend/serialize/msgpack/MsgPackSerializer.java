package com.netty.extend.serialize.msgpack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netty.extend.vo.MessageHead;
import com.netty.extend.vo.MessageVo;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MsgPackSerializer {


    private static final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    /**
     * Serialize a Java object to byte array
     * @param object
     * @return
     * @param <T>
     * @throws JsonProcessingException
     */
    public static <T> byte[] serialize(T object) throws JsonProcessingException {
        byte[] bytes;
        bytes = objectMapper.writeValueAsBytes(object);
        return bytes;
    }

    /**
     * Deserialize the byte array to a Java object
     * @param bytes
     * @param clazz
     * @return
     * @param <T>
     * @throws IOException
     */
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        T object = objectMapper.readValue(bytes, clazz);
        return object;
    }


    public static void main(String[] args) {

        MessageVo pojo = new MessageVo();
        MessageHead head = new MessageHead();
        head.setId("woshiid");
        head.setType((byte) 1);
        Map<String, Object> map = new HashMap<>();
        map.put("111", "222");
        head.setExtendAttr(map);
        pojo.setHead(head);
        pojo.setBody("aaa");

        byte[] bytes;
        try {
            bytes = serialize(pojo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(bytes.length);

        //
        MessageVo deserialized = null;
        try {
            deserialized = deserialize(bytes, MessageVo.class) ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(deserialized);
    }

}
