package com.project.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.Employee;
import com.project.demo.exception.ResourceNotFound;
import com.project.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="http://localhost:4200")
public class EmployeeController {
	@Autowired
	public EmployeeRepository repository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployee()
	{
		return repository.findAll();	
	}
	  
	@PostMapping("/employees")
	public Employee saveEmployee(@RequestBody Employee employee)
	{
	  return repository.save(employee);
	}
	  
	  @GetMapping("/employees/{id}")
	  public ResponseEntity<Employee> getEmployeeById(@PathVariable int id)
	  {
		  Employee employee=repository.findById(id).orElseThrow(()-> new ResourceNotFound("No Record Found With This ID "+id));
		  return ResponseEntity.ok(employee);
	  }
	  
	  @PutMapping("/employees/{id}")
	  public ResponseEntity<Employee> updateEmployee (@PathVariable int id,@RequestBody Employee employee)
	{
		  Employee employee2=repository.findById(id)
				  .orElseThrow(() -> new ResourceNotFound("No Record Found  With This ID "+id));
		   employee2.setName(employee.getName());
		   employee2.setAddress(employee.getAddress());
		   employee2.setSalary(employee.getSalary());
		   Employee updateEmployee=repository.save(employee2);  
		   return ResponseEntity.ok(updateEmployee);
	}
	  @DeleteMapping("/employees/{id}")
	  public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable int id)
	  {
		  Employee employee=repository.findById(id)
				  .orElseThrow(() -> new ResourceNotFound("No Record Found  With This ID "+id));
		  repository.delete(employee);
		  Map<String,Boolean>  response=new HashMap<>();
		  response.put("deleted", Boolean.TRUE);
		  return ResponseEntity.ok(response);
		  

	  }
	    
}
