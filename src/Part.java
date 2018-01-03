import java.lang.Comparable;
import java.text.DecimalFormat;

public class Part implements Comparable<Part>
{
	//instance variables
	private String number;		//part number
	private int classCode;		//classification code
	private String desc;		//part description, trimmed to 11 chars
	private BinLocation binLoc;	//physical bin location, trimmed to 5 chars
	private int qtyOnHand;		//quantity on hand
	private int monthsNoSale;	//number of months without sale
	private double cost;		//part cost per unit
	
	//constructors
	//default
	public Part()
	{
		number = "PART-NO.";
		classCode = 0;
		desc = "DESCRIPTION";
		qtyOnHand = 0;
		binLoc = new BinLocation();
		monthsNoSale = 0;
		cost = 0.0;
	}
	//full
	public Part(String number, int classCode, String desc, int qtyOnHand,
		BinLocation binLoc,	int monthsNoSale, double cost)
	{
		this.number = number.toUpperCase();
		this.classCode = classCode;
		if(desc.length() <= 11)
		{
			this.desc = desc;
		}
		else
		{
			this.desc = desc.substring(0, 11);
		}
		this.qtyOnHand = qtyOnHand;
		this.binLoc = binLoc;
		this.monthsNoSale = monthsNoSale;
		this.cost = cost;
	}
	//copy
	public Part(Part other)
	{
		this.number = other.number.toUpperCase();
		this.classCode = other.classCode;
		if(other.desc.length() <= 11)
		{
			this.desc = other.desc;
		}
		else
		{
			this.desc = other.desc.substring(0, 11);
		}
		this.qtyOnHand = other.qtyOnHand;
		this.binLoc = other.binLoc;
		this.monthsNoSale = other.monthsNoSale;
		this.cost = other.cost;
	}
	//accessors
	public String getNumber()
	{
		return number;
	}
	public int getClassCode()
	{
		return classCode;
	}
	public String getDesc()
	{
		return desc;
	}
	public int getQtyOnHand()
	{
		return qtyOnHand;
	}
	public BinLocation getBinLoc()
	{
		return binLoc;
	}
	public int getMonthsNoSale()
	{
		return monthsNoSale;
	}
	public double getCost()
	{
		return cost;
	}
	//mutators
	public void setNumber(String number)
	{
		this.number = number.toUpperCase();
	}
	public void setClassCode(int classCode)
	{
		this.classCode = classCode;
	}
	public void setDesc(String desc)
	{
		if(desc.length() <= 11)
		{
			this.desc = desc;
		}
		else
		{
			this.desc = desc.substring(0, 11);
		}
	}
	public void setQtyOnHand(int qtyOnHand)
	{
		this.qtyOnHand = qtyOnHand;
	}
	public void setBinLoc(BinLocation binLoc)
	{
		this.binLoc = binLoc;
	}
	public void setMonthsNoSale(int monthsNoSale)
	{
		this.monthsNoSale = monthsNoSale;
	}
	public void setCost(double cost)
	{
		this.cost = cost;
	}
	//equals
	public boolean equals(Part other)
	{
		return ((number.equals(other.getNumber()))
		     && (classCode == other.getClassCode())
		     && (desc.equals(other.getDesc()))
		     && (qtyOnHand == other.getQtyOnHand())
		     && (binLoc.equals(other.getBinLoc()))
		     && (monthsNoSale == other.getMonthsNoSale())
		     && (cost == other.getCost()));
	}
	//toString
	public String toString()
	{
		DecimalFormat formatter = new DecimalFormat("#0000");
		String formatted = 
			String.format("%10s %6s %-11s %6d %5s %5d %9.2f %9.2f",
				number, formatter.format(classCode), desc, qtyOnHand,
					binLoc, monthsNoSale, cost, getTotalValue());
		return (formatted);
	}
	//toString with commas for .csv file output.
	public String toCSVString()
	{
		DecimalFormat formatter = new DecimalFormat("#0000");
		return (number + ","
		      + formatter.format(classCode) + ","
		      + desc + ","
		      + qtyOnHand + ","
		      + binLoc + ","
		      + monthsNoSale + ","
		      + cost + ","
		      + getTotalValue());
	}
	/*	precon:	cost and qtyOnHand are defined.
	 * postcon:	returns value of cost * qtyOnHand as a double.
	 */
	public double getTotalValue()
	{
		return (cost * qtyOnHand);
	}
	//compareTo
	public int compareTo(Part other)
	{
		if(this.getNumber().compareTo(other.getNumber()) < 0)
		{
			//this number comes before other number
			return -1;
		}
		else if(this.getNumber().compareTo(other.getNumber()) > 0)
		{
			//this number comes after other number
			return 1;
		}
		else
		{
			//this number equals other number
			return 0;
		}
	}
}