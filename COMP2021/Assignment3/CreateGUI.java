import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class CreateGUI{

	// This list is to store all the added books
    private ArrayList<Books> st = new ArrayList<Books>();
    private static SetOfActions setOfActions = new SetOfActions();
	// These two strings may be useful for you to display messages on the screen.
    private String s1 = "The total number of all books is: ";
    private String s2 = "The total value of all books is: ";
	// These are the initial states of the Non-Fiction and Fiction Checkbox
	private boolean nfict = false;
	private boolean fict = false;
	int tvalue;
	int tnumber;
	
	
	
    public CreateGUI() {
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(800, 300);
    	
    	JPanel panel = SetPanel(frame);    	
    	
    	frame.getContentPane().add(panel);
    	frame.setVisible(true);
    }
    
    private JPanel SetPanel(JFrame frame){
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(7,4));
    	
    	frame.getContentPane().add(panel);

    	JLabel lBookName = new JLabel("Book Name");
    	JLabel lPrice = new JLabel("Price");
    	JLabel lQuantity = new JLabel("Quantity");
    	JLabel lTotalValue = new JLabel(s2 + tvalue);
    	JLabel lTotalNumber = new JLabel(s1 + tnumber);
    	
    	JTextField fBookName = new JTextField();
    	JTextField fPrice = new JTextField();
    	JTextField fQuantity = new JTextField();
   	
    	JButton bBio = new JButton(	"Biographies");
    	JButton bHis = new JButton("Histories");
    	JButton bHor = new JButton(	"Horror");
    	JButton bJour = new JButton("Journals");
    	JButton bMys = new JButton(	"Mystery");
    	JButton bRoma = new JButton("Romance");
    	JButton bScien = new JButton("Science Fiction");
    	JButton bTrav = new JButton("Travel");
    	
    	bBio.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity = 0;
    					Double price = 0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					setOfActions.addBiographies(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	bJour.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity =0;
    					Double price =0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					setOfActions.addJournals(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	bMys.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity =0;
    					Double price =0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					setOfActions.addMystery(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	bRoma.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity =0;
    					Double price =0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					setOfActions.addRomance(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	bScien.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity =0;
    					Double price =0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					setOfActions.addScienceFiction(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	bTrav.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity =0;
    					Double price =0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					} 
    					setOfActions.addTravel(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	bHis.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity =0;
    					Double price =0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					setOfActions.addHistory(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	bHor.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					Integer quantity =0;
    					Double price =0.0;
    					try{
    						price = Double.parseDouble(fPrice.getText());
    						quantity = Integer.parseInt(fQuantity.getText());
        					if(fBookName.getText().equals("")){
        						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
        						quantity = 0;
        						price = 0.0;
        					}
    					}catch(NullPointerException ex){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}catch(NumberFormatException ex2){
    						JOptionPane.showMessageDialog(null, "Please make sure you have input name, price and quantity the price and quantity must be a number", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					setOfActions.addHorror(st, fBookName.getText(), price, quantity);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    					lTotalNumber.setText(s1 + st.size());
    					clearField(fBookName, fPrice, fQuantity);
    				}
    			});
    	
    	JCheckBox cFiction = new JCheckBox("Fiction");
    	JCheckBox cNonFiction = new JCheckBox("Non-Fiction");

    	JButton bIncrease = new JButton("Increase Pirce");
    	JButton bDecrease = new JButton("Decrease Price");
    	
    	bIncrease.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					nfict = cNonFiction.isSelected();
    					fict = cFiction.isSelected();
    					SetOfActions.increaseTotalPrice(st, nfict, fict);
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    				}
    			});
    	
    	bDecrease.addActionListener(
    			new ActionListener(){
    				public void actionPerformed(ActionEvent e){
    					nfict = cNonFiction.isSelected();
    					fict = cFiction.isSelected();
    					Double tp = setOfActions.decreaseTotalPrice(st, nfict, fict);
    					if(tp == -1){
    						JOptionPane.showMessageDialog(null, "The price of books can not be lower than 0", "Error Message", JOptionPane.ERROR_MESSAGE);
    					}
    					lTotalValue.setText(s2 + setOfActions.getTotalPrice(st));
    				}
    			});
    	    	
    	bIncrease.setBackground(Color.green);
    	bDecrease.setBackground(Color.red);

    	panel.add(lBookName);
    	panel.add(fBookName);
    	
    	panel.add(new JLabel());
    	panel.add(new JLabel());
    	panel.add(lPrice);
    	panel.add(fPrice);
    	panel.add(lTotalValue);	
    	panel.add(new JLabel());
    	panel.add(lQuantity);
    	panel.add(fQuantity);
    	panel.add(lTotalNumber);   
    	panel.add(new JLabel());

    	
    	panel.add(bBio);
    	panel.add(bHis);
    	panel.add(bTrav);
    	panel.add(bJour);
    	panel.add(bScien);	
    	panel.add(bMys);
    	panel.add(bHor);
    	panel.add(bRoma);
    	
    	panel.add(cNonFiction);
    	panel.add(cFiction);
    	panel.add(new JLabel());
    	panel.add(new JLabel());
    	panel.add(bIncrease);
    	panel.add(bDecrease);
    	panel.add(new JLabel());
    	panel.add(new JLabel());
    	
    	return panel;
    }
        
    private void clearField(JTextField a, JTextField b, JTextField c){
		a.setText("");
		b.setText("");
		c.setText("");
    }
    
}
