package com.carrot.sec.parser;

import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;

public class CarrotConstructorParser implements CarrotParser<BodyDeclaration<?>> {
    @Override
    public void parser(BodyDeclaration<?> type, CarrotUMLContext context) {
        //TODO i don't known ...
    }

    @Override
    public boolean support(Object o) {
        return o instanceof ConstructorDeclaration;
    }
}
