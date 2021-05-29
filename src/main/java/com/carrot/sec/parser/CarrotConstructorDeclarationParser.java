package com.carrot.sec.parser;

import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;

public class CarrotConstructorDeclarationParser implements CarrotParser<BodyDeclaration<?>> {
    @Override
    public void parser(BodyDeclaration<?> type, CarrotUMLContext context) {
        System.out.println(type);
    }

    @Override
    public boolean support(Object o) {
        return o instanceof ConstructorDeclaration;
    }
}
