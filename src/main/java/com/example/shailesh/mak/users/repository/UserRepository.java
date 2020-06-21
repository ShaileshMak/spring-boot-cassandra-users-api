package com.example.shailesh.mak.users.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.shailesh.mak.users.model.User;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {
	
	@AllowFiltering
	List<User> findByAgeGreaterThan(int age);
	
}
