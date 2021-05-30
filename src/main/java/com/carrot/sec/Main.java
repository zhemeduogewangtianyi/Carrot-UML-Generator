package com.carrot.sec;

import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.control.CarrotControl;
import com.carrot.sec.generator.CarrotGeneratorUML;

import java.util.List;

/**
 * -DPLANTUML_LIMIT_SIZE=100000
 * */
public class Main {

    public static void main(String[] args) {

        //项目目录
        String projectFolder = "D:\\project\\Dubbo\\dubbo";
        //输出目录
        String outFolder = "d://uml";
        //10个类一张图
        int step = 10;

        CarrotControl carrotControl = new CarrotControl();
        List<CarrotUMLContext> ret = carrotControl.descriptor(projectFolder);
        CarrotGeneratorUML generatorUML = new CarrotGeneratorUML();
        List<String> generators = generatorUML.generator(ret,step);
        generatorUML.writer(outFolder,generators);

    }

}
