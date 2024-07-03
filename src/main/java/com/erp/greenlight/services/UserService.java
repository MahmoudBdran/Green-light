package com.erp.greenlight.services;

import com.erp.greenlight.models.Admin;
import com.erp.greenlight.repositories.AdminRepository;
import com.erp.greenlight.security.AppUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements  UserDetailsService  {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

/*    public UserDto updatePassword(String newPassword)  {
            AppUser currentUser = getCurrentAuthUser();

            if(Objects.equals(currentUser.getPassword(), passwordEncoder.encode( newPassword))){
                currentUser.setPassword(newPassword);
              return  UserMapper.entityToDto(userRepository.save(currentUser));
            }
        throw new InternalServerErrorException("password does not match old password");
    }*/


/*    public List<UserDto> findAll() {
        List<UserDto> result = new ArrayList<>();
        userRepository.findAll().forEach(e-> result.add(UserMapper.entityToDto(e)));
        return result;
    }*/


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
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminRepository.findByUsername( user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("This User Not found with selected user name :- " + user.getUsername()));
    }

/*    public AppUser save(UserDto registerRequest) {
        Optional<AppUser> user = userRepository.findUserByEmail(registerRequest.getEmail());
        if(user.isPresent()){
            throw new DuplicateRecordException("This Email is already exist");
        }else{
            registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            return userRepository.save(UserMapper.dtoToEntity( registerRequest));
        }
    }*/

}
