package com.example.UserService.Repository;

import com.example.UserService.Entity.Password;
import com.example.UserService.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {


	public Optional<User> findByemail(String email);




	public Optional<List<User>> findByCreationDateBetween(Date startDate, Date endDate);


	//@Query("SELECT u FROM User u WHERE u.creationDate BETWEEN :startDate AND :endDate")
	//@Query(value = "SELECT * FROM User  WHERE creationDate BETWEEN startDate AND endDate",nativeQuery = true)
	@Query(name = "findUsersBetweenDates")
	List<User> findUsersBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
