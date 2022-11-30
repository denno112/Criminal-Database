import java.util.Comparator;
public class SortFname implements Comparator<criminals> {//method to sort criminal data
    public int compare(criminals c1, criminals c2)
    {
        return c1.getfName().compareTo(c2.getfName());
    }
}
