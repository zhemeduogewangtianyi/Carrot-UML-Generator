package com.carrot.sec.interfaces;

import com.carrot.sec.context.CarrotUMLContext;

public interface CarrotParser<T> extends Supported<Object>{

    void parser(T type, CarrotUMLContext context);

    @Override
    boolean support(Object o);
}
