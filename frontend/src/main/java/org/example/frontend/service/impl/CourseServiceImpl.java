package org.example.frontend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.frontend.model.request.AccountRequest;
import org.example.frontend.model.request.CourseRequest;
import org.example.frontend.model.response.AccountResponse;
import org.example.frontend.model.response.ApiResponse;
import org.example.frontend.model.response.CourseResponse;
import org.example.frontend.service.CourseService;
import org.example.frontend.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL_COURSE = ConstantUtil.HOST_URL + "/api/courses";


    @Override
    public ApiResponse<List<CourseResponse>> getAllCourses(int currentPage, int pageSize, String accessToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL_COURSE).queryParam("currentPage", currentPage).queryParam("pageSize", pageSize);

        HttpHeaders httpHeaders = createAuthHeaders(accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try{
            ResponseEntity<ApiResponse<List<CourseResponse>>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ApiResponse<List<CourseResponse>>>(){
            });

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse countAllCourses(String accessToken) {
        HttpHeaders httpHeaders = createAuthHeaders(accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(API_URL_COURSE + "/count", HttpMethod.GET, httpEntity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse getCourseById(Long id, String accessToken) {
        HttpHeaders httpHeaders = createAuthHeaders(accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try{
            ResponseEntity<ApiResponse<CourseResponse>> responseEntity = restTemplate.exchange(API_URL_COURSE + "/detail/" + id, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
            });

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse updateCourse(CourseRequest courseRequest, String accessToken) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CourseRequest> entity = new HttpEntity<>(courseRequest, headers);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(API_URL_COURSE + "/id/" + courseRequest.getCourseId(), HttpMethod.PUT, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }



    @Override
    public ApiResponse addCourse(CourseRequest courseRequest, String accessToken) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CourseRequest> entity = new HttpEntity<>(courseRequest, headers);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(API_URL_COURSE, HttpMethod.POST, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse<List<CourseResponse>> getAllCoursesByAccount(int currentPage, int pageSize,String accessToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL_COURSE+"/account-courses").queryParam("currentPage", currentPage).queryParam("pageSize", pageSize);

        HttpHeaders httpHeaders = createAuthHeaders(accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try{
            ResponseEntity<ApiResponse<List<CourseResponse>>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ApiResponse<List<CourseResponse>>>(){
            });

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private HttpHeaders createAuthHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private ApiResponse handleClientException(HttpClientErrorException ex) {
        try {
            if (ex.getStatusCode() != HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
