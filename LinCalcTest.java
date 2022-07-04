package se.liu.ida.tddc77;

import static org.junit.Assert.assertEquals;
import java.util.regex.Pattern;
import org.junit.Test;
import lincalc.LinCalcJohn;


public class LinCalcTest {

	private static final double TOLERANCE = 0.0001;
	private static final boolean USE_TILDE_FOR_UNARY_MINUS = false;

	@Test
	public void testNormal() {
		runTestcase("1+1", 2);
		runTestcase("~1", -1);
		runTestcase("0.5", 0.5);
		runTestcase("1+2-3*4/5", 0.6);
		runTestcase("~(3.5-3.5)", 0);
		runTestcase("3+~2*4", -5);
		runTestcase("2*3*(2+2)", 24.0);
		runTestcase("12000", 12000);
		runTestcase("3+4", 7);
		runTestcase("0+0", 0);
		runTestcase("1+10+100+1000", 1111);
		runTestcase("34+1.55", 35.55);
		runTestcase("1.3+3.7", 5.0);

		runTestcase("4-3", 1);
		runTestcase("3-4", -1);
		runTestcase("10-10", 0);
		runTestcase("1.0-0.94", 0.06);
		runTestcase("13.5-16.6", -3.1);

		runTestcase("3*4", 12);
		runTestcase("13*37", 481);
		runTestcase("0.27*0.60", 0.162);
		runTestcase("~3*5", -15);
		runTestcase("~10*~10", 100);

		runTestcase("1/1", 1);
		runTestcase("45/9", 5);
		runTestcase("42/12", 3.5);
		runTestcase("4.6/1.6", 2.875);
		runTestcase("~7/3.5", -2.0);
		runTestcase("2+(~2)", 0);

		runTestcase("(3+4)", 7);
		runTestcase("(133+337)", 470);
		runTestcase("(19+91)+(91+19)", 220);
		runTestcase("0*(133-34)", 0);
		runTestcase("10/((5*2)+10)+4", 4.5);
		runTestcase("11+(22-33*1+11)/99-8*3.0", -13);

		// From the course material
		runTestcase("(((123.123)))", 123.123);
		runTestcase("2.0/2.0*5", 5.0);
		runTestcase("5*~(6/2)", -15);
		runTestcase("1*2/3*4", 2.666666666667);
		runTestcase("~1*7/~2/7*2", 1);
		runTestcase("(1+2)*(3-1)", 6.0);
		runTestcase("2.0/2.0*5", 5.0);
		runTestcase("1-2*3+4", -1.0);
	}

	private void runTestcase(String test, double ans){
		if (!USE_TILDE_FOR_UNARY_MINUS){
			test = Pattern.compile("~").matcher(test).replaceAll("-");
		}
		System.out.println("Testing \"" + test + "\"...");
		assertEquals("\""+test+"\"", LinCalc.evaluate( test ), ans, TOLERANCE);
	}

}
