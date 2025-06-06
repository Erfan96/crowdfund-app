package ir.digixo.crowdfundapp.service;

import ir.digixo.crowdfundapp.dto.UserDto;
import ir.digixo.crowdfundapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto saveUser(UserDto userDto);
    UserDto getUserByUsername(String username);
    User findByUsername(String username);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDto);
    void deleteUserByUsername(String username);
    Boolean existsByUsername(String username);
}
