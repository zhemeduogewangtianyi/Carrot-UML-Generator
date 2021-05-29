package com.carrot.sec.adapter;


import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CarrotVisitorAdapter extends VoidVisitorAdapter<Object> {

    @Override
    public void visit(CompilationUnit n, Object arg) {
        super.visit(n, arg);

        CarrotUMLContext context = (CarrotUMLContext) arg;
        NodeList<TypeDeclaration<?>> types = n.getTypes();
        for(TypeDeclaration<?> type : types){

            CarrotParser carrot = CarrotDispatchCenter.findCarrot(type);
            if(carrot == null){
                throw new RuntimeException("error type !");
            }
            carrot.parser(type,context);
        }

    }

}
