package com.mongo.utilities;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.entities.mongo.BaseEntity;

import java.util.logging.*;
/* **************************************************
 * Configuración para la conexión a mongo
 * A futuro se tomará toda la información de un xml 
 * Por ahora son constantes hardcodeadas
 * Agradecimiento especial al señor Philipp Kreen
 * https://github.com/xeraa/morphia-demo/        
 * ***************************************************/
public class MongoDB {

	  public static final String DB_HOST = "127.0.0.1";
	  public static final int DB_PORT = 27017;
	  public static final String DB_NAME = "SAPo_NoSQL";
	  private static final MongoDB INSTANCE = new MongoDB();
	  private static final Logger LOG = Logger.getLogger(MongoDB.class.getName());	  
	  private final Datastore datastore;
	  
	  private MongoDB() {
		  
		    MongoClientOptions mongoOptions = MongoClientOptions.builder()
			.socketTimeout(60000) // Wait 1m for a query to finish, https://jira.mongodb.org/browse/JAVA-1076
			.connectTimeout(15000) // Try the initial connection for 15s, http://blog.mongolab.com/2013/10/do-you-want-a-timeout/
			.maxConnectionIdleTime(600000) // Keep idle connections for 10m, so we discard failed connections quickly
			.readPreference(ReadPreference.primaryPreferred()) // Read from the primary, if not available use a secondary
			.build();
		    MongoClient mongoClient;
		    
		    mongoClient = new MongoClient(new ServerAddress(DB_HOST, DB_PORT), mongoOptions);

		    mongoClient.setWriteConcern(WriteConcern.SAFE);
		    datastore = new Morphia().mapPackage(BaseEntity.class.getPackage().getName())
			.createDatastore(mongoClient, DB_NAME);
		    datastore.ensureIndexes();
		    datastore.ensureCaps();
		    LOG.info("Connection to database '" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "' initialized");
		  }

		  public static MongoDB instance() {
		    return INSTANCE;
		  }

		  // Creating the mongo connection is expensive - (re)use a singleton for performance reasons.
		  // Both the underlying Java driver and Datastore are thread safe.
		  public Datastore getDatabase() {
		return datastore;
	}
}
