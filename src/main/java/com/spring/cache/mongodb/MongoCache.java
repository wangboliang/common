package com.spring.cache.mongodb;

import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Base64;
import java.util.concurrent.Callable;


/**
 * <p>
 * 自定义缓存实现
 * </p>
 *
 * @author wangliang
 * @since 2018/8/1
 */
public class MongoCache implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(MongoCache.class);
    private String cacheName;
    private MongoTemplate mongoTemplate;

    @Override
    public String getName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Object getNativeCache() {
        return mongoTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        final Object value = getFromCache(key);
        if (value != null) {
            return new SimpleValueWrapper(value);
        }

        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        try {
            final Object value = getFromCache(key);
            if (value == null) {
                return null;
            }
            return type.cast(value);
        } catch (ClassCastException e) {
            throw new IllegalStateException("Unable to cast the object.", e);
        }
    }

//    @Override
//    public <T> T get(Object key, Callable<T> callable) {
//        Assert.isTrue(key instanceof String, "'key' must be an instance of 'java.lang.String'.");
//        Assert.notNull(callable, "'valueLoader' must not be null");
//
//        Object cached = getFromCache(key);
//        if (cached != null) {
//            return (T) cached;
//        }
//
//        final Object dynamicLock = ((String) key).intern();
//
//        synchronized (dynamicLock) {
//            cached = getFromCache(key);
//            if (cached != null) {
//                return (T) cached;
//            }
//
//            T value;
//            try {
//                value = callable.call();
//            } catch (Throwable ex) {
//                throw new ValueRetrievalException(key, callable, ex);
//            }
//
//            ValueWrapper newCachedValue = putIfAbsent(key, value);
//            if (newCachedValue != null) {
//                return (T) newCachedValue.get();
//            } else {
//                return value;
//            }
//        }
//    }

    @Override
    public void put(Object key, Object value) {
        Assert.isTrue(key instanceof String, "'key' must be an instance of 'java.lang.String'.");

        try {
            final String id = (String) key;
            String result = null;
            if (value != null) {
                Assert.isTrue(value instanceof Serializable, "'value' must be serializable.");
                result = serialize(value);
            }

            final CacheDocument cache = new CacheDocument(id, result);
            mongoTemplate.save(cache);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can not serialize the value: %s", key), e);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Assert.isTrue(key instanceof String, "'key' must be an instance of 'java.lang.String'.");

        try {
            final String id = (String) key;
            String result = null;

            if (value != null) {
                Assert.isTrue(value instanceof Serializable, "'value' must be serializable.");
                result = serialize(value);
            }

            final CacheDocument cache = new CacheDocument(id, result);
            mongoTemplate.insert(cache);
            return null;
        } catch (DuplicateKeyException e) {
            logger.info(String.format("Key: %s already exists in the cache. Element will not be replaced.", key), e);
            return get(key);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot serialize the value for key: %s", key), e);
        }
    }

    @Override
    public void evict(Object key) {
        Assert.isTrue(key instanceof String, "'key' must be an instance of 'java.lang.String'.");

        final String id = (String) key;
        final Criteria criteria = Criteria.where("_id").is(id);
        final Query query = Query.query(criteria);

        mongoTemplate.remove(query);
    }

    @Override
    public void clear() {
        mongoTemplate.remove(new Query(), CacheDocument.class);
    }

    private Object getFromCache(Object key) {
        Assert.isTrue(key instanceof String, "'key' must be an instance of 'java.lang.String'.");

        final String id = (String) key;
        final CacheDocument cache = mongoTemplate.findById(id, CacheDocument.class);

        if (cache != null) {
            final String element = cache.getElement();
            if (element != null && !"".equals(element)) {
                try {
                    return deserialize(element);
                } catch (IOException | ClassNotFoundException e) {
                    throw new IllegalStateException("Unable to read the object from cache.", e);
                }
            }
        }

        return null;
    }

    private Object deserialize(String value) throws IOException, ClassNotFoundException {
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] data = decoder.decode(value);
        try (final ByteArrayInputStream buffer = new ByteArrayInputStream(data);
             final ObjectInputStream output = new ObjectInputStream(buffer)) {
            return output.readObject();
        }
    }

    private String serialize(Object value) throws IOException {
        try (final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
             final ObjectOutputStream output = new ObjectOutputStream(buffer)) {

            output.writeObject(value);

            final byte[] data = buffer.toByteArray();

            final Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(data);
        }
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }
}
