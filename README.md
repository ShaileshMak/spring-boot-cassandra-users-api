# usersApi
Spring boot micro service with Cassandra

#Install the Cassandra
Install the cassandra Instance if you don't have. Follow the instruction as mentioned int this link
https://cassandra.apache.org/download/

#Create KeySpace 
Create keySpace and configure in the application.properties file

#Cretate Table
Create table in newly created keyspace.
  #Schema
   use <KEYSPACE>;
    CREATE TABLE user (
      id timeuuid PRIMARY KEY,
      age int,
      city text,
      firstname text,
      lastname text
    )

#Start the Cassandra Instance
Use the step mention in this link https://docs.datastax.com/en/cql-oss/3.3/cql/cql_using/startCqlLinuxMac.html

#Run the App
Run the application and access the API using rest client
