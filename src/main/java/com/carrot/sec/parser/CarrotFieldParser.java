package com.carrot.sec.parser;

import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;

public class CarrotFieldParser implements CarrotParser<FieldDeclaration> {

    @Override
    public void parser(FieldDeclaration type, CarrotUMLContext context) {
        NodeList<Modifier> modifiers = type.getModifiers();
        NodeList<VariableDeclarator> variables = type.getVariables();

        if(modifiers == null || modifiers.size() == 0){
            for(VariableDeclarator variableDeclarator : variables){

                StringBuilder builderField = new StringBuilder();
                builderField.append("~ ");
                builderField.append(variableDeclarator.getName().asString());
                builderField.append(" : ");
                builderField.append(variableDeclarator.getType().asString());

                if(!context.isInner()){
                    context.getFieldName().add(builderField.toString());
                }else{
                    context.getInnerClass().getLast().getFieldName().add(builderField.toString());
                }
            }

        }

        //fieldModifier fieldType fieldName
        for(int i = 0 ; modifiers != null && i < modifiers.size() ; i++){
            Modifier modifier = modifiers.get(i);
            String modifierKeyword = modifier.getKeyword().asString();
            VariableDeclarator variableDeclarator = variables.get(i);
            String fieldType = variableDeclarator.getType().asString();
            String fieldName = variableDeclarator.getName().asString();

            StringBuilder builderField = new StringBuilder();
            switch (modifierKeyword.trim()){
                case "public":
                    builderField.append("+ ");
                    break;
                case "protected":
                    builderField.append("/# ");
                    break;
                case "private":
                    builderField.append("- ");
                    break;
                default:
                    builderField.append("~ ");
                    break;
            }
            builderField.append(fieldName);
            builderField.append(" : ");
            builderField.append(fieldType);

            if(!context.isInner()){
                context.getFieldName().add(builderField.toString());
            }else{
                context.getInnerClass().getLast().getFieldName().add(builderField.toString());
            }

        }
        NodeList<AnnotationExpr> annotations = type.getAnnotations();
        for(AnnotationExpr annotationExpr : annotations){
            CarrotParser carrot = CarrotDispatchCenter.findCarrot(annotationExpr);
            if(carrot != null){
                carrot.parser(annotationExpr,context);
            }
        }

    }

    @Override
    public boolean support(Object o) {
        return o instanceof FieldDeclaration;
    }
}
