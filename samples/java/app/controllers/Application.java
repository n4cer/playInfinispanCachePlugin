package controllers;

import play.cache.Cache;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("playInfinispanCachePlugin - sample"));
    }
    
    public static Result set() {
        Cache.set("key", "value", 10);
        
        return ok("cache set");
    }
    
    public static Result get() {
        String value = (String) Cache.get("key");
        if (value == null) {
            value = "";
        }
        return ok(value);
    }
    
    public static Result delete() {
        Cache.remove("key");
        
        return ok("key removed");
    }
}