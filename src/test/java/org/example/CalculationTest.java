package org.example;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculationTest {

    @Test
    public void testcheckingPlacementBrackets() {
        Calculator calc = new Calculator("25+sin(25)+sqrt(20-5+10))+2*2");

        assertTrue(!calc.checkingPlacementBrackets());
    }

    @Test
    public void testVariablesAndFunctions() {
        Calculator calc = new Calculator("25+sin(25)+sqrt(20-5+10)+2*2");
        assertTrue(calc.variablesAndFunctions());
        assertEquals("25+0.42261826174069944+5.0+2*2", calc.getExpression());
    }

    @Test
    public void testRemoveRepetitions() {
        Calculator calc = new Calculator("2*5*3+4/2");
        assertTrue(calc.removeRepetitions());
        assertEquals("32.0", calc.getExpression());
    }

    @Test
    public void testCalculationByPriority() {
        Calculator calc = new Calculator("2+3*4-1");
        assertTrue(calc.calculationByPriority());
        assertEquals("13.0", calc.getExpression());
    }

    @Test
    public void testCalculateExpressionWithBrackers() {
        Calculator calc = new Calculator("(5*(7+1)*(1))*(2+2*8)+(123-8+22+5)");
        assertTrue(calc.calculateExpressionWithBrackers());
        assertEquals("808.0", calc.getExpression());
    }

}
