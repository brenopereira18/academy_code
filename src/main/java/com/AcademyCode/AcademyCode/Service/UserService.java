package com.AcademyCode.AcademyCode.Service;

import com.AcademyCode.AcademyCode.exceptions.EntityFoundException;
import com.AcademyCode.AcademyCode.exceptions.ResourceNotFoundException;
import com.AcademyCode.AcademyCode.model.UserModel;
import com.AcademyCode.AcademyCode.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel create(UserModel userModel) {
        this.userRepository.findByUsername(userModel.getUsername())
                .ifPresent(user -> {
                    throw new EntityFoundException("Usuário já existe");
                });

        return userRepository.save(userModel);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public UserModel update(UUID id, UserModel userModel) {
        UserModel userToBeUpdate = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));

        userToBeUpdate.setName(userModel.getName());
        userToBeUpdate.setUsername(userModel.getUsername());
        userToBeUpdate.setPassword(userModel.getPassword());

        return userRepository.save(userToBeUpdate);
    }

    public void delete(UUID id) {
        UserModel userToBeDeleted = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));

        userRepository.delete(userToBeDeleted);
    }
}
