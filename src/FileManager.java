import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager
{

    private String _filePath = "C:\\Users\\";
    private String _folderName = "SessionData\\";
    private String _fileName;
    private String spacing="  ";


    public FileManager(String FileName)
    {
        _fileName=FileName+".csv";
        //TMP
        _filePath = System.getProperty("user.dir");
       // System.out.println("NEW DIR=" +_filePath);

        File f = new File(_filePath);
        if(f.isDirectory())
        {
            System.out.println("WE found C://Users");
            File folder= new File(f, _folderName);
            //make the folder if it doesnt exist
            if (!folder.exists())
            {
                System.out.println("MAKE FOLDER  " + f.canWrite());
                System.out.println(folder.mkdir());


            }
            else if (folder.isDirectory())
                System.out.println("GOOD! , Folder exists ");

            File output = new File(folder, _fileName);
            if(!output.exists())
            {
                System.out.println("CREATE:::: " + folder.getAbsolutePath()+_fileName);
                try {
                    output.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void CreateSessionFile(aClass c, Session session) throws IOException
    {
        if(c==null)
            return;
        //Unsure what happens if it already exists
        String path =(_filePath+"\\"+_folderName+_fileName);
        File f= new File(path);
        if(f.exists()&& !f.isDirectory())
        {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(c.GetClassName() + "\n");
            for (Student s : c.GetStudents())
            {
                String name = s.GetFirstName() + ", " + s.GetLastName();
                int skips = session.GetRating(s, Session.eRating.SKIP);
                int ex = session.GetRating(s, Session.eRating.EXCEPTIONAL);
                int sat = session.GetRating(s, Session.eRating.SATISFACTORY);
                int un = session.GetRating(s, Session.eRating.UNSATISFCTORY);

                myWriter.write(name + spacing + "SKIP:" + skips + spacing + "EXCEPTIONAL:" + ex + spacing + "SATISFACTORY" + sat + spacing + "UNSATISFACTORY" + un + "\n");
            }
            myWriter.flush();
            myWriter.close();
        }
    }

    public boolean CreateClassFile(aClass c)
    {
        if(c==null )
            return false;
        boolean success=false;
        try
        {
            String path =(_filePath+"\\"+_folderName+_fileName);
            File f= new File(path);

            if(f.exists()&& !f.isDirectory())
            {
                FileWriter myWriter = new FileWriter(path, true);
                //APPEND Class Name to List
                myWriter.append("--" + c.GetClassName() + "--" + "\n");
                //Write all Student Names
                for (Student s : c.GetStudents()) {
                    String name = s.GetFirstName() + ", " + s.GetLastName();
                    myWriter.append(name + "\n");
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
}
