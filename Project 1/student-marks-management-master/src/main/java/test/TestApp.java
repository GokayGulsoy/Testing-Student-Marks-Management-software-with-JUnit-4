package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import app.Main;
import group.Group;
import group.GroupName;
import group.GroupService;
import mark.Mark;
import mark.MarkService;
import module.Module;
import module.ModuleName;
import module.ModuleService;
import student.Student;
import student.StudentService;
import teacher.Grade;
import teacher.Teacher;
import teacher.TeacherService;

public class TestApp {

	private GroupService groupService;
	private StudentService studentService;
	private ModuleService moduleService;
	private TeacherService teacherService;
	private MarkService markService;

	@Before
	public void initializeServices() {
		// initializing groupService
		groupService = new GroupService();
		GroupName nameGroup[] = { GroupName.MIAD, GroupName.MSIA, GroupName.MSIR };

		for (GroupName groupName : nameGroup) {
			groupService.saveGroup(groupName);
		}

		// initializing studentService
		studentService = new StudentService();
		String fullNames[] = { "sofian gasb", "amine kaci", "hamid jebri", "hanane safi" };
		LocalDate dateOfBirth[] = { LocalDate.of(2000, 1, 8), LocalDate.of(1999, 5, 11), LocalDate.of(1997, 10, 26),
				LocalDate.of(1995, 10, 26) };

		GroupName[] studGroups = { GroupName.MIAD, GroupName.MSIA, GroupName.MIAD, GroupName.MSIR };
		for (int i = 0; i < fullNames.length; i++) {
			Group group = groupService.findByReference(studGroups[i].toString());
			studentService.saveStudent(i + 1, fullNames[i], dateOfBirth[i], group);
		}

		// initializing moduleService
		moduleService = new ModuleService();
		ModuleName nameRefModules[] = { ModuleName.BDA, ModuleName.CRY, ModuleName.RI, ModuleName.GL };
		String nameModules[] = { "base de donnée avancé", "cryptographie", "réseaux Informatique", "génie logiciel" };
		Integer numberHours[] = { 40, 35, 28, 30 };
		moduleService = new ModuleService();
		for (int i = 0; i < nameRefModules.length; i++) {
			moduleService.saveModule(nameRefModules[i], nameModules[i], numberHours[i]);
		}

		// initializing teacherService
		teacherService = new TeacherService();
		String[] names = { "khalifa ahmed", "brahim gasbi" };
		GroupName[][] groupNames = { { GroupName.MIAD, GroupName.MSIA }, { GroupName.MSIR } };
		ModuleName[][] moduleName = { { ModuleName.BDA }, { ModuleName.GL, ModuleName.RI } };
		Grade[] grade = { Grade.MCA, Grade.MCB };

		for (int i = 0; i < names.length; i++) {
			List<Group> listGroups = new ArrayList<Group>();
			Integer length = groupNames[i].length;
			for (int j = 0; j < length; j++) {
				Group group = groupService.findByReference(groupNames[i][j].toString());
				listGroups.add(group);

			}

			List<Module> listModules = new ArrayList<Module>();
			length = moduleName[i].length;
			for (int j = 0; j < length; j++) {

				Module module = moduleService.findByReference(moduleName[i][j].toString());
				listModules.add(module);

			}

			teacherService.saveTeacher(i + 1, names[i], grade[i], listModules, listGroups);
		}

		// initializing markService
		markService = new MarkService();
		Integer[] idStudent = { 1, 1, 2 };
		ModuleName[] refModule = { ModuleName.BDA, ModuleName.CRY, ModuleName.CRY };
		Integer[] notes = { 15, 11, 10 };
		markService = new MarkService();
		for (int i = 0; i < idStudent.length; i++) {
			// -1 because the index list starts from 0
			Integer idx = idStudent[i] - 1;
			Student student = studentService.findById(idx);
			Module module = moduleService.findByReference(refModule[i].toString());
			markService.createMark(student, notes[i], module);

		}
	}

	@Test
	public void testCreateGroups() {
		int numOfGroups = groupService.allGroups().size();
		assertEquals(3, numOfGroups);

		for (Group group : groupService.allGroups()) {
			int numOfStudentsInGroup = group.getNumberOfStudents();
			assertEquals(0, numOfStudentsInGroup);
			assertEquals("Group{" + "reference=" + group.getReference() + '}', group.toString());
			assertEquals("Group{" + "reference=" + group.getReference() + " , number student="
					+ group.getNumberOfStudents() + '}', group.showGroup());
		}
	}

