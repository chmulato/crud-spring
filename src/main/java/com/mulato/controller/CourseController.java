package com.mulato.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mulato.model.Course;
import com.mulato.repository.CourseRepository;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
    private CourseRepository courseRepository;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity <Course> findById(@PathVariable("id") Long id) {
        return courseRepository.findById(id)
            .map(courseFound -> ResponseEntity.ok().body(courseFound))
            .orElse(ResponseEntity.notFound().build());
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        return courseRepository.save(course);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity <Course> update(@PathVariable("id") @NotNull @Positive Long id, 
        @RequestBody @Valid Course course) {
        return courseRepository.findById(id)
            .map(courseFound -> {
                courseFound.setName(course.getName());
                courseFound.setCategory(course.getCategory());
                Course updated = courseRepository.save(courseFound);
                return ResponseEntity.ok().body(updated);
            })
            .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") @NotNull @Positive Long id) {
        return courseRepository.findById(id)
        .map(courseFound -> {
            courseRepository.delete(courseFound);
            return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
}
}
