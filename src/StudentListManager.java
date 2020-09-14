import java.util.*;



public class StudentListManager
{


    List<Student> _newStudents;
    public StudentListManager()
    {
        _newStudents = new LinkedList<Student>();
    }


    public List<Student> GetStudents()
    {
        return _newStudents;
    }
    public void Add(String first, String last)
    {
        if(!Contains(first,last) && _newStudents.size()<50)
        {
            if(first.length()>1 && last.length()>1 && !first.equals("") && !last.equals("")) // no empty strings
            {
                Student s = new Student(first, last);
                _newStudents.add(s);
            }
        }
    }
    public void Clear()
    {
        _newStudents.clear();
    }
    public void Remove(String first, String last)
    {
        ContainRemove(first, last, true);
    }

    public boolean Contains(String first, String last)
    {
        return ContainRemove(first, last, false);
    }

    private boolean ContainRemove(String first, String last, boolean shouldRemove)
    {
        boolean contains=false;

        for (Student s : _newStudents)
        {
            if(s.GetLastName().equals(last))
            {
                if(s.GetFirstName().equals(first))
                {
                    contains=true;
                    if(shouldRemove)
                        _newStudents.remove(s);
                    break;
                }
            }
        }


        return contains;
    }


}
