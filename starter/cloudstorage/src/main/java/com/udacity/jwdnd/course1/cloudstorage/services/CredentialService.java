package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential, int userId)
    {
        credential.setUserid(userId);
        SecureRandom random = new SecureRandom();
        byte[] key1 = new byte[16];
        random.nextBytes(key1);
        String key = Base64.getEncoder().encodeToString(key1);
        credential.setKey(key);
        String password = encryptionService.encryptValue(credential.getPassword(),credential.getKey());
        credential.setPassword(password);
        return credentialMapper.addCredential(credential);
    }

    public List<Credential> getCredentials(int userId)
    {
        return credentialMapper.getAllCredentials(userId);
    }

    public void editCredential(Credential credential, int userId)
    {
        credential.setUserid(userId);
        Credential original = credentialMapper.getCredential(credential.getCredentialid());
        String password = encryptionService.encryptValue(credential.getPassword(),original.getKey());
        original.setPassword(password);
        credentialMapper.editCredential(original);
    }

    public int deleteCredential(int credentialId)
    {
        return credentialMapper.deleteCredential(credentialId);
    }



}
