package com.github.nijian.jkeel.algorithms.serration.operands;

import com.github.nijian.jkeel.algorithms.Serration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BigDecimalOperandTest {

    private static Logger logger = LoggerFactory.getLogger(BigDecimalOperandTest.class);

    @Test
    public void testBytePlus() {
        byte a = 1;
        byte b = 2;
        BigDecimalOperand operand1 = new BigDecimalOperand(a, 2, false);
        BigDecimalOperand operand2 = new BigDecimalOperand(b, 2, false);

        BigDecimal expectedValue = new BigDecimal("3.00");
        BigDecimal resultValue = operand1.plus(operand2).getValue();
        logger.info(resultValue.toString());
        assertEquals(resultValue.compareTo(expectedValue), 0);
    }

    @Test
    public void testShortPlus() {
        short a = 5;
        short b = 6;
        BigDecimalOperand operand1 = new BigDecimalOperand(a, 3, false);
        BigDecimalOperand operand2 = new BigDecimalOperand(b, 3, false);

        BigDecimal expectedValue = new BigDecimal("11.00");
        BigDecimal resultValue = operand1.plus(operand2).getValue();
        logger.info(resultValue.toString());
        assertEquals(resultValue.compareTo(expectedValue), 0);
    }
}