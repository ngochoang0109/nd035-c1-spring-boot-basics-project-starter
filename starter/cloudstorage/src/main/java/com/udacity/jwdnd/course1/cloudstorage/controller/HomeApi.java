package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.CredentialRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.request.NoteRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.CredentialResponse;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.NoteResponse;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/home/api")
public class HomeApi {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/add-note", method = RequestMethod.POST)
    public ResponseEntity<List<NoteResponse>> addNewNote(@RequestBody NoteRequest noteRequest,
                                                         Authentication authentication){
        List<NoteResponse> noteResponses = homeService.addNewNote(noteRequest, authentication.getName());
        if (noteResponses!=null){
            return new ResponseEntity<List<NoteResponse>>(noteResponses, HttpStatus.OK);
        }
        return new ResponseEntity<List<NoteResponse>>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/view-note", method = RequestMethod.GET)
    public ResponseEntity<List<NoteResponse>> viewNote(Authentication authentication){
        List<NoteResponse> noteResponses = homeService.viewNote(authentication.getName());
        return new ResponseEntity<List<NoteResponse>>(noteResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-note/{id}", method = RequestMethod.GET)
    public ResponseEntity<NoteResponse> getNote(@PathVariable("id") int noteId, Authentication authentication) throws Exception {
        NoteResponse noteResponse = homeService.getNote(noteId, authentication.getName());
        return new ResponseEntity<NoteResponse>(noteResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit-note", method = RequestMethod.POST)
    public ResponseEntity<List<NoteResponse>> editNote(@RequestBody NoteRequest noteRequest,
                                                         Authentication authentication){
        try {
            List<NoteResponse> noteResponses = homeService.editNote(noteRequest, authentication.getName());
            return new ResponseEntity<List<NoteResponse>>(noteResponses,HttpStatus.OK);
        }catch (DataAccessException exception){
            return new ResponseEntity<List<NoteResponse>>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete-note/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<NoteResponse>> deleteNote(@PathVariable("id") int noteId, Authentication authentication){
        try {
            List<NoteResponse> noteResponses = homeService.deleteNote(noteId, authentication.getName());
            return new ResponseEntity<List<NoteResponse>>(noteResponses,HttpStatus.OK);
        } catch (Exception exception ){
            return new ResponseEntity<List<NoteResponse>>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add-credential", method = RequestMethod.POST)
    public ResponseEntity<List<CredentialResponse>> addNewCredentials(@RequestBody CredentialRequest credentialRequest,
                                                         Authentication authentication){
        List<CredentialResponse> credentialResponses = homeService.addNewCredentials(credentialRequest, authentication.getName());
        if (credentialResponses!=null){
            return new ResponseEntity<List<CredentialResponse>>(credentialResponses, HttpStatus.OK);
        }
        return new ResponseEntity<List<CredentialResponse>>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/view-credential", method = RequestMethod.GET)
    public ResponseEntity<List<CredentialResponse>> viewCredentials(Authentication authentication){
        List<CredentialResponse> credentialResponses = homeService.viewCredentials(authentication.getName());
        return new ResponseEntity<List<CredentialResponse>>(credentialResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-credential/{id}", method = RequestMethod.GET)
    public ResponseEntity<CredentialResponse> getCredential(@PathVariable("id") int credentialId, Authentication authentication) throws Exception {
        CredentialResponse credentialResponse = homeService.getCredential(credentialId, authentication.getName());
        return new ResponseEntity<CredentialResponse>(credentialResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit-credential", method = RequestMethod.POST)
    public ResponseEntity<List<CredentialResponse>> editCredential(@RequestBody CredentialRequest credentialRequest,
                                                       Authentication authentication){
        try {
            List<CredentialResponse> credentialResponses = homeService.editCredential(credentialRequest, authentication.getName());
            return new ResponseEntity<List<CredentialResponse>>(credentialResponses,HttpStatus.OK);
        }catch (DataAccessException exception){
            return new ResponseEntity<List<CredentialResponse>>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete-credential/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<CredentialResponse>> deleteCredential(@PathVariable("id") int credentialId, Authentication authentication){
        try {
            List<CredentialResponse> credentialResponses = homeService.deleteCredential(credentialId, authentication.getName());
            return new ResponseEntity<List<CredentialResponse>>(credentialResponses,HttpStatus.OK);
        } catch (Exception exception ){
            return new ResponseEntity<List<CredentialResponse>>(HttpStatus.BAD_REQUEST);
        }
    }
}
