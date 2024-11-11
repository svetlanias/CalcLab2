package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Scanner;

import static java.lang.Math.pow;

/**
 * класс, осуществляющий вычисление выражения
 */

public class Calculator {
    private String expression;

    /**
     * конструктор
     * @param str само выражение
     */
    public Calculator(String str)
    {
        expression=str;
    }

    /**
     * заменяет выражение на новое
     * @param str новое выражение
     */
    public void setExpression(String str)
    {
        expression=str;
    }

    /**
     * возвращяет выражение
     * @return выражение
     */
    public String getExpression()
    {
        return expression;
    }

    /**
     * метод, работающий с sin,cos,sqrt и переменными: проверка на правильность и вычисление
     * @return false если введеное выражение неправильно true если верно
     */
    public boolean variablesAndFunctions()
    {
        boolean result=true;
        StringBuilder strb= new StringBuilder();

        char q='1';
        String n="";
        for(int pos=0; pos<expression.length()-1;pos++)
        {
            StringBuilder stra= new StringBuilder();
            char ch = expression.charAt(pos);
            if (ch=='s' || ch=='c' && expression.charAt(pos+1)>='a' && expression.charAt(pos+1)<='z')
            {
                String l=expression.substring(pos,pos+3);
                if(l.equals("sin") || l.equals("cos"))
                {
                    pos+=4;
                    ch=expression.charAt(pos);
                    while(ch!=')')
                    {
                        stra.append(ch);
                        pos++;
                        ch=expression.charAt(pos);
                    }
                    Calculator str=new Calculator(stra.toString());
                    if (str.removeRepetitions()) {
                        if (l.equals("sin"))
                            strb.append(Math.sin(Math.toRadians(Double.parseDouble(str.getExpression()))));
                        if (l.equals("cos"))
                            strb.append(Math.cos(Math.toRadians(Double.parseDouble(str.getExpression()))));
                    } else return false;
                }
                else if(expression.substring(pos,pos+4).equals("sqrt"))
                {
                    pos+=5;
                    ch=expression.charAt(pos);
                    while(ch!=')')
                    {
                        stra.append(ch);
                        pos++;
                        ch=expression.charAt(pos);
                    }
                    Calculator str=new Calculator(stra.toString());

                    if(str.removeRepetitions()) strb.append(Math.sqrt(Double.parseDouble(str.getExpression())));
                    else return false;
                }
                else  return false;
            } else if(ch>='a' || ch<='z' && expression.charAt(pos+1)<='a' && expression.charAt(pos+1)>='z'){
                if(ch==q) strb.append(n);
                else {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Введите выражение: ");
                    String line = scanner.nextLine();

                    Calculator str = new Calculator(line);
                    if (str.removeRepetitions()) {
                        strb.append(str.getExpression());
                        q = ch;
                        n = str.getExpression();
                    } else return false;
                }
            }
            else strb.append(ch);
        }
        strb.append(expression.charAt(expression.length()-1));
        expression= strb.toString();
        return result;
    }


    /**
     * части со знаками * / - если они не окружены скобками и вызывает остальные функции, если они нужны для вычисдения
     * @return false если выражение не может быть вычисленно true если вычисления произведены
     */

    public boolean removeRepetitions()
    {
        int branch=0;
        int branchers=0;
        StringBuilder strb= new StringBuilder();
        for(int pos=0; pos<expression.length();pos++)
        {
            char ch = expression.charAt(pos);

            if(ch=='*' || ch=='/' || ch=='-')
            {
                if(expression.charAt(pos-1)>='0' && expression.charAt(pos-1)<='9' &&
                        expression.charAt(pos+1)>='0' && expression.charAt(pos+1)<='9')
                {
                    int k=1;
                    StringBuilder a= new StringBuilder();
                    StringBuilder b= new StringBuilder();
                    int posb=strb.length();
                    while(strb.charAt(posb-k)==46 || strb.charAt(posb-k)>=48 && strb.charAt(posb-k)<=57)
                    {
                        char h=strb.charAt(posb-k);
                        a.append(h);
                        k++;
                        if(0>(posb-k)) break;
                    }
                    a=a.reverse();
                    strb.delete(strb.length()-k+1,strb.length());
                    k=1;
                    while(expression.charAt(pos+k)=='.' || expression.charAt(pos+k)>='0' && expression.charAt(pos+k)<='9')
                    {
                        char h=expression.charAt(pos+k);
                        b.append(h);
                        k++;
                        if(expression.length()<=(pos+k)) break;
                    }
                    pos+=(k-1);
                    if(ch=='*') strb.append(Double.parseDouble(a.toString())*Double.parseDouble(b.toString()));
                    if(ch=='/')
                    {
                        if(b.toString().equals("0") || b.toString().equals("0.0")) return false;
                        else strb.append(Double.parseDouble(a.toString())/Double.parseDouble(b.toString()));
                    }
                    if(ch=='-')
                    {
                        if(Double.parseDouble(a.toString())-Double.parseDouble(b.toString())<0)
                        {
                            strb.append("(");
                            strb.append(Double.parseDouble(a.toString())-Double.parseDouble(b.toString()));
                            strb.append(")");
                            branch++;
                            branchers++;
                        }
                        else strb.append(Double.parseDouble(a.toString())-Double.parseDouble(b.toString()));
                    }
                }
                else  strb.append(ch);
            }
            else if (ch=='(' || ch==')')
            {
                strb.append(ch);
                branch++;
            } else strb.append(ch);
        }

        expression= strb.toString();
        if(branch-branchers>0)
            if(!calculateExpressionWithBrackers()) return false;
            if(!calculationByPriority()) return false;
        return true;
    }


