package com.daicy.koala;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
//        mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//        mapper.getSerializationConfig().without(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                if (value instanceof String) {
                    jgen.writeString("");
                } else {
                    jgen.writeNull();
                }
            }
        });
        // 设置全局的NullValueSerializer
    }

    public static String TO_JSON(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error("TO_JSON error", e);
        }
        return null;
    }

    public static <T> T TO_OBJ(String source, Class<T> clazz) {
        try {
            return mapper.readValue(source, clazz);
        } catch (IOException e) {
            logger.error("TO_OBJ error", e);
        }
        return null;
    }


    public static void main(String[] args) {
//		Map<String, Object> map = new HashMap<String, Object>();
//
		ArrayList<String> list = new ArrayList<String>();
		list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
		list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
		list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
//
//		map.put("list", list);
//		map.put("pageSize", 3);
//		map.put("pageTotal", 5);
//
//		String string = STATUS_OK(map);
//		System.out.println("string=" + string);

        boolean isTrue = false;
        isTrue |= true;
        System.out.println(isTrue);
        System.out.println(JsonUtils.TO_JSON(list));
        List list1 = JsonUtils.TO_OBJ(JsonUtils.TO_JSON(list),ArrayList.class);
        System.out.println(list1);
    }
}
