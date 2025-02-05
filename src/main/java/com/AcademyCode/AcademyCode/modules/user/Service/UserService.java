package com.AcademyCode.AcademyCode.modules.user.Service;

import com.AcademyCode.AcademyCode.modules.user.DTO.UserProfileDTO;
import com.AcademyCode.AcademyCode.modules.user.DTO.UserRoleDTO;
import com.AcademyCode.AcademyCode.exceptions.EntityFoundException;
import com.AcademyCode.AcademyCode.exceptions.ResourceNotFoundException;
import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import com.AcademyCode.AcademyCode.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel register(UserModel userModel) {
        this.userRepository.findByUsername(userModel.getUsername())
                .ifPresent(user -> {
                    throw new EntityFoundException("Usuário já existe");
                });

        String encryptedPassword = new BCryptPasswordEncoder().encode(userModel.getPassword());
        userModel.setPassword(encryptedPassword);

        return userRepository.save(userModel);
    }

    public UserModel getUser(UUID id) {
        UserModel user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));

        return user;
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public UserModel updateOwnProfile(String username, UserProfileDTO userProfileDTO) {
        UserModel user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("Usuário não encontrado"));

        String encryptedPassword = new BCryptPasswordEncoder().encode(userProfileDTO.getPassword());
        user.setName(userProfileDTO.getName());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    @Transactional
    public UserModel updateUserRole(UUID id, UserRoleDTO userRoleDTO) {
        UserModel userToBeUpdate = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));

        userToBeUpdate.setRole(userRoleDTO.getRole());
        userToBeUpdate.setStatus(userRoleDTO.getStatus());

        return userRepository.save(userToBeUpdate);
    }

    public void delete(UUID id) {
        UserModel userToBeDeleted = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));

        userRepository.delete(userToBeDeleted);
    }
}
