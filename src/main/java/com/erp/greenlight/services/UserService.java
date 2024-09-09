package com.erp.greenlight.services;

import com.erp.greenlight.exception.InternalServerErrorException;
import com.erp.greenlight.models.Admin;
import com.erp.greenlight.DTOs.UserDto;
import com.erp.greenlight.models.Role;
import com.erp.greenlight.repositories.AdminRepository;
import com.erp.greenlight.repositories.RoleRepository;
import com.erp.greenlight.security.AppUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements  UserDetailsService  {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

/*    public UserDto updatePassword(String newPassword)  {
            AppUser currentUser = getCurrentAuthUser();

            if(Objects.equals(currentUser.getPassword(), passwordEncoder.encode( newPassword))){
                currentUser.setPassword(newPassword);
              return  UserMapper.entityToDto(userRepository.save(currentUser));
            }
        throw new InternalServerErrorException("password does not match old password");
    }*/


    public List<Admin> findAll() {
        return adminRepository.findAll();
    }


/*
    public UserDto getMyProfile() {
        return UserMapper.entityToDto(getCurrentAuthUser());
    }

    @Override
    public UserDto updateProfile(UserDto newData) {

        AppUser currentUser = getCurrentAuthUser();

        currentUser.setFirstName(newData.getFirstName());
        currentUser.setLastName(newData.getLastName());
        currentUser.setDateOfBirth(newData.getDateOfBirth());
        // currentUser.setNationality(newData.getNationality());

        return UserMapper.entityToDto(userRepository.save(currentUser));
    }

    @Override
    public void updateProfileImage(MultipartFile file) throws IOException {

        AppUser currentUser = getCurrentAuthUser();
        String fileName = FileUtils.SaveFileAndGetName(file, currentUser.getUsername());
        currentUser.setProfileImage(fileName);

        userRepository.save(currentUser);
    }
*/

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Admin> appUser =	adminRepository.findByUsername(userName);
        if (!appUser.isPresent()) {
            throw new UsernameNotFoundException("This User Not found with selected user name :- " + userName);
        }
        return new AppUserDetail(appUser.get());
    }

    public Admin getCurrentAuthUser() {
        AppUserDetail user = (AppUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Admin(user.getId());
    }

   public Admin save(UserDto user) {
        Optional<Admin> admin = adminRepository.findByUsername(user.getUsername());
        if(admin.isPresent()){
            throw new InternalServerErrorException("This User is already exist");
        }else{

            Admin newAdmin = new Admin();

            newAdmin.setName(user.getName());
            newAdmin.setEmail(user.getEmail());
            newAdmin.setUsername(user.getUsername());
            newAdmin.setPassword(passwordEncoder.encode(user.getPassword()));

            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findById(user.getRole()).orElseThrow());
            newAdmin.setRoles(roles);

            return adminRepository.save(newAdmin);
        }
    }


    public Admin update(UserDto user) {
        Optional<Admin> admin = adminRepository.findById(user.getId());
        if(admin.isPresent()){
            Admin newAdmin = admin.get();

            newAdmin.setName(user.getName());
            newAdmin.setEmail(user.getEmail());
            newAdmin.setUsername(user.getUsername());
            newAdmin.setPassword(passwordEncoder.encode(user.getPassword()));

            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findById(user.getRole()).orElseThrow());
            newAdmin.setRoles(roles);

            return adminRepository.save(newAdmin);


        }else{

            throw new InternalServerErrorException("This User is NOt Found");
        }
    }

}