    /**
     * вычисляет выражение согласно приоритету знаков
     * @return false если выражение не может быть посичитано true если оно вычисленнл
     */
    public boolean calculationByPriority()
    {
        ArrayList<String> expr=new ArrayList<>();
        ArrayList<String> vsp=new ArrayList<>();
        for(int pos=0; pos<expression.length();pos++)
        {
            StringBuilder b=new StringBuilder();
            char ch=expression.charAt(pos);

            if(ch=='+' || ch=='-' || ch=='*' || ch=='/')
            {
                b.append(ch);
            }
            else if (ch=='(')
            {
                pos++;
                ch=expression.charAt(pos);
                while (ch=='-' || ch=='.' || ch>='0' && ch<='9')
                {
                    b.append(ch);
                    pos++;
                    ch=expression.charAt(pos);
                }
                pos--;
            }
            else if(ch>='0' && ch<='9')
            {
                while (ch=='.' || ch>='0' && ch<='9')
                {
                    b.append(ch);
                    pos++;
                    if(pos==expression.length())  break;
                    else ch=expression.charAt(pos);
                }
                pos--;
            }
            expr.add(b.toString());
        }

        for(int pos=0; pos<expr.size();pos++)
        {
            if(expr.get(pos).equals("*") || expr.get(pos).equals("/"))
            {
                double a=Double.parseDouble(vsp.get(vsp.size()-1));
                vsp.remove(vsp.size()-1);

                if(expr.get(pos).equals("*")) vsp.add(Double.toString(a*Double.parseDouble(expr.get(pos+1))));
                if(expr.get(pos).equals("/"))
                {
                    if(expr.get(pos+1).equals("0") || expr.get(pos+1).equals("0.0")) return false;
                    else vsp.add(Double.toString(a/Double.parseDouble(expr.get(pos+1))));
                }

                pos++;
            } else vsp.add(expr.get(pos));
        }


        expr=vsp;

        double x=Double.parseDouble(expr.get(0));
        for(int pos=1; pos<expr.size();pos++)
        {
            if(expr.get(pos).equals("-") || expr.get(pos).equals("+"))
            {

                if(expr.get(pos).equals("-")) x-=Double.parseDouble(expr.get(pos+1));
                if(expr.get(pos).equals("+")) x+=Double.parseDouble(expr.get(pos+1));

                pos++;
            }
        }
        expression=Double.toString(x);

        return true;
    }


    /**
     * вычисляет выражение, если в нем присутствуют скобки
     * @return false если выражение не может быть посичитано true если оно вычисленнл
     */
    public boolean calculateExpressionWithBrackers()
    {
        Stack<Double> numbers=new Stack<>();
        Stack<Character> symbols=new Stack<>();
        Stack<Integer> quantity=new Stack<>();

        int k=0;

        for(int pos=0; pos<expression.length();pos++)
        {
            StringBuilder b=new StringBuilder();
            char ch=expression.charAt(pos);

            if(ch=='(')
            {
                quantity.push(0);
                if(expression.charAt(pos+1)=='-') pos++;
            }
            if(ch>='0' && ch<='9')
            {
                if(pos!=0 && expression.charAt(pos-1)=='-' && expression.charAt(pos-2)=='(') b.append('-');
                int iter=0;
                while(expression.charAt(pos+iter)==46 || expression.charAt(pos+iter)>=48 && expression.charAt(pos+iter)<=57)
                {
                    char h=expression.charAt(pos+iter);
                    b.append(h);
                    iter++;
                    if(expression.length()<=(pos+iter)) break;
                }
                pos+=(iter-1);
                numbers.push(Double.parseDouble(b.toString()));
            }
            else if(ch=='+' || ch=='-' || ch=='*' || ch=='/')
            {
                symbols.push(ch);
                if(!quantity.empty()) {
                    k= quantity.peek();
                    k++;
                    quantity.pop();
                    quantity.push(k);
                }

            }
            else if(ch==')')
            {
                k=quantity.pop();
                if(k==0 && numbers.peek()<0)
                {
                    char c = symbols.pop();
                    double val = numbers.pop();

                    if (c == '+') val += numbers.pop();
                    else if (c == '-') val = numbers.pop()-val;
                    else if (c == '*') val *= numbers.pop();
                    else if (c == '/') val /= numbers.pop();
                    numbers.push(val);
                }
                while (k!=0) {
                    char c = symbols.pop();
                    double val = numbers.pop();

                    if (c == '+') val += numbers.pop();
                    else if (c == '-') val = numbers.pop()-val;
                    else if (c == '*') val *= numbers.pop();
                    else if (c == '/')
                    {
                        if(numbers.peek()==0) return false;
                        else val /= numbers.pop();
                    }
                    numbers.push(val);
                    k--;
                }
            }
        }

        if(!symbols.empty())
        {

            StringBuilder b=new StringBuilder();
            ArrayList<String> arr=new ArrayList<>();
            while (!symbols.isEmpty())
            {

                if(numbers.peek()<0)
                {
                    arr.add(")");
                    arr.add(numbers.pop().toString());
                    arr.add("(");
                }
                else arr.add(numbers.pop().toString());
                arr.add(symbols.pop().toString());
            }
            if(numbers.peek()<0)
            {
                arr.add(")");
                arr.add(numbers.pop().toString());
                arr.add("(");
            }else arr.add(numbers.pop().toString());
            Collections.reverse(arr);
            expression=String.join("",arr);

            removeRepetitions();

        } else expression=numbers.pop().toString();
        return true;
    }

}




