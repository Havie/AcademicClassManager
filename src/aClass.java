import java.util.*;


public class aClass
{
    private String _className;
    private List<Student> _students;

    public aClass(String name)
    {
        _className=name;
        _students= new LinkedList<Student>();

    }

    public aClass(String name, List<Student> students)
    {
        _className=name;
        _students=students;
    }


    //METHODS
    public void Add(Student s)
    {
        if(!_students.contains(s))
            _students.add(s);
    }
    public void Remove(Student s)
    {
        if(_students.contains(s))
            _students.remove(s);
    }
    public List<Student> GetStudents()
    {
        return _students;
    }
    public String GetClassName()
    {
        return _className;
    }

}
