package BoxOffice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BoxOffice extends JFrame
{
	private JTextArea tSumtxt,tDatatxt,innerPaneltxt;
	private JPanel topP;;
	private JComboBox<String> rbox,lbox;
	private JButton reciptbt,ticketSumbt;
	private JLabel eveninglb,matineelb;
	private Ticket [] array;
	private int begIndex,endIndex,houseCapacity;
	private String currentTransaction;
	private Map<String,Double> m = new HashMap<String,Double>();
	
	
	
	
	public BoxOffice(int houseCapacity)
	{
		super("Joshua Wright Theatre Box Office");
		setSize(700, 600); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridLayout(3, 1, 10, 10));
		this.houseCapacity = houseCapacity;
		
		m.put("Adult",12.50);
		m.put("Child",8.00);
		m.put("Senior",10.00);
		
		begIndex = 0;
		endIndex = 0;
		currentTransaction = "";
		array = new Ticket [houseCapacity];
		
		buildTopP();
		
		buildOther();
		
		
		
		this.setVisible(true);
	}
	
	private void buildTopP()
	{
		topP = new JPanel();
	
		topP.setBorder(BorderFactory.
				createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3), "Ticket Transaction"));
		String [] type = {"Adult","Child","Senior"};
		eveninglb = new JLabel("Eevening");
		matineelb = new JLabel("Matinee");
		
		rbox = new JComboBox<String>(type);
		lbox = new JComboBox<String>(type);
		lbox.addActionListener(new TicketPurchaseListener());
		rbox.addActionListener(new TicketPurchaseListener());
		
		reciptbt = new JButton("Recepit");
		reciptbt.addActionListener(new ReceiptListener());
		ticketSumbt = new JButton("Ticket Summary");
		ticketSumbt.addActionListener(new SummaryListener());
		innerPaneltxt = new JTextArea();
		innerPaneltxt.setPreferredSize(new Dimension(200, 150));
		
		innerPaneltxt.setBorder(BorderFactory.
				createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Transaction"));
		
		topP.add(matineelb);
		topP.add(lbox);
		topP.add(eveninglb);
		topP.add(rbox);
		topP.add(innerPaneltxt);
		topP.add(reciptbt);
		topP.add(ticketSumbt);
		
		this.add(topP);	
	}
	
	private void buildOther()
	{
		
		tSumtxt = new JTextArea();
		tDatatxt = new JTextArea();

		tSumtxt.setBorder(BorderFactory.
				createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3), "Ticket Recepit"));
	
		tDatatxt.setBorder(BorderFactory.
				createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3), "Ticket Data Summary"));
		
		
		this.add(tSumtxt);
		this.add(tDatatxt);
		
	}
	
	
	private  class TicketPurchaseListener implements ActionListener
	{
		private String selection;;
		@Override
		public void actionPerformed(ActionEvent e)
		{	
			array[endIndex] = new Ticket();
			
			if(e.getSource() == lbox)
			{
				selection = (String)lbox.getSelectedItem();
				for (Map.Entry<String, Double> entry : m.entrySet()) 
			    {      	
			        if(selection.equals(entry.getKey()))
			        {
			        	array[endIndex].setType(selection);
			        	array[endIndex].setPrice((double)entry.getValue() - (entry.getValue() / 100) * 20);
			        	array[endIndex].setEvening(false);
			        	break;
			        }
			    } 
			}
			else
			{
				selection = (String)rbox.getSelectedItem();
				for (Map.Entry<String, Double> entry : m.entrySet()) 
			    {   
			        if(selection.equals(entry.getKey()))
			        {
			        	array[endIndex].setType(selection);
			        	array[endIndex].setPrice((double)entry.getValue());
			        	array[endIndex].setEvening(true);
			        	break;	
			        }
			    }
			}
			currentTransaction += array[endIndex].toString()+"\n";
			innerPaneltxt.setText(currentTransaction);
			endIndex++;
		}
		
		
	}
	
	
	private class ReceiptListener implements ActionListener
	{
		private double totalPriceM,totalPriceE;
		private int totalM,totalE;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			innerPaneltxt.setText("");
			currentTransaction = "";
			
			for(int i = begIndex; i < endIndex;i++)
			{
				if(array[i].isEvening() == true)
				{
					totalE++;
					totalPriceE += array[i].getPrice();
				}
				else
				{
					totalM++;
					totalPriceM += array[i].getPrice();
				}
			}
			String s = String.format("%d tickets for Matinee show $%.2f\n",totalM,totalPriceM);
			s += String.format("%d tickets for Evening show $%.2f\n",totalE,totalPriceE);
			s += String.format("Ttoal $%.2f",+totalPriceE+totalPriceM);
			totalM = 0;
			totalE = 0;
			totalPriceE = 0;
			totalPriceM = 0;
			tSumtxt.setText(s);
			begIndex = endIndex;
		}
		
	}
	
	
	private class SummaryListener implements ActionListener
	{
		private double totalPriceM,totalPriceE;
		private int totalM,totalE;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			for(int i = 0; i < endIndex;i++)
			{
				if(array[i].isEvening() == true)
				{
					totalE++;
					totalPriceE += array[i].getPrice();
				}
				else
				{
					totalM++;
					totalPriceM += array[i].getPrice();
				}
				String s = "";
				s = String.format("%-55s %d\n","Number of Matinee tickets sold:",totalM);
				s += String.format("%-51s $%.2f\n","Total Revenue for Matinee tickets sold:",totalPriceM);
				s += String.format("%-55s %d\n","Number of Evening tickets sold:",totalE);
				s += String.format("%-51s $%.2f\n","Total Revenue for Evening tickets sold:",totalPriceE);
				s += String.format("%-59s %d\n","Total Number of tickets sold:",totalE+totalM);
				s += String.format("%-69s $%.2f\n","Total Revenue:",totalPriceE+totalPriceM);
				s += String.format("%-53s %d%%\n","Perentage of House Capacity filled:"
						,(int) ( ( (totalE+totalM) / (double)houseCapacity ) * 100) );
				tDatatxt.setText(s);
				System.out.print(s);
			}
		}
		
	}
	
		
	public static void main (String [] args)
	{
	//Create an instance of the frame, passing it the following house capacity value
	BoxOffice frame = new BoxOffice(10);
	}//End of main method
	
	
}
