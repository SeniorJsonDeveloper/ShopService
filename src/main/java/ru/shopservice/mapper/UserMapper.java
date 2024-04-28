package ru.shopservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.shopservice.dto.UserOutDto;
import ru.shopservice.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserEntity toEntity(UserOutDto userOutDto);

    UserOutDto toDto(UserEntity userEntity);

    List<UserOutDto> toDtoList(List<UserEntity> userEntities);

    List<UserEntity> toEntityList(List<UserOutDto> userOutDto);
}
