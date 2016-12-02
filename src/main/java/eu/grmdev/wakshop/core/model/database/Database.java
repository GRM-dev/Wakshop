package eu.grmdev.wakshop.core.model.database;

import java.io.File;

import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.FluentFactoryBuilder;
import com.github.fluent.hibernate.cfg.HibernateProperties;
import com.github.fluent.hibernate.cfg.HibernateProperties.Hbm2DllAuto;

import eu.grmdev.wakshop.core.model.Config;
import eu.grmdev.wakshop.core.model.Workshop;

public class Database {
	private static String filename = "data.db";
	
	public Database() throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		@SuppressWarnings("rawtypes")
		Class[] classes = new Class[]{Workshop.class, Config.class};
		FluentFactoryBuilder hib = Fluent.factory().annotatedClasses(classes).dontUseHibernateCfgXml();
		HibernateProperties props = HibernateProperties.create();
		props.connectionUrl("jdbc:sqlite:" + filename);
		props.driverClass("org.sqlite.JDBC");
		props.dialect(SQLiteDialect.class);
		props.hbm2DllAuto(Hbm2DllAuto.UPDATE);
		hib.hibernateProperties(props);
		hib.build();
	}
}
