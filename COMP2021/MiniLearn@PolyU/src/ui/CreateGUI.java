package ui;

import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.System;

public class CreateGUI implements ActionListener{

	System system;
	JPanel container;
	JFrame frame;
	JPanel pWelcome;
	JPanel pTask;
	JPanel pAddAssignment;
	JPanel pAddQuiz;
	JPanel pShowAssignment;
	JPanel pShowQuiz;
	JPanel pDoAssignment;
	JPanel pDoQuiz;
	CardLayout cards;
	ArrayList<String> workNames;
	boolean isTeacher = false;
	ArrayList<String> studentAssignmentAnswer;
	ArrayList<Integer> studentQuizAnswer;
	static int numberOfQuestion = 0;
	
	int workIndex = -1;
	static int questionIndex = 0;
	ArrayList<ArrayList> workStorage;

	/**
	 * This method create the LEARN@PolyU graphical user interface for client
	 */
    public CreateGUI() {
    	frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(1000, 500);
    	
    	system = new System();
    	
    	setPWelcome();
    	setPTask();
    	setPAddAssignment();
    	setPAddQuiz();
    	setPAddAssignment();
    	setPShowAssignment();
    	setPShowQuiz();
    	setPDoAssignment();
    	setPDoQuiz();
    	
    	cards = new CardLayout();
    	container = new JPanel(cards);
    	container.add(pWelcome,"pWelcome");
    	container.add(pTask,"pTask");
    	container.add(pAddAssignment,"pAddAssignment");
    	container.add(pAddQuiz,"pAddQuiz");
    	container.add(pShowAssignment,"pShowAssignment");
    	container.add(pShowQuiz,"pShowQuiz");
    	container.add(pDoAssignment,"pDoAssignment");
    	container.add(pDoQuiz,"pDoQuiz");

    	
    	cards.show(container, "pWelcome");
    	frame.getContentPane().add(container);
    	frame.setVisible(true);
    }
    
