package com.example.shailesh.mak.users.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.utils.UUIDs;
import com.example.shailesh.mak.users.model.User;
import com.example.shailesh.mak.users.repository.UserRepository;

@RestController
@RequestMapping(path="/api")
public class UsersController {
	@Autowired
	private UserRepository userRepository;

	
	@PostMapping("/users")
	public ResponseEntity<User> setUsers(@RequestBody User user) {
		try {
			User _user = new User(UUIDs.timeBased(), user.getFirstName(), user.getLastName(), user.getCity(), user.getAge());
			userRepository.save(_user);
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users")
	public @ResponseBody ResponseEntity<List<User>>  getUsers() {
		try {
			List<User> users = new ArrayList<User>();
			userRepository.findAll().forEach(users::add);
			if(users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable UUID id) {
		try {
			Optional<User>_user = userRepository.findById(id);
			if(_user.isPresent()) {
				return new ResponseEntity<>(_user.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
		try {
			Optional<User>_userData = userRepository.findById(id);
			if(_userData.isPresent()) {
				User _user = _userData.get();
				_user.setFirstName(user.getFirstName());
				_user.setLastName(user.getLastName());
				_user.setAge(user.getAge());
				_user.setCity(user.getCity());
				return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getUserFilterByAge/{age}")
	public ResponseEntity<List<User>> getUserFilterByAge(@PathVariable int age) {
		try {
			List<User> users = userRepository.findByAgeGreaterThan(age);
			if(users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
