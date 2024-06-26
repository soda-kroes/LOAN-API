package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.entity.UserRole;
import com.java.year3.loan_api.exception.AlreadyExistException;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.repository.UserRoleRepository;
import com.java.year3.loan_api.services.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserRole create(UserRole userRoleRequest) throws Exception {
        UserRole userRole= new UserRole();
        userRole.setName(userRoleRequest.getName());

        //check exists name
        boolean find = userRoleRepository.existsByName(userRoleRequest.getName());
        if (find){
            throw new AlreadyExistException("Name Role is not available.");
        }
        try {
            return userRoleRepository.save(userRole);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    @Override
    public UserRole getById(Long id) throws Exception {
        return userRoleRepository.findById(id).orElseThrow(()-> new NotFoundException("User Role with id " + id + " not found."));
    }

    @Override
    public List<UserRole> getAll() {
        return userRoleRepository.findAll();
    }
}
