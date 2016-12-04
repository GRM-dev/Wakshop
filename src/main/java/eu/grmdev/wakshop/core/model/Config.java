package eu.grmdev.wakshop.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "config")
public class Config {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "f_save_dir", nullable = false)
	private String saveDirPath;
	@Column(name = "f_save_name", nullable = false)
	private boolean saveName;
	@Column(name = "f_default_port", nullable = false)
	private int defaultPort;
	
	public static Config getDefaultConfig() {
		Config c = new Config();
		c.setSaveDirPath("");
		c.setSaveName(true);
		c.setDefaultPort(8081);
		return c;
	}
}
