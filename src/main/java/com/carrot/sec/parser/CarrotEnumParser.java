package com.carrot.sec.parser;

import com.carrot.sec.annotations.CarrotFind;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.SimpleName;

@CarrotFind
public class CarrotEnumParser implements CarrotParser<TypeDeclaration<?>> {

    @Override
    public void parser(TypeDeclaration<?> type, CarrotUMLContext context) {
        String enumName = type.getName().asString();

        context.setClassName(enumName);
        System.out.println("枚举名称：" + enumName);

        NodeList<EnumConstantDeclaration> entries = ((EnumDeclaration) type).getEntries();

        for(int i = 0 ; entries!=null && i < entries.size() ; i++){
            EnumConstantDeclaration enumConstantDeclaration = entries.get(i);
            SimpleName enumConstantDeclarationName = enumConstantDeclaration.getName();

            System.out.println("枚举" + enumConstantDeclarationName.asString());
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
