package com.carrot.sec.parser;

import com.carrot.sec.annotations.CarrotFind;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.expr.AnnotationExpr;

@CarrotFind
public class CarrotAnnotationExprParser implements CarrotParser<AnnotationExpr> {

    @Override
    public void parser(AnnotationExpr type, CarrotUMLContext context) {
        System.out.println("注解：" + type.getName().asString());
    }

    @Override
    public boolean support(Object o) {
        return o instanceof AnnotationExpr;
    }
}
