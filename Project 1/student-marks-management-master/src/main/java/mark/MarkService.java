package mark;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mark.exception.MarkException;
import module.Module;
import student.Student;

public class MarkService implements MarkRepository {
	List<Mark> listMarks;

	public MarkService() {
		this.listMarks = new ArrayList<Mark>();
	}

	@Override
	public void createMark(Student student, Integer mark, Module module) {
		if (!isValid(mark)) {

			try {
				throw new MarkException("invalid mark , it should be between 5 and 20");
			}

			catch (MarkException e) {
				return;
			}
		}
		Mark markObj = new Mark(student, mark, module);
		listMarks.add(markObj);
	}

	private boolean isValid(Integer mark) {
		return mark >= 5 && mark <= 20;
	}

	@Override
	public List<Mark> allMarks() {

		return listMarks;
	}

	@Override
	public List<Mark> findMarkByModule(Module module) {
		return listMarks.stream()
				.filter(m -> m.getModule().getReference().toString().equalsIgnoreCase(module.getReference().toString()))
				.collect(Collectors.toList());

	}

	@Override
	public Student bestMarkByModule(Module module) {
		Integer val = 0;
		Integer index = null;

		for (int i = 0; i < listMarks.size(); i++) {
			if (listMarks.get(i).getMark() > val && listMarks.get(i).getModule().getReference().toString()
					.equalsIgnoreCase(module.getReference().toString())) {
				val = listMarks.get(i).getMark();
				index = i;
			}
		}

		if (index != null)
			return listMarks.get(index).getStudent();

		return null;
	}

}
