package studentList.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import studentList.services.diary.Diary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class DiaryEntry implements Comparable<DiaryEntry> {

    @Getter
    private final LocalDate dateOfClass;

    private final List<Presence> presences;

    private final Diary diary;

    public DiaryEntry(LocalDate creationDate, Diary diary, List<Student> presentStudents) {
        this.dateOfClass = creationDate;
        this.presences = new ArrayList<>(diary.getStudents().size());
        this.diary = diary;
        for (Student student : diary.getStudents()) {
            this.presences.add(new Presence(student, presentStudents.contains(student)));
        }
    }

    @Override
    public int compareTo(DiaryEntry o) {
        return dateOfClass.compareTo(o.getDateOfClass());
    }

    public void changePresenceFor(Student student, boolean isPresent) {
        ListIterator<Presence> presenceListIterator = presences.listIterator();
        while (presenceListIterator.hasNext()) {
            if (presenceListIterator.next().getStudent().equals(student)) {
                presenceListIterator.set(new Presence(student, isPresent));
            }
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public final static class Presence {
        private final Student student;
        private final boolean present;
    }

    public List<Presence> getPresences() {
        return Collections.unmodifiableList(presences);
    }
}
