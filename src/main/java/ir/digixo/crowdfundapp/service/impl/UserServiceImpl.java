package ir.digixo.crowdfundapp.service.impl;

import ir.digixo.crowdfundapp.dto.UserDto;
import ir.digixo.crowdfundapp.entity.User;
import ir.digixo.crowdfundapp.mapper.UserMapper;
import ir.digixo.crowdfundapp.repository.UserRepository;
import ir.digixo.crowdfundapp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toEntity(userDto)
                )
        );
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = findByUsername(username);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userMapper.updateUserFromDto(userDto, user);
        User userUpdated = userRepository.save(user);
        return userMapper.toDto(userUpdated);
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = findByUsername(username);
        userRepository.delete(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
