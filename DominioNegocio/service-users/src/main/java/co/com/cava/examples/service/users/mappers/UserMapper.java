package co.com.cava.examples.service.users.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.commons.users.entitis.User;

import co.com.cava.examples.service.users.dto.UserDTO;

@Mapper
public interface UserMapper {
	
	UserMapper INSTANCE  = Mappers.getMapper(UserMapper.class); 
	
	User user (UserDTO userdto);

}
