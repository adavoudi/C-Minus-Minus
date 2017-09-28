package frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;


import Core.Code;
import Core.ParseException;
import Core.CMM;

public class test {

    /**
     * @param args
     * @throws ParseException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws ParseException, FileNotFoundException {
        String path = "../../test";
        InputStream in = new FileInputStream(path);
        CMM minic = new CMM(in);
        minic.start();

        StringBuilder asm = new StringBuilder();
        Scanner asmReader = new Scanner(new File("asm"));

        while (asmReader.hasNext()) {
            asm.append(asmReader.nextLine() + "\n");
        }

        int indexOfRes = asm.indexOf("{res_size}");
        asm.replace(indexOfRes, indexOfRes + "{res_size}".length(), minic.getCurrentAddr() + "");



        StringBuilder codeSeg = new StringBuilder();

        for (Code c : minic.getPB()) {
            codeSeg.append(c + "\n");
        }

        int indexOfCode = asm.indexOf("{code_segment}");
        asm.replace(indexOfCode, indexOfCode + "{code_segment}".length(), codeSeg.toString());

        System.out.println(asm.toString());


    }

    public int fact(int n) {
        if (n <= 0) {
            return 1;
        }
        return n * fact(n - 1);
    }
}
