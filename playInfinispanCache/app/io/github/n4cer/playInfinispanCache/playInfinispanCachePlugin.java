package io.github.n4cer.playInfinispanCache;

import play.*;
import play.api.cache.CacheAPI;
import play.api.cache.CachePlugin;
import play.Play;
import play.Application;
import play.Logger;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;

public class playInfinispanCachePlugin extends CachePlugin{
    private InfinispanCache infinispanCache;
    
    public playInfinispanCachePlugin(Application app) {
        
    }
    
    public void onStart() {
        Logger.info("Start playInfinispanCachePlugin");
        
        Configuration conf = Play.application().configuration();
        
        final String cluster_name = conf.getString("playinfinispancacheplugin.clustername");
        final String port = conf.getString("playinfinispancacheplugin.port");
        final String lifespan = conf.getString("playinfinispancacheplugin.lifespan");
        final String max_idle = conf.getString("playinfinispancacheplugin.maxidle");
        
        try {
            System.setProperty("jgroups.udp.mcast_port", port);
            
            DefaultCacheManager cMan = new DefaultCacheManager(GlobalConfigurationBuilder.defaultClusteredBuilder()
                    .transport().defaultTransport().addProperty("configurationFile", "udp.xml").defaultTransport()
                    .clusterName(cluster_name).build(), new ConfigurationBuilder().clustering()
                    .cacheMode(CacheMode.REPL_SYNC).eviction().strategy(EvictionStrategy.NONE).expiration()
                    .lifespan(Long.valueOf(lifespan)).maxIdle(Long.valueOf(max_idle)).build(), true);
            
            infinispanCache = new InfinispanCache(cMan);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void onStop() {
        Logger.info("Stop playInfinispanCachePlugin");
        infinispanCache.stop();
    }
    
    public boolean enabled() {
        return true;
    }
    
    @Override
    public CacheAPI api() {
        return infinispanCache;
    }
}