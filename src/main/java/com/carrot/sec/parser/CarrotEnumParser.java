package com.carrot.sec.parser;

import com.carrot.sec.annotations.CarrotFind;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.SimpleName;

@SuppressWarnings({"unchecked", "rawtypes"})
@CarrotFind
public class CarrotEnumParser implements CarrotParser<TypeDeclaration<?>> {

    @Override
    public void parser(TypeDeclaration<?> type, CarrotUMLContext context) {
        String enumName = type.getName().asString();

        context.setClassName(enumName);
        context.setEnum(true);

        NodeList<EnumConstantDeclaration> entries = ((EnumDeclaration) type).getEntries();

        for(int i = 0 ; entries!=null && i < entries.size() ; i++){
            EnumConstantDeclaration enumConstantDeclaration = entries.get(i);
            SimpleName enumConstantDeclarationName = enumConstantDeclaration.getName();

            //enum cost
            context.getFieldName().add(enumConstantDeclarationName.asString());

        }

        NodeList<BodyDeclaration<?>> members = type.getMembers();
        for(int i = 0 ; members != null && i < members.size() ; i++){
            BodyDeclaration<?> bodyDeclaration = members.get(i);
            CarrotParser carrot = CarrotDispatchCenter.findCarrot(bodyDeclaration);
            if(carrot!= null){
                carrot.parser(bodyDeclaration,context);
            }
        }

    }

    @Override
    public boolean support(Object o) {
        return o instanceof EnumDeclaration;
    }


}
