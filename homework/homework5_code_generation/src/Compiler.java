import frontend.SourceFileLexer;
import frontend.lexer.TokenLexer;
import frontend.parser.CompUnit;
import frontend.parser.CompUnitParser;
import middle.error.ErrorTable;
import middle.llvmir.IrBuilder;
import middle.llvmir.IrModule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Compiler {
    private static int choose = 3; // 3 -> llvm_ir

    public static void main(String[] args) {
        String inputFileName = "testfile.txt"; // 注意文件路径的书写，是以相对项目而言的
        InputStream inputFileStream = null;
        try {
            inputFileStream = new FileInputStream(inputFileName);
        } catch (FileNotFoundException e) {
            System.err.println("Can not open " + inputFileName);
        }
        SourceFileLexer sourceFileLexer = new SourceFileLexer(inputFileStream);
        TokenLexer tokenLexer = new TokenLexer(sourceFileLexer);
        CompUnitParser compUnitParser = new CompUnitParser(tokenLexer.getTokenList());
        CompUnit compUnit = compUnitParser.parseCompUnit();
        IrBuilder irBuilder = new IrBuilder(compUnit);
        IrModule irModule = irBuilder.genIrModule();
        //String outputFileName = "output.txt";
        String errorFileName = "error.txt";
        String llvmFileName = "llvm_ir.txt";
        if (choose == 2) {
            try {
                // OutputStream outputStream = new FileOutputStream(outputFileName);
                OutputStream outputStream = new FileOutputStream(errorFileName);
                try {
                    outputStream.write(ErrorTable.output().getBytes());
                    //outputStream.write(compUnit.syntaxOutput().getBytes());
                } catch (IOException e) {
                    //System.err.println("Can not write " + outputFileName);
                    System.err.println("Can not write " + errorFileName);
                }
            } catch (FileNotFoundException e) {
                // System.err.println("Can not open " + outputFileName);
                System.err.println("Can not open " + errorFileName);
            }
        } else if (choose == 3) {
            ArrayList<String> irs = irModule.irOutput();
            /*String ans = "declare i32 @getint()\n" +
                    "declare i32 @getarray(i32*)\n" +
                    "declare i32 @getch()\n" +
                    "declare void @putint(i32)\n" +
                    "declare void @putch(i32)\n" +
                    "declare void @putarray(i32,i32*)";*/
            String ans = "";
            for (String s : irs) {
                ans = ans + s;
            }
            try {
                OutputStream outputStream = new FileOutputStream(llvmFileName);
                try {
                    outputStream.write(ans.getBytes());
                } catch (IOException e) {
                    System.err.println("Can not write " + llvmFileName);
                }
            } catch (FileNotFoundException e) {
                System.err.println("Can not open " + llvmFileName);
            }
        }
        //System.out.println(tokenLexer.getTokenList());
    }
}
