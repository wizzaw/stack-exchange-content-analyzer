package com.nayki.analyzer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nayki.analyzer.exceptions.DeleteFromCacheException;
import com.nayki.analyzer.exceptions.NotFoundInCacheException;
import io.quarkus.redis.client.RedisClient;
import io.vertx.redis.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class CacheUtils {

    @Inject
    RedisClient redisClient;

    @Inject
    SerializationUtils serializationUtil;

    public <T> T get(String id, Class<T> cls) throws JsonProcessingException {
        final Response response = redisClient.get(id);
        if (response == null) {
            throw new NotFoundInCacheException(cls.getName(), id);
        }

        return serializationUtil.toObject(response.toString(), cls);
    }

    public void set(String id, Object obj) throws JsonProcessingException {
        final Response response = redisClient.set(Arrays.asList(id, serializationUtil.toJson(obj)));
        if (response == null) {
            throw new NotFoundInCacheException(obj.getClass().getName(), id);
        }
    }

    public void delete(String id) {
        final Response response = redisClient.del(List.of(id));
        if (response == null) {
            throw new DeleteFromCacheException(id);
        }
    }
}
