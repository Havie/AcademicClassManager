import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JPopupMenu;

public class ClassTrackerGUI
{
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel centerPanel;
    private JPanel centerPanelALT;
    private JPanel centerPanel_input;
    private JPanel centerPanel_output;

    private JList _listClasses;
    private JList _listAddFromFile;
    private JList _listNewStudents;




    private JLabel _labelClassName;
    private JLabel _labelMonitorClassName;
    private JLabel _labelMonitorCurrStudent;

    private JPopupMenu _popupExisting;
    private JPopupMenu _popupCreate;
    private String _popupRow="";

    private JFileChooser _fc;
    private StudentListManager _slm;
    private FileManager _fm;
    private Session _session;

    private HashMap<String, aClass> _classes;

    public ClassTrackerGUI()
    {
        //Keep track of our students from text
        _slm = new StudentListManager();
        _fc = new JFileChooser(".");
        _fm = new FileManager("SavedClasses");

        mainFrame = new JFrame("Grid Layout");
        mainPanel= new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);

        centerPanel= new JPanel();
        centerPanelALT= new JPanel();
        sidePanel = new JPanel();

        SetUpLists();
        SetUpPopUpMenu();


        centerPanel_input= new JPanel();
        centerPanel_output= new JPanel();
        SetUpIOComponents();
        SetUpMonitorComponents();


