package io.students.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.students.StudentModel;


@Repository
public interface StudentRepository extends PagingAndSortingRepository <StudentModel, Integer> {
	@Query(value = "SELECT * from student " +
			"WHERE admissionYear >= :admissionYearAfter "+
			"AND admissionYear <= :admissionYearBefore "+
			"AND active IN (:actives) "+
			"AND class IN (:classes)", nativeQuery = true)
	
	public Page<StudentModel> findWithClasses(@Param("admissionYearAfter") Integer admissionYearAfter, 
			@Param("admissionYearBefore") Integer admissionYearBefore,
			@Param("actives") List<Boolean> actives, @Param("classes") List<Integer> classes , Pageable pageable);
	
	@Query(value = "SELECT * from student " +
			"WHERE admissionYear >= :admissionYearAfter "+
			"AND admissionYear <= :admissionYearBefore "+
			"AND active IN (:actives)",nativeQuery = true)
	public Page<StudentModel> findWithoutClasses(@Param("admissionYearAfter") Integer admissionYearAfter, 
			@Param("admissionYearBefore") Integer admissionYearBefore,
			@Param("actives") List<Boolean> actives, Pageable pageable);
}
