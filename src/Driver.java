public class Driver
{

    private final static String FileDIR="C:\\Users\\sdatz\\Desktop\\script\\campaign\\mod\\";

    public static void main (String[] args)
    {
        //JFrame gui = new GUIForm("CS358 Classes");
        //gui.setVisible(true);
       // gui.setSize(1028, 720);
        ClassTrackerGUI gui = new ClassTrackerGUI();
    }

    public static void print(String s)
    {
        System.out.println(s);
    }

}
