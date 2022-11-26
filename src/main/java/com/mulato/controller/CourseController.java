package com.mulato.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

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
    public Course create(@RequestBody Course course) {
        return courseRepository.save(course);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity <Course> update(@PathVariable("id") Long id, @RequestBody Course course) {
        return courseRepository.findById(id)
            .map(courseFound -> {
                courseFound.setName(course.getName());
                courseFound.setCategory(course.getCategory());
                Course updated = courseRepository.save(courseFound);
                return ResponseEntity.ok().body(updated);
            })
            .orElse(ResponseEntity.notFound().build());

    }
}
