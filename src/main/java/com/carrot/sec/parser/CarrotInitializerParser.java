package com.carrot.sec.parser;

import com.carrot.sec.annotations.CarrotFind;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;

@CarrotFind
public class CarrotInitializerParser implements CarrotParser<BodyDeclaration<?>> {

    @Override
    public void parser(BodyDeclaration<?> type, CarrotUMLContext context) {
        //TODO static code block ... InitializerParser
    }

    @Override
    public boolean support(Object o) {
        return o instanceof InitializerDeclaration;
    }
}
