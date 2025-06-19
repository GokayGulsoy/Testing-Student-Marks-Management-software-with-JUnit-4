package student;

import java.time.LocalDate;
import java.util.List;

import group.Group;

public interface StudentRepository {
	void saveStudent(Integer id, String fullName, LocalDate dateOfBirth, Group group);

	void deleteStudent(int idStudent);

	Student findById(Integer id);

	void updateStudent(Integer id, String fullName, LocalDate dateOfBirth, Group group);

	List<Student> allStudents();
}
