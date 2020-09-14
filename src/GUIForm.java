import javax.swing.*;

public class GUIForm extends JFrame {

    private JPanel mainPanel;
    private JList list1;
    private JFormattedTextField formattedTextField1;
    private JRadioButton radioButton1;


    public GUIForm(String title)
    {
        super(title);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }



}
