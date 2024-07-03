package com.erp.greenlight.services;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import com.erp.greenlight.DTOs.AccessTokenDto;
import com.erp.greenlight.models.Admin;
import com.erp.greenlight.models.TokenInfo;
import com.erp.greenlight.repositories.AdminRepository;
import com.erp.greenlight.repositories.RoleRepository;
import com.erp.greenlight.security.AppUserDetail;
import com.erp.greenlight.security.JwtTokenUtils;
import com.erp.greenlight.security.TokenInfoService;
import com.erp.greenlight.utils.AppResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService  {

    private final AuthenticationManager authManager;
    private final AdminRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpRequest;
    private final TokenInfoService tokenInfoService;
    private final JwtTokenUtils jwtTokenUtils;
    private final RoleRepository roleRepository;

    public AppResponse login(String username, String password) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.isAuthenticated()){

            AppUserDetail userDetails = (AppUserDetail) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            TokenInfo tokenInfo = createLoginToken(username, userDetails.getId());
            Map<String, Object> userData = new HashMap<>();
            Optional<Admin> appUser = userRepository.findById(userDetails.getId());

            if(appUser.isPresent()){
                userData.put("authUser", appUser.get());
                userData.put("token", tokenInfo.getAccessToken());
                userData.put("refreshToken", tokenInfo.getRefreshToken());

                return AppResponse.builder()
                        .ok(true)
                        .message("user_logged_in_success")
                        .status(HttpStatus.OK)
                        .data(userData)
                        .build();
            }
        }
        return null;
    }

    public TokenInfo createLoginToken(String userName, Integer userId) {
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String accessTokenId = UUID.randomUUID().toString();
        String accessToken = JwtTokenUtils.generateToken(userName, accessTokenId, false);

        String refreshTokenId = UUID.randomUUID().toString();
        String refreshToken = JwtTokenUtils.generateToken(userName, refreshTokenId, true);

        TokenInfo tokenInfo = TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(new Admin(userId))
                .userAgentText(userAgent)
                .localIpAddress(ip.getHostAddress())
                .remoteIpAddress(httpRequest.getRemoteAddr())
                .build();

        return tokenInfoService.save(tokenInfo);
    }

    public AccessTokenDto refreshAccessToken(String refreshToken) {
        if (jwtTokenUtils.isTokenExpired(refreshToken)) {
            return null;
        }
        String userName = jwtTokenUtils.getUserNameFromToken(refreshToken);
        Optional<TokenInfo> refresh = tokenInfoService.findByRefreshToken(refreshToken);
        if (!refresh.isPresent()) {
            return null;
        }

        return new AccessTokenDto(JwtTokenUtils.generateToken(userName, UUID.randomUUID().toString(), false));
    }

    public void logoutUser(String refreshToken) {
        log.info("begin logoutUser()");

        Optional<TokenInfo> tokenInfo = tokenInfoService.findByRefreshToken(refreshToken);
        if (tokenInfo.isPresent()) {

            log.info("User loged Out");

            tokenInfoService.deleteById(tokenInfo.get().getId());
        }
    }

    public Admin getCurrentAuthUser() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername( user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("This User Not found with selected user name :- " + user.getUsername()));
    }


}
