import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.NumberFormatException;

public class MonthlyCSO extends Return
{
	//instance variables
	private String RIMSimulationFileName;			//RIM simulation csv file
	private String MNRMNSReportFileName;			//MNRMNR report csv file
	private ArrayList<String> dataFromRIMSimulation;//RIM simulation data
	private ArrayList<Part> returnList;				//output data
	
	//default constructor
	public MonthlyCSO()
	{
		super();
		super.setType("MR11 - 9-11MNR 9+MNS CSO");
		RIMSimulationFileName = null;
		MNRMNSReportFileName = null;
		dataFromRIMSimulation = new ArrayList<String>();
		returnList = new ArrayList<Part>();
	}
	//accessors
	public String getRIMSimulationFileName()
	{
		return RIMSimulationFileName;
	}
	public String getMNRMNSReportFileName()
	{
		return MNRMNSReportFileName;
	}
	public ArrayList<String> getDataFromRIMSimulation()
	{
		return dataFromRIMSimulation;
	}
	public ArrayList<Part> getReturnList()
	{
		return returnList;
	}
	//mutators
	public void setRIMSimulationFileName(String RIMSimulationFileName)
	{
		this.RIMSimulationFileName = RIMSimulationFileName;
	}
	public void setMNRMNSReportFileName(String MNRMNSReportFileName)
	{
		this.MNRMNSReportFileName = MNRMNSReportFileName;
	}
	public void setDataFromRIMSimulation(
		ArrayList<String> dataFromRIMSimulation)
	{
		this.dataFromRIMSimulation = dataFromRIMSimulation;
	}
	public void setReturnList(ArrayList<Part> returnList)
	{
		this.returnList = returnList;
	}
	//equals
	public boolean equals(MonthlyCSO other)
	{
		return (RIMSimulationFileName.equals(other.getRIMSimulationFileName())
			 && MNRMNSReportFileName.equals(other.getMNRMNSReportFileName())
			 && dataFromRIMSimulation.equals(other.getDataFromRIMSimulation())
			 && returnList.equals(other.getReturnList()));
	}
	//toString
	public String toString()
	{
		return (RIMSimulationFileName + " "
			  + MNRMNSReportFileName + "\n"
			  + dataFromRIMSimulation + "\n"
			  + returnList);
	}
	/*	precon:	none
	 * postcon:	setRIMSimulationFileName method is invoked, which invokes
	 *			inherited fileNamePrompt method to get file name via user
	 *			input. file	data is read into ArrayList 
	 *			dataFromRIMSimulation. repeats if file is inaccessible for
	 *			any reason.
	 */
	public void readRIMSimulationFile()
	{
		boolean repeat;
		Scanner fileReader;
		int lineCount = 0;
		int readCount = 0;
		
		do
		{
			repeat = false;
			//get file name from user
			setRIMSimulationFileName(
				fileNamePrompt("RIM simulation data", ".csv", "RIM.csv"));
			System.out.print("\nReading data from "
				+ getRIMSimulationFileName() + "...");
			//open file to read
			try
			{
				fileReader = new Scanner(
							 new FileInputStream(RIMSimulationFileName));
				//count the lines in file
				while(fileReader.hasNextLine())
				{
					lineCount++;
					fileReader.nextLine();
				}
				//close the scanner & stream
				fileReader.close();
			}
			catch(IOException e)
			{	
				System.out.println("\n" + e.getMessage() + "\nTry again.");
			}
			//open file to read again
			try
			{
				fileReader = new Scanner(
							 new FileInputStream(RIMSimulationFileName));
				//advance scanner past the first 16 lines of header info
				for(int i = 0;i < 16;i++)
				{
					fileReader.nextLine();
				}
				//set scanner to delimit by commas
				fileReader.useDelimiter(",");
				//read data from the file one line at a time
				//part data ends before the last 8 lines of file
				while(readCount < (lineCount - 24))
				{
					String s = fileReader.next();
					//add first token of each line to list of parts
					dataFromRIMSimulation.add(s);
					//advance the scanner to the next line
					fileReader.nextLine();
					readCount++;
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
	/*	precon:	none
	 * postcon:	setMNRMNSReportFileName method is invoked, which invokes
	 *			inherited fileNamePrompt method to get file name via user
	 *			input. file data is read into ArrayList returnList. repeats
	 *			if file is inaccessible for any reason.
	 */
	public void readMNRMNSReportFile()
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
			setMNRMNSReportFileName(
				fileNamePrompt("MNR/MNS data", ".csv", "MNRMNS.csv"));
			System.out.print("\nReading data from "
				+ getMNRMNSReportFileName() + "...");
			//open file to read
			try
			{
				fileReader = new Scanner(
							 new FileInputStream(MNRMNSReportFileName));
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
						binLoc,	monthsNoSale, cost);
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
	/*	precon:	ArrayLists dataFromRIMSimulation and returnList are defined.
	 * postcon:	Strings from dataFromRIMSimulation are compared to partNum
	 *			Strings from returnList. matches are removed from returnList.
	 */
	public void trim()
	{
		System.out.print("Removing parts that match RIM simulation from "
			+ "return list...");
		String partNum = null;
		int deletionIndex = -1;
		boolean deleteFlag =  false;
		int deletionCounter = 0;
		
		//traverse the RIM sim arraylist
		for(String s : dataFromRIMSimulation)
		{
			//for each part string from RIM simulation, traverse return list
			for(Part p : returnList)
			{
				//pull the part number string out of each Part object
				partNum = p.getNumber();
				//compare part number strings
				//if match is found, mark for deletion and end search
				if(s.equals(partNum))
				{
					deletionIndex = returnList.indexOf(p);
					deleteFlag = true;
					break;
				}
			}
			//if index of part was recorded for deletion,
			//remove that part from return list
			if(deleteFlag)
			{
				returnList.remove(deletionIndex);
				deletionCounter++;
				//reset deletion flag
				deleteFlag = false;
			}
		}
		System.out.println("done.\n");
		System.out.println(deletionCounter
			+ " parts removed from return list.\n");
	}
	/*	precon:	ArrayList returnList is defined and passed into method.
	 * postcon:	Overrides method from Return class. totalValue of return is
	 *			reset, then returnList is traversed and return's totalValue
	 *			is incremented by each Part's totalValue. resulting sum is
	 *			reduced by 35% due to type of material return.
	 */
	@Override
	public void calcTotalValue(ArrayList<Part> returnList)
	{
		//reset totalValue
		setTotalValue(0.0);
		//traverse returnList
		//each part's totalValue is added to return totalValue
		for(Part p : returnList)
		{
			setTotalValue(getTotalValue() + p.getTotalValue());
		}
		//reduce totalValue by 35% to reflect return type
		setTotalValue(getTotalValue() * 0.65);
	}
}