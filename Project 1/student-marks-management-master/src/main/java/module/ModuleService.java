package module;

import java.util.ArrayList;
import java.util.List;

public class ModuleService implements ModuleRepository {
	List<Module> listModules;

	public ModuleService() {
		this.listModules = new ArrayList<>();
	}

	@Override
	public void saveModule(ModuleName reference, String name, Integer numberHours) {
		Module module = new Module(reference, name, numberHours);
		listModules.add(module);
	}

	@Override
	public List<Module> allModules() {
		return listModules;
	}

	@Override
	public Module findByReference(String reference) {
		Integer index = findIndex(reference);
		if (index != null)
			return listModules.get(index);
		else
			return null;
	}

	private Integer findIndex(String reference) {
		for (int i = 0; i < listModules.size(); i++) {
			if (listModules.get(i).getReference().toString().equalsIgnoreCase(reference))
				return i;
		}
		return null;
	}
}
