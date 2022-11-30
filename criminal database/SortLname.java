import java.util.Comparator;
public class SortLname implements Comparator<criminals>{
    public int compare(criminals c1, criminals c2)
    {
        return c1.getlName().compareTo(c2.getlName());
    }
    
}
