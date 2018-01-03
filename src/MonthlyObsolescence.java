import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class MonthlyObsolescence extends Return
{
	private String SO17ReportFileName;		//SO17 report .csv file
	private double returnReserveCap;		//return reserve cap in dollars
	private ArrayList<Part> returnList;		//output data
	
	//default constructor
	public MonthlyObsolescence()
	{
		super();
		super.setType("MR01 - Return Reserve");
		SO17ReportFileName = null;
		returnReserveCap = 0.0;
		returnList = new ArrayList<Part>();
	}
	//accessors
	public String getSO17ReportFileName()
	{
		return SO17ReportFileName;
	}
	public double getReturnReserveCap()
	{
		return returnReserveCap;
	}
	public ArrayList<Part> getReturnList()
	{
		return returnList;
	}
	//mutators
	public void setSO17ReportFileName(String SO17ReportFileName)
	{
		this.SO17ReportFileName = SO17ReportFileName;
	}
	public void setReturnReserveCap(double returnReserveCap)
	{
		this.returnReserveCap = returnReserveCap;
	}
	public void setReturnList(ArrayList<Part> returnList)
	{
		this.returnList = returnList;
	}
	//equals
	public boolean equals(MonthlyObsolescence other)
	{
		return (SO17ReportFileName.equals(other.getSO17ReportFileName())
			 && (returnReserveCap == other.getReturnReserveCap())
			 && returnList.equals(other.getReturnList()));
	}
	//toString
	public String toString()
	{
		return (SO17ReportFileName + " "
			  + returnReserveCap + "\n"
			  + returnList);
	}
	/*	precon:	none
	 * postcon:	user is prompted for value of returnReserveCap, repeats
	 *			prompt until a valid double is entered.
	 */
	public void promptForReturnReserveCap()
	{
		setReturnReserveCap(
			promptForDouble("current return reserve cap", 0.01, 99999));
	}
	/*	precon:	none
	 * postcon:	setSO17ReportFileName method is invoked, which invokes
	 *			inherited fileNamePrompt method to get file name via user
	 *			input. file	data is read into ArrayList returnList. repeats
	 *			if file is inaccessible for any reason.
	 */
	public void readSO17ReportFile()
	{
		boolean repeat;
		Scanner fileReader;
		String line;
		String number = null;
		int classCode = 0;
		String desc = null;
		int qtyOnHand = 0;
		BinLocation binLoc = null;
		int monthsNoSale = 0;
		double cost = 0.0;
		int readCount = 0;
		
		do
		{
			repeat = false;
			//get file name from user
			setSO17ReportFileName(
				fileNamePrompt("SO17 data", ".csv", "SO17.csv"));
			System.out.print("\nReading data from SO17 report...");
			//open file to read
			try
			{
				fileReader = new Scanner(
							 new FileInputStream(SO17ReportFileName));
				//advance scanner past the 1st line to ignore column headers
				fileReader.nextLine();
				//read file one line at a time until each line has been read
				while(fileReader.hasNextLine())
				{
					//store the line as a string
					line = fileReader.nextLine();
					//create a scanner to read the string
					Scanner lineReader = new Scanner(line);
					//change lineReader delimiter to comma to read .csv file
					lineReader.useDelimiter(",");
					//read line one token at a time
					while(lineReader.hasNext())
					{
						number = lineReader.next();
						//class may be blank, parse as double if not
						if(lineReader.hasNext(""))
						{
							classCode = 0;
							lineReader.next();
						}
						else
						{
							classCode = Integer.parseInt(lineReader.next());
						}
						desc = lineReader.next();
						qtyOnHand = Integer.parseInt(lineReader.next());
						binLoc = new BinLocation(lineReader.next());
						try
						{
							monthsNoSale =
								Integer.parseInt(lineReader.next());
						}
						catch(NumberFormatException e)
						{
							//MNS field is blank sometimes, set it to 0
							monthsNoSale = 0;
						}
						cost = Double.parseDouble(lineReader.next());
						//advance scanner past total field
						lineReader.next();
					}
					//store the line's data as a Part object in an ArrayList
					Part p = new Part(number, classCode, desc, qtyOnHand,
						binLoc, monthsNoSale, cost);
					readCount++;
					returnList.add(p);
				}
				//close the scanner & stream
				fileReader.close();
			}
			catch(IOException e)
			{
				System.out.println("\n" + e.getMessage() + "\nTry again.");
				repeat = true;
			}
		}while(repeat);
		System.out.println("done.");
		System.out.println(readCount + " parts read.\n");
	}
	/*	precon:	ArrayList returnList and double returnReserveCap are defined.
	 * postcon:	totalValue of each part in returnList is added to totalValue
	 *			of return as returnList is traversed. when returnReserveCap
	 *			is reached, the index is stored and parts are removed from
	 *			returnList from the end to the index.
	 */
	public void trim()
	{
		//
		System.out.print("Calculating total value of oldest parts...");
		int count = 0;
		int length = returnList.size();
		int deletionCounter = 0;
		setTotalValue(0.0);
		
		//traverse the return list
		for(Part p : returnList)
		{
			//traverse the list
			//keep a running total of the total return value
			setTotalValue(getTotalValue() + p.getTotalValue());
			if(getTotalValue() >= returnReserveCap)
			{
				System.out.print("\nReturn reserve cap ("
					+ returnReserveCap + ") reached. Truncating list...");
				break;
			}
			else
			{
				count++;
			}
		}
		//remove parts from return list
		//start at end and continue until count is reached
		for(int i = length - 1;i > count - 1;i--)
		{
			returnList.remove(i);
			deletionCounter++;
		}
		System.out.println("done.\n");
		System.out.println(deletionCounter
			+ " parts removed from return list.\n");
	}
}