import java.util.Comparator;

//compares part1 to part2
//for sorting by MNS in *descending order*
//then by bin, then by class, then by number, each in ascending order
public class ByMNSByBinByClassByNumber implements Comparator<Part>
{
	public int compare(Part part1, Part part2)
	{
		if(part1.getMonthsNoSale() > part2.getMonthsNoSale())
		{
			//part1 MNS comes before part2 MNS
			return -1;
		}
		else if(part1.getMonthsNoSale() < part2.getMonthsNoSale())
		{
			//part1 MNS comes after part2 MNS
			return 1;
		}
		else
		{
			//part1 MNS equals part2 MNS, compare by bin, by class, by number
			return (new ByBinByClassByNumber().compare(part1, part2));
		}
	}
}