        //Set up Layouts
        centerPanel.setLayout(new GridLayout(0,2, 5, 10) );
        centerPanelALT.setLayout(new GridLayout(7,1, 5, 10) );
        sidePanel.setLayout(new GridLayout(1,1, 5, 10) );
        sidePanel.setBorder(BorderFactory.createRaisedBevelBorder());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);


        //Add items to things
        centerPanel.add(centerPanel_input);
        centerPanel.add(centerPanel_output);
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        mainFrame.add(mainPanel,  BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("358 Classes HW1    by Steve Datz");
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setSize(1280, 720);
    }

    /**
     * ----------------------------------
     * -----------MISC Starts------------
     * ----------------------------------
     */
    private void SetUpPopUpMenu()
    {
        _popupExisting = new JPopupMenu();
        _popupCreate = new JPopupMenu();
        _popupRow="";
        AddPopUpChoices();
    }
    private void SetUpLists()
    {
        _listAddFromFile= new JList(new DefaultListModel());
        _listNewStudents= new JList(new DefaultListModel());
        _listNewStudents.setLayoutOrientation(JList.VERTICAL);
        _listAddFromFile.setLayoutOrientation(JList.VERTICAL);

        SetUpClassList();
    }
    private void SetUpClassList()
    {
        _listClasses = new JList();
        _listClasses.setModel(new DefaultListModel());
        _listClasses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        _listClasses.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(_listClasses);
        listScroller.setPreferredSize(new Dimension(100, 200));
        listScroller.setSize(new Dimension(100,200));
        listScroller.setMinimumSize(new Dimension(100,200));
        sidePanel.add(listScroller);
        AddClassesFromFile();

        _listClasses.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jList1ValueChanged(evt);
            }
        });

        _listClasses.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // if(SwingUtilities.isRightMouseButton(evt) && _listClasses.locationToIndex(evt.getPoint())==_listClasses.getSelectedIndex())
                if( e.getButton() ==3)
                {
                    if(! _listClasses.isSelectionEmpty())
                        _popupExisting.show(_listClasses, e.getX(), e.getY());
                    else
                        _popupCreate.show(_listClasses, e.getX(), e.getY());
                }
                else if(e.getButton()==1)
                {
                    //??DOESNT WORK
                    //Clear selection if not clicked
                    int index = _listClasses.locationToIndex(e.getPoint());
                    System.out.println("INDEX="+ index);
                    if(index==0)
                        _listClasses.clearSelection();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });


    }
    private void AddClassesFromFile()
    {
        _classes= new HashMap<>();
        DefaultListModel gm = (DefaultListModel)_listClasses.getModel();

        //TODO add from file
    }

    /**
     * ----------------------------------
     * --------COMPONENT SECTION---------
     * ----------------------------------
    */
    private JPanel SetUpPanelMidTop()
    {
        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());

        //TOP LABEL
        JLabel inLabelTop = new JLabel("Add From File");
        inLabelTop.setForeground(Color.BLACK);
        inLabelTop.setHorizontalAlignment(SwingConstants.CENTER);
        //BUTTON FOR IMPORT
        JButton jb= new JButton("..");
        jb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int response = _fc.showOpenDialog(null);
                if(response == JFileChooser.APPROVE_OPTION)
                    ImportFileToJList(_fc.getSelectedFile());
            }
        });
        //ADD
        panel.add(inLabelTop);
        panel.add(jb);

        return panel;
    }
    private JPanel SetUpPanelListInput1()
    {
        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());

        //INPUT LIST
        _listAddFromFile.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        _listAddFromFile.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(_listAddFromFile);
        listScroller.setPreferredSize(new Dimension(300, 200));
        listScroller.setSize(new Dimension(300,200));
        listScroller.setMinimumSize(new Dimension(300,200));
        //BUTTON
        JButton jb= new JButton("-->");
        jb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DefaultListModel dm =  (DefaultListModel) _listAddFromFile.getModel();
                if(!_listAddFromFile.isSelectionEmpty())
                {
                    String fullName= _listAddFromFile.getSelectedValue().toString();
                    String first = ParseFullName(fullName, true);
                    String last = ParseFullName(fullName, false);
                    if (TryMoveText(first, last)) //clear entry if added successfully
                    {
                        dm.remove(_listAddFromFile.getSelectedIndex());
                    }
                }
            }
        });


        //ADD
        panel.add(listScroller);
        panel.add(Box.createRigidArea(new Dimension(2, 0)));
        panel.add(jb);


        return panel;
    }
    private JPanel SetUpManualArea()
    {
        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new FlowLayout());

        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new FlowLayout());

        //TextInputs
        JTextField firstname= new JTextField("firstname");
        JTextField lastname= new JTextField("lastname");
        firstname.setMinimumSize(new Dimension(100,25));
        firstname.setPreferredSize(new Dimension(100,25));
        firstname.setMaximumSize(new Dimension(100,25));
        firstname.setSize(new Dimension(100,25));
        lastname.setMinimumSize(new Dimension(100,25));
        lastname.setMaximumSize(new Dimension(100,25));
        lastname.setPreferredSize(new Dimension(100,25));
        lastname.setSize(new Dimension(100,25));

        //Button
        JButton jb= new JButton("-->");
        jb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String first= firstname.getText();
                String last = lastname.getText();
               if( TryMoveText( first,  last)) //clear entry if added successfully
               {
                   firstname.setText("");
                   lastname.setText("");
               }
            }
        });


        subPanel.add(firstname);
        subPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        subPanel.add(lastname);
        subPanel2.add(jb);

        panel.add(Box.createRigidArea(new Dimension(70, 0)));
        panel.add(subPanel);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(subPanel2);

        return panel;
    }
    private JPanel SetUpManualLabel()
    {

        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());
        //TOP LABEL
        JLabel inLabelTop = new JLabel("Add Manually");
        inLabelTop.setForeground(Color.BLACK);

        panel.add(inLabelTop);
        return panel;
    }
    private JPanel SetUpClassLabel()
    {

        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());
        //TOP LABEL
        _labelClassName = new JLabel("Class Name");
        _labelClassName.setForeground(Color.BLACK);

        panel.add(_labelClassName);
        return panel;
    }
    private JPanel SetUpPanelListOutput1()
    {
        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());

        //INPUT LIST
        _listNewStudents.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        _listNewStudents.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(_listNewStudents);
        listScroller.setPreferredSize(new Dimension(300, 200));
        listScroller.setSize(new Dimension(300,200));
        listScroller.setMinimumSize(new Dimension(300,200));

        //ADD
        panel.add(listScroller);
        panel.add(Box.createRigidArea(new Dimension(2, 0)));


        return panel;
    }
    private JPanel SetUpRemoveButton()
    {

        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());
        //TOP LABEL
        JButton jb  = new JButton("<--");
        jb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                RemoveEntry(_listNewStudents);
            }
        });

        panel.add(jb);
        return panel;
    }
    private JPanel SetUpSaveClassButton()
    {

        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());
        //TOP LABEL
        JButton jb  = new JButton("Save Class");
        jb.setMinimumSize(new Dimension(250,200));
        jb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SaveCurrentClass();
            }
        });

        panel.add(jb);
        return panel;
    }
    private void SetUpInputPanelADD()
    {
        centerPanel_input.setLayout(new BoxLayout(centerPanel_input, BoxLayout.Y_AXIS) );

        JPanel top=SetUpPanelMidTop();
        JPanel midList= SetUpPanelListInput1();
        JPanel manualLabel= SetUpManualLabel();
        JPanel bottom= SetUpManualArea();

        centerPanel_input.add(Box.createRigidArea(new Dimension(0, 100)));
        centerPanel_input.add(top);
        centerPanel_input.add(midList);
        centerPanel_input.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel_input.add(manualLabel);
        centerPanel_input.add(bottom);
        centerPanel_input.add(Box.createRigidArea(new Dimension(0, 180)));
    }
    private void SetUpOutputPanelADD()
    {
        centerPanel_output.setLayout(new BoxLayout(centerPanel_output, BoxLayout.Y_AXIS) );

        JPanel className=SetUpClassLabel();
        JPanel outputBox= SetUpPanelListOutput1();
        JPanel removeButton= SetUpRemoveButton();
        JPanel saveButton = SetUpSaveClassButton();

        centerPanel_output.add(Box.createRigidArea(new Dimension(0, 100)));
        centerPanel_output.add(className);
        centerPanel_output.add(outputBox);
        centerPanel_output.add(removeButton);
        centerPanel_output.add(Box.createRigidArea(new Dimension(0, 180)));
        centerPanel_output.add(saveButton);
    }
    private void SetUpIOComponents()
    {
        SetUpInputPanelADD();
        SetUpOutputPanelADD();

    }
    private JPanel SetUpMonitorName()
    {
        JPanel jp = new JPanel();
        _labelMonitorClassName = new JLabel("Class Name");
        _labelMonitorClassName.setForeground(Color.BLUE);
        Font f = _labelMonitorClassName.getFont();
        Font bigger=  new Font("Class Name", Font.BOLD, 25);
        //_labelMonitorClassName.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        _labelMonitorClassName.setFont(bigger);
        jp.add(_labelMonitorClassName);

        return jp;
    }
    private JPanel SetUpMonitorStudent()
    {
        JPanel jp = new JPanel();
        //jp.setLayout(new FlowLayout(FlowLayout.CENTER));

        //LABEL
        _labelMonitorCurrStudent = new JLabel(" First, Last   Question:#");
        _labelMonitorCurrStudent.setMinimumSize(new Dimension(400,200));
        _labelMonitorCurrStudent.setMaximumSize(new Dimension(400,200));
        _labelMonitorCurrStudent.setSize(new Dimension(400,200));


        //ADD
        jp.add(_labelMonitorCurrStudent);
        return jp;
    }
    private JPanel SetUpMonitorRating()
    {
        JPanel jp = new JPanel();
        //jp.setLayout(new FlowLayout(FlowLayout.CENTER));

        //DROPDOWN
        String[] choices= {"select rating", "SKIP", "EXCEPTIONAL", "SATISFACTORY", "UNSATISFACTORY"};
        JComboBox jcb = new JComboBox(choices);
        jcb.setSelectedIndex(0);
        jcb.setMinimumSize(new Dimension(10,10));
        jcb.setMaximumSize (new Dimension(100,100));
        jcb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //System.out.println(e.getActionCommand());
                if(e.getActionCommand().equals("comboBoxChanged")&& jcb.getSelectedIndex()!=0)
                {
                    //TODO
                    System.out.println("Change rating pick next student");
                }
            }
        });

        jp.add(jcb);
        return jp;
    }
    private JPanel SetUpMonitorEnd()
    {
        JPanel jp = new JPanel();
        JButton jb = new JButton("END Session");

        jb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //SAVE DATA TODO
                SwitchToAdd();
            }
        });

        jp.add(jb);
        return jp;
    }
    private void SetUpMonitorComponents()
    {
        JPanel jp1= SetUpMonitorName();
        JPanel jp2= SetUpMonitorStudent();
        JPanel jp3=SetUpMonitorRating();
        JPanel jp4= SetUpMonitorEnd();

        centerPanelALT.add(Box.createRigidArea(new Dimension(0, 100)));
        centerPanelALT.add(jp1);
        centerPanelALT.add(jp2);
        centerPanelALT.add(jp3);
        centerPanelALT.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanelALT.add(jp4);
    }


    /**
     * ----------------------------------
     * --------STATE TOGGLES---------
     * ----------------------------------
     */
    private void SwitchToAdd()
    {
        centerPanelALT.setVisible(false);
        mainPanel.remove(centerPanelALT);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setVisible(true);
    }

    private void SwitchToMonitor()
    {
        centerPanel.setVisible(false);
        mainPanel.remove(centerPanel);
        mainPanel.add(centerPanelALT, BorderLayout.CENTER);
        centerPanelALT.setVisible(true);
    }

    /**
     * ----------------------------------
     * -------EVENTS FOR BUTTONS---------
     * ----------------------------------
     */

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {
        //set text on right here
        if(!_listClasses.isSelectionEmpty()) {
            _popupRow = (String) _listClasses.getSelectedValue();
            if (_popupRow.equals("Class 1")) {
                //IDK
            }
            if (_popupRow.equals("Class 2")) {
                // jTextArea1.setText("You clicked on list 2");
            }
        }
    }

    private boolean TryMoveText(String first, String last)
    {
        //Check if the entry already exists (Student)
        if(!_slm.Contains(first, last))
        {
            if(first.length()>1 && last.length()>1 && !first.equals("") && !last.equals("")) {
                _slm.Add(first, last); // kind of meh because we look through everything twice, but max 50 so
                //System.out.println("FIRST=" + first);
                //Create entry
                DefaultListModel dm = (DefaultListModel) _listNewStudents.getModel();
                dm.addElement(first + "  " + last);
                return true;
            }
        }


        return false;
    }
    private void RemoveEntry(JList currentList)
    {
        if(currentList.isSelectionEmpty())
            return;

        int index= currentList.getSelectedIndex();
        DefaultListModel lm= (DefaultListModel)currentList.getModel();

        String fullName= currentList.getSelectedValue().toString();
        _slm.Remove(ParseFullName(fullName,true), ParseFullName(fullName, false));
        //System.out.println("Firstname="+ ParseFullName(fullName,true));
        //System.out.println("Lastname="+ ParseFullName(fullName,false));

        lm.remove(index);
    }

    private void ImportFileToJList(File file)
    {
        try
        {
           BufferedReader csvReader = new BufferedReader(new FileReader(file));
            LinkedList<String> seen = new LinkedList<String>();
            if(file.isFile())
            {
                while ((csvReader.readLine()) != null) {
                    String row =csvReader.readLine();
                    String first= row.substring(0, row.indexOf(","));
                    String last= row.substring(row.indexOf(",")+1, row.length());
                   // System.out.println("FIRST: " +first);
                    //System.out.println("LAST: " +last);
                    String fullName= first+"  " +last;
                    if(!seen.contains(fullName))
                    {
                        AddEntryToList(_listAddFromFile, fullName);
                        seen.add(fullName);
                    }

                }
            }
            csvReader.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /*** Choices for our pop up menus on the sidebar*/
    private void AddPopUpChoices()
    {
        //CREATE
        JMenuItem createClass = new JMenuItem("Create Class");
        _popupCreate.add(createClass);

        createClass.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CreateNewClass();
            }
        });


        //EDIT
        JMenuItem editClass= new JMenuItem("Edit Class");
        _popupExisting.add(editClass);

        //events
        editClass.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //ToDo
              String currClass= _listClasses.getSelectedValue().toString();
              aClass c= _classes.get(currClass);
              if(c!=null)
                StartNewSession(c);
              else
                  JOptionPane.showMessageDialog(null, "Error Finding Class");
            }
        });

        //START
        JMenuItem startClass= new JMenuItem("Start Class");
        _popupExisting.add(startClass);

        //events
        startClass.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //ToDo
                //start class
            }
        });


    }

    public void CreateNewClass()
    {
        String name= JOptionPane.showInputDialog(this, "Enter Class Name");
        System.out.println("String=" + name);

        ClearAndRefresh();
        DefaultListModel dm = (DefaultListModel) _listClasses.getModel();
        dm.addElement(name);
        _labelClassName.setText(name);
        //Save our Class to be added onto later
        aClass c= new aClass(name);
        _classes.put(name, c);

    }


    /** Saves the Current Students from SLM via FILE**/
    private void SaveCurrentClass()
    {
        String message= "Error: Class Empty";

        if(_slm.GetStudents().size()>0)
        {
           // aClass c = new aClass(_labelClassName.getText(), _slm.GetStudents());
            aClass c= FindProperClass(_labelClassName.getText());
            if(c!=null)
            {
                for(Student s: _slm.GetStudents())
                    c.Add(s);

                if (_fm.CreateClassFile(c))
                    message = "Class Saved";
                else
                    message = "Error: Not Saved";
            }
        }
        JOptionPane.showMessageDialog(null, message);


    }
    private void StartNewSession(aClass c)
    {
        if(_session!=null)
        {
            //Save off old session
        }
        _session= new Session(c);
        SwitchToMonitor();
        _labelMonitorClassName.setText(c.GetClassName());
        Student s= _session.SelectRandom();
        int Qs= _session.GetQuestionAsked(s);
        _labelMonitorCurrStudent.setText(s.GetFirstName()+", " +s.GetLastName() +"   Question Answered: " + Qs);
    }


    /**
     * ----------------------------------
     * ------------HELPERS---------------
     * ----------------------------------
     */

    private void ClearAndRefresh()
    {
        //Possibly cache any session data or un saved class data???

        ClearList(_listAddFromFile);
        ClearList(_listNewStudents);

    }
    private void ClearList(JList list)
    {
        DefaultListModel dm = (DefaultListModel) list.getModel();
        dm.clear();
    }
    private void AddEntryToList(JList list, String name)
    {
        DefaultListModel dm = (DefaultListModel) list.getModel();
        dm.addElement(name);
    }
    private String ParseFullName(String s, boolean first)
    {
        if(first)
            return s.substring(0, s.indexOf("  "));
        else
            return s.substring(s.indexOf("  ")+2, s.length());
    }

    private aClass FindProperClass(String name)
    {
       if(_classes!=null)
           return _classes.get(name);
       else
           return null;
    }



}
