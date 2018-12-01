package studentList.services.file;

import com.google.common.io.Files;
import studentList.model.Student;
import studentList.services.LaboratoryClass;

import java.io.File;
import java.util.stream.Collectors;

public class DiaryToFileSaver implements ToFileSaver {

    public static final String SPACE = " ";
    public static final String SPACE_WITH_COMMA = ", ";

    @Override
    public void saveToFile(String path, LaboratoryClass laboratoryClass) {
        File file1 = new File(path);

        StringBuilder sb = new StringBuilder();

        for (Student student : laboratoryClass.getStudentList()) {
            sb.append("Student: ").append(student.getName()).append(SPACE).append(student.getSurname()).append(SPACE)
                    .append(student.getIndexNumber()).append("\n");
            sb.append("Grades: ");
            String grades = laboratoryClass.getGradesFor(student).stream().map(g -> String.valueOf(g.getValue()))
                    .collect(Collectors.joining(SPACE_WITH_COMMA));
            sb.append(grades).append("\n");
            sb.append("Presence: ").append("\n");
            String presences = laboratoryClass.getDiaryEntries().stream()
                    .map(entry -> entry.getDateOfClass() + SPACE + entry.getPresences().stream()
                            .filter(presence -> presence.getStudent().equals(student))
                            .map(presence -> presence.isPresent() ? "Present" : "Absent")
                            .collect(Collectors.joining()) + "\n").collect(Collectors.joining());
            sb.append(presences);
        }

        System.out.println(sb.toString());
        //Files.write();
    }
}
