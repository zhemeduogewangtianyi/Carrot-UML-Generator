package com.carrot.sec.dispatch;


import com.carrot.sec.interfaces.CarrotParser;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CarrotDispatchCenter {

    private static final Map<String, CarrotParser> DISPATCH_MAP = new ConcurrentHashMap<>();

    public static synchronized boolean register(CarrotParser carrotParser){
        String key = carrotParser.getClass().getSimpleName();
        if(DISPATCH_MAP.containsKey(key)){
            return false;
        }
        DISPATCH_MAP.put(key,carrotParser);
        return true;
    }

    public static synchronized boolean unregister(CarrotParser carrotParser){
        String key = carrotParser.getClass().getSimpleName();
        if(!DISPATCH_MAP.containsKey(key)){
            return false;
        }
        DISPATCH_MAP.remove(key,carrotParser);
        return true;
    }

    public static synchronized CarrotParser findCarrot(CarrotParser carrotParser){
        String key = carrotParser.getClass().getSimpleName();
        if(!DISPATCH_MAP.containsKey(key)){
            return null;
        }
        return DISPATCH_MAP.get(key);
    }

    public static synchronized CarrotParser findCarrot(Object support){

        for(Iterator<Map.Entry<String, CarrotParser>> car = DISPATCH_MAP.entrySet().iterator() ; car.hasNext() ; ){
            Map.Entry<String, CarrotParser> next = car.next();
            CarrotParser value = next.getValue();
            if(value.support(support)){
                return value;
            }
        }
        return null;
    }

    public static synchronized Map<String,CarrotParser> info(){
        Map<String,CarrotParser> res = DISPATCH_MAP;
        return res;
    }

}
