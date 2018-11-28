package studentList.services.diary;

import lombok.NonNull;
import studentList.model.DiaryEntry;
import studentList.services.LaboratoryClass;
import studentList.model.Student;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CollegeDiary implements Diary {

    private static final int MAXIMUM_CAPACITY = 15;
    private final List<DiaryEntry> entries = new LinkedList<>();
    private final LaboratoryClass laboratoryClass;

    public CollegeDiary(LaboratoryClass laboratoryClass) {
        this.laboratoryClass = laboratoryClass;
    }

    @Override
    public List<Student> getStudents() {
        return laboratoryClass.getStudentList();
    }

    @Override
    public void addEntry(LocalDate now, List<Student> presentStudents) {
        if (MAXIMUM_CAPACITY == entries.size()) throw new IllegalStateException("Entry list exceeded its maximum capacity!");
        if (Objects.isNull(presentStudents)) throw new IllegalArgumentException("Student cant be null");

        entries.add(new DiaryEntry(now, this, presentStudents));
    }

    @Override
    public List<DiaryEntry> getDiaryEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Override
    public int countAbsenceForStudent(@NonNull Student student) {
        List<DiaryEntry> entries = getDiaryEntries();
        int overallEntriesSize = entries.size();
        int presentCount = (int) entries.stream().flatMap(entry -> entry.getPresences().stream())
                .filter(presence -> presence.getStudent().equals(student) && presence.isPresent()).count();

        return overallEntriesSize - presentCount;
    }
}
