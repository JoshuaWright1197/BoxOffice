package BoxOffice;

public class Ticket
{
	private String type;
	private double price;
	private boolean evening;
	
	Ticket()
	{
		type = "";
		price = 0;
		evening = false;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public boolean isEvening()
	{
		return evening;
	}

	public void setEvening(boolean evening)
	{
		this.evening = evening;
	}
	
	public String toString()
	{
		String show;
		if(evening == true)
			show = " Evneing Show ";
		else
			show = " Matinee Show ";
			
		return String.format("%s%s$%.2f","1 "+type,show,price);
		
	}
	
	
}
