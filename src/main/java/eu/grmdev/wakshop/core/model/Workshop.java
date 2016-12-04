package eu.grmdev.wakshop.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.fluent.hibernate.H;

import lombok.Data;

@Entity
@Table(name = "workshops")
@Data
public class Workshop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "f_title", unique = true, nullable = false)
	private String title;
	@Column(name = "f_archieved")
	private boolean archieved;
	@Column(name = "f_chat_on")
	private boolean chatEnabled;
	@Column(name = "f_file_transfer_on")
	private boolean fileTransferEnabled;
	
	public static Map<Integer, Workshop> getAllWorkshops() {
		List<Object> listO = H.request(Workshop.class).list();
		Map<Integer, Workshop> map = new HashMap<>();
		for (Object object : listO) {
			Workshop w = (Workshop) object;
			map.put(w.getId(), w);
		}
		return map;
	}
	
	public static boolean exists(String title) {
		int count = H.request(Workshop.class).eq("title", title).count();
		return count > 0;
	}
	
	public static Workshop save(Workshop w) {
		if (H.getById(Workshop.class, w.getId()) == null) {
			return H.save(w);
		}
		else {
			H.saveOrUpdate(w);
			return w;
		}
	}
	
	public static void remove(Workshop workshop) {
		H.delete(workshop);
	}
}
