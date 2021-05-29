package com.carrot.sec.parser;

import com.carrot.sec.annotations.CarrotFind;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.interfaces.CarrotParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;

@CarrotFind
public class CarrotClassOrInterfaceParser implements CarrotParser<TypeDeclaration<?>> {

    @Override
    public void parser(TypeDeclaration<?> type, CarrotUMLContext context) {
        String className = type.getName().asString();

        //类名称
        if(!context.isInner()){
            context.setClassName(className);
            context.setInterface(((ClassOrInterfaceDeclaration) type).isInterface());
        }else{
            context.getInnerClass().getLast().setClassName(className);
            context.getInnerClass().getLast().setInterface(((ClassOrInterfaceDeclaration) type).isInterface());
        }

        System.out.println("类名称：" + className);

        NodeList<AnnotationExpr> annotations = type.getAnnotations();
        if(annotations != null){
            for(AnnotationExpr annotationExpr : annotations){
                Name annotationName = annotationExpr.getName();

                //TODO
                System.out.println("类上面的注解：" + annotationName);

            }
        }

        NodeList<ClassOrInterfaceType> extendedTypes = ((ClassOrInterfaceDeclaration) type).getExtendedTypes();
        if(extendedTypes != null){
            for(ClassOrInterfaceType extendClass : extendedTypes){
                String extendedType = extendClass.getName().asString();
                //父类
                if(!context.isInner()){
                    context.getParentClass().add(extendedType);
                }else{
                    context.getInnerClass().getLast().getParentClass().add(extendedType);
                }

                System.out.println("继承了 ： " + extendedType);

            }
        }

        NodeList<ClassOrInterfaceType> implementedTypes = ((ClassOrInterfaceDeclaration) type).getImplementedTypes();
        if(implementedTypes != null){
            for(ClassOrInterfaceType interfaceType : implementedTypes){
                String interfaceTypeName = interfaceType.getName().asString();

                //接口
                if(!context.isInner()){
                    context.getInterfaceName().add(interfaceTypeName);
                }else{
                    context.getInnerClass().getLast().getInterfaceName().add(interfaceTypeName);
                }

                System.out.println("实现了：" + interfaceTypeName);
            }
        }

        NodeList<TypeParameter> typeParameters = ((ClassOrInterfaceDeclaration) type).getTypeParameters();
        if(typeParameters != null){
            for(TypeParameter typeParameter : typeParameters){
                SimpleName typeParameterName = typeParameter.getName();

                //TODO
                System.out.println("泛型是：" + typeParameterName);
            }
        }

        NodeList<BodyDeclaration<?>> members = type.getMembers();

        if(members != null && members.size() > 0){
            int membersCnt = 0;
            while(membersCnt < members.size()){
                BodyDeclaration<?> bodyDeclaration = members.get(membersCnt);
                if(bodyDeclaration instanceof ClassOrInterfaceDeclaration){

                    CarrotParser carrot = CarrotDispatchCenter.findCarrot(bodyDeclaration);
                    if(carrot != null){

                        context.addInner();
                        context.getInnerClass().add(new CarrotUMLContext());

                        carrot.parser(bodyDeclaration,context);
                        context.subInner();
                    }

                }else if(bodyDeclaration instanceof FieldDeclaration){
                    CarrotParser carrot = CarrotDispatchCenter.findCarrot(bodyDeclaration);
                    if(carrot != null){
                        carrot.parser(bodyDeclaration,context);
                    }
                }else if(bodyDeclaration instanceof MethodDeclaration){
                    CarrotParser carrot = CarrotDispatchCenter.findCarrot(bodyDeclaration);
                    if(carrot != null){
                        carrot.parser(bodyDeclaration,context);
                    }
                }else{
                    throw new RuntimeException("unknown type !");
                }

                membersCnt++;
            }
        }
    }

    @Override
    public boolean support(Object o) {
        return o instanceof ClassOrInterfaceDeclaration;
    }


}
