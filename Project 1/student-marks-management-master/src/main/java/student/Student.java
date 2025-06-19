package student;

import java.time.LocalDate;

import group.Group;

public class Student {
	private Integer id;
	private String fullName;
	private LocalDate dateBirth;
	private Group group;

	public Student(Integer id, String fullName, LocalDate dateBirth, Group group) {
		this.id = id;
		this.fullName = fullName;
		this.dateBirth = dateBirth;
		this.group = group;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setDateBirth(LocalDate dateBirth) {
		this.dateBirth = dateBirth;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Integer getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public LocalDate getBirthDate() {
		return dateBirth;
	}

	public Group getGroup() {
		return group;
	}

	@Override
	public String toString() {
		return "Student{" + "id=" + id + ", fullName='" + fullName + '\'' + ", dateBirth=" + dateBirth + ", group="
				+ group + '}';
	}

}
