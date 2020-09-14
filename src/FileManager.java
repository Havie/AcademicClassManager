import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.FileReader;

public class FileManager
{

    private String _filePath = "C:\\Users\\"; //Doesn't work no write permissions despite saying true
    private String _folderName = "SessionData\\";
    private String _fileName;
    private String spacing="  ";

    /** FileManager creates , writes and reads a File from Passed in FileName. Location is project directory**/
    public FileManager(String FileName)
    {
        _fileName=FileName+".csv";
        _filePath = System.getProperty("user.dir");
       // System.out.println("NEW DIR=" +_filePath);

        //Setup our output folder and file
        File f = new File(_filePath);
        if(f.isDirectory())
        {
            File folder= new File(f, _folderName);
            //make the folder if it doesnt exist
            if (!folder.exists())
                folder.mkdir();
           /* else if (folder.isDirectory())
                System.out.println("GOOD! , Folder exists ");
              //else major issue lol */

            File output = new File(folder, _fileName);
            if(!output.exists())
            {
                try {
                    output.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
    /** This method creates a Session file (CSV) at the program location and cached FileName   */
    public void CreateSessionFile(aClass c, Session session)
    {
        if(c==null)
            return;
        try {
            //Unsure what happens if it already exists
            String path = (_filePath + "\\" + _folderName + _fileName);
            File f = new File(path);
            if (f.exists() && !f.isDirectory()) {
                FileWriter myWriter = new FileWriter(path);
                myWriter.write(c.GetClassName() + "\n");
                for (Student s : c.GetStudents()) {
                    String name = s.GetFirstName() + ", " + s.GetLastName();
                    int skips = session.GetRating(s, Session.eRating.SKIP);
                    int ex = session.GetRating(s, Session.eRating.EXCEPTIONAL);
                    int sat = session.GetRating(s, Session.eRating.SATISFACTORY);
                    int un = session.GetRating(s, Session.eRating.UNSATISFCTORY);

                    myWriter.write(name + spacing + "SKIP:" + skips + spacing + "EXCEPTIONAL:" + ex + spacing + "SATISFACTORY:" + sat + spacing + "UNSATISFACTORY:" + un + "\n");
                }
                myWriter.flush();
                myWriter.close();
            }
        }
        catch(Exception e)
            {e.printStackTrace();}
    }

    /** This method creates a Session file (CSV) at the program location and Cached FileName   */
    public boolean CreateClassFile(LinkedList<aClass> classes)
    {
        if(classes==null )
            return false;
        boolean success=false;
        try
        {
            String path =(_filePath+"\\"+_folderName+_fileName);
            File f= new File(path);

            if(f.exists()&& !f.isDirectory())
            {
                FileWriter myWriter = new FileWriter(path, true);
                for(aClass c : classes) {
                    //APPEND Class Name to List
                    myWriter.append("--" + c.GetClassName() + "--" + "\n");
                    //Write all Student Names
                    for (Student s : c.GetStudents()) {
                        String name = s.GetFirstName() + ", " + s.GetLastName();
                        myWriter.append(name + "\n");
                    }
                }
                myWriter.flush();
                myWriter.close();
                success=true;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    return success;
    }
    /**Helper Clears CSV file**/
    private void ClearClassFile()
    {
        try {
            String path = (_filePath + "\\" + _folderName + _fileName);
            FileWriter myWriter = new FileWriter(path, false);
            myWriter.write("");
            myWriter.flush();
            myWriter.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /** This Method Reads a CSV at the cached Location + fileName . It rebuilds new Classes and Students based off its findings, then returns a List of those Classes*/
    public ArrayList<aClass> ParseClassFile()
    {
        ArrayList<aClass> _foundClasses = new ArrayList<>();

        String path =(_filePath+"\\"+_folderName+_fileName);
        File file= new File(path);
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            aClass _currClass = null;
            while((line= br.readLine() )!=null)
            {
                if(line.contains("--"))    //ClassName
                {
                    //Save old Class
                    if(_currClass!=null)
                        _foundClasses.add(_currClass);

                    _currClass= new aClass(ParseClassName(line));

                }
                else if(line.contains(","))    //Student
                {
                    String first= ParseStudent(line, true);
                    String last = ParseStudent(line, false);
                    if(_currClass!=null)
                        _currClass.Add(new Student(first, last));
                }
            }
            _foundClasses.add(_currClass);
            br.close();

        }catch(Exception e) { e.printStackTrace(); }

        ClearClassFile(); //Clear so when program closes it re writes

        return _foundClasses;
    }
    /**Helper for ParseClassFile**/
    private String ParseClassName(String line)
    {
        return line.substring(line.indexOf("--")+2, line.lastIndexOf("--"));
    }
    /**Helper for ParseClassFile**/
    private String ParseStudent(String line, boolean first)
    {
        if(first)
            return line.substring(0, line.indexOf(","));
        else
            return line.substring(line.indexOf(",")+2, line.length());
    }
}
