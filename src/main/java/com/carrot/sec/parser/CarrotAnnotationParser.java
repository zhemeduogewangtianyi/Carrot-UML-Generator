package com.carrot.sec.parser;

import com.carrot.sec.annotations.CarrotFind;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

@CarrotFind
public class CarrotAnnotationParser implements CarrotParser<TypeDeclaration<?>> {

    @Override
    public void parser(TypeDeclaration<?> type, CarrotUMLContext context) {
        NodeList<BodyDeclaration<?>> members = type.getMembers();
        for(BodyDeclaration<?> bodyDeclaration : members){
            CarrotParser carrot = CarrotDispatchCenter.findCarrot(bodyDeclaration);
            if(carrot != null){
                carrot.parser(bodyDeclaration,context);
            }
        }
        context.setClassName("annotation " + type.getName().asString());
        context.setAnnotation(true);
        //TODO content
    }

    @Override
    public boolean support(Object o) {
        return o instanceof AnnotationDeclaration;
    }


}
