package teacher;

import java.util.List;

import group.Group;
import module.Module;

public interface TeacherRepository {
	void saveTeacher(Integer id, String fullName, Grade grade, List<Module> listModules, List<Group> listGroup);

	List<Teacher> allTeachers();

	void deleteTeacher(int teacherId);
}
