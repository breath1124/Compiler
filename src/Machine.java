import cubyType.*;
import exception.ImcompatibleTypeError;
import exception.OperatorError;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Machine {
    
    CubyStringType str = new CubyStringType();

    public static void main(String[] args) throws FileNotFoundException, IOException, OperatorError, ImcompatibleTypeError {
        if (args.length == 0)
            System.out.println("Usage: java Machine <programfile> <arg1> ...\n");
        else {
            execute(args, false);
        }

//        args = new String[1];
//        args[0] = "D:\\Yuby\\Cuby\\testing\\ex(float).out";
////        args[1] = "Hello";
////        args[2] = "1.2";
////        args[3] = "100";
////        args[4] = "123Hel";
//        execute(args, false);
    }

    final static int
        CSTI = 0, ADD = 1, SUB = 2, MUL = 3, DIV = 4, MOD = 5, 
        EQ = 6, LT = 7, NOT = 8, 
        DUP = 9, SWAP = 10, 
        LDI = 11, STI = 12, 
        GETBP = 13, GETSP = 14, INCSP = 15, 
        GOTO = 16, IFZERO = 17, IFNZRO = 18, CALL = 19, TCALL = 20, RET = 21, 
        PRINTI = 22, PRINTC = 23, 
        LDARGS = 24,
        STOP = 25,
        CSTF = 26, CSTC = 27, CSTS = 28;
  
  
    final static int STACKSIZE = 1000;

    static void execute(String[] args, boolean trace) throws FileNotFoundException, IOException, OperatorError, ImcompatibleTypeError {
        ArrayList<Integer> program = readfile(args[0]);

        CubyBaseType[] stack = new CubyBaseType[STACKSIZE];

        CubyBaseType[] inputArgs = new CubyBaseType[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            if(Pattern.compile("(?i)[a-z]").matcher(args[i]).find()){
                char[] input = args[i].toCharArray();
                CubyCharType[] array = new CubyCharType[input.length];
                for(int j = 0; j < input.length; ++j) {
                    array[j] = new CubyCharType(input[j]);
                }
                inputArgs[i-1] = new CubyArrayType(array);
            }
            else if(args[i].contains(".")){
                inputArgs[i-1] = new CubyFloatType(new Float(args[i]).floatValue());
            }
            else {
                inputArgs[i-1] = new CubyIntType(new Integer(args[i]).intValue());
            }
        }


//        for(int i = 0; i < inputArgs.length; ++i){
//            if(inputArgs[i] instanceof CubyArrayType){
//                CubyBaseType[] a = ((CubyArrayType)inputArgs[i]).getValue();
//                for(int j = 0; j < a.length; ++j){
//                    if(a[j] instanceof CubyCharType){
//                        System.out.print(((CubyCharType)a[j]).getValue());
//                    }
//                    else if(a[j] instanceof CubyIntType){
//                        System.out.print(((CubyIntType)a[j]).getValue());
//                    }
//                    else if(a[j] instanceof CubyFloatType){
//                        System.out.print(((CubyFloatType)a[j]).getValue());
//                    }
//                }
//                System.out.println();
//            }
//            else if(inputArgs[i] instanceof CubyCharType){
//                System.out.println(((CubyCharType)inputArgs[i]).getValue());
//            }
//            else if(inputArgs[i] instanceof CubyIntType){
//                System.out.println(((CubyIntType)inputArgs[i]).getValue());
//            }
//            else if(inputArgs[i] instanceof CubyFloatType){
//                System.out.println(((CubyFloatType)inputArgs[i]).getValue());
//            }
//
//        }

        long startTime = System.currentTimeMillis();
        execCode(program, stack, inputArgs, trace);
        long runtime = System.currentTimeMillis() - startTime;
        System.err.println("\nRan " + runtime/1000.0 + " seconds");
    }


    private static int execCode(ArrayList<Integer> program, CubyBaseType[] stack, CubyBaseType[] inputArgs, boolean trace) throws ImcompatibleTypeError, OperatorError {
        int bp = -999;
        int sp = -1;
        int pc = 0;
        int hr = -1;
        for (;;) {
            if (trace)
                printSpPc(stack, bp, sp, program, pc);
            switch (program.get(pc++)) {
                case CSTI:
                    stack[sp + 1] = new CubyIntType(program.get(pc++)); sp++; break;
                case CSTF:
                    stack[sp + 1] = new CubyFloatType(Float.intBitsToFloat(program.get(pc++))); sp++; break;
                case CSTC:
                    stack[sp + 1] = new CubyCharType((char)(program.get(pc++).intValue())); sp++; break;
                case CSTS:
                    stack[sp + 1] = new CubyStringType((String.valueOf(program.get(pc++).intValue()))); sp++; break;
                case ADD: {
                    stack[sp - 1] = binaryOperator(stack[sp-1], stack[sp], "+");
                    sp--;
                    break;
                }
                case SUB:{
                    stack[sp - 1] = binaryOperator(stack[sp-1], stack[sp], "-");
                    sp--;
                    break;
                }

                case MUL: {
                    stack[sp - 1] = binaryOperator(stack[sp-1], stack[sp], "*");
                    sp--;
                    break;
                }
                case DIV:
                    if(((CubyIntType)stack[sp]).getValue()==0)
                    {
                        System.out.println("hr:"+hr+" exception:"+1);
                        while (hr != -1 && ((CubyIntType)stack[hr]).getValue() != 1 )
                        {
                            hr = ((CubyIntType)stack[hr+2]).getValue();
                            System.out.println("hr:"+hr+" exception:"+new CubyIntType(program.get(pc)).getValue());
                        }
                            
                        if (hr != -1) { 
                            sp = hr-1;    
                            pc = ((CubyIntType)stack[hr+1]).getValue();
                            hr = ((CubyIntType)stack[hr+2]).getValue();    
                        } else {
                            System.out.print(hr+"not find exception");
                            return sp;
                        }
                    }
                    else{
                        stack[sp - 1] = binaryOperator(stack[sp-1], stack[sp], "/");
                        sp--; 
                    }
                    
                    break;
                case MOD:
                    stack[sp - 1] = binaryOperator(stack[sp-1], stack[sp], "%");
                    sp--;
                    break;
                case EQ:
                    stack[sp - 1] = binaryOperator(stack[sp-1], stack[sp], "==");
                    sp--;
                    break;
                case LT:
                    stack[sp - 1] = binaryOperator(stack[sp-1], stack[sp], "<");
                    sp--;
                    break;
                case NOT: {
                    Object result = null;
                    if(stack[sp] instanceof CubyFloatType){
                        result = ((CubyFloatType)stack[sp]).getValue();
                    }else if (stack[sp] instanceof CubyIntType){
                        result = ((CubyIntType)stack[sp]).getValue();
                    }
                    stack[sp] = (Float.compare(new Float(result.toString()), 0.0f) == 0 ? new CubyIntType(1) : new CubyIntType(0));
                    break;
                }
                case DUP:
                    stack[sp+1] = stack[sp];
                    sp++;
                    break;
                case SWAP: {
                    CubyBaseType tmp = stack[sp];  stack[sp] = stack[sp-1];  stack[sp-1] = tmp;
                    break;
                }
                case LDI:
                    stack[sp] = stack[((CubyIntType)stack[sp]).getValue()]; break;
                case STI:
                    stack[((CubyIntType)stack[sp-1]).getValue()] = stack[sp]; stack[sp-1] = stack[sp]; sp--; break;
                case GETBP:
                    stack[sp+1] = new CubyIntType(bp); sp++; break;
                case GETSP:
                    stack[sp+1] = new CubyIntType(sp); sp++; break;
                case INCSP:
                    sp = sp + program.get(pc++); break;
                case GOTO:
                    pc = program.get(pc); break;
                case IFZERO: {
                    Object result = null;
                    int index = sp--;
                    if(stack[index] instanceof CubyIntType){
                        result = ((CubyIntType)stack[index]).getValue();
                    }else if(stack[index] instanceof CubyFloatType){
                        result = ((CubyFloatType)stack[index]).getValue();
                    }
                    pc = (Float.compare(new Float(result.toString()), 0.0f) == 0 ? program.get(pc) : pc + 1);
                    break;
                }
                case IFNZRO: {
                    Object result = null;
                    int index = sp--;
                    if (stack[index] instanceof CubyIntType) {
                        result = ((CubyIntType) stack[index]).getValue();
                    } else if (stack[index] instanceof CubyFloatType) {
                        result = ((CubyFloatType) stack[index]).getValue();
                    }
                    pc = (Float.compare(new Float(result.toString()), 0.0f) != 0 ? program.get(pc) : pc + 1);
                    break;
                }
                case CALL: {
                    int argc = program.get(pc++);
                    for (int i=0; i<argc; i++)
                        stack[sp-i+2] = stack[sp-i];
                    stack[sp-argc+1] = new CubyIntType(pc+1); sp++;
                    stack[sp-argc+1] = new CubyIntType(bp);   sp++;
                    bp = sp+1-argc;
                    pc = program.get(pc);
                    break;
                }
                case TCALL: {
                    int argc = program.get(pc++);
                    int pop  = program.get(pc++);
                    for (int i=argc-1; i>=0; i--)
                        stack[sp-i-pop] = stack[sp-i];
                    sp = sp - pop; pc = program.get(pc);
                } break;
                case RET: {
                    CubyBaseType res = stack[sp];
                    sp = sp - program.get(pc); bp = ((CubyIntType)stack[--sp]).getValue(); pc = ((CubyIntType)stack[--sp]).getValue();
                    stack[sp] = res;
                } break;
                case PRINTI: {
                    Object result;
                    if(stack[sp] instanceof CubyIntType){
                        result = ((CubyIntType)stack[sp]).getValue();
                    }else if(stack[sp] instanceof CubyFloatType){
                        result = ((CubyFloatType)stack[sp]).getValue();
                    }else if(stack[sp] instanceof CubyStringType){
                        result = ((CubyStringType)stack[sp]).getValue();
                    }else {
                        result = ((CubyCharType)stack[sp]).getValue();
                    }

                    System.out.print(String.valueOf(result) + " ");
                    break;
                }
                case PRINTC:
                    System.out.print((((CubyCharType)stack[sp])).getValue()); break;
                case LDARGS:
                    for (int i=0; i < inputArgs.length; i++) // Push commandline arguments
                        stack[++sp] = inputArgs[i];
                    break;
                case STOP:
                    return sp;
            
                default:
                    throw new RuntimeException("Illegal instruction " + program.get(pc-1)
                            + " at address " + (pc-1));

            }


        }


    }

    public static CubyBaseType binaryOperator(CubyBaseType lhs, CubyBaseType rhs, String operator) throws ImcompatibleTypeError, OperatorError {
        Object left;
        Object right;
        int flag = 0;
        if (lhs instanceof CubyFloatType) {
            left = ((CubyFloatType) lhs).getValue();
            flag = 1;
        } else if (lhs instanceof CubyIntType) {
            left = ((CubyIntType) lhs).getValue();
        } else {
            throw new ImcompatibleTypeError("ImcompatibleTypeError: Left type is not int or float");
        }

        if (rhs instanceof CubyFloatType) {
            right = ((CubyFloatType) rhs).getValue();
            flag = 1;
        } else if (rhs instanceof CubyIntType) {
            right = ((CubyIntType) rhs).getValue();
        } else {
            throw new ImcompatibleTypeError("ImcompatibleTypeError: Right type is not int or float");
        }
        CubyBaseType result = null;

        switch(operator){
            case "+":{
                if (flag == 1) {
                    result =  new CubyFloatType(Float.parseFloat(String.valueOf(left)) + Float.parseFloat(String.valueOf(right)));
                } else {
                    result = new CubyIntType(Integer.parseInt(String.valueOf(left)) + Integer.parseInt(String.valueOf(right)));
                }
                break;
            }
            case "-":{
                if (flag == 1) {
                    result = new CubyFloatType(Float.parseFloat(String.valueOf(left)) - Float.parseFloat(String.valueOf(right)));
                } else {
                    result = new CubyIntType(Integer.parseInt(String.valueOf(left)) - Integer.parseInt(String.valueOf(right)));
                }
                break;
            }
            case "*":{
                if (flag == 1) {
                    result = new CubyFloatType(Float.parseFloat(String.valueOf(left)) * Float.parseFloat(String.valueOf(right)));
                } else {
                    result = new CubyIntType(Integer.parseInt(String.valueOf(left)) * Integer.parseInt(String.valueOf(right)));
                }
                break;
            }
            case "/":{
                if(Float.compare(Float.parseFloat(String.valueOf(right)), 0.0f) == 0){
                    throw new OperatorError("OpeatorError: Divisor can't not be zero");
                }
                if (flag == 1) {
                    result = new CubyFloatType(Float.parseFloat(String.valueOf(left)) / Float.parseFloat(String.valueOf(right)));
                } else {
                    result = new CubyIntType(Integer.parseInt(String.valueOf(left)) / Integer.parseInt(String.valueOf(right)));
                }
                break;
            }
            case "%":{
                if (flag == 1) {
                    throw new OperatorError("OpeatorError: Float can't mod");
                } else {
                    result = new CubyIntType(Integer.parseInt(String.valueOf(left)) % Integer.parseInt(String.valueOf(right)));
                }
                break;
            }
            case "==":{
                if (flag == 1) {
                    if((float) left == (float) right){
                        result = new CubyIntType(1);
                    }
                    else{
                        result = new CubyIntType(0);
                    }
                } else {
                    if((int) left == (int) right){
                        result = new CubyIntType(1);
                    }
                    else{
                        result = new CubyIntType(0);
                    }
                }
                break;
            }
            case "<":{
                if (flag == 1) {
                    if((float) left < (float) right){
                        result = new CubyIntType(1);
                    }
                    else{
                        result = new CubyIntType(0);
                    }
                } else {
                    if((int) left < (int) right){
                        result = new CubyIntType(1);
                    }
                    else{
                        result = new CubyIntType(0);
                    }
                }
                break;
            }
        }
        return result;
    }


    private static String insName(ArrayList<Integer> program, int pc) {
        switch (program.get(pc)) {
            case CSTI:   return "CSTI " + program.get(pc+1);
            case CSTF:   return "CSTF " + program.get(pc+1);
            case CSTC:   return "CSTC " + (char)(program.get(pc+1).intValue());
            case CSTS:   return "CSTS " + (String.valueOf(program.get(pc+1).intValue()));
            case ADD:    return "ADD";
            case SUB:    return "SUB";
            case MUL:    return "MUL";
            case DIV:    return "DIV";
            case MOD:    return "MOD";
            case EQ:     return "EQ";
            case LT:     return "LT";
            case NOT:    return "NOT";
            case DUP:    return "DUP";
            case SWAP:   return "SWAP";
            case LDI:    return "LDI";
            case STI:    return "STI";
            case GETBP:  return "GETBP";
            case GETSP:  return "GETSP";
            case INCSP:  return "INCSP " + program.get(pc+1);
            case GOTO:   return "GOTO " + program.get(pc+1);
            case IFZERO: return "IFZERO " + program.get(pc+1);
            case IFNZRO: return "IFNZRO " + program.get(pc+1);
            case CALL:   return "CALL " + program.get(pc+1) + " " + program.get(pc+2);
            case TCALL:  return "TCALL " + program.get(pc+1) + " " + program.get(pc+2) + " " +program.get(pc+3);
            case RET:    return "RET " + program.get(pc+1);
            case PRINTI: return "PRINTI";
            case PRINTC: return "PRINTC";
            case LDARGS: return "LDARGS";
            case STOP:   return "STOP";
            default:     return "<unknown>";
        }
    }


    private static void printSpPc(CubyBaseType[] stack, int bp, int sp, ArrayList<Integer> program, int pc) {
        System.out.print("[ ");
        for (int i = 0; i <= sp; i++) {
            Object result = null;
            if(stack[i] instanceof CubyIntType){
                result = ((CubyIntType)stack[i]).getValue();
            }else if(stack[i] instanceof CubyFloatType){
                result = ((CubyFloatType)stack[i]).getValue();
            }else if(stack[i] instanceof CubyCharType){
                result = ((CubyCharType)stack[i]).getValue();
            }
            System.out.print(String.valueOf(result) + " ");
        }
        System.out.print("]");
        System.out.println("{" + pc + ": " + insName(program, pc) + "}");
    }


    private static ArrayList<Integer> readfile(String filename) throws FileNotFoundException, IOException {
        ArrayList<Integer> program = new ArrayList<Integer>();
        Reader inp = new FileReader(filename);

        StreamTokenizer tStream = new StreamTokenizer(inp);
        tStream.parseNumbers();
        tStream.nextToken();
        while (tStream.ttype == StreamTokenizer.TT_NUMBER) {
            program.add(new Integer((int)tStream.nval));
            tStream.nextToken();
        }

        inp.close();

        return program;
    }
}


class Machinetrace {
    public static void main(String[] args)
            throws FileNotFoundException, IOException, OperatorError, ImcompatibleTypeError {
        if (args.length == 0)
            System.out.println("Usage: java Machinetrace <programfile> <arg1> ...\n");
        else
            Machine.execute(args, true);
    }
}
