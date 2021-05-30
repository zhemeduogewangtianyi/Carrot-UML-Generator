package com.carrot.sec;

import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.control.CarrotControl;
import com.carrot.sec.generator.CarrotGeneratorUML;

import java.io.IOException;
import java.util.List;

//-DPLANTUML_LIMIT_SIZE=100000
public class Main {

    public static void main(String[] args) throws IOException {

        CarrotControl carrotControl = new CarrotControl();
        List<CarrotUMLContext> ret = carrotControl.descriptor("D:\\project\\Dubbo\\dubbo");
        CarrotGeneratorUML generatorUML = new CarrotGeneratorUML();
        List<String> generators = generatorUML.generator(ret,10);
        generatorUML.writer("d://uml",generators);
//        FileOutputStream fos = new FileOutputStream(new File("d://carrotUml.txt"));
//        fos.write(generator.getBytes());
//        fos.close();


    }

}
