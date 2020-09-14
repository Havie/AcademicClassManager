import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Session
{
    public enum eRating {SKIP(0), EXCEPTIONAL(1), SATISFACTORY(2), UNSATISFCTORY(3);
        //ABSURD way to be able to cast an enum as an INT in Java.
        private int value;
        eRating(int value)
        {
            this.value=value;
        }
    };


    private aClass _class;
    private Student _currentStudent;
    HashMap<Student, ArrayList<Integer>> _studentRatings;


    public Session(aClass c)
    {
        _class = c;
        _studentRatings= new HashMap<>();
        for(Student s : c.GetStudents())
        {
            ArrayList<Integer> _ratings = new ArrayList<>();
            //Could for loop but yolo
            _ratings.add(0);
            _ratings.add(0);
            _ratings.add(0);
            _ratings.add(0);
           _studentRatings.put(s,_ratings);
        }
    }
    private String GetDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.getMonth()+"_" + now.getYear();
    }

    public Student SelectRandom()
    {
        if(_class==null)
            return null;

        List<Student> studentList= _class.GetStudents();

        if(studentList==null)
            return null;

        int max= studentList.size();

        int choice= (int)Math.random() * (max) +0;
        _currentStudent= studentList.get(choice);

        return  _currentStudent;

    }

    public int GetQuestionAsked(Student s)
    {
        if(_class==null)
            return 0;

        List<Student> studentList= _class.GetStudents();

        if(studentList==null)
            return 0;

        ArrayList<Integer> ratings= _studentRatings.get(s);
        int total=0;

        for(Integer i : ratings)
            total+=i;

        return total;

    }

    public void AssignRating(eRating rating)
    {
        //based on current student so GUI doesn't have to keep track of
        if(_currentStudent!=null)
        {
            ArrayList<Integer> ratings = _studentRatings.get(_currentStudent);
            int index = rating.value;
            ratings.set(index, ratings.get(index) + 1);
        }
    }
    public int GetRating(Student s, eRating rating)
    {
        int amnt = 0;

        if(_studentRatings.containsKey(s))
        {
            ArrayList<Integer> ratings = _studentRatings.get(s);
            amnt= ratings.get(rating.value);
        }
        return amnt;
    }
    public void OnClose()
    {
        FileManager fc= new FileManager(GetDate()+"_"+_class.GetClassName());
       try
           { fc.CreateSessionFile(_class, this );}
       catch(Exception e )
           {System.out.println("Error writing file");}
    }
}
