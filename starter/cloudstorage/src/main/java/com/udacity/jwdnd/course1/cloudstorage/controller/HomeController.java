package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.FileRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHome(Model model, Authentication authentication) throws Exception {
        model.addAttribute("fileRequest", new FileRequest());
        model.addAttribute("listFile", homeService.getAllFile(authentication.getName()));
        return "home";
    }

    @RequestMapping(value = "home/upload-file", method = RequestMethod.POST)
    public String uploadFile(@ModelAttribute FileRequest request, Authentication authentication){
        homeService.uploadFile(request, authentication.getName());
        return "redirect:/home";
    }

    @RequestMapping(value = "home/delete-file/{id}", method = RequestMethod.GET)
    public String deleteFile(@PathVariable("id") int fileId, Authentication authentication){
        try {
            homeService.deleteFile(fileId, authentication.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "home/download-file/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") int fileId, Authentication authentication){
        try {
            FileResponse fileResponse = homeService.downloadFile(fileId, authentication.getName());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(fileResponse.getContenttype()))
                    .header(fileResponse.getFilename())
                    .body(fileResponse.getFiledata());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new byte[0], HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
