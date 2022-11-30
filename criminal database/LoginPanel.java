import javax.swing.*;
import javax.swing.event.InternalFrameListener;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginPanel extends JPanel
{
    private JTextField  userid;       
    private JPasswordField  passfield;


    private JButton     login;
    private JButton     close;

    private JPanel      inputPanel;
    private JPanel      inputButton;
    private JPanel      greeting;
    private JPanel      errorPanel;
    private databaseInterface dinterface;
    private static LoginPanel lpane;
    private ArrayList<authorizedPersonnel> ulist;
    private main firstScreen;

    public LoginPanel()//Login Panel
    {
        this.dinterface=dinterface;
        lpane=this;

        inputPanel = new JPanel();
        inputButton= new JPanel();

        ulist=loadUsers("authorizedUsers.txt");

        

        inputPanel.add(new JLabel("ID number:"));
        userid=new JTextField(20);
        inputPanel.add(userid);

        inputPanel.add(new JLabel("Password:"));
        passfield=new JPasswordField(20);
        inputPanel.add(passfield);

        

        login= new JButton("Login");
        login.addActionListener(new LoginButtonListener());
        close = new JButton("Close");
        close.addActionListener(new CloseButtonListener());

        inputButton.add(login);
        inputButton.add(close);

        add(inputPanel);
        add(inputButton);
        
    }

    private ArrayList<authorizedPersonnel> loadUsers(String uFile){//reads user information from file
        Scanner uscan= null;
        ArrayList<authorizedPersonnel> userlist = new ArrayList<authorizedPersonnel>();
        try{
            uscan  = new Scanner(new File(uFile));
            while(uscan.hasNext())
            {
                String [] nextLine = uscan.nextLine().split(" ");
                String fname = nextLine[0];
                String lname= nextLine[1];
                int idNum = Integer.parseInt(nextLine[2]);
                String passWord= nextLine[3];

                authorizedPersonnel userPersonnel= new authorizedPersonnel(fname,lname,idNum, passWord);
                userlist.add(userPersonnel);
                
            }
            uscan.close();
        }
        catch(IOException e){}
        return userlist;

    }


    public String getIDText(){//gets text from ID textfield
        return userid.getText();
    }

    public Boolean checkCredentials(int iD, char[] cs){//compares user information with information in textfield
        
        boolean check= false;
        
        if (ulist.size()>0)
        {
            for(authorizedPersonnel u: ulist)
            {
                String s=String.valueOf(cs);
                if (u.getpWord().equals(s) && u.getID()==iD)
                {
                    check= true;
                    break;
                }

            }
        }
        return check;
    }

    
    
    private class CloseButtonListener implements ActionListener{//closes frame
        public void actionPerformed(ActionEvent e){
            System.exit(0);
            
        }
    }

    private class LoginButtonListener implements ActionListener//enables users to log in
    {
        public void actionPerformed(ActionEvent event)
        {
            if(checkCredentials(Integer.parseInt(userid.getText()),passfield.getPassword()))
            {
              //correct credentials
                lpane.setVisible(false);
                createDatabaseGUI();
            }else{
                //incorrect credentials
                JOptionPane.showMessageDialog(null,"Login Credentials are incorrect"+ "\n"+"Please try again");
            }        
         }
    }

    private static void createDatabaseGUI() {//opens database panel
            //Create and set up the window.
            JFrame frame = new JFrame("LIST OF CRIMINALS");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
            //Create and set up the content pane.
            databaseInterface newContentPane = new databaseInterface(lpane);
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);
    
            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }




    
}
