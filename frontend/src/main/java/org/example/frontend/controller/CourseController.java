package org.example.frontend.controller;


import jakarta.servlet.http.HttpSession;
import org.example.frontend.model.request.CourseRequest;
import org.example.frontend.model.response.AccountResponse;
import org.example.frontend.model.response.ApiResponse;
import org.example.frontend.model.response.AuthResponse;
import org.example.frontend.model.response.CourseResponse;
import org.example.frontend.service.CourseService;
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
@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public String getAllCourse(Model model, @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                            HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }
            ApiResponse<List<CourseResponse>> apiResponse = courseService.getAllCoursesByAccount(currentPage, pageSize, userLogin.getAccessToken());

            System.out.println(apiResponse);

            List<CourseResponse> courseList = new ArrayList<>();
            Map<String, Object> metadata = new HashMap<>();

            if (apiResponse != null) {
                courseList = apiResponse.getPayload();
                metadata = apiResponse.getMetadata();
            }

            model.addAttribute("courseList", courseList);
            model.addAttribute("totalPages", metadata.get("totalPages"));
            model.addAttribute("totalElements", metadata.get("totalElements"));
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("size", pageSize);

            return "course-list";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/detail/{courseId}")
    public String getCourseById(@PathVariable("courseId") Long id, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }

            ApiResponse<CourseResponse> apiResponse = courseService.getCourseById(id, userLogin.getAccessToken());
            if (apiResponse != null) {
                model.addAttribute("course", apiResponse.getPayload());
                return "course-detail";
            }

            return "error"; // Return error view if fetching account data fails
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/add")
    public String addCourse(Model model) {
        model.addAttribute("course", new CourseResponse());
        model.addAttribute("isUpdate", false);
        return "course-update";
    }

    @PostMapping("/save")
    public String addCourse(@ModelAttribute("course") CourseRequest course, BindingResult bindingResult, Model model, HttpSession session) {
        try
        {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }

            if (bindingResult.hasErrors()) {
                return "account-update"; // Return to the form if there are validation errors
            }

            ApiResponse apiResponse = courseService.addCourse(course, userLogin.getAccessToken());
            if (apiResponse != null && apiResponse.getStatus().equals("SUCCESS"))
            {
                return "redirect:/course/list";
            }
            return "error";
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return "error";
        }
    }



}

