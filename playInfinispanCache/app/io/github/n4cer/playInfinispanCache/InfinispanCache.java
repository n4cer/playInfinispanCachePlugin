package io.github.n4cer.playInfinispanCache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import play.api.cache.CacheAPI;
import scala.Option;

public class InfinispanCache implements CacheAPI {
    private static DefaultCacheManager cMan;
    
    public InfinispanCache(DefaultCacheManager cacheManager) {
        cMan = cacheManager;
    }
    
    @Override
    public Option<Object> get(String key) {
        if(key.isEmpty()) {
            return null;
        }
        
        return Option.apply(cMan.getCache().get(key));
    }
    
    public <V> List<V> getValues() {
        ArrayList<V> items = new ArrayList<>();
        Cache<String, V> c = cMan.getCache();
        
        if (c != null) {
            for (V item : c.values()) {
                items.add(item);
            }
        }
        return items;
    }
    
    @Override
    public void set(String key, Object value, int expiration) {
        if (expiration == 0) {
            cMan.getCache().put(key, value, -1, TimeUnit.SECONDS);
        } else {
            cMan.getCache().put(key, value, expiration, TimeUnit.SECONDS);
        }
    }
    
    @Override
    public void remove(String key) {
        cMan.getCache().remove(key);
    }
    
    public String getClusterMembers() {
        return cMan.getClusterMembers();
    }
    
    public String getCacheManagerStatus() {
        return cMan.getCacheManagerStatus();
    }
    
    public String getClusterName() {
        return cMan.getClusterName();
    }
    
    public String getCreatedCacheCount() {
        return cMan.getCreatedCacheCount();
    }
    
    public String getCacheNames() {
        String result = "";
        
        final Set<String> names = cMan.getCacheNames();
        final Iterator<String> iter = names.iterator();
        while (iter.hasNext()) {
            result += iter.next() + " ";
        }
        
        return result;
    }
    
    public long lifespan() {
        return cMan.getDefaultCacheConfiguration().expiration().lifespan();
    }
    
    public long maxIdle() {
        return cMan.getDefaultCacheConfiguration().expiration().maxIdle();
    }
    
    public String cacheMode() {
        return cMan.getDefaultCacheConfiguration().clustering().cacheModeString();
    }
    
    public String eviction() {
        return cMan.getDefaultCacheConfiguration().eviction().toString();
    }
    
    public int size() {
        return cMan.getClusterSize();
    }
    
    public int cacheSize() {
        int result = 0;
        
        final Set<String> names = cMan.getCacheNames();
        final Iterator<String> iter = names.iterator();
        while (iter.hasNext()) {
            result += cMan.getCache(iter.next()).values().size();
        }
        
        return result;
    }
    
    public void stop() {
        cMan.stop();
    }
}