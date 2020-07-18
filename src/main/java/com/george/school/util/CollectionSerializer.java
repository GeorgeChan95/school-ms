package com.george.school.util;

import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * <p>
 *     自定义redis序列化类
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/18 19:30
 * @since JDK 1.8
 */
public class CollectionSerializer<T extends Serializable> implements RedisSerializer<T> {
    private CollectionSerializer(){}
    public static volatile CollectionSerializer<Serializable> collectionSerializer=null;
    public static CollectionSerializer<Serializable> getInstance(){
        if(collectionSerializer==null){
            synchronized (CollectionSerializer.class) {
                if(collectionSerializer==null){
                    collectionSerializer=new CollectionSerializer<>();
                }
            }
        }
        return collectionSerializer;
    }
    @Override
    public byte[] serialize(T t) throws SerializationException {
        return SerializationUtils.serialize(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return SerializationUtils.deserialize(bytes);
    }
}
