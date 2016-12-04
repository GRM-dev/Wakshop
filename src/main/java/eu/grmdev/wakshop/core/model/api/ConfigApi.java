package eu.grmdev.wakshop.core.model.api;

import com.github.fluent.hibernate.H;

import eu.grmdev.wakshop.core.model.Config;

public class ConfigApi {
	public synchronized Config getConfig() {
		Object object = H.request(Config.class).first();
		if (object != null) {
			return (Config) object;
		}
		else {
			object = Config.getDefaultConfig();
			object = H.save(object);
			return (Config) object;
		}
	}
	
	public synchronized void save(Config config) {
		H.saveOrUpdate(config);
	}
}
