package teacher;

import java.util.ArrayList;
import java.util.List;

import group.Group;
import module.Module;

public class TeacherService implements TeacherRepository {
	List<Teacher> listTeachers;

	public TeacherService() {
		this.listTeachers = new ArrayList<Teacher>();
	}

	@Override
	public void saveTeacher(Integer id, String fullName, Grade grade, List<Module> listModules, List<Group> listGroup) {
		Teacher teacher = new Teacher(id, fullName, grade, listModules, listGroup);
		listTeachers.add(teacher);

	}

	@Override
	public List<Teacher> allTeachers() {
		return listTeachers;
	}

	@Override
	public void deleteTeacher(int teacherId) {
		listTeachers.remove(teacherId);
	}
}
