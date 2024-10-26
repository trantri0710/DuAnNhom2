/*
package org.example.frontend.controller;

import com.fsa.lms.frontend.model.request.CourseRequest;
import com.fsa.lms.frontend.model.response.ApiResponse;
import com.fsa.lms.frontend.model.response.AuthResponse;
import com.fsa.lms.frontend.model.response.CourseResponse;
import com.fsa.lms.frontend.service.CourseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/instructor")
public class InstructorController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String viewHome(Model model, @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                           @RequestParam(required = false, defaultValue = "5") Integer size, HttpSession session) {
        AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");

        ApiResponse<List<CourseResponse>> apiResponse = courseService.getByAccount(userLogin.getAccessToken(), currentPage, size);

        List<CourseResponse> courseList = null;
        Map<String, Object> metadata = null;

        if (apiResponse == null) {
            courseList = new ArrayList<>();
            metadata = new HashMap<>();
        } else {
            courseList = apiResponse.getPayload();
            metadata = apiResponse.getMetadata();
        }
        model.addAttribute("courseList", courseList);

        model.addAttribute("totalPages", metadata.get("totalPages"));
        model.addAttribute("totalElements", metadata.get("totalElements"));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", size);

        return "instructor";
    }

    @GetMapping(value = {"/course", "/course/{courseId}"})
    public String viewDetail(Model model, @PathVariable(required = false) Long courseId) {
        CourseResponse course = null;
        if (courseId == null) {
            course = new CourseResponse();
        } else {
            ApiResponse<CourseResponse> apiResponse = courseService.getCourseById(courseId);
            if (apiResponse == null) {
                return "redirect:/error";
            }

            course = apiResponse.getPayload();
        }

        model.addAttribute("course", course);

        return "course";
    }

    @PostMapping(value = "/course")
    public String submitCourse(Model model, @Valid @ModelAttribute CourseResponse courseResponse,
            BindingResult bindingResult, HttpSession session) {
        AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");

        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourseId(courseResponse.getCourseId());
        courseRequest.setName(courseResponse.getName());
        courseRequest.setDescription(courseResponse.getDescription());
        courseRequest.setImage(courseResponse.getImage());

        ApiResponse<CourseResponse> apiResponse = courseService.saveCourse(courseRequest, userLogin.getAccessToken());
        if (apiResponse == null) {
            return "redirect:/error";
        }

        return "redirect:/instructor";
    }

}
*/