	@Test
	public void testCreateStudentsAndUpdateNumOfStudent() {
		String fullNames[] = { "sofian gasb", "amine kaci", "hamid jebri", "hanane safi" };
		LocalDate dateOfBirth[] = { LocalDate.of(2000, 1, 8), LocalDate.of(1999, 5, 11), LocalDate.of(1997, 10, 26),
				LocalDate.of(1995, 10, 26) };

		// testing findByReference, saveStudent methods
		GroupName[] studGroups = { GroupName.MIAD, GroupName.MSIA, GroupName.MIAD, GroupName.MSIR };
		int numOfCreatedStudents = studentService.allStudents().size();
		assertEquals(4, numOfCreatedStudents);

		// testing updateStudent, allStudents, getFullName, getBirthDate, getGroup,
		// Student.toString
		// methods
		Student studentToBeUpdated = studentService.allStudents().get(0);
		Group studentGroup = studentToBeUpdated.getGroup();
		studentService.updateStudent(0, "leona gibson", LocalDate.of(2001, 10, 24), studentGroup);

		studentToBeUpdated = studentService.allStudents().get(0);
		assertEquals("leona gibson", studentToBeUpdated.getFullName());
		assertEquals(LocalDate.of(2001, 10, 24), studentToBeUpdated.getBirthDate());
		assertEquals(studentGroup, studentToBeUpdated.getGroup());

		int id = studentToBeUpdated.getId();
		String fullName = studentToBeUpdated.getFullName();
		String dateBirth = studentToBeUpdated.getBirthDate().toString();
		String group = studentToBeUpdated.getGroup().toString();

		String expectedStringRepresentation = "Student{" + "id=" + id + ", fullName='" + fullName + '\''
				+ ", dateBirth=" + dateBirth + ", group=" + group + '}';

		assertEquals(expectedStringRepresentation, studentToBeUpdated.toString());

		// testing deleteStudent method
		int studentCount = 0;
		while (studentCount < 4) {
			studentService.deleteStudent(0);
			studentCount++;
		}

		assertEquals(0, studentService.allStudents().size());

		for (int i = 0; i < fullNames.length; i++) {
			Group studGroup = groupService.findByReference(studGroups[i].toString());
			studentService.saveStudent(i + 1, fullNames[i], dateOfBirth[i], studGroup);
		}

		groupService.updateNumberOfStudent(studentService.allStudents());
		assertEquals(2, (int) groupService.allGroups().get(0).getNumberOfStudents()); // MIAD
		assertEquals(1, (int) groupService.allGroups().get(1).getNumberOfStudents()); // MSIA
		assertEquals(1, (int) groupService.allGroups().get(2).getNumberOfStudents()); // MSIR

	}

	@Test
	public void testCreateModules() {
		List<Module> moduleList = moduleService.allModules();
		assertEquals("base de donnée avancé", moduleList.get(0).getName());
		assertEquals(40, (int) moduleList.get(0).getNumberHours());
		assertEquals(ModuleName.BDA, moduleList.get(0).getReference());

		assertEquals("cryptographie", moduleList.get(1).getName());
		assertEquals(35, (int) moduleList.get(1).getNumberHours());
		assertEquals(ModuleName.CRY, moduleList.get(1).getReference());

		assertEquals("réseaux Informatique", moduleList.get(2).getName());
		assertEquals(28, (int) moduleList.get(2).getNumberHours());
		assertEquals(ModuleName.RI, moduleList.get(2).getReference());

		assertEquals("génie logiciel", moduleList.get(3).getName());
		assertEquals(30, (int) moduleList.get(3).getNumberHours());
		assertEquals(ModuleName.GL, moduleList.get(3).getReference());
	}

