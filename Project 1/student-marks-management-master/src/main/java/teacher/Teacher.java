package teacher;

import java.util.List;

import group.Group;
import module.Module;

public class Teacher {
	private Integer id;
	private String fullName;
	private Grade grade;
	private List<Module> listModules;
	private List<Group> listGroup;

	public Teacher(Integer id, String fullName, Grade grade, List<Module> listModules, List<Group> listGroups) {
		this.id = id;
		this.fullName = fullName;
		this.grade = grade;
		this.listModules = listModules;
		this.listGroup = listGroups;
	}

	public Integer getId() {
		return id;
	}

	public String getfullName() {
		return fullName;
	}

	public Grade getGrade() {
		return grade;
	}

	public List<Module> getModules() {
		return listModules;
	}

	public List<Group> getGroups() {
		return listGroup;
	}

	@Override
	public String toString() {
		return "Teacher{" + "id=" + id + ", fullName='" + fullName + '\'' + ", grade=" + grade + ", listModules="
				+ listModules + ", listGroup=" + listGroup + '}';
	}
}
