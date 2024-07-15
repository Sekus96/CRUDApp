package com.example.CRUDApp.services;

import com.example.CRUDApp.entities.UserEntity;
import com.example.CRUDApp.repositories.CourseRepository;
import com.example.CRUDApp.entities.Course;
import com.example.CRUDApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;


    @Override
    public String upsert(Course course, Integer id) {
        Optional<Course> findById = courseRepo.findById(course.getId());
        if(findById.isPresent()) {
            return "fail";
        } else {
            courseRepo.save(course);
        }
        return "success";
    }

    @Override
    public String addCourse(Course course) {
        Optional<Course> findById = courseRepo.findById(course.getId());
        if(findById.isPresent()) {
            return "fail";
        } else {
            courseRepo.save(course);
        }
        return null;
    }

    @Override
    public void save(Course updatedCourse) {
        courseRepo.save(updatedCourse);
    }

//    @Override
//    public UserEntity findByUsername(String username) {
//        return null;
//    }


    @Override
    public Course getById(Integer id) {
        Optional<Course> findById = courseRepo.findById(id);

        return findById.orElse(null);
    }

    @Override
    public List<Course> getAllCoursees() {
        return courseRepo.findAll();
    }

    @Override
    public Course findById(Integer id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Override
    public void updateCourse(Course course, Integer id) {
        Course courseFound = null;
        List<Course> courses=courseRepo.findById(getById(id));
        if(courses!=null){
            for(Course course1:courses){
                if(course1.getName().equals(course.getName()) && course1.getPrice() == (course.getPrice())){
                    courseFound = course1;
                }
            }
        }
        if(courseFound==null){
            courseFound = new Course();
            courseFound.setName(course.getName());
            courseFound.setPrice(course.getPrice());
            courseRepo.save(courseFound);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
//        Optional<Course> findById = courseRepo.findById(id);
//
//        if(findById.isPresent()){
//            courseRepo.deleteById(id);
//        }
//        return null;

        boolean isRemoved = false;

        if(courseRepo.existsById(id)){
            courseRepo.deleteById(id);
            isRemoved = true;
        }
        return isRemoved;
    }

    @Override
    public boolean existsById(Integer id) {
        return courseRepo.existsById(id);
    }
}