	@Test
	public void testCreateTeachersAndDeleteTeachers() {
		List<Teacher> teacherList = teacherService.allTeachers();
		assertEquals(1, (int) teacherList.get(0).getId());
		assertEquals("khalifa ahmed", teacherList.get(0).getfullName());
		assertEquals(Grade.MCA, teacherList.get(0).getGrade());

		assertEquals(ModuleName.BDA, teacherList.get(0).getModules().get(0).getReference());

		assertEquals(GroupName.MIAD, teacherList.get(0).getGroups().get(0).getReference());
		assertEquals(GroupName.MSIA, teacherList.get(0).getGroups().get(1).getReference());
		String expectedToStringRepresentation = "Teacher{" + "id=" + teacherList.get(0).getId() + ", fullName='"
				+ teacherList.get(0).getfullName() + '\'' + ", grade=" + teacherList.get(0).getGrade()
				+ ", listModules=" + teacherList.get(0).getModules() + ", listGroup=" + teacherList.get(0).getGroups()
				+ '}';

		assertEquals(expectedToStringRepresentation, teacherList.get(0).toString());

		assertEquals(2, (int) teacherList.get(1).getId());
		assertEquals("brahim gasbi", teacherList.get(1).getfullName());
		assertEquals(Grade.MCB, teacherList.get(1).getGrade());

		assertEquals(ModuleName.GL, teacherList.get(1).getModules().get(0).getReference());
		assertEquals(ModuleName.RI, teacherList.get(1).getModules().get(1).getReference());

		assertEquals(GroupName.MSIR, teacherList.get(1).getGroups().get(0).getReference());
		expectedToStringRepresentation = "Teacher{" + "id=" + teacherList.get(1).getId() + ", fullName='"
				+ teacherList.get(1).getfullName() + '\'' + ", grade=" + teacherList.get(1).getGrade()
				+ ", listModules=" + teacherList.get(1).getModules() + ", listGroup=" + teacherList.get(1).getGroups()
				+ '}';

		assertEquals(expectedToStringRepresentation, teacherList.get(1).toString());

		int teacherCount = 2;
		while (teacherList.size() > 0) {
			teacherService.deleteTeacher(0);
			teacherCount--;
		}

		assertEquals(0, teacherCount);
	}

	@Test
	public void testCreateMarks() {
		List<Mark> markList = markService.allMarks();

		assertEquals(1, (int) markList.get(0).getStudent().getId());
		assertEquals(ModuleName.BDA, markList.get(0).getModule().getReference());
		assertEquals(15, (int) markList.get(0).getMark());
		String expectedToStringRepresentation = "Mark{" + "student=" + markList.get(0).getStudent() + ", mark="
				+ markList.get(0).getMark() + ", module=" + markList.get(0).getModule() + '}';
		assertEquals(expectedToStringRepresentation, markList.get(0).toString());

		assertEquals(1, (int) markList.get(1).getStudent().getId());
		assertEquals(ModuleName.CRY, markList.get(1).getModule().getReference());
		assertEquals(11, (int) markList.get(1).getMark());
		expectedToStringRepresentation = "Mark{" + "student=" + markList.get(1).getStudent() + ", mark="
				+ markList.get(1).getMark() + ", module=" + markList.get(1).getModule() + '}';
		assertEquals(expectedToStringRepresentation, markList.get(1).toString());

		assertEquals(2, (int) markList.get(2).getStudent().getId());
		assertEquals(ModuleName.CRY, markList.get(2).getModule().getReference());
		assertEquals(10, (int) markList.get(2).getMark());
		expectedToStringRepresentation = "Mark{" + "student=" + markList.get(2).getStudent() + ", mark="
				+ markList.get(2).getMark() + ", module=" + markList.get(2).getModule() + '}';
		assertEquals(expectedToStringRepresentation, markList.get(2).toString());
	}

	@Test
	public void testBestMarkByModuleAndFindMarkByModule() {

		Module moduleCry = markService.allMarks().get(1).getModule();
		assertEquals("sofian gasb", markService.bestMarkByModule(moduleCry).getFullName());

		List<Mark> marksByModule = markService.findMarkByModule(moduleCry);
		assertEquals(11, (int) marksByModule.get(0).getMark());
		assertEquals(10, (int) marksByModule.get(1).getMark());
	}

	@Test
	public void testBestMarkByModuleWhenNoModuleExists() {

		List<Mark> marks = markService.allMarks();
		Module moduleCry = marks.get(1).getModule();
		marks.removeAll(marks);
		assertNull(markService.bestMarkByModule(moduleCry));
	}

	@Test
	public void testFindByReferenceForNonExistingGroup() {
		List<Group> groups = groupService.allGroups();
		groups.remove(2);
		assertNull(groupService.findByReference(GroupName.MSIR.toString()));
	}

	@Test
	public void testFindByReferenceForNonExistingModule() {
		List<Module> modules = moduleService.allModules();
		modules.remove(0);
		assertNull(moduleService.findByReference(ModuleName.BDA.toString()));
	}

	@Test
	public void testMarkExceptionForLessThanFive() {

		Student student = studentService.allStudents().get(0);
		Module module = moduleService.allModules().get(0);

		markService.createMark(student, 4, module); // mark < 5
	}

	@Test
	public void testMarkExceptionForGreaterThanTwenty() {

		Student student = studentService.allStudents().get(0);
		Module module = moduleService.allModules().get(0);

		markService.createMark(student, 21, module); // mark > 21
	}

	@AfterClass
	public static void testApp() {
		Main.main(null);
	}

}
