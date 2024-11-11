package org.example;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        //Calculator str=new Calculator("1*5+3.1*5.3*2*(2+2*8)+(123+22+5)+1*1");//+
        //Calculator str=new Calculator("(5*(7+1)*(1))*(2+2*8)+(123-8+22+5)"); //+
        //Calculator str=new Calculator("1+3*5*6*(123+22-23+5)*2+1"); //+
        //Calculator str=new Calculator("1+3+(-55)-6-(123-22+5)"); //+
        //Calculator str=new Calculator("(2+3)*4"); //+
        //Calculator str=new Calculator("25+sin(25)"); //+
        //Calculator str=new Calculator("33+x+x"); //+
        //str.calculateExpressionWithBrackers();
        //str.removeRepetitions();
        //str.variablesAndFunctions();
        //System.out.println(str.removeRepetitions());
        //System.out.println(str.getExpression());


        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String line = scanner.nextLine();
        Calculator str = new Calculator(line);

        if(!str.variablesAndFunctions()) System.out.println("выражение собержит ошибки либо в промежуточных вычислениях есть деление на 0");
        else if(!str.removeRepetitions()) System.out.println("выражение собержит деление на 0");
        else System.out.println("вычисленное выражение= "+ str.getExpression());


    }
}
