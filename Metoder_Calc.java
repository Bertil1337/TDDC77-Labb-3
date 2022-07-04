package se.liu.ida.tddc77;

import java.util.Stack;

//import lincalc.LinCalcJohn;

public class Metoder_Calc {
	// Eriks
	// räknar ut postfix
	// Sätt in postfix uttrycket i en stack så sedan så tar den upp
	// de 2 första värdena i när det stöter på en operator
	// Skickar sedan tillbaka reslutatet i stacken och fortsätter
	// returnerar sista värdet omvandlat till en double ur stacken

	public static double calc(String[] lexedPostfix) {
		Stack<Double> kalkstack = new Stack<Double>();
		String operators = "~()*/+-";

		for (String calcStrang : lexedPostfix) {
			if (stringMemberOf(calcStrang, operators)) {
				double temporary = 0;

				switch (calcStrang) {

				case "+":
					temporary = kalkstack.pop();
					kalkstack.push(kalkstack.pop() + temporary); // Poppar upp de två senaste operanderd pushrerar och
																	// pushar.
					break;

				case "-":
					temporary = kalkstack.pop();
					kalkstack.push(kalkstack.pop() - temporary);// Om det är - så subtraherar den från den vänsta
																// operanden med den högra
					break;

				case "*":
					temporary = kalkstack.pop();
					kalkstack.push(kalkstack.pop() * temporary); // Om det är * så gångrar den från den vänsta operanden
																	// med den högra
					break;

				case "/":
					temporary = kalkstack.pop();
					kalkstack.push(kalkstack.pop() / temporary); // poppar upp Dividerar den vänsta operatorn med den
																	// högra pushar sedan till kalkstacken
					break;

				case "~":
					kalkstack.push(kalkstack.pop() * -1); // tar operanden multiplicerat med -1 för att få den negativ
					break;
				}
			} else {
				kalkstack.push(Double.parseDouble(calcStrang)); // Returnerar en stränen som formen av en double, för
																// att kunna hantera decimaler.
			}
		}
		return kalkstack.pop(); // om det finns ett element kvar i stacken så kommer den poppas
	}

	// En memberof fast med String String //Eriks inte för Ali:s, 
	public static boolean stringMemberOf(String strang1, String strang2) {
		for (char Kalkstrang : strang2.toCharArray()) {
			if (Kalkstrang == strang1.charAt(0)) {
				return true;
			}
		}

		return false;

	}

	/* Ali kod
	public static double calculator(String[] lexedPostfix) {
        Stack<Double> operands = new Stack<Double>(); // stack för operander, så när vi får en operand så läggs den in i stacken

        for(String str : lexedPostfix) // foor loop som loopar igenom varje element i lextpostfix arrayen
        
        {
            if (str.trim().equals("")) // en if sats som skiljer mellan tomma element och icke tomma, om inte tom fortsätter den
            {
                continue;
            }

            switch (str) {
                case "+":
                case "-":
                case "*":
                case "/":
               
                	double right = operands.pop(); // poppar våran högra operand
                    double left = operands.pop(); // poppar våran vänstra operand
                    double value = 0;
                    switch(str) {              // switch tittar på elemntet är en operatör, nedan visas 
                        case "+":
                            value = left + right; // om det är +, adderar den från vänster plus höger
                            break;
                        case "-":
                            value = left - right; //om det är -, subtraherar den från vänster minus höger
                            break;
                        case "*":
                            value = left * right; // om det är multiplikation, mutiplicerar den från vänster till höger
                            break;
                        case "/":
                            value = left / right; // om det är division, dividerar den från vänster till höger
                            break;
                        default:
                            break;
                            
                    }
                    operands.push(value);  // pushar value i operands stack
                    break;
                case "~":
                	operands.push(operands.pop() * -1); // om str är tilde så multiplicerar du dett elementet högst uppe i stacken med -1
					break;
                default:
                    operands.push(Double.parseDouble(str)); // om det inte är en opetör så måste det va en operand, då pushas operanden till operand stack
                    break;  
            }
        }
        return operands.pop(); // kommer finnas ett element kvar i stacket som är svaret som sedan returnas
	}

	 */

	// Tar in en StringArray, uppdelat mellan operatorer och operander.
	// Konverterar lexat utttryck till ett postfix uttrck
	// som returernas som en stringArray i postfix form.

