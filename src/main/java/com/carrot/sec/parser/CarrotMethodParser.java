package com.carrot.sec.parser;

import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.Type;

public class CarrotMethodParser implements CarrotParser<MethodDeclaration> {

    @Override
    public void parser(MethodDeclaration type, CarrotUMLContext context) {

        String methodReturnType = type.getType().asString();
        String methodName = type.getName().asString();
        NodeList<Parameter> parameters = type.getParameters();

        for(Parameter parameter : parameters){
            NodeList<AnnotationExpr> annotations = parameter.getAnnotations();
            for(AnnotationExpr annotationExpr : annotations){
                CarrotParser carrot = CarrotDispatchCenter.findCarrot(annotationExpr);
                if(carrot != null){
                    carrot.parser(annotationExpr,context);
                }
            }
            Type parameterType = parameter.getType();
            SimpleName parameterName = parameter.getName();

            //TODO paramType paramName
            System.out.println("paramType：" + parameterType.asString() + " paramName：" + parameterName);
        }

        NodeList<AnnotationExpr> annotations = type.getAnnotations();
        for(AnnotationExpr annotationExpr : annotations){
            CarrotParser carrot = CarrotDispatchCenter.findCarrot(annotationExpr);
            if(carrot != null){
                carrot.parser(annotationExpr,context);
            }
        }

        NodeList<Modifier> modifiers = type.getModifiers();

        StringBuilder builderMethod = new StringBuilder();

        if(modifiers.size() == 0){
            builderMethod.append("~ ");
        }
        //methodModifier methodReturnType methodName
        a:for(int i = 0 ; modifiers != null && i < modifiers.size() ; i++){
            switch (modifiers.get(i).toString().trim()){
                case "public":
                    builderMethod.append("+ ");
                    break a;
                case "protected":
                    builderMethod.append("/# ");
                    break a;
                case "private":
                    builderMethod.append("- ");
                    break a;
                default:
                    builderMethod.append("~ ");
                    break a;
            }
        }

        builderMethod.append(methodName);
        builderMethod.append(" : ");
        builderMethod.append(methodReturnType);

        if(!context.isInner()){
            context.getMethodNames().add(builderMethod.toString());
        }else{
            context.getInnerClass().getLast().getMethodNames().add(builderMethod.toString());
        }

    }

    @Override
    public boolean support(Object o) {
        return o instanceof MethodDeclaration;
    }
}
