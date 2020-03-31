package ro.fortech.internship.vinylshop.data;

import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.role.model.RoleType;
import ro.fortech.internship.vinylshop.user.dto.CreateUserDto;
import ro.fortech.internship.vinylshop.user.model.User;

public class UserDataProvider {

    public static User createValidUser(RoleType roleType) {
        return User.builder()
                .email("John@vinyl")
                .firstName("John")
                .lastName("Pierce")
                .password("stringQ!3")
                .cart(new Cart())
                .build();
    }

    public static CreateUserDto createCreateUserDto(String email, String fName, String lName, String pwd) {
        return CreateUserDto.builder()
                .email(email)
                .firstName(fName)
                .lastName(lName)
                .password(pwd)
                .build();
    }
}
