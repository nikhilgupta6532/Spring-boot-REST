package com.nikhilgupta.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.consistency.ScanConsistency;

public class CouchbaseProject {

	public static void main(String[] args) {
		CouchbaseEnvironment environment = DefaultCouchbaseEnvironment.builder()
				.connectTimeout(10000)
				.build();
		
		Cluster cluster = CouchbaseCluster.create(environment,"couchbase://localhost:8091");
		
		Bucket bucket = cluster.openBucket("firstBucket","nikhil");
		
		JsonObject person = JsonObject.create();
		person.put("firstname", "Nikhil");
		person.put("lastname", "Gupta");
		
		JsonArray socialMedia = JsonArray.create();
		socialMedia.add(JsonObject.create().put("title","Twitter").put("link", "https://www/twitter.com"));
		socialMedia.add(JsonObject.create().put("title","Github").put("link", "https://www/Github.com"));
		person.put("socialMedia", socialMedia);
		
		JsonDocument document = JsonDocument.create("nikhil",person);
		
		bucket.upsert(document);
		
		System.out.println(bucket.get("nikhil").content());
		
		bucket.query(
				N1qlQuery.parameterized("upsert into firstBucket (key,value) values($id,{'firstname':$firstname,'lastname':$lastname})",
						JsonObject.create().put("id", "nik").put("firstname", "nik").put("lastname", "gup"))
				);
		
		N1qlQueryResult result = bucket.query(
				N1qlQuery.parameterized("select firstBucket.* from firstBucket where META().id=$id",
						JsonObject.create().put("id", "nik"),
						N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS))
				);
		
		for(N1qlQueryRow row : result) {
			System.out.println(row);
		}
	}

}
