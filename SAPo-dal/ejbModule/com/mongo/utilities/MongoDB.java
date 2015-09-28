package com.mongo.utilities;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

/* **************************************************
 * Configuración para la conexión a mongo
 * A futuro se tomará toda la información de un xml 
 * Por ahora son constantes hardcodeadas
 * Agradecimiento especial al señor Philipp Kreen
 * https://github.com/xeraa/morphia-demo/        
 * ***************************************************/
public class MongoDB {

	public static final String DB_HOST = "ds051933.mongolab.com";
	public static final int DB_PORT = 51933;
	public static final String DB_NAME = "sapongo";
	private static final MongoDB INSTANCE = new MongoDB();
	private final Datastore datastore;

	private MongoDB() {

		Morphia morphia = new Morphia();
		ServerAddress addr = new ServerAddress("ds051933.mongolab.com", 51933);
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		MongoCredential credential = MongoCredential.createCredential("admin",
				"sapongo", "admin".toCharArray());
		credentialsList.add(credential);
		MongoClient client = new MongoClient(addr, credentialsList);

		datastore = morphia.createDatastore(client, "sapongo");

	}

	public static MongoDB instance() {
		return INSTANCE;
	}

	// Creating the mongo connection is expensive - (re)use a singleton for
	// performance reasons.
	// Both the underlying Java driver and Datastore are thread safe.
	public Datastore getDatabase() {
		return datastore;
	}
}
