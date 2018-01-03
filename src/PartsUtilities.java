/* CS112 Final Project
   File Name:          PartsUtilities.java
   Programmers:        Daniel Kirby
   Date Last Modified: May 17, 2015

   Problem Statement: Create a program that reads parts report data from
   multiple .csv files into memory, manipulates the data, and outputs the
   changed data to a new .csv file for a material return. Design methods to
   cross-reference and trim matching parts from multiple files, add new
   parts, change part quantities, remove parts, and sort parts.
      
   Overall Plan (step-by-step, how you want the code to make it happen):
   1. Create a Part class that represents an individual part number.
   	a) Create instance variables for:
   	   String part number, int classification code, String part description,
   	   int quantity on hand, int months no sale, double cost per unit
   	b) Create a BinLocation class that represents a physical bin location.
   	 1) Store the bin location as a String inside the BinLocation class
   	 2) Implement the Comparable interface with a compareTo method that can
   	 	properly sort BinLocations with varying lengths and compositions.
   	c) Create a getTotalValue method that multiplies the qty by cost and
   	   returns the total value as a double.
   	d) Implement the Comparable interface with a compareTo method that
   	   compares the part number variable of each Part.
   	e) Create a toCSVString method that returns the Part data as a String
   	   separated by commas.
   2. Create an abstract Return class that represents a material return.
   	a) Create instance variables for:
   	   String return type, String application number, String date, String
   	   output file name, double total return value
   	b) Create a method to return today's date as a String in the format
   	   DDMMMYY
   	c) Create a method to prompt the user for app number.
   	d) Create a method to prompt the user for file names.
   	e) Create methods that take an ArrayList of Parts as a parameter and:
   	 1) Display the contents of the list to the screen.
   	 2) Generate an output .csv file of the contents of the list.
   	 3) Prompt the user for a new Part, then add it to the list.
   	 4) Prompt the user for a Part, then search the list for it and allow
   	    the user to change quantity.
   	 5) Prompt the user for a Part, then search the list for it and remove
   	    it from the list.
   	 6) Sort the list by bin, then by class, then by part number.
   	  a) Create a Comparator ByBinByClassByNumber that handles this sorting.
   	 7) Sort the list by months no sale, then by bin, then by class, then by
   	 	part number.
   	  a) Create a Comparator ByMNSByBinByClassByNumber that handles this
   	  	 sorting.
   	f) Create a method to calculate the total value of a return by adding
   	   each Part's total value to the sum.
   	g) Create abstract method that will have different definitions based on
   	   return type.
   	 1) trim
   3. Create a MonthlyCSO class that extends Return.
    a) Create instance variables for 2 input file names (Strings) and 2 
       ArrayLists to store their data. RIM data will only be stored as Strings,
       MNRMNS data will be stored as Parts.
    b) Create methods to read from each file using Scanner and FileInputStream.
    c) Define the trim method to cross reference data in each input file and
       and remove matching parts from the MNRMNS list.
    d) Override the calcTotalValue method to add each Part's total value to the
       sum and then reduce the total return value by 35% due to return type.
   4. Create a MonthlyObsolescence class that extends Return.
    a) Create instance variables for an input file name (String), the return
       reserve cap (double), and an ArrayList of Parts to store Part data.
    b) Create a method to read the input file using Scanner and FileInputStream.
    c) Define the trim method add each Part's total value to the return's total
       until the return reserve cap is reached. Remove the remaining Parts from
       the list.
   5. Create a main class/program that provides the user with menus to utilize
      each of the classes and methods created.
   
   Classes needed and Purpose (Input, Processing, Output)
   PartsUtilities - main class
   Part - class to store individual part data
   BinLocation - class to store bin location
   Return - abstract class to store and manipulate list of Parts
   ByBinByClassByNumber - Comparator for sorting
   ByMNSByBinByClassByNumber - Comparator for sorting
   MonthlyCSO - extends Return class for a specific return type
   MonthlyObsolescence - extends Return class for a specific return type
*/

import java.util.Scanner;

public class PartsUtilities
{
	private MonthlyCSO rtn1 = new MonthlyCSO();
	private MonthlyObsolescence rtn2 = new MonthlyObsolescence();
	