	public static String[] toPostfix(String[] inputData) { // våran postfix metod
		String operators = "+-*/()"; // deklarerar våra operatörer
		Stack<String> operatorstack = new Stack<String>(); // gör en stack som ska innehålla operatörer
		Stack<String> slutstack = new Stack<String>(); // stacken som ska returnas i slutet
		for (int i = 0; i < inputData.length; i++) {

			if (inputData[i].equals("~")) {
				operatorstack.add(inputData[i]); // Om det är tilde pusha dirrekt in i opertorstack. då den har högst prio,	
			} else if (inputData[i].equals(")")) {
				String parantes = operatorstack.pop();// om en karaktär är) popa den från stacken och lagra den i memory stringen parantes
				while (!parantes.equals("(")) {
					slutstack.add(parantes);
					parantes = operatorstack.pop(); // else if som poppar operator stacken tills en öppen parantes ses
				}
			} else if (inputData[i].equals("(")) {
				operatorstack.add(inputData[i]); // else if sats som lägger till parantesen

			} else if (memberOf(inputData[i].charAt(0), operators)) { // tittar om våran input data är en operatör

				if (!operatorstack.empty()) { 
					while (!operatorstack.isEmpty() && !prio(inputData[i].charAt(0), operatorstack.peek().charAt(0))) {
						slutstack.add(operatorstack.pop());
					}
					operatorstack.add(inputData[i]);
				} else {
					operatorstack.add(inputData[i]);
				}
			} else {
				slutstack.add(inputData[i]);// pushar ut alla våra operander
				if (operatorstack.size() != 0 && operatorstack.peek().equals("~")) {// Om tilde ligger i opperatorsstacken slutstacken
					slutstack.push(operatorstack.pop());
				}
			}
		}
		slutstack = stakfyllnad(slutstack, operatorstack);
		return slutstack.toArray(new String[0]);
	}

	// Tar in en stack
	// returnerare en string array
	// Vi delar upp lexen i två delar en splitter och en för unärt minus
	public static String[] lex(String expression) {
		String[] splittat = splitter(expression);
		String[] insignal = unary(splittat);
		return insignal;
	}

	// här räknar vi varje operand/operatör som ett element i expression strängen
	// retuernerar ett splitat infix uttryck
	public static String[] splitter(String expression) {
		Stack<String> infix = new Stack<>();
		String operator = "*+-/()";
		String split = "";
		char infixresultat;
		for (int i = 0; i < expression.length(); i++) {// en for loop för att köra igenom strängen
			infixresultat = expression.charAt(i);// loppar igenom varje element i expression

			if (memberOf(infixresultat, operator)) {
				if (split != "") {
					infix.push(split); // Om det är en operator så splittar den och lägger till i stacken
				}
				infix.push(String.valueOf(infixresultat));
				split = "";

			} else if (infixresultat != ' ') {
				split += infixresultat;
			}
		}
		if (split != "") {
			infix.push(split);
		}
		return infix.toArray(new String[0]);
	}

	// Konverterar unärt minus i lexat infix uttryck till "~"
	// vi får tillbaka ett lexat uttryck med unärt minus konverterat
	public static String[] unary(String[] infix) {
		Stack<String> unarystack = new Stack<>();
		String operatorer = "()*/+-";
		boolean tillagd = true;

		// Konverterar alla unära minus i infix expression till tilde
		for (String u : infix) {
			if (u.equals("-") && tillagd) {
				unarystack.push("~");
				tillagd = true;
			} else {

				unarystack.push(u); // Om det är en operator innan minus, så ska den lägga till vanligt minus
				if (operatorer.indexOf(u) != -1) {
					tillagd = true;
				} else {
					tillagd = false;
				}
			}
		}
		return unarystack.toArray(new String[0]);
	}

	// En som kontrollerar om en char finns i strängen
	// får in en sträng och en char
	// om den charen finns med i strängen returnerar true, annars falskt.
	public static boolean memberOf(char symbol, String indata) {
		for (int i = 0; i < indata.length(); i++) {
			if (symbol == indata.charAt(i)) {
				return true;
			}
		}

		return false; // false om det inte stämmer, exempelvis om det icke är en operator
	}

	// Jämför prioriteten av två operatorer
	// Tar in tecken one och two dessa
	// Vi får tillbaka en true, om den första char har högre prio
	// Annars får vi false

	public static boolean prio(char one, char two) {
		String[] operatorer = { "()", "+-", "*/" };

		boolean prioresult = false;

		double prioone = 0;
		double priotwo = 0;
		// Kollar vilken plats i operatorarrayen som operatorerna ligger på, ger detta
		// värde då till prio
		// Som som då jämför två operatörer , returnerarar true om den första char har
		// en högre prio, annars false
		for (int i = 0; i < operatorer.length; i++) {

			if (memberOf(one, operatorer[i])) {
				prioone = i;
			}
			if (memberOf(two, operatorer[i])) {
				priotwo = i;
			}
		}
		// Här jämförs prioriteten bara om det första tecknet har högre prio så
		// returneras true
		if (prioone > priotwo) {
			prioresult = true;
		} else {
			prioresult = false;
		}

		return prioresult;
	}

	// Tömmer en stack i en annan
	// tar in en "källa" stack och ett intag stack som töms

	public static Stack<String> stakfyllnad(Stack<String> kalla, Stack<String> intag) {

		int storlek = intag.size();

		for (int i = 0; i < storlek; i++) {

			kalla.push(intag.pop());
		}

		return kalla;
	}

}
