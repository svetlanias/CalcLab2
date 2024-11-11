package org.example;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        //Calculator str=new Calculator("1+3*5*6*(123+22-23+5)*2+1"); //+
        //Calculator str=new Calculator("1+3+(-55)-6-(123-22+5)"); //+
        //Calculator str=new Calculator("(2+3)*4"); //+
        //Calculator str=new Calculator("25+sin(25)+sqrt(20-5+10)+2*2"); //+
        //Calculator str=new Calculator("33+x+x"); //+
        //Calculator str=new Calculator("(33)+(x+x)");
        //str.calculateExpressionWithBrackers();
        //str.removeRepetitions();
        //str.variablesAndFunctions();
        //System.out.println(str.removeRepetitions());
        //System.out.println(str.getExpression());

        //System.out.println(str.checkingPlacementBrackets());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String line = scanner.nextLine();
        Calculator str = new Calculator(line);

        if(!str.checkingPlacementBrackets()) System.out.println("не верно расставлены скобки");
        else if(!str.variablesAndFunctions()) System.out.println("выражение собержит ошибки либо в промежуточных вычислениях есть деление на 0");
        else if(!str.removeRepetitions()) System.out.println("выражение собержит деление на 0");
        else System.out.println("вычисленное выражение= "+ str.getExpression());


    }
}
