import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.Collections;
import java.awt.Color;

public class databaseInterface extends JPanel{
    private JButton close;
    private JButton addCriminal;
    private JButton sortFname;
    private JButton sortLname;
    private JButton status;
    private JTable table;
    private databaseInterface thisForm;
    private JPanel buttonPanel;
    private JPanel display;
    private ArrayList<criminals> clist;
    private String[] locationString;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private CriminalEntry crimEntry;
    private static LoginPanel lpanel;
    private JComboBox locationList;
    private  String comboBoxLocation;
    private String userLocation;
    

    
    public databaseInterface(LoginPanel lpanel){
        //Houses the list of criminal information
        super(new GridLayout(2,1));
        this.lpanel=lpanel;
        thisForm = this;
        this.comboBoxLocation=comboBoxLocation;

        buttonPanel= new JPanel();
        display= new JPanel();
        
        clist=loadCriminals("CriminalData.txt");
        String[] columnNames= {"First Name", "Last Name","Location","Criminal Level", "Inmate Number", "Date Arrested", "Date of Release"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        showTable(clist);

        table.setPreferredScrollableViewportSize(new Dimension(500, clist.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
       
        add(scrollPane);

        String[] locationString = { "Kingston", "SpanishTown", "Montego-bay", "OchoRios" };


        locationList = new JComboBox(locationString);
        userLocation=lpanel.getIDText().substring(0,3);
        if(userLocation.equals("111")){
            locationList.setSelectedIndex(0);
        }else if(userLocation.equals("222")){
            locationList.setSelectedIndex(2);
        }else if(userLocation.equals("333")){
            locationList.setSelectedIndex(3);
        }else if(userLocation.equals("444")){
            locationList.setSelectedIndex(1);
        }

        
        addCriminal= new JButton("Add Criminal") ;
        sortFname= new JButton("Sort First Name");
        sortLname= new JButton("Sort Last Name");
        status= new JButton ("Sort Criminal Level");
        close= new JButton("Close");

        addCriminal.setBackground(Color.BLUE);
        sortFname.setBackground(Color.BLUE);
        sortLname.setBackground(Color.BLUE);
        status.setBackground(Color.BLUE);
        close.setBackground(Color.BLUE);

        locationList.addActionListener(new VPNListener());
        addCriminal.addActionListener(new AddButtonListener());
        sortFname.addActionListener(new SortFirstButtonListener());
        sortLname.addActionListener(new SortLastButtonListener());
        status.addActionListener(new SortStatusButtonListener());
        close.addActionListener(new CloseButtonListener());;

        buttonPanel.add(locationList);
        buttonPanel.add(addCriminal);
        buttonPanel.add(sortFname);
        buttonPanel.add(sortLname);
        buttonPanel.add(status);
        buttonPanel.add(close);

        add(buttonPanel);
    }

    private void showTable(ArrayList<criminals> clist)//adds criminal data to table
    {
        if (clist.size()>0)
        {
            for(criminals c: clist)
            {
                addToTable(c);
            }
        }
    }
    private void showTableFSort(ArrayList<criminals> list)//sorts criminals by first name
    {
        if (list.size()>0)
        {
            Collections.sort(list, new SortFname());
            for(criminals l: list)
            {
                addToTable(l);
            }
        }
    }
    private void showTableLSort(ArrayList<criminals> list)//sorts criminals by last name
    {
        if (list.size()>0)
        {
            Collections.sort(list, new SortLname());
            for(criminals l: list)
            {
                addToTable(l);
            }
        }
    }
    private void showTableSSort(ArrayList<criminals> list)//sorts criminals by criminal status
    {
        if (list.size()>0)
        {
            Collections.sort(list, new SortStatus());
            for(criminals l: list)
            {
                addToTable(l);
            }
        }
    }

    private void addToTable(criminals c)
    {

        String[] item={c.getfName(),""+c.getlName(),""+c.getLocation(),""+c.get_crimeOffenseLevel(),""+c.get_inmateNum(),""+c.get_arrestDate(),""+c.get_relsaseDate(),""};
        model.addRow(item);
    }

    public void addPerson(criminals c)// adds criminal to file with criminal information
    {
        clist.add(c);
        try{
            
            BufferedWriter writer= new BufferedWriter(new FileWriter("CriminalData.txt",true));
            writer.write( c.toString()+ "\n");
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        String Location = (String)locationList.getName();
        changeLocation(Location);
        addToTable(c);

    }


    private ArrayList<criminals> loadCriminals(String tFile)
    {//has specific order for criminal information and reads criminal information from file
        Scanner cscan = null;
        ArrayList<criminals> crimlist = new ArrayList<criminals>();
        String locString=getCurrentLocation(lpanel.getIDText());

        try
        {
            cscan  = new Scanner(new File(tFile));
            while(cscan.hasNext())
            {
                String [] nextLine = cscan.nextLine().split(" ");
                String fname = nextLine[0];
                String lname= nextLine[1];
                String location= nextLine[2];
                int status = Integer.parseInt(nextLine[3]);
                int inmateNum = Integer.parseInt(nextLine[4]);
                String dateArrested =  nextLine[5];
                String dateReleased= nextLine[6];
                
                
                if (location.equals(getCurrentLocation(this.lpanel.getIDText()))){
                    criminals c= new criminals(dateArrested,location,dateReleased,status,inmateNum,fname,lname);
                    
                    crimlist.add(c);
                }
                    
            }

            cscan.close();
        }
        catch(IOException e)
        {}
        return crimlist;
    }

    public String getCurrentLocation(String userID)
    {//decides location of user based on ID number
        String loc="";
        String subString=userID.substring(0,3);
        switch(subString)
        {
            case "111":
                loc="Kingston";
                break;
            
            case "222":
                loc="Montego-bay";
                break;
            case "333":
                loc="OchoRios";
                break;
            case "444":
                loc="SpanishTown";
                break;
        }
        return loc;
    }

    public ArrayList<criminals> changeLocation(String VPNloc)
    {
        Scanner cscan = null;
        ArrayList<criminals> crimList = new ArrayList<criminals>();
        try
        {
            cscan  = new Scanner(new File("CriminalData.txt"));
            while(cscan.hasNext())
            {
                String [] nextLine = cscan.nextLine().split(" ");
                String fname = nextLine[0];
                String lname= nextLine[1];
                String location= nextLine[2];
                int status = Integer.parseInt(nextLine[3]);
                int inmateNum = Integer.parseInt(nextLine[4]);
                String dateArrested =  nextLine[5];
                String dateReleased= nextLine[6];
                
                
                
                if (location.equals(VPNloc)){
                    criminals c= new criminals(dateArrested,location,dateReleased,status,inmateNum,fname,lname);
                    crimList.add(c);
                
                }
                    
            }

            cscan.close();
        }
        catch(IOException e)
        {}
        return crimList;
    }


        private class CloseButtonListener implements ActionListener
        {//exits frame
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }

        }


        private class AddButtonListener implements ActionListener
        {// opens new frame to add criminal data
            public void actionPerformed(ActionEvent e)
            {
                
                CriminalEntry crimEntry= new CriminalEntry(thisForm);
                
            }

        }

        private class SortFirstButtonListener implements ActionListener
        {//sorts criminal data
            public void actionPerformed(ActionEvent e)
            {
                model.setRowCount(0);
                showTableFSort(changeLocation((String)locationList.getSelectedItem()));
            }

        }

        private class SortLastButtonListener implements ActionListener
        {//sorts criminal data
            public void actionPerformed(ActionEvent e)
            {
                model.setRowCount(0);
                showTableLSort(changeLocation((String)locationList.getSelectedItem()));
            }

        }

        private class SortStatusButtonListener implements ActionListener
        {//sorts criminal data
            public void actionPerformed(ActionEvent e)
            {
                model.setRowCount(0);
                showTableSSort(changeLocation((String)locationList.getSelectedItem()));
            }

        }

        public class VPNListener implements ActionListener {

            public void actionPerformed(ActionEvent e) 
            {//changes user locagtion

                JComboBox cb = (JComboBox)e.getSource();
                String Location = (String)cb.getSelectedItem();

                model.setRowCount(0);
                showTable(changeLocation(Location));

            }
            
        }

        





    




}
    

