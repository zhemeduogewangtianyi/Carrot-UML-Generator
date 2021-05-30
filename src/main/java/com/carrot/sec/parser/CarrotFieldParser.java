package com.carrot.sec.parser;

import com.carrot.sec.annotations.CarrotFind;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;

@SuppressWarnings({"unchecked", "rawtypes"})
@CarrotFind
public class CarrotFieldParser implements CarrotParser<FieldDeclaration> {

    @Override
    public void parser(FieldDeclaration type, CarrotUMLContext context) {
        NodeList<Modifier> modifiers = type.getModifiers();
        NodeList<VariableDeclarator> variables = type.getVariables();

        if(modifiers == null || modifiers.size() == 0){
            for(VariableDeclarator variableDeclarator : variables){

                StringBuilder builderField = new StringBuilder();

                String fieldName = variableDeclarator.getName().asString();
                String fieldType = variableDeclarator.getType().asString();

                builderField.append("~ ");
                builderField.append(fieldName);
                builderField.append(" : ");
                builderField.append(fieldType);

                if(!context.isInner()){
                    context.getFieldName().add(builderField.toString());
                }else{
                    context.getInnerClass().getLast().getFieldName().add(builderField.toString());
                }

                //fieldModifier fieldType fieldName
                for(int i = 0 ; modifiers != null && i < modifiers.size() ; i++){
                    Modifier modifier = modifiers.get(i);
                    String modifierKeyword = modifier.getKeyword().asString();

                    StringBuilder builderModifierKeyword = new StringBuilder();
                    switch (modifierKeyword.trim()){
                        case "public":
                            builderModifierKeyword.append("+ ");
                            break;
                        case "protected":
                            builderModifierKeyword.append("/# ");
                            break;
                        case "private":
                            builderModifierKeyword.append("- ");
                            break;
                        default:
                            builderModifierKeyword.append("~ ");
                            break;
                    }
                    builderModifierKeyword.append(fieldName);
                    builderModifierKeyword.append(" : ");
                    builderModifierKeyword.append(fieldType);

                    if(!context.isInner()){
                        context.getFieldName().add(builderModifierKeyword.toString());
                    }else{
                        context.getInnerClass().getLast().getFieldName().add(builderModifierKeyword.toString());
                    }

                }

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
