package DBUnit;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;

public class InteractDBU {

	public static void inject(Connection jdbcConnection, String xmlFile) {
		try {
			IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
			connection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			IDataSet dataSet = builder.build(new File(xmlFile));
			ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
			DatabaseOperation.CLEAN_INSERT.execute(connection, replacementDataSet);
			System.out.println("injected from " + xmlFile);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void extract(Connection jdbcConnection, String xmlFile) {
		try {
			IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
			connection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
			IDataSet dataSet = connection.createDataSet();
			FlatXmlDataSet.write(dataSet, new FileOutputStream(xmlFile));
			System.out.println("extracted to " + xmlFile);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
