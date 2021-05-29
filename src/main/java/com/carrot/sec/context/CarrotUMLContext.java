package com.carrot.sec.context;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * context
 * */
@Getter
public class CarrotUMLContext {

    private final AtomicInteger ctl = new AtomicInteger(0);

    /** 类名称 */
    @Setter
    private String className;

    @Setter
    private boolean isInterface;

    @Setter
    private boolean isAnnotation;

    @Setter
    private boolean isEnum;

    /** 父类们 */
    private final List<String> parentClass = new ArrayList<>();

    /** 接口们 */
    private final Set<String> interfaceName = new HashSet<>();

    /** 方法们 */
    private final List<String> methodNames = new ArrayList<>();

    /** 内部类们 */
    private final LinkedList<CarrotUMLContext> innerClass = new LinkedList<>();

    /** 字段名字们 */
    private final List<String> fieldName = new ArrayList<>();

    public boolean isInner(){
        return ctl.get() > 0;
    }

    public void addInner(){
        ctl.incrementAndGet();
    }

    public void subInner(){
        ctl.decrementAndGet();
    }

}
