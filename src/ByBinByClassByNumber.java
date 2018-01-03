import java.util.Comparator;

//compares part1 to part2
//for sorting by bin, then by class, then by number, each in ascending order
public class ByBinByClassByNumber implements Comparator<Part>
{
	public int compare(Part part1, Part part2)
	{
		if(part1.getBinLoc().compareTo(part2.getBinLoc()) < 0)
		{
			//part1 bin comes before part2 bin
			return -1;
		}
		else if(part1.getBinLoc().compareTo(part2.getBinLoc()) > 0)
		{
			//part1 bin comes after part2 bin
			return 1;
		}
		else
		{
			//part1 bin equals part2 bin, compare class codes
			Integer part1Class = new Integer(part1.getClassCode());
			Integer part2Class = new Integer(part2.getClassCode());
			if(part1Class.compareTo(part2Class) < 0)
			{
				//part1 class comes before part2 class
				return -1;
			}
			else if(part1Class.compareTo(part2Class) > 0)
			{
				//part1 class comes after part2 class
				return 1;
			}
			else
			{
				//part1 class equals part2 class, compare part numbers
				return part1.compareTo(part2);
			}
		}
	}
}