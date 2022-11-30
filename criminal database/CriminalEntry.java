import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CriminalEntry extends JFrame//frame to enter criminal information
{
    private JTextField  txtfname;       
    private JTextField  txtlname;
    private JTextField  adate;       
    private JTextField  location;
    private JTextField  rdate;
    private JTextField  status;
    private JTextField  inmatenum;
    private JLabel fname,lname,arrestD,releaseD,loc,crimLvl,inmateN;
    private JButton     Save;
    private JButton     Close;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    private databaseInterface dinterface;

    
    private JPanel errorPanel;
    
    public CriminalEntry(databaseInterface dInterface)
    {
    
       
        this.dinterface=dInterface;

        setTitle("Entering new criminal");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        
       
        fname=new JLabel("First Name:");
        fname.setBounds(100, 30, 400, 30); 
        pnlDisplay.add(fname); 
        txtfname = new JTextField(20);
        pnlDisplay.add(txtfname);

        lname=new JLabel("Last Name:");
        lname.setBounds(80, 70, 200, 30); 
        pnlDisplay.add(lname); 
        txtlname = new JTextField(20);
        pnlDisplay.add(txtlname);

        arrestD=new JLabel("Location:");
        arrestD.setBounds(80, 110, 200, 30);
        pnlDisplay.add(arrestD); 
        location = new JTextField(20);
        pnlDisplay.add(location);

        releaseD=new JLabel("Arrest Date(dd/mm/yyyy):");
        releaseD.setBounds(80, 150, 200, 30);
        pnlDisplay.add(releaseD); 
        adate = new JTextField(20);
        pnlDisplay.add(adate);

        loc=new JLabel("Release Date(dd/mm/yyyy):");
        loc.setBounds(80, 190, 200, 30);
        pnlDisplay.add(loc); 
        rdate = new JTextField(20);
        pnlDisplay.add(rdate);

        crimLvl=new JLabel("Criminal Level");
        crimLvl.setBounds(80, 230, 200, 30);
        pnlDisplay.add(crimLvl); 
        status = new JTextField(20);
        pnlDisplay.add(status);

        inmateN=new JLabel("Inmate Number:");
        inmateN.setBounds(80, 270, 200, 30);
        pnlDisplay.add(inmateN); 
        inmatenum = new JTextField(20);
        pnlDisplay.add(inmatenum);

        pnlDisplay.setLayout(new GridLayout(2,4));
       
        Save      = new JButton("Save");
        Save.addActionListener(new SaveButtonListener());
        Close   = new JButton("Close");
        Close.addActionListener(new CloseButtonListener());
        

        pnlCommand.add(Save);
        pnlCommand.add(Close);
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    //rem accessors
    public String getlocation()
    {
        return location.getText();
    }

    public void writeCrimData()//gets text from textfields
    {
        String fnameText =txtfname.getText();
        String lnameText =txtlname.getText();
        String adateText =adate.getText();
        String locationText =location.getText();
        String rdateText =rdate.getText();
        String statusText =status.getText();
        String inmateNumText= inmatenum.getText();

        try{
            FileWriter writer= new FileWriter("CriminalData.txt");
            writer.append( fnameText+" "+lnameText+" "+locationText+" "+ statusText+" "+inmateNumText+" "+adateText+" "+rdateText+ "\n");
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    private class CloseButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisible(false);
            
        }
    }

    private class SaveButtonListener implements ActionListener{//saves criminal data
        public void actionPerformed(ActionEvent e){
            
            try{
                String fnameText =txtfname.getText();
                String lnameText =txtlname.getText();
                String adateText =adate.getText();
                String locationText =location.getText();
                String rdateText =rdate.getText();
                String statusText =status.getText();
                String inmateNumText= inmatenum.getText();
                
                int intStatus= Integer.parseInt(statusText);
                int intInmatenum= Integer.parseInt(inmateNumText);
                
                dinterface.addPerson(new criminals(adateText,locationText,rdateText,intStatus,intInmatenum,fnameText,lnameText));
                setVisible(false);
                
            }
            catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"Please ensure you enter a number in both Criminal Level and Inmate Number textfields");
            }
        
        
        
        }
    }


}
