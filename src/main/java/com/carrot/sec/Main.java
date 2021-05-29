package com.carrot.sec;

import com.carrot.sec.context.CarrotUMLContext;
import com.carrot.sec.control.CarrotControl;
import com.carrot.sec.generator.CarrotGeneratorUML;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        CarrotControl carrotControl = new CarrotControl();
        List<CarrotUMLContext> ret = carrotControl.descriptor("D:\\project\\play_code\\src\\main\\java\\com\\opencode\\generatoruml\\test");
        CarrotGeneratorUML generatorUML = new CarrotGeneratorUML();
        String generator = generatorUML.generator(ret);
        generatorUML.writer("d://carrotUml.png");



    }

}
