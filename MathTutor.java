/**
 * @author Taylor Smith
 * @version 08/02/2016
 * 
 * Class creates an application to tutor students in math. 
 * The app has 3 levels of difficulty: easy, moderate, and difficult. 
 * The user is able to choose the level on which s(he) wishes to work.
 * The user can choose between addition, subtraction, multiplication and division.
 */

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.Random;

public class MathTutor extends JFrame 
{
	//Labels
	private JLabel header;
	private JLabel labelNumberA;
	private JLabel labelNumberB;
	private JLabel labelAnswer;
	private JLabel firstNumber;
	private JLabel secondNumber;
	private JLabel answer;
	private JLabel equalSign;
	private JLabel operationSymbol;
	private JLabel userAnsLabel;
	//TextField
	private JTextField userAnswer;
	//Buttons
	private JButton checkAnswer;
	private JButton logOff;
	private JButton newProblem;
	private JButton historyButton;
	//Difficulty buttons and labels
	private JLabel selectDifficulty;
	private JRadioButton easy;
	private JRadioButton moderate;
	private JRadioButton difficult;
	//Operation buttons and labels
	private JLabel selectProbType;
	private JRadioButton addition;
	private JRadioButton subtraction;
	private JRadioButton multiplication;
	private JRadioButton division;
	//Height/Width
	private final int W_HEIGHT = 350;
	private final int W_WIDTH = 850;
	//Variables used in calculations
	private int max;
	private int min;
	private double numA;
	private double numB;
	private double ans;
	String op;
	String hist;
	
	public MathTutor()
	{
		super("Math Tutor");
		
		//Sets the height and width of the frame
		setSize(W_WIDTH, W_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creates and sets header for frame
		header = new JLabel("Math Tutor");
		header.setFont(header.getFont().deriveFont (20.0f));
		header.setHorizontalAlignment(JLabel.CENTER) ;
		add(header, BorderLayout.NORTH);
		
		//Sets buttons
		checkAnswer = new JButton ("Check Answer");
		logOff = new JButton("Log Off");
		newProblem = new JButton("New Problem");
		historyButton = new JButton("View History");
		
		//Creates Panel for buttons
		//Add buttons to panel
		Panel buttons = new Panel();
		buttons.add(historyButton);
		buttons.add(checkAnswer);
		buttons.add(logOff);
		
		//Adds ActionListeners to buttons
		checkAnswer.addActionListener(new CheckAnswerListener());
		logOff.addActionListener(new LogOffListener());
		historyButton.addActionListener(new HistoryButtonListener());
		
		//Calls label create methods
		createRadioButton();
		createMathOperation();
		
		//Add buttons to frame
		add(buttons, BorderLayout.SOUTH);
		
		//Displays window
		setVisible(true);	
	}
	
	/**
	 * Creates and adds selection labels to frame. 
	 */
	private void createRadioButton()
	{
		//Panel that contains both boxes for difficulty and operation type
        Panel selectionPanel = new Panel(); 
        
        selectDifficulty = new JLabel("Select Difficulty"); //Label for difficulty
		
        //Difficulty Selection Labels
		easy = new JRadioButton("Easy");
	 	moderate = new JRadioButton("Moderate");
	    difficult = new JRadioButton("Difficult");
	    
	    //Groups difficulty selection radio buttons together
	    ButtonGroup bGroupDiff = new ButtonGroup( ); 
	    bGroupDiff.add(easy);
	 	bGroupDiff.add(moderate);
	 	bGroupDiff.add(difficult);
	 	
	 	//Places difficulty selection radio buttons in a vertical box
	 	Box verticalBoxDiff = Box.createVerticalBox();
	 	verticalBoxDiff.add(selectDifficulty);
	 	verticalBoxDiff.add(easy);
	 	verticalBoxDiff.add(moderate);
	 	verticalBoxDiff.add(difficult);
	   
        selectProbType = new JLabel("Select Problem Type");//Label for operations
		
		//Operation Selection Labels
		addition = new JRadioButton("Addition");
		subtraction = new JRadioButton("Subtraction");
		multiplication = new JRadioButton("Multiplication");
		division = new JRadioButton("Division");

		//Groups operation radio buttons together
		ButtonGroup bGroupType = new ButtonGroup();
		bGroupType.add(addition);
		bGroupType.add(subtraction);
		bGroupType.add(multiplication);
		bGroupType.add(division);
		
		//Places operation selection radio buttons in a vertical box
		Box verticalBoxType = Box.createVerticalBox();
		verticalBoxType.add(selectProbType);
		verticalBoxType.add(addition);
		verticalBoxType.add(subtraction);
		verticalBoxType.add(multiplication);
		verticalBoxType.add(division);
		
		//Combines the two boxes and adds newProblem button 
		Box vertCombined = Box.createVerticalBox();
		vertCombined.add(verticalBoxDiff);
		vertCombined.add(Box.createRigidArea(new Dimension(0,10))); //Creates a space between 2 boxes
		vertCombined.add(verticalBoxType);
		vertCombined.add(Box.createRigidArea(new Dimension(0,10))); //Creates a space between 2 boxes
		vertCombined.add(newProblem);
		
		newProblem.addActionListener(new NewProblemListener()); //Adds listener to newProblem button
		
		selectionPanel.add(vertCombined); //Adds combined vertical box to panel
		add(selectionPanel, BorderLayout.WEST); //Adds pannel to the frame
	}
	
	/**
	 * Creates and adds operation labels to frame. 
	 */
	private void createMathOperation()
	{
		//Sets labels
		hist = "";
		labelNumberA = new JLabel("A");
		labelNumberB = new JLabel("B");
		labelAnswer = new JLabel("Correct Answer");
		firstNumber = new JLabel("(----)");
		secondNumber = new JLabel("(----)");
		answer = new JLabel("(----)");
		equalSign = new JLabel("=");
		operationSymbol = new JLabel("--");
		userAnsLabel = new JLabel("User Input");
		
		//Change font for labels
		labelNumberA.setFont(labelNumberA.getFont().deriveFont (35.0f));
		labelNumberB.setFont(labelNumberB.getFont().deriveFont (35.0f));
		labelAnswer.setFont(labelAnswer.getFont().deriveFont (20.0f));
		firstNumber.setFont(firstNumber.getFont().deriveFont (35.0f));
		secondNumber.setFont(secondNumber.getFont().deriveFont (35.0f));
		answer.setFont(answer.getFont().deriveFont (35.0f));
		equalSign.setFont(equalSign.getFont().deriveFont (45.0f));
		operationSymbol.setFont(operationSymbol.getFont().deriveFont (35.0f));
		userAnsLabel.setFont(userAnsLabel.getFont().deriveFont (20.0f));
		
		//Sets text fields
		userAnswer = new JTextField(8);
		userAnswer.setFont(userAnswer.getFont().deriveFont(25.0f));
		
		//Creates panel for numbers
		Panel numbers = new Panel();
		
		//Creates vertical box for the first number, A, and centers them vertically
		Box vertNumA = Box.createVerticalBox();
		labelNumberA.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
		vertNumA.add(Box.createRigidArea(new Dimension(0,72)));
		vertNumA.add(labelNumberA);
		vertNumA.add(firstNumber);
		
		//Creates vertical box for the second number, B, and centers them vertically
		Box vertNumB = Box.createVerticalBox(); 
		labelNumberB.setAlignmentX(Component.CENTER_ALIGNMENT);
		secondNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
		vertNumB.add(Box.createRigidArea(new Dimension(0,72)));
		vertNumB.add(labelNumberB);
		vertNumB.add(secondNumber);
		
		//Creates vertical box for the operation symbol and label. Centers them vertically
		Box vertNumOp = Box.createVerticalBox();
		operationSymbol.setAlignmentX(Component.CENTER_ALIGNMENT);
		vertNumOp.add(Box.createRigidArea(new Dimension(0,112)));
		vertNumOp.add(operationSymbol);
		
		//Creates vertical box for the correct answer and label. Centers them vertically
		Box vertNumAnswer = Box.createVerticalBox();
		labelAnswer.setAlignmentX(Component.CENTER_ALIGNMENT);
		answer.setAlignmentX(Component.CENTER_ALIGNMENT);
		vertNumAnswer.add(Box.createRigidArea(new Dimension(0,90)));
		vertNumAnswer.add(labelAnswer);
		vertNumAnswer.add(answer);
		
		Box vertEqualSign = Box.createVerticalBox();
		equalSign.setAlignmentX(Component.CENTER_ALIGNMENT);
		vertEqualSign.add(Box.createRigidArea(new Dimension(0,112)));
		vertEqualSign.add(equalSign);
		//Creates vertical box for the users answer and label. Centers them vertically
		Box vertUserInput = Box.createVerticalBox();
		userAnsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userAnswer.setAlignmentX(Component.CENTER_ALIGNMENT);
		vertUserInput.add(Box.createRigidArea(new Dimension(0,90)));
		vertUserInput.add(userAnsLabel);
		vertUserInput.add(userAnswer);
		
		//Adds the vertical boxes to the numbers panel
		numbers.add(vertNumA);
		numbers.add(vertNumOp);
		numbers.add(vertNumB);
		numbers.add(vertEqualSign);
		numbers.add(vertUserInput);
		numbers.add(vertNumAnswer);
		
		//Adds numbers panel to the center border layout
		add(numbers, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * ActionListener for logoff button. Pretty straight forward.
	 *
	 */
	private class LogOffListener implements ActionListener 
	{
      public void actionPerformed(ActionEvent e)
      {
    	  System.exit(0);
      } 
	}
	
	/**
	 * 
	 * ActionListener for check answer button. Gets user input answer and compared to calculated answer.
	 * Sets textbox to green if correct, red if incorrect. Adds to history string.
	 *
	 */
	private class CheckAnswerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Sets user answer to double to be compared.
			double userA = Double.parseDouble(userAnswer.getText());
			//compares user answer to answer calculated in NewProblemListener
			if(userA == ans)
			{
				//Changes label to correct or incorrect.
				userAnsLabel.setText("CORRECT!");
				userAnswer.setBackground(Color.GREEN);
				//Adds to history
				hist += "CORRECT - Problem: " + numA + "" + op + "" + numB + " = " + ans + ". Your answer: " + userA+ "\n";
			}
			else if(userA != ans)
			{
				userAnsLabel.setText("INCORRECT!");
				userAnswer.setBackground(Color.RED);
				hist += "INCORRECT - Problem: " + numA + "" + op + "" + numB + " = " + ans + ". Your answer: " + userA + "\n";
			}
			//Sets answer label to string to be displayed in GUI
			answer.setText(Double.toString(ans));
		}
	}
	
	/**
	 * 
	 * ActionListener for new problem button. When radio buttons are selected and button pressed, 
	 * finds correct selection. Generates random number and performs operation. Sets labels to numbers
	 * generated so they can be displayed in the GUI. Resets user answers and labels to be performed again.
	 *
	 */
	private class NewProblemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Resets labels and answers so action can be performed again
			userAnswer.setBackground(Color.WHITE);
			userAnswer.setText("");
			userAnsLabel.setText("User Input");
			answer.setText("(----)");
			
			Random rand = new Random(); 
            double temp = 0; //Temp to hold variables if they need to be switched for * and / operations
                
                //Finds the selected radio button operation and difficulty. 
            	//Generates random number based on the max and min for the difficulty
            	//Calculates the correct answer
                if (easy.isSelected())
                {
                	if(addition.isSelected())
                	{
                		max = 50;
                		min = 1;
                		numA = rand.nextInt((max - min) + 1) + min;
                		numB = rand.nextInt((max - min) + 1) + min;
                		ans = numA + numB;
                	}
                	else if(subtraction.isSelected())
                	{
                		max = 50;
               		 	min = 1;
               		 	numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	if(numA < numB)
               		 	{
               		 		temp = numB;
               		 		numB = numA;
               		 		numA = temp;
               		 	}
               		 	ans = numA - numB;
               		}
                	else if(multiplication.isSelected())
                	{
                		max = 12;
                		min = 1;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	ans = numA * numB;
             		}
                	else if(division.isSelected())
                	{
                		max = 12;
                		min = 1;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	if(numA < numB)
               		 	{
               		 	    temp = numB;
               		 		numB = numA;
               		 		numA = temp;
               		 	}
               		 	ans = numA / numB;
               		}
                }
                if (moderate.isSelected())
                {
                	if(addition.isSelected())
                	{
                		max = 100;
                		min = 10;
                		numA = rand.nextInt((max - min) + 1) + min;
                		numB = rand.nextInt((max - min) + 1) + min;
                		ans = numA + numB;
                	}
                	else if(subtraction.isSelected())
                	{
                		max = 100;
                		min = 10;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	if(numA < numB)
               		 	{
               		 		temp = numB;
               		 		numB = numA;
               		 		numA = temp;
               		 	}
               		 	ans = numA - numB;
               		}
                	else if(multiplication.isSelected())
                	{
                		max = 50;
                		min = 10;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	ans = numA * numB;
                	}
                	else if(division.isSelected())
                	{
                		max = 50;
                		min = 10;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	if(numA < numB)
               		 	{
               		 		temp = numB;
               		 		numB = numA;
               		 		numA = temp;
               		 	}
               		 	ans = numA / numB;
               		}
                }
                if (difficult.isSelected())
                {
                	if(addition.isSelected())
                	{
                		max = 1000;
                		min = 100;
                		numA = rand.nextInt((max - min) + 1) + min;
                		numB = rand.nextInt((max - min) + 1) + min;
                		ans = numA + numB;
                	}
                	else if(subtraction.isSelected())
                	{
                		max = 1000;
                		min = 100;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	if(numA < numB)
               		 	{
               		 		temp = numB;
               		 		numB = numA;
               		 		numA = temp;
               		 	}
               		 	ans = numA - numB;
               		}
                	else if(multiplication.isSelected())
                	{
                		max = 1000;
                		min = 10;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	ans = numA * numB;
             		}
                	else if(division.isSelected())
                	{
                		max = 1000;
                		min = 10;
                		numA = rand.nextInt((max - min) + 1) + min;
               		 	numB = rand.nextInt((max - min) + 1) + min;
               		 	if(numA < numB)
               		 	{
               		 		temp = numB;
               		 		numB = numA;
               		 		numA = temp;
               		 	}
               		 	ans = numA / numB;
               		}
                }
                
                //Sets operation JLabel and String to the selected operation to be displayed
                if(addition.isSelected())
                {
                	operationSymbol.setText(" + ");
                	op = " + ";
                }
                else if(subtraction.isSelected())
                {
                	operationSymbol.setText(" - ");
                	op = " - ";
                }
                else if(division.isSelected())
                {
                	operationSymbol.setText(" / ");
                	op = " / ";
                }
                else if(multiplication.isSelected())
                {
                	operationSymbol.setText(" * ");
                	op = " * ";
                }
                
                //Sets the JLabels for 1st and 2nd numbers to random num to be displayed
                firstNumber.setText(String.valueOf(numA));
                secondNumber.setText(String.valueOf(numB));
        }
	}
	
	/**
	 * 
	 * ActionListener for historyButton. Creates new frame with scroll pane.
	 *
	 */
	private class HistoryButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Create new frame for history
			JFrame hFrame = new JFrame("History");
			//Set size
			hFrame.setSize(600, 600);
			//Create Text Area
			JTextArea textArea = new JTextArea(5, 15);
			//Create Scroll Pane and add text area
			JScrollPane sP = new JScrollPane(textArea);
			//Update text area with history string
			textArea.append(hist);
			//Set scroll pane prefferences
			sP.setPreferredSize(new Dimension(50, 50));
			sP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			//Add pane to frame and set visible
	        hFrame.add(sP, BorderLayout.CENTER);
			hFrame.setVisible(true);
		}
	}
}
