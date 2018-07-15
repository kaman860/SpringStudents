package io.students.controller;

import io.students.repository.StudentRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import io.students.StudentModel;
 
@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/students")
	public ResponseEntity<?> getDetails(@RequestParam Map<String, String> arguments,
			@PageableDefault(size=20, page=0) Pageable pageable) {
		
		Integer admissionYearAfter = 1000, admissionYearBefore = 9999;
		List<Boolean> actives = Arrays.asList(true, false);
		boolean areClassesPresent = false;
		List<Integer> classes = new ArrayList<Integer>();

		
		String admissionYearAfterParameter = arguments.get("admissionYearAfter");
		if(admissionYearAfterParameter != null) {

			if(!isProperAdmissionYear(admissionYearAfterParameter))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			admissionYearAfter = Integer.parseInt(admissionYearAfterParameter);
		}
		
		String admissionYearBeforeParameter = arguments.get("admissionYearBefore");
		if(admissionYearBeforeParameter != null) {
			if(!isProperAdmissionYear(admissionYearBeforeParameter))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			admissionYearBefore = Integer.parseInt(admissionYearBeforeParameter);
		}
		
		String activeParameter = arguments.get("active");
		if(activeParameter != null) {
			if(!isProperActive(activeParameter))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			actives = Arrays.asList(Boolean.parseBoolean(activeParameter));
		} 
		
		String classesParameter = arguments.get("classes");
		if(classesParameter != null) {
			areClassesPresent = true;
			String[] params = classesParameter.split(",");
			for(String param: params) {
				if(!isProperClass(param))
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				classes.add(Integer.parseInt(param));
			}
		}
		
		try {
			if(areClassesPresent)
				return new ResponseEntity<>(studentRepository.findWithClasses(admissionYearAfter, admissionYearBefore,
						actives,classes, pageable), HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<>(studentRepository.findWithoutClasses(admissionYearAfter, admissionYearBefore,
						actives, pageable), HttpStatus.ACCEPTED);
		} catch(Exception exc) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/students/{id}")
	public ResponseEntity<StudentModel> getParticularDetails(@PathVariable Integer id) {
		Optional<StudentModel> student = studentRepository.findById(id);
		if(student.isPresent())
			return new ResponseEntity<>(student.get(), HttpStatus.ACCEPTED); 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	boolean isProperActive(String active) {
		try {
			Boolean.parseBoolean(active);
			return true;
		} catch(Exception exc) {
			return false;
		}
	}
	
	boolean isProperAdmissionYear(Integer year) {
		try {
			return year >= 1000 && year<=9999;
		} catch(Exception exc) {
			return false;
		}
	}
	
	boolean isProperAdmissionYear(String year) {
		try {
			return isProperAdmissionYear(Integer.parseInt(year));
		} catch(Exception exc) {
			return false;
		}
	}
	
	boolean isProperName(String name) {
		return name.trim().length() > 0;
	}
	
	boolean isProperClass(Integer studentClass) {
		return studentClass > 0;
	}
	
	boolean isProperClass(String studentClass) {
		try {
			return isProperClass(Integer.parseInt(studentClass));
		} catch(Exception exc) {
			return false;
		}
	}
	
	boolean isProperStudent(StudentModel student) {
		return isProperName(student.getName()) && isProperAdmissionYear(student.getAdmissionYear())
				&& isProperClass(student.getStudentClass());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/students")
	public ResponseEntity<?> addStudent(@RequestBody StudentModel argStudent) {
		try {
			if(!isProperStudent(argStudent))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			studentRepository.save(argStudent);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception exc) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/students")
	public ResponseEntity<?> updateStudent(@RequestBody StudentModel argStudent) {
		Optional<StudentModel> student = studentRepository.findById(argStudent.getId());
		if(student.isPresent()) {
			try {
				if(!isProperStudent(argStudent))
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				studentRepository.save(argStudent);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			} catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		Optional<StudentModel> student = studentRepository.findById(id);
		
		if(student.isPresent()) {
			StudentModel givenStudent = student.get();
			givenStudent.setActive(false);
			studentRepository.save(givenStudent);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	

}
