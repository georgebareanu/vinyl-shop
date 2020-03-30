package ro.fortech.internship.vinylshop.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.common.exception.ResourceNotFoundException;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AuthenticatedUser {

    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = principal.getUsername();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
