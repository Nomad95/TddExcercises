package studentList.services.diary;

import studentList.model.DiaryEntry;
import studentList.model.Student;

import java.time.LocalDate;
import java.util.List;

public interface Diary {
    List<Student> getStudents();

    void addEntry(LocalDate now, List<Student> presentStudents);

    List<DiaryEntry> getDiaryEntries();

    int countAbsenceForStudent(Student student);
}
