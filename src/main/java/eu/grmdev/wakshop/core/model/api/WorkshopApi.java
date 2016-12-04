package eu.grmdev.wakshop.core.model.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.fluent.hibernate.H;

import eu.grmdev.wakshop.core.model.Workshop;

public class WorkshopApi {
	public Map<Integer, Workshop> getAllWorkshops() {
		List<Object> listO = H.request(Workshop.class).list();
		Map<Integer, Workshop> map = new HashMap<>();
		for (Object object : listO) {
			Workshop w = (Workshop) object;
			map.put(w.getId(), w);
		}
		return map;
	}
	
	public boolean exists(String title) {
		int count = H.request(Workshop.class).eq("title", title).count();
		return count > 0;
	}
	
	public Workshop save(Workshop w) {
		if (H.getById(Workshop.class, w.getId()) == null) {
			return H.save(w);
		}
		else {
			H.saveOrUpdate(w);
			return w;
		}
	}
	
	public void remove(Workshop workshop) {
		H.delete(workshop);
	}
	
	public List<String> getAllWorkshopsTitles() {
		List<Object> list = H.request(Workshop.class).list();
		List<String> l = new ArrayList<>();
		for (Object object : list) {
			l.add(((Workshop) object).getTitle());
		}
		return l;
	}
}
