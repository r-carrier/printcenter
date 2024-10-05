package com.solu.printcenter.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

public class AppInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AppInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();

        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                parameters.put(paramName, request.getParameter(paramName));
            }
        }

        if (request instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();

            while (fileNames.hasNext()) {
                String fileName = fileNames.next();
                List<MultipartFile> files = multipartHttpServletRequest.getFiles(fileName);

                List<String> fileNamesList = new ArrayList<>();
                for (MultipartFile file : files) {
                    if (file != null) {
                        fileNamesList.add(file.getOriginalFilename());
                    }
                }
                parameters.put(fileName, String.join(", ", fileNamesList));
            }
        }

        log.info("Request URI: {}", request.getRequestURI());
        if (parameters.isEmpty()) {
            log.info("There are no parameters in this request");
        } else {
            log.info("Request parameters: {}", parameters);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("HTTP Status: {}", response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
