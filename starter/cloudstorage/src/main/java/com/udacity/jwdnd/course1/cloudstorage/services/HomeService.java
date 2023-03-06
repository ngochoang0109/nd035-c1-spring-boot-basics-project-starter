package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.CredentialRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.request.FileRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.request.NoteRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.CredentialResponse;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.NoteResponse;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class HomeService {

    private UserMapper userMapper;
    private NoteMapper noteMapper;
    private FileMapper fileMapper;
    private CredentialMapper credentialMapper;
    private HashService hashService;
    private EncryptionService encryptionService;

    public HomeService(UserMapper userMapper, NoteMapper noteMapper, FileMapper fileMapper,
                       CredentialMapper credentialMapper, HashService hashService,
                       EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
        this.fileMapper = fileMapper;
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    @Transactional
    public List<NoteResponse> addNewNote(NoteRequest noteRequest, String username){
        List<NoteResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        String title = noteRequest.getTitle();
        if (title != null && users != null){
            int row = noteMapper.addNewNote(noteRequest, users.getUserid());
            if (row > 0){
                result = noteMapper.findNoteByUserid(users.getUserid());
            }
        }
        return result;
    }

    public List<NoteResponse> viewNote(String username){
        List<NoteResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        if (users != null){
            result = noteMapper.findNoteByUserid(users.getUserid());
        }
        return result;
    }

    public NoteResponse getNote(int noteId, String username) throws Exception {
        Users users = userMapper.findUserByUsername(username);
        if (users == null) {
            throw new Exception();
        }
        NoteResponse noteResponse = noteMapper.getNote(noteId, users.getUserid());
        if (noteResponse == null){
            throw new BadHttpRequest();
        }
        return noteResponse;
    }

    @Transactional
    public List<NoteResponse> editNote(NoteRequest noteRequest, String username) throws DataAccessException {
        List<NoteResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        String title = noteRequest.getTitle();
        if (title != null && users != null){
            int row = noteMapper.editNote(noteRequest, users.getUserid());
            if (row > 0){
                result = noteMapper.findNoteByUserid(users.getUserid());
            }
        }
        return result;
    }

    public List<NoteResponse> deleteNote(int noteId, String username) throws Exception   {
        List<NoteResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        if (users == null) {
            throw new Exception();
        }
        int row = noteMapper.deleteNote(noteId, users.getUserid());
        if (row > 0){
            result = noteMapper.findNoteByUserid(users.getUserid());
        }
        return result;
    }

    @Transactional
    public void uploadFile(FileRequest fileRequest, String username){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Users users = userMapper.findUserByUsername(username);
            if (users == null) {
                return;
            }
            byte[] fileToByteArray = fileRequest.getMultipartFile().getBytes();
            long sizeOfFile = fileRequest.getMultipartFile().getSize();
            String fileName =  formatter.format(now)+" - "+     fileRequest.getMultipartFile().getOriginalFilename() ;
            String contentType = fileRequest.getMultipartFile().getContentType();
            fileMapper.insertFileUpload(fileName, contentType,sizeOfFile, fileToByteArray, users.getUserid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FileResponse> getAllFile(String username) throws Exception {
        List<FileResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        if (users == null) {
            throw new Exception("Username not found!!");
        }
        result = fileMapper.getAllFile(users.getUserid());
        return result;
    }

    public void deleteFile(int fileId, String username) throws Exception {
        Users users = userMapper.findUserByUsername(username);
        if (users == null) {
            throw new Exception("Username not found!!");
        }
        fileMapper.deleteFile(fileId, users.getUserid());
    }

    public FileResponse downloadFile(int fileid, String username) throws Exception {
        Users users = userMapper.findUserByUsername(username);
        if (users == null) {
            throw new Exception("Username not found!!");
        }
        return fileMapper.getFile(fileid, users.getUserid());
    }

    public List<CredentialResponse> viewCredentials(String username){
        List<CredentialResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        if (users != null){
            result = credentialMapper.findCredentialByUserid(users.getUserid());
        }
        return result;
    }

    @Transactional
    public List<CredentialResponse> addNewCredentials(CredentialRequest credentialRequest, String username){
        List<CredentialResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        if (users != null){
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credentialRequest.getPassword(), encodedKey);
            credentialRequest.setKey(encodedKey);
            credentialRequest.setPassword(encryptedPassword);
            int row = credentialMapper.addNewCredential(credentialRequest, users.getUserid());
            if (row > 0){
                result = credentialMapper.findCredentialByUserid(users.getUserid());
            }
        }
        return result;
    }

    public CredentialResponse getCredential(int credentialId, String username) throws Exception {
        Users users = userMapper.findUserByUsername(username);
        if (users == null) {
            throw new Exception();
        }
        CredentialResponse credentialResponse = credentialMapper.getCredential(credentialId, users.getUserid());
        String decryptPassword = encryptionService.decryptValue(credentialResponse.getPassword(), credentialResponse.getKey());
        credentialResponse.setPassword(decryptPassword);
        if (credentialResponse == null){
            throw new BadHttpRequest();
        }
        return credentialResponse;
    }

    @Transactional
    public List<CredentialResponse> editCredential(CredentialRequest credentialRequest, String username) throws DataAccessException {
        List<CredentialResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        if (users != null){
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credentialRequest.getPassword(), encodedKey);
            credentialRequest.setKey(encodedKey);
            credentialRequest.setPassword(encryptedPassword);
            int row = credentialMapper.editCredential(credentialRequest, users.getUserid());
            if (row > 0){
                result = credentialMapper.findCredentialByUserid(users.getUserid());
            }
        }
        return result;
    }

    public List<CredentialResponse> deleteCredential(int credentialId, String username) throws Exception   {
        List<CredentialResponse> result = new ArrayList<>();
        Users users = userMapper.findUserByUsername(username);
        if (users == null) {
            throw new Exception();
        }
        int row = credentialMapper.deleteCredential(credentialId, users.getUserid());
        if (row > 0){
            result = credentialMapper.findCredentialByUserid(users.getUserid());
        }
        return result;
    }
}
