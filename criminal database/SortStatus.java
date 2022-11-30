import java.util.Comparator;
public class SortStatus implements Comparator<criminals> {
    public int compare(criminals c1, criminals c2)
    {
        if (c1.get_crimeOffenseLevel()>c2.get_crimeOffenseLevel()) return 1;
        if (c1.get_crimeOffenseLevel()<c2.get_crimeOffenseLevel()) return -1;
        else return 0; 
    }
    
}
