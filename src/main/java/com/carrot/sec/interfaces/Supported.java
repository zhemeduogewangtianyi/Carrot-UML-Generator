package com.carrot.sec.interfaces;

public interface Supported<T> {

    default boolean support(T t){
        return false;
    }

}
