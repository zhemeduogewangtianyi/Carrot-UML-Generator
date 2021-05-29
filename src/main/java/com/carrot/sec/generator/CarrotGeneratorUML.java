package com.carrot.sec.generator;

import com.carrot.sec.context.CarrotUMLContext;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CarrotGeneratorUML {

    private String generator;

    public String generator(List<CarrotUMLContext> contexts){
        StringBuilder ret = new StringBuilder();
        ret.append("@startuml").append("\n");
        ret.append("skinparam classAttributeIconSize 0\n");


        for(CarrotUMLContext c : contexts){

            String className = c.getClassName();

            //Parent
            List<String> parentClass = c.getParentClass();

            if(!c.isAnnotation()){
                if(c.isInterface()){
                    ret.append("interface ");
                }else{
                    ret.append("class ");
                }
            }


            if(parentClass != null && parentClass.size() > 0){
                //interface N extends
                if(c.isInterface()){
                    for(int i = 0 ; i < parentClass.size() ; i++){
                        if(i == 0){
                            ret.append(className).append(" extends ");
                        }
                        ret.append(parentClass.get(i));
                        if(i < parentClass.size() - 1){
                            ret.append(",");
                        }else{
                            ret.append("{").append("\n");
                        }

                        generatorField(c,ret);
                        generatorMethod(c,ret);

                        ret.append("}").append("\n");

                    }

                }else{
                    ret.append(className).append(" extends ").append(parentClass.get(0)).append("{").append("\n");
                    generatorField(c,ret);
                    generatorMethod(c,ret);

                    ret.append("}").append("\n");
                }

            }else{
                ret.append(className).append("{").append("\n");
                generatorField(c,ret);
                generatorMethod(c,ret);

                ret.append("}").append("\n\n");
            }


        }

        ret.append("@enduml");
        String s = ret.toString();
        this.generator = s;
        return s;
    }

    private void generatorField(CarrotUMLContext c,StringBuilder builder){
        List<String> fieldNames = c.getFieldName();
        for(String fieldName : fieldNames){
            builder.append(fieldName).append("\n");
        }
    }

    private void generatorMethod(CarrotUMLContext c,StringBuilder builder){
        List<String> methodNames = c.getMethodNames();
        for(String methodName : methodNames){
            builder.append(methodName).append("\n");
        }
    }

    public void writer(String folder) {

        SourceStringReader reader = new SourceStringReader(generator);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(folder));
            reader.generateImage(fos);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
