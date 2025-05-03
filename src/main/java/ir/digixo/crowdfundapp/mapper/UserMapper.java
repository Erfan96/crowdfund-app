package ir.digixo.crowdfundapp.mapper;

import ir.digixo.crowdfundapp.dto.UserDto;
import ir.digixo.crowdfundapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<UserDto> toDtoList(List<User> users);
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
