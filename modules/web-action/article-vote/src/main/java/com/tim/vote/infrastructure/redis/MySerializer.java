//package com.tim.vote.infrastructure.redis;
//
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.SerializationException;
//
///**
// * Created by luolibing on 2018/5/16.
// */
//public class MyFstSerializer implements RedisSerializer {
//    @Override
//    public byte[] keyToBytes(String key) {
//        return FstSerializer.me.keyToBytes(key);
//    }
//
//    @Override
//    public String keyFromBytes(byte[] bytes) {
//        return FstSerializer.me.keyFromBytes(bytes);
//    }
//
//    @Override
//    public byte[] fieldToBytes(Object field) {
//        return valueToBytes(field);
//    }
//
//    @Override
//    public Object fieldFromBytes(byte[] bytes) {
//        return valueFromBytes(bytes);
//    }
//
//    @Override
//    public byte[] valueToBytes(Object value) {
//        if (value instanceof String) {
//            return keyToBytes((String) value);
//        }
//        if (value instanceof Integer) {
//            return keyToBytes(String.valueOf(value));
//        }
//        return FstSerializer.me.valueToBytes(value);
//    }
//
//    @Override
//    public Object valueFromBytes(byte[] bytes) {
//        return FstSerializer.me.valueFromBytes(bytes);
//    }
//
//    @Override
//    public byte[] serialize(Object o) throws SerializationException {
//        return new byte[0];
//    }
//
//    @Override
//    public Object deserialize(byte[] bytes) throws SerializationException {
//        return null;
//    }
//}
