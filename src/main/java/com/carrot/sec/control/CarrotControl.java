package com.carrot.sec.control;

import com.carrot.sec.adapter.CarrotVisitorAdapter;
import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.dispatch.CarrotDispatchCenter;
import com.carrot.sec.parser.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarrotControl {

    private static final List<String> FILES = new ArrayList<>();

    public List<CarrotUMLContext> descriptor(String folder){
        List<CarrotUMLContext> list = new ArrayList<>();
        return this.readClass(folder,list);
    }

    private List<CarrotUMLContext> readClass(String folder, List<CarrotUMLContext> list){

        File folderName = new File(folder);
        File[] fileNames = folderName.listFiles();
        if(fileNames == null || fileNames.length == 0){
            return null;
        }
        String name = null;
        for (File file : fileNames) {
            name = file.getName();
            if(file.isDirectory()){
                this.readClass(file.getAbsolutePath(),list);
            }
            try {
                if (file.getName().endsWith(".java")) {
                    if(file.isDirectory()){
                        this.readClass(file.getAbsolutePath(),list);
                    }
                    FILES.add(file.getName().split("\\.")[0]);
                    FileInputStream in = new FileInputStream(file.getAbsolutePath());
                    CarrotUMLContext context = new CarrotUMLContext();
                    parseCode(in,context);
                    list.add(context);

                    System.out.println();
                    System.out.println();
                }
            } catch (FileNotFoundException e) {
                System.out.println(name);
                e.printStackTrace();
            }
        }

        return list;

    }

    private void parseCode(FileInputStream in, CarrotUMLContext context) {

        ParseResult<CompilationUnit> parse = new JavaParser().parse(in);
        Optional<CompilationUnit> result = parse.getResult();
        CompilationUnit compilationUnit = result.get();
        VoidVisitorAdapter<Object> adapter = new CarrotVisitorAdapter();
        adapter.visit(compilationUnit,context);

    }

    static{
        CarrotDispatchCenter.register(new CarrotAnnotationParser());
        CarrotDispatchCenter.register(new CarrotClassOrInterfaceParser());
        CarrotDispatchCenter.register(new CarrotEnumParser());
        CarrotDispatchCenter.register(new CarrotRecordParser());
        CarrotDispatchCenter.register(new CarrotFieldParser());
        CarrotDispatchCenter.register(new CarrotMethodParser());
        CarrotDispatchCenter.register(new CarrotAnnotationExprParser());
        CarrotDispatchCenter.register(new CarrotConstructorParser());
        CarrotDispatchCenter.register(new CarrotInitializerParser());
    }

}
