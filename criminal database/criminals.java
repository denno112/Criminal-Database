public class criminals 
{
    //Has the crimminals
    private int inmateNum;
    private int criminalLevel;
    private String arrestDate,relsaseDate, fname,lname,location;

    public criminals (String arrestDate, String location, String relsaseDate,int criminalLevel ,int inmateNum,String fname,String lname)
    {
        this.criminalLevel = criminalLevel;
        this.inmateNum = inmateNum;
        this.arrestDate= arrestDate;
        this.relsaseDate = relsaseDate;
        this.fname=fname;
        this.lname=lname;
        this.location=location;


    }

    public String getLocation()
    {
        return location;
    }

    public String getfName()
    {
        return fname;
    }

    public String getlName()
    {
        return lname;
    }

    public int get_crimeOffenseLevel()
    {
        return criminalLevel;
    }

    public int get_inmateNum()
    {
        return inmateNum;
    }

    public String get_arrestDate()
    {
        return arrestDate;
    }
    public String get_relsaseDate()
    {
        return relsaseDate;
    }
    

    public String toString()
    {
        return getfName()+" "+getlName()+" "+getLocation()+" "+ get_crimeOffenseLevel()+" "+get_inmateNum()+" "+get_arrestDate()+" "+get_relsaseDate();
    }
    
}