import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class authorizedPersonnel 
{  // Has the authorized persons
    private int id;
    private String fName, lName, pWord;
    private ArrayList<authorizedPersonnel> alist;
    private Boolean access = false;

    public authorizedPersonnel(String fName, String lName,int id ,String pWord)
    {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.pWord = pWord;
    }

    public String getfName()
    {
        return fName;
    }

    public String getlName()
    {
        return lName;
    }

    public int getID()
    {
        return id;
    }
    public String getpWord()
    {
        return pWord;
    }



    public String toString()
    {
        return getfName()+"\t"+getlName()+"\t"+getID()+"\t"+getpWord();
    }

   
}
