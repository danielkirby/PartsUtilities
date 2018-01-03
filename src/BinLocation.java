import java.lang.Comparable;

public class BinLocation implements Comparable<BinLocation>
{
	//instance variable
	private String binLoc;//max length of 5, uppercase only
	
	//constructors
	//default
	public BinLocation()
	{
		binLoc = "BIN LOC";
	}
	//full
	public BinLocation(String binLoc)
	{
		if(binLoc.length() <= 5)
		{
			this.binLoc = binLoc.toUpperCase().trim();
		}
		else
		{
			this.binLoc = binLoc.toUpperCase().substring(0, 5).trim();
		}
	}
	//copy
	public BinLocation(BinLocation other)
	{
		if(other.getBinLoc().length() <= 5)
		{
			this.binLoc = other.getBinLoc().toUpperCase().trim();
		}
		else
		{
			this.binLoc =
				other.getBinLoc().toUpperCase().substring(0, 5).trim();
		}
	}
	//accessor
	public String getBinLoc()
	{
		return binLoc;
	}
	//mutator
	public void setBinLoc(String binLoc)
	{
		if(binLoc.length() <= 5)
		{
			this.binLoc = binLoc.toUpperCase().trim();
		}
		else
		{
			this.binLoc = binLoc.toUpperCase().substring(0, 5).trim();
		}
	}
	//toString
	public String toString()
	{
		return binLoc;
	}
	//equals
	public boolean equals(BinLocation other)
	{
		return this.getBinLoc().equals(other.getBinLoc());
	}
	//compareTo
	public int compareTo(BinLocation other)
	{
		Integer thisLength = new Integer(this.getBinLoc().length());
		Integer otherLength = new Integer(other.getBinLoc().length());
		
		//if either bin is blank, compare lengths
		if((thisLength == 0) || (otherLength == 0))
		{
			return thisLength.compareTo(otherLength);
		}
		//if each bin is all digits, compare as Integers
		else if(isAllDigits(this.getBinLoc())
			 && isAllDigits(other.getBinLoc()))
		{
			Integer thisInt = new Integer(this.getBinLoc());
			Integer otherInt = new Integer(other.getBinLoc());
			return thisInt.compareTo(otherInt);
		}
		//if either is all non-digits, compare as Strings
		else if(isAllNonDigits(this.getBinLoc())
			|| (isAllNonDigits(other.getBinLoc())))
		{
			return this.getBinLoc().compareTo(other.getBinLoc());
		}
		//if last char of either is a letter
		else if((!(isLastCharDigit(this.getBinLoc())))
			 || (!(isLastCharDigit(other.getBinLoc())))) 
		{
			//if both last chars are letters, compare as Strings
			if((!(isLastCharDigit(this.getBinLoc())))
			 && (!(isLastCharDigit(other.getBinLoc()))))
			{
				return this.getBinLoc().compareTo(other.getBinLoc());
			}
			//else isolate Integer portions and compare as Integers
			else
			{
				Integer thisInt;
				Integer otherInt;
				//this last char is a letter
				//create Integer that trims letter off
				if(!(isLastCharDigit(this.getBinLoc())))
				{
					thisInt = new Integer(
						this.getBinLoc().substring(0, thisLength - 1));
				}
				//this last char is not a letter, treat as an Integer
				else
				{
					thisInt = new Integer(this.getBinLoc());
				}
				//other last char is a letter
				//create Integer that trims letter off
				if(!(isLastCharDigit(other.getBinLoc())))
				{
					otherInt = new Integer(
						other.getBinLoc().substring(0, otherLength - 1));
				}
				//other last char is not a letter, treat as an Integer
				else
				{
					otherInt = new Integer(other.getBinLoc());
				}
				return thisInt.compareTo(otherInt);
			}
		}
		//else, at least one bin has a letter in it, compare as Strings
		else
		{
			return this.getBinLoc().compareTo(other.getBinLoc());
		}
	}
	//return true if last char of string is a digit
	public boolean isLastCharDigit(String s)
	{
		return isDigit(s.charAt(s.length() - 1));
	}
	//return true if all chars of string are non-digits
	public boolean isAllNonDigits(String s)
	{
		int n = s.length();
		for(int i = 0;i < n;i++)
		{
			if(isDigit(s.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	//return true if all chars of string are digits
	public boolean isAllDigits(String s)
	{
		int n = s.length();
		for(int i = 0;i < n;i++)
		{
			if(!(isDigit(s.charAt(i))))
			{
				return false;
			}
		}
		return true;
	}
	//return true if char is a digit
	public boolean isDigit(char c)
	{
		return ((c == '0')
			 || (c == '1')
			 || (c == '2')
			 || (c == '3')
			 || (c == '4')
			 || (c == '5')
			 || (c == '6')
			 || (c == '7')
			 || (c == '8')
			 || (c == '9'));
	}
}