    /**
     * This method creates welcome panel, which is part of GUI
     */
    private void setPWelcome(){
    	pWelcome = new JPanel();
    	pWelcome.setLayout(new BorderLayout());
    	JPanel pButton = new JPanel();
    	JPanel pText = new JPanel();
    	
    	JButton logTeacher = new JButton("I am a teacher"); 
    	JButton logStudent = new JButton("I am a student");
    	logTeacher.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					isTeacher = true;
    					system.logInTeacher();
    					cards.show(container, "pTask");
    				}
    			});
    	
    	logStudent.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					isTeacher = false;
    					system.logInStudent();
    					cards.show(container, "pTask");
    				}
    			});
    	
    	
    	JLabel welcomeText = new JLabel("Welcome to LEARN@PolyU     Please choose"
    			+ "your identity");
    	pWelcome.add(new JPanel(),BorderLayout.LINE_END);
    	pWelcome.add(new JPanel(),BorderLayout.LINE_START);
    	pWelcome.add(new JPanel(),BorderLayout.NORTH);

    	pWelcome.add(pText,BorderLayout.CENTER);
    	pText.add(welcomeText, BorderLayout.CENTER);
    	
    	pWelcome.add(pButton, BorderLayout.SOUTH);
    	pButton.add(logTeacher);
    	pButton.add(logStudent);
    }
    
    /**
     * This method creates task panel, which is part of GUI
     */
    private void setPTask(){
    	pTask = new JPanel();
    	pTask.setLayout(new BorderLayout());
    	JPanel pText = new JPanel ();
    	JPanel pButton = new JPanel();
    	
    	JTextField name = new JTextField("If you are a teacher, please enter name of assignment/quiz"
    			+ " here");
    	
    	name.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					name.setText("");
    				}
				});
    	
    	JLabel lIdentity = new JLabel("Please choose your task (add assignment"
    			+ "/quiz for teacher do assignment/quiz for student)");
    	
    	JButton bAssignment = new JButton("Assignment");
    	JButton bQuiz = new JButton("Quiz");
    	bAssignment.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					if(isTeacher){
        					if(name.getText().isEmpty())
        						JOptionPane.showMessageDialog(null, "Please write name.", "Error Message", JOptionPane.ERROR_MESSAGE);
        					else{
    						system.addAssignment(name.getText());
    						cards.show(container, "pAddAssignment");
        					}
    					}
    					else{
        					if(system.getNumberOfAssignment() == 0){
        						JOptionPane.showMessageDialog(null, "No assignment in database currently", "Error Message", JOptionPane.ERROR_MESSAGE);
        						cards.show(container, "pWelcome");
        					}
        					else{
    						setPShowAssignment();
    				    	container.add(pShowAssignment,"pShowAssignment");
    						cards.show(container, "pShowAssignment");
        					}
    					}
    				}
    			});
    	bQuiz.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					if(isTeacher){
        					if(name.getText().isEmpty())
        						JOptionPane.showMessageDialog(null, "Please write name.", "Error Message", JOptionPane.ERROR_MESSAGE);
        					else{
    						system.addQuiz(name.getText());
    						cards.show(container, "pAddQuiz");
        					}
    					}
    					else{
        					if(system.getNumberOfQuiz() == 0){
        						JOptionPane.showMessageDialog(null, "No quiz in database currently", "Error Message", JOptionPane.ERROR_MESSAGE);
        						cards.show(container, "pWelcome");
        					}
        					else{
    						setPShowQuiz();
    				    	container.add(pShowQuiz,"pShowQuiz");
    						cards.show(container, "pShowQuiz");
        					}
    					}
    				}
    			});
    	
    	pTask.add(pText, BorderLayout.CENTER);
    	pText.add(lIdentity, BorderLayout.CENTER);
    	
    	pTask.add(pButton, BorderLayout.SOUTH);
    	pButton.add(name);
    	pButton.add(bAssignment);
        pButton.add(bQuiz);
    }
    
    /**
     * This method creates AddAssignment panel, which is part of GUI
     */
    private void setPAddAssignment(){
    	pAddAssignment = new JPanel();
    	pAddAssignment.setLayout(new BorderLayout());
    	JPanel pButten = new JPanel();
    	JTextField tQuestion  = new JTextField("Wirite the question here");
    	
    	tQuestion.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tQuestion.setText("");
    				}
				});
    	
    	JTextField tMark = new JTextField("Set mark of the question here");
    	
    	tMark.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tMark.setText("");
    				}
				});
    	
    	JButton bAdd = new JButton("Add this question");
    	JButton bFinish = new JButton("Finish");
    	
    	bAdd.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					if(tQuestion.getText().isEmpty())
    						JOptionPane.showMessageDialog(null, "Please write qustion.", "Error Message", JOptionPane.ERROR_MESSAGE);
    					else{
    					try{
    						system.addAssignQuestion(tQuestion.getText(), Integer.parseInt(tMark.getText()));
    						numberOfQuestion++;
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Mark should be an integer.", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input question and mark. Mark should be an integer.", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					}
    				}
    			});
    	
    	bFinish.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
        				if(numberOfQuestion == 0){
    						JOptionPane.showMessageDialog(null, "You must add at least one question. Use left button to add new question.", "Error Message", JOptionPane.ERROR_MESSAGE);
        				}
        				else
    					cards.show(container, "pWelcome");
    				}
    			});    	
    	pAddAssignment.add(pButten, BorderLayout.SOUTH);
    	pAddAssignment.add(tQuestion, BorderLayout.CENTER);
    	pAddAssignment.add(tMark, BorderLayout.NORTH);
    	pButten.add(bAdd);
    	pButten.add(bFinish);
    }
    
    /**
     * This method creates AddQuiz panel, which is part of GUI
     */
    private void setPAddQuiz(){
    	pAddQuiz = new JPanel();
    	pAddQuiz.setLayout(new BorderLayout());
    	
    	JPanel pButten = new JPanel();
    	JPanel pQuestion = new JPanel();
    	pQuestion.setLayout(new BoxLayout(pQuestion, BoxLayout.Y_AXIS));
    	
    	JTextField tQuestion  = new JTextField("Wirite the question here");
    	tQuestion.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tQuestion.setText("");
    				}
				});
    	JTextField tChoice1 = new JTextField("Write choice 1 here");
    	tChoice1.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tChoice1.setText("");
    				}
				});
    	JTextField tChoice2 = new JTextField("Write choice 2 here");
    	tChoice2.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tChoice2.setText("");
    				}
				});
    	JTextField tChoice3 = new JTextField("Write choice 3 here");
    	tChoice3.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tChoice3.setText("");
    				}
				});
    	JTextField tChoice4 = new JTextField("Write choice 4 here");
    	tChoice4.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tChoice4.setText("");
    				}
				});
    	JComboBox cAnswer = new JComboBox<String>();
    	cAnswer.addItem("Please choose the correct answer");
    	cAnswer.addItem("Choice 1");
    	cAnswer.addItem("Choice 2");
    	cAnswer.addItem("Choice 3");
    	cAnswer.addItem("Choice 4");

    	JTextField tMark = new JTextField("Set mark of the question here");
    	tMark.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tMark.setText("");
    				}
				});
    	
    	JButton bAdd = new JButton("Add this question");
    	JButton bFinish = new JButton("Finish");
    	
    	bAdd.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					if(tQuestion.getText().isEmpty()||tChoice1.getText().isEmpty()||tChoice2.getText().isEmpty()
    							||tChoice3.getText().isEmpty()||tChoice4.getText().isEmpty())
    						JOptionPane.showMessageDialog(null, "Please input qustion and all four choices.", "Error Message", JOptionPane.ERROR_MESSAGE);
    					else if (cAnswer.getSelectedIndex() == 0) {
    						JOptionPane.showMessageDialog(null, "Please choose correct answer", "Error Message", JOptionPane.ERROR_MESSAGE);
						}
    					else{
    					try{system.addQuizQuestion(tQuestion.getText(), Integer.parseInt(tMark.getText()), 
    							tChoice1.getText(), tChoice2.getText(), tChoice3.getText()
    							, tChoice4.getText(), cAnswer.getSelectedIndex());
    					cards.show(container, "pAddQuiz");
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Mark should be an integer.", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Mark should be an integer.", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					}
    				}
    			});
    	
    	bFinish.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
        				if(numberOfQuestion == 0){
    						JOptionPane.showMessageDialog(null, "You must add at least one question. Use left button to add new question.", "Error Message", JOptionPane.ERROR_MESSAGE);
        				}
        				else
    					cards.show(container, "pWelcome");
    				}
    			});
    	    	
    	pAddQuiz.add(pButten, BorderLayout.SOUTH);
    	pAddQuiz.add(pQuestion, BorderLayout.CENTER);
    	pAddQuiz.add(tMark, BorderLayout.NORTH);
    	
    	pButten.add(bAdd);
    	pButten.add(bFinish);
    	
    	pQuestion.add(tQuestion);
    	pQuestion.add(tChoice1);
    	pQuestion.add(tChoice2);
    	pQuestion.add(tChoice3);
    	pQuestion.add(tChoice4);
    	pQuestion.add(cAnswer);
    }
    
    /**
     * This method creates ShowAssignment panel, which is part of GUI
     */
    private void setPShowAssignment(){
    	pShowAssignment = new JPanel();
    	pShowAssignment.setLayout(new BorderLayout());
    	JComboBox<String> cSelection = new JComboBox<String>();
    	cSelection.addItem("Click to choose a quiz by its name");
		workNames = system.getAssignmentNames();
    	if(workNames.size() > 0)
    		for(int i = 0; i < workNames.size(); i++)
    			cSelection.addItem("Assignment " + (i + 1) + " : " + workNames.get(i));
    	
    	JButton bContinue = new JButton("Continue");
    	
    	bContinue.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					workIndex = cSelection.getSelectedIndex() - 1;
    					setPDoAssignment();
    					container.add(pDoAssignment, "pDoAssignment");
    					cards.show(container, "pDoAssignment");
    				}
    			});
    	
    	
    	pShowAssignment.add(cSelection, BorderLayout.NORTH);
    	pShowAssignment.add(bContinue, BorderLayout.SOUTH);
    }
    
    /**
     * This method creates ShowQuiz panel, which is part of GUI
     */
    public void setPShowQuiz(){
    	pShowQuiz = new JPanel();
    	pShowQuiz.setLayout(new BorderLayout());
    	JComboBox<String> cSelection = new JComboBox<String>();
    	
    	workNames = system.getQuizNames();
    	cSelection.addItem("Click to choose a quiz by its name");
    	if(workNames.size() > 0)
    		for(int i = 0; i < workNames.size(); i++)
    			cSelection.addItem("Quiz " + (i + 1) + " : " + workNames.get(i));
    	
    	JButton bContinue = new JButton("Continue");
    	bContinue.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					workIndex = cSelection.getSelectedIndex() - 1;
    					setPDoQuiz();
    					container.add(pDoQuiz, "pDoQuiz");
    					cards.show(container, "pDoQuiz"); 				
    				}
    				
    			});
    	
    	
    	pShowQuiz.add(cSelection, BorderLayout.NORTH);
    	pShowQuiz.add(bContinue, BorderLayout.SOUTH);
    }
    
    /**
     * This method creates DoAssignment panel, which is part of GUI
     */
    private void setPDoAssignment(){
    	if(questionIndex == 0)
    		studentAssignmentAnswer = new ArrayList<String>();
    	
    	pDoAssignment = new JPanel();
    	pDoAssignment.setLayout(new BorderLayout());
    	
    	JPanel pButten = new JPanel();
    	JPanel pQuestion = new JPanel();
    	
    	JLabel lQuestion;
    	JLabel lMark;
    	if(workIndex == -1){
    		lQuestion  = new JLabel("Question");
    		lMark = new JLabel("mark");
    	}
    	else{
    		workStorage = system.getAssignmentContent(workIndex);
    		lQuestion = new JLabel((String)workStorage.get(questionIndex).get(0));
    		lMark = new JLabel("Mark of this question:" + String.valueOf(workStorage.get(questionIndex).get(1)));
    		questionIndex++;
    	}
    	
    	JTextField tAnswer = new JTextField("Write your answer here");
    	tAnswer.addMouseListener(
    			new MouseAdapter() {
    				public void mouseClicked(MouseEvent e){
    					tAnswer.setText("");
    				}
				});
    	
    	JButton bNext = new JButton("Next");
    	JButton bFinish = new JButton("Finish");
    	
    	bNext.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){    
    					studentAssignmentAnswer.add(tAnswer.getText());
    					if(questionIndex < workStorage.size()){

    					setPDoAssignment();
    					container.add(pDoAssignment,"pDoAssignment");
    					cards.show(container, "pDoAssignment");
    					}
    					else
    						JOptionPane.showMessageDialog(null, "You hava finished all questions!");
    				}
    			});
    	
    	bFinish.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					system.recordStudentAssignmentAnswer(studentAssignmentAnswer);
    					JOptionPane.showMessageDialog(null, "Your answer have benn recorded."
    							+" Teacher will grade it later.");
    					questionIndex = 0;
    					cards.show(container, "pWelcome");
    				}
    			});
    	
    	pDoAssignment.add(pButten, BorderLayout.SOUTH);
    	pDoAssignment.add(pQuestion, BorderLayout.CENTER);
    	pDoAssignment.add(lMark, BorderLayout.NORTH);
    	
    	pQuestion.add(lQuestion);
    	pQuestion.add(tAnswer);
    	pButten.add(bNext);
    	pButten.add(bFinish);
    }
    
    /**
     * This method creates DoQuiz panel, which is part of GUI
     */
    private void setPDoQuiz(){
       	if(questionIndex == 0)
    		studentQuizAnswer = new ArrayList<Integer>();
    	pDoQuiz = new JPanel();
    	pDoQuiz.setLayout(new BorderLayout());
    	
    	JPanel pButten = new JPanel();
    	JPanel pQuestion = new JPanel();
    	pQuestion.setLayout(new BoxLayout(pQuestion, BoxLayout.Y_AXIS));
    	
    	JLabel lQuestion;
    	JLabel lChoice1;
    	JLabel lChoice2;
    	JLabel lChoice3;
    	JLabel lChoice4;
    	JLabel lMark;
    	
    	final String def[] =
	        { "Choose your answer","1", "2", "3", "4"};    
    	JComboBox<Object> cAnswer = new JComboBox<Object>(def);
    	
    	if(workIndex == -1){
        	lQuestion  = new JLabel("Question");
        	lMark = new JLabel("mark");
        	lChoice1 = new JLabel("1. ");
        	lChoice2 = new JLabel("2. ");
        	lChoice3 = new JLabel("3. ");
        	lChoice4 = new JLabel("4. ");
    	}
    	else{
    		workStorage = system.getQuizContent(workIndex);
    		lQuestion = new JLabel((String)workStorage.get(questionIndex).get(0));
    		lMark = new JLabel("Mark of this question:" + String.valueOf(workStorage.get(questionIndex).get(1)));
    		lChoice1 = new JLabel((String)workStorage.get(questionIndex).get(2));
    		lChoice2 = new JLabel((String)workStorage.get(questionIndex).get(3));
    		lChoice3 = new JLabel((String)workStorage.get(questionIndex).get(4));
    		lChoice4 = new JLabel((String)workStorage.get(questionIndex).get(5));
    		questionIndex++;
    	}
    	
    	
    	
    	JButton bNext = new JButton("Next");
    	JButton bFinish = new JButton("Finish");
    	
    	bNext.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					if(cAnswer.getSelectedIndex() == 0)
    						JOptionPane.showMessageDialog(null, "Please choose your answer.");
    					else{
    					studentQuizAnswer.add(cAnswer.getSelectedIndex() + 1);
    					if(questionIndex < workStorage.size()){
    					setPDoQuiz();
    					container.add(pDoQuiz,"pDoQuiz");
    					cards.show(container, "pDoQuiz");
    					}
    					else
    						JOptionPane.showMessageDialog(null, "You hava finished all questions!");
    					}
    				}
    			});
    	
    	bFinish.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					system.selectOnlineWork(0);
    					int mark = system.recordStudentQuizResult(studentQuizAnswer);
    					JOptionPane.showMessageDialog(null, "Your grade is " +
    					 mark);
    					cards.show(container, "pWelcome");
    				}
    			});
    	
    	pDoQuiz.add(pButten, BorderLayout.SOUTH);
    	pDoQuiz.add(pQuestion, BorderLayout.CENTER);
    	pDoQuiz.add(lMark, BorderLayout.NORTH);
    	
    	pQuestion.add(lQuestion);
    	pQuestion.add(lChoice1);
    	pQuestion.add(lChoice2);
    	pQuestion.add(lChoice3);
    	pQuestion.add(lChoice4);
    	
    	pQuestion.add(cAnswer);
    	pButten.add(bNext);
    	pButten.add(bFinish);
    }

    /**
     * This method override the actionPerfoemed method required by ActionListener interface
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
