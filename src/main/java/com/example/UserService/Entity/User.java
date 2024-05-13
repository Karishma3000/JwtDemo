package com.example.UserService.Entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
@NamedQuery(name = "findUsersBetweenDates",
			query ="SELECT u FROM User u WHERE u.creationDate BETWEEN :startDate AND :endDate")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Transient
	private List<Task> task;
	

	@Column(name = "creation_date")
	@CreationTimestamp
	private Date creationDate;
	
	@Column(name="fileName")
	private String fileName;


	

}