	public static void main(String[] args)
	{
		//instantiate class
		PartsUtilities util = new PartsUtilities();
		//begin program
		util.beginProgram();
	}
	/*	precon:	none
	 * postcon:	generateMenu method is invoked with initial program menu.
	 */
	public void beginProgram()
	{
		generateMenu(3, 0,
			"\nMonthly Return Generator",
			"1: CSO returns (MR11, 35%)",
			"2: Obsolescence returns (return reserve)",
			"3: Quit program",
			"Make a selection (1-3): ");
	}
	/*	precon:	int numChoices, int menuLevel, and any number of Strings
	 *			representing menuLines are defined and passed into method.
	 * postcon:	a repeating menu is generated using the lines given in the
	 *			order given, the user is prompted for a menu selection based
	 *			on numChoices, and the appropriate executeMenuChoice method
	 *			is invoked based on the menuLevel.
	 */
	public void generateMenu(int numChoices, int menuLevel,
													String... menuLines)
	{
		Scanner kb = new Scanner(System.in);
		String input = null;
		int choice = 0;
		boolean repeat = true;
		
		do
		{
			for(String s : menuLines)
			{
				System.out.println(s);
			}
			input = kb.nextLine();
			if(input.length() == 1)
			{
				for(int i = 0;i < numChoices;i++)
				{
					if(input.equals(String.valueOf(i + 1)))
					{
						choice = Integer.parseInt(input);
					}
				}
			}
			else
			{
				System.out.println("Invalid input. Please make a selection"
					+ " from 1-" + numChoices + ".");
			}
			switch(menuLevel)
			{
				case 0: //main menu
					for(int i = 0;i < numChoices;i++)
					{
						if(choice == (i + 1))
						{
							repeat = executeMenuChoice0(i + 1);
						}
					}
					break;
				case 1: //first submenu
					for(int i = 0;i < numChoices;i++)
					{
						if(choice == (i + 1))
						{
							repeat = executeMenuChoice1(i + 1);
						}
					}
					break;
				case 2: //second submenu
					for(int i = 0;i < numChoices;i++)
					{
						if(choice == (i + 1))
						{
							repeat = executeMenuChoice2(i + 1);
						}
					}
					break;
				default:
					break;
			}
		}while(repeat);
	}
	/*	precon:	int choice is defined and passed into the method.
	 * postcon:	choice is used to determine which menu selection to process.
	 *			returns boolean value for repeat.
	 */
	public boolean executeMenuChoice0(int choice)
	{
		boolean repeat = true;
		switch(choice)
		{
			case 1:	//generate submenu for MonthlyCSO options
				generateMenu( 9, 1,
					"\nCSO Returns",
					"1: Create a new return/clear current return",
					"2: Display return",
					"3: Add a part to return",
					"4: Change return quantity for a part",
					"5: Remove a part from return",
					"6: Sort return (by bin/class/number)",
					"7: Export return to file",
					"8: Return to main menu",
					"9: Quit program",
					"Make a selection (1-9): ");
				break;
			case 2:	//generate submenu for MonthlyObsolescence options
				generateMenu( 9, 2,
					"\nObsolescence Return Generator",
					"1: Create a new return/clear current return",
					"2: Display return",
					"3: Add a part to return",
					"4: Change return quantity for a part",
					"5: Remove a part from return",
					"6: Sort return (by MNS/bin/class/number)",
					"7: Export return to file",
					"8: Return to main menu",
					"9: Quit program",
					"Make a selection (1-9): ");
				break;
			case 3:	//quit
				System.out.println("End of program.");
				repeat = false;
				System.exit(0);
				break;
			default:
				break;
		}
		return repeat;
	}
	/*	precon:	int choice is defined and passed into the method.
	 * postcon:	choice is used to determine which menu selection to process.
	 *			returns boolean value for repeat.
	 */
	public boolean executeMenuChoice1(int choice)
	{
		boolean repeat = true;
		switch(choice)
		{
			case 1:	//create a new monthly cso return
				rtn1 = new MonthlyCSO();
				//prompt user for application number
				rtn1.promptForAppNum();
				//read RIM file
				rtn1.readRIMSimulationFile();
				//read MNR/MNS file
				rtn1.readMNRMNSReportFile();
				//cross-reference files to trim data
				rtn1.trim();
				break;
			case 2: //display return
				rtn1.displayList(rtn1.getReturnList());
				break;
			case 3: //manually add a part
				rtn1.addPart(rtn1.getReturnList());
				break;
			case 4: //manually change a quantity
				rtn1.changeQty(rtn1.getReturnList());
				break;
			case 5: //manually remove a part
				rtn1.removePart(rtn1.getReturnList());
				break;
			case 6: //sort return
				rtn1.sortByBinClassNumber(rtn1.getReturnList());
				break;
			case 7: //export return to file
				rtn1.generateReturnOutput(rtn1.getReturnList());
				break;
			case 8: //return to main menu
				beginProgram();
				break;
			case 9: //quit program
				System.out.println("End of program.");
				repeat = false;
				System.exit(0);
				break;
			default:
				break;
		}
		return repeat;
	}
	/*	precon:	int choice is defined and passed into the method.
	 * postcon:	choice is used to determine which menu selection to process.
	 *			returns boolean value for repeat.
	 */
	public boolean executeMenuChoice2(int choice)
	{
		boolean repeat = true;
		switch(choice)
		{
			case 1:	//create a new monthly obsolescence return
				rtn2 = new MonthlyObsolescence();
				//prompt user for application number
				rtn2.promptForAppNum();
				//prompt user for return reserve cap
				rtn2.promptForReturnReserveCap();
				//read SO17 file
				rtn2.readSO17ReportFile();
				//trim data
				rtn2.trim();
				break;
			case 2: //display return
				rtn2.displayList(rtn2.getReturnList());
				break;
			case 3: //manually add a part
				rtn2.addPart(rtn2.getReturnList());
				break;
			case 4: //manually change a quantity
				rtn2.changeQty(rtn2.getReturnList());
				break;
			case 5: //manually remove a part
				rtn2.removePart(rtn2.getReturnList());
				break;
			case 6: //sort return
				rtn2.sortByMNSByBinClassNumber(rtn2.getReturnList());
				break;
			case 7: //export return to file
				rtn2.generateReturnOutput(rtn2.getReturnList());
				break;
			case 8: //return to main menu
				beginProgram();
				break;
			case 9: //quit program
				System.out.println("End of program.");
				repeat = false;
				System.exit(0);
				break;
			default:
				break;
		}
		return repeat;
	}
}