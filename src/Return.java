import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Calendar;
import java.text.SimpleDateFormat;

abstract class Return
{
	private String type;				//return type
	private String appNum;				//application number, max 7 digits
	private String date;				//today's date, DDMMMYY
	private String returnOutputFileName;//output csv file
	private double totalValue;			//total return value
	
	//constructors
	//default
	public Return()
	{
		type = "RETURN TYPE";
		appNum = "APP #";
		date = today();
		returnOutputFileName = null;
		totalValue = 0.0;
	}
	//full
	public Return(String type, String appNum, String date, 
		String returnOutputFileName, double totalValue)
	{
		this.type = type;
		this.appNum = appNum;
		this.date = date.toUpperCase();
		this.returnOutputFileName = returnOutputFileName;
		this.totalValue = totalValue;
	}
	//accessors
	public String getType()
	{
		return type;
	}
	public String getAppNum()
	{
		return appNum;
	}
	public String getDate()
	{
		return date;
	}
	public String getReturnOutputFileName()
	{
		return returnOutputFileName;
	}
	public double getTotalValue()
	{
		return totalValue;
	}
	//mutators
	public void setType(String type)
	{
		this.type = type;
	}
	public void setAppNum(String appNum)
	{
		this.appNum = appNum;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public void setReturnOutputFileName(String returnOutputFileName)
	{
		this.returnOutputFileName = returnOutputFileName;
	}
	public void setTotalValue(double totalValue)
	{
		this.totalValue = totalValue;
	}
	//equals
	public boolean equals(Return other)
	{
		return (type.equals(other.getType())
		     && appNum.equals(other.getAppNum())
		     && date.equals(other.getDate())
		     && returnOutputFileName.equals(other.getReturnOutputFileName())
		     && totalValue == other.getTotalValue());
	}
	//toString
	public String toString()
	{
		return (type + " "
		      + appNum + " "
		      + date + " "
		      + returnOutputFileName + " "
		      + totalValue);
	}
	/*	precon:	none
	 * postcon:	user is prompted for String input using promptForString
	 *			method. recursive call is made until input is valid, then
	 *			that String is assigned as appNum.
	 */
	public void promptForAppNum()
	{
		String app = promptForString("application number", 1, 7, null);
		if(isValidApp(app))
		{
			setAppNum(app);
		}
		else
		{
			System.out.println("Invalid application number. "
				+ "Must be 1-7 digits in length, numbers only. Try again.");
			promptForAppNum();
		}
	}
	/*	precon:	none
	 * postcon:	a String in format DDMMMYY is returned for today's date.
	 */
	public String today()
	{
		SimpleDateFormat ddMMMyy = new SimpleDateFormat("ddMMMyy");
		Calendar cal = Calendar.getInstance();
		String today = ddMMMyy.format(cal.getTime());
		return today.toUpperCase();
	}
	/*	precon:	String promptText, int minChars, and int maxChars are
	 *			defined and passed into method. String defaultAnswer may be
	 *			defined or null and is passed into method.
	 * postcon:	user is prompted for String input based on promptText,
	 *			minChars, and maxChars. defaultAnswer is optional. prompt
	 *			repeats until user enters valid String, then that String is
	 *			returned.
	 */
	public String promptForString(
		String promptText, int minChars, int maxChars, String defaultAnswer)
	{
		Scanner kb = new Scanner(System.in);
		String input;
		boolean repeat;
		//if default is not null, include default option
		if(defaultAnswer != null)
		{
			do
			{
				repeat = false;
				System.out.print("\nEnter " + promptText
					+ " (if blank, default " + "\"" + defaultAnswer
						+ "\" will be used): ");
				input = kb.nextLine();
				if(input.length() == 0)
				{
					input = defaultAnswer;
				}
				else if(input.length() < minChars)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be at least " + minChars
							+ " characters long. Try again.");
					repeat = true;
				}
				else if(input.length() > maxChars)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be less than " + maxChars
							+ " characters long. Try again.");
					repeat = true;
				}
			}while(repeat);
		}
		//default is null, don't include default option
		else
		{
			do
			{
				repeat = false;
				System.out.print("\nEnter " + promptText + ": ");
				input = kb.nextLine();
				if(input.length() < minChars)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be at least " + minChars
							+ " characters long. Try again.");
					repeat = true;
				}
				else if(input.length() > maxChars)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be less than " + maxChars
							+ " characters long. Try again.");
					repeat = true;
				}
			}while(repeat);
		}
		return input;
	}
	/*	precon:	String promptText, double min, and double max are
	 *			defined and passed into method.
	 * postcon:	user is prompted for double input based on promptText, min,
	 *			and max. prompt repeats until user enters valid double, then
	 *			that value is returned.
	 */
	public double promptForDouble(String promptText, double min, double max)
	{
		Scanner kb = new Scanner(System.in);
		double input = 0.0;
		boolean repeat;
		do
		{
			repeat = false;
			System.out.print("\nEnter " + promptText + ": ");
			if(kb.hasNextDouble())
			{
				input = kb.nextDouble();
			}
			else
			{
				kb.nextLine();
				System.out.println("Invalid " + promptText + ". Try again.");
				repeat = true;
			}
			if(!repeat)
			{
				if(input < min)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be at least " + min + ". Try again.");
					repeat = true;
				}
				else if(input > max)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be less than " + max + ". Try again.");
					repeat = true;
				}
			}
		}while(repeat);
		return input;
	}
	/*	precon:	String promptText, int min, and int max are
	 *			defined and passed into method.
	 * postcon:	user is prompted for int input based on promptText, min, and
	 *			max. prompt repeats until user enters valid int, then that
	 *			value is returned.
	 */
	public int promptForInt(String promptText, int min, int max)
	{
		Scanner kb = new Scanner(System.in);
		int input = 0;
		boolean repeat;
		do
		{
			repeat = false;
			System.out.print("\nEnter " + promptText + ": ");
			if(kb.hasNextInt())
			{
				input = kb.nextInt();
			}
			else
			{
				kb.nextLine();
				System.out.println("Invalid " + promptText + ". Try again.");
				repeat = true;
			}
			if(!repeat)
			{
				if(input < min)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be at least " + min + ". Try again.");
					repeat = true;
				}
				else if(input > max)
				{
					System.out.println("Invalid " + promptText
						+ ". Must be less than " + max + ". Try again.");
					repeat = true;
				}
			}
		}while(repeat);
		return input;
	}
	/*	precon:	String app is defined and passed into method.
	 * postcon:	attempts to parse app as an integer. boolean true is returned
	 *			if it parses, boolean false is returned if it throws a
	 *			NumberFormatException.
	 */
	public boolean isValidApp(String app)
	{
		int appNum;
		try
		{
			appNum = Integer.parseInt(app);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	/*	precon:	String promptText, String extension, and String defaultAnswer
	 *			are defined and	passed into method.
	 * postcon:	user is prompted for String input based on promptText. prompt
	 *			repeats until user enters valid file name for given
	 *			extension, then that String is returned.
	 */	public String fileNamePrompt(
		String promptText, String extension, String defaultAnswer)
	{
		Scanner kb = new Scanner(System.in);
		String input;
		boolean repeat;
		do
		{
			repeat = false;
			System.out.print("\nEnter " + promptText + "\n"
				+ "(if blank, default \"" + defaultAnswer
					+ "\" will be used): ");
			input = kb.nextLine();
			if(input.length() == 0)
			{
				input = defaultAnswer;
			}
			else if(input.length() < 5)
			{
				System.out.println("Invalid file name. Try again.");
				repeat = true;
			}
			else if(!
				(input.substring(
					(input.length() - 4)).equalsIgnoreCase(extension)))
			{
				System.out.println("Invalid file name. File must be of"
					+ " type " + extension + ". Try again.");
				repeat = true;
			}
		}while(repeat);
		return input;
	}
	/*	precon:	ArrayList list is defined and passed into method.
	 * postcon:	each Part in list is displayed on screen in succession along
	 *			with the total number of parts listed and their total value.
	 */
	public void displayList(ArrayList<Part> list)
	{
		System.out.println("\nApp Number: " + getAppNum()
						 + "\nReturn type: " + getType()
						 + "\nDate: " + getDate() + "\n"
						 + "\n  PART-NO. "
						 + " CLASS "
						 + "DESCRIPTION "
						 + "  O.H. "
						 + "  BIN "
						 + "  MNS "
						 + "     COST "
						 + "    TOTAL\n");
		for(Part p : list)
		{
			System.out.println(p);
		}
		calcTotalValue(list);
		System.out.printf(
			"\n%4d lines %46s $%9.2f\n",
				list.size(), "Total return value:", getTotalValue());
	}
	/*	precon:	ArrayList list is defined and passed into method.
	 * postcon:	setReturnOutputFileName method is invoked, which invokes
	 *			fileNamePrompt method to get file name via user input. data
	 *			from list is written to file using toCSVString method of
	 *			Part class. repeats if file is inaccessible for any reason.
	 */
	public void generateReturnOutput(ArrayList<Part> list)
	{
		boolean repeat;
		BufferedWriter fileOut;
		do
		{
			repeat = false;
			//get file name from user
			setReturnOutputFileName(fileNamePrompt("return output", ".csv",
				getType() + " " + getAppNum() + ".csv"));
			try
			{
				fileOut = new BufferedWriter(
						  new PrintWriter(returnOutputFileName));
				System.out.print("\nGenerating return output in "
					+ getReturnOutputFileName() + "...");
				fileOut.write(
					"PART-NO.,CLASS,DESC,O.H.,BIN,MNS,COST,CUR.VAL");
				fileOut.newLine();
				for(Part p : list)
				{
					String s = p.toCSVString();
					fileOut.write(s);
					fileOut.newLine();
				}
				fileOut.close();
			}
			catch(IOException e)
			{
				System.out.println("\n" + e.getMessage() + "\nTry again.");
				repeat = true;
			}
		}while(repeat);
		System.out.print("done.\n");
	}
	/*	precon:	ArrayList list is defined and passed into method.
	 * postcon:	user is prompted for Part attritubes, then a new Part is
	 *			created with them and added to the end of list.
	 */
	public void addPart(ArrayList<Part> list)
	{
		Part p;
		String number = null;
		int classCode = 0;
		String desc = null;
		BinLocation binLoc = null;
		int qtyOnHand = 0;
		int monthsNoRec = 0;
		int monthsNoSale = 0;
		double cost = 0.0;
		number = promptForString("part number", 1, 10, null).toUpperCase();
		classCode = promptForInt("classification code", 0, 99999);
		desc = promptForString("part description", 1, 11, null).toUpperCase();
		binLoc = new BinLocation(promptForString("bin location", 0, 5, null));
		qtyOnHand = promptForInt("quantity on hand", 1, 99999);
		monthsNoSale = 0;//N/A when manually adding part
		cost = promptForDouble("cost per unit", 0.01, 99999);
		p = new Part(number, classCode, desc, qtyOnHand, binLoc,
			monthsNoSale, cost);
		list.add(p);
		System.out.println(p + "\n added to return.");
	}
	/*	precon:	ArrayList list is defined and passed into method.
	 * postcon:	user is prompted for a part number. list is searched for the
	 *			part number. if part number is found, user is prompted for a
	 *			new quantity and the quantity is changed. if part number is
	 *			not found, user is notified and method ends.
	 */
	public void changeQty(ArrayList<Part> list)
	{
		String change = null;
		change = promptForString("part number", 1, 10, null).toUpperCase();
		String partNum = null;
		int changeIndex = -1;
		int changeTo = 0;
		Part part = null;
		for(Part p : list)
		{
			partNum = p.getNumber();
			if(change.equals(partNum))
			{
				changeIndex = list.indexOf(p);
				break;
			}
		}
		if(changeIndex != -1)
		{
			changeTo = promptForInt("new quantity", 1, 99999);
			part = list.get(changeIndex);
			part.setQtyOnHand(changeTo);
			list.set(changeIndex, part);
		}
		else
		{
			System.out.println("Part number " + change
				+ " not found in return.");
		}
	}
	/*	precon:	ArrayList list is defined and passed into method.
	 * postcon:	user is prompted for a part number. list is searched for the
	 *			part number. if part number is found, the index is stored and
	 *			the part is removed by index. if part number is	not found,
	 *			user is notified and method ends.
	 */
	public void removePart(ArrayList<Part> list)
	{
		String remove = null;
		remove = promptForString("part number", 1, 10, null).toUpperCase();
		String partNum = null;
		int removeIndex = -1;
		for(Part p : list)
		{
			partNum = p.getNumber();
			if(remove.equals(partNum))
			{
				removeIndex = list.indexOf(p);
				break;
			}
		}
		if(removeIndex != -1)
		{
			Part r = list.remove(removeIndex);
			System.out.println(r.getNumber() + " removed from list.");
		}
		else
		{
			System.out.println("Part number " + remove
				+ " not found in return.");
		}
	}
	/*	precon:	ArrayList list is defined and passed into method.
	 * postcon:	list is passed into sort method using ByBinByClassByNumber
	 *			Comparator.
	 */
	public void sortByBinClassNumber(ArrayList<Part> list)
	{
		System.out.print("\nSorting by bin, by class, by part...");
		Collections.sort(list, new ByBinByClassByNumber());
		System.out.println("done.\n");
	}
	/*	precon:	ArrayList list is defined.
	 * postcon:	list is passed into sort method using
	 *			ByMNSByBinByClassByNumber Comparator.
	 */
	public void sortByMNSByBinClassNumber(ArrayList<Part> list)
	{
		System.out.print("\nSorting by MNS, by bin, by class, by part...");
		Collections.sort(list, new ByMNSByBinByClassByNumber());
		System.out.println("done.\n");
	}
	/*	precon:	ArrayList list is defined and passed into method.
	 * postcon:	totalValue of return is reset, then returnList is traversed
	 *			and return's totalValue is incremented by each Part's
	 *			totalValue.
	 */
	public void calcTotalValue(ArrayList<Part> list)
	{
		setTotalValue(0.0);
		for(Part p : list)
		{
			setTotalValue(getTotalValue() + p.getTotalValue());
		}
	}
	//abstract method defined by subclasses
	abstract void trim();
}