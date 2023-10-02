package ndt.java.spring.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ndt.java.spring.entities.UserInfo;
import ndt.java.spring.repository.UserInfoRepository;


// Tầng service của UserInfo (entities user trong bảng user để quản lý của hệ thống, này là user của mình không phải usre detail của spring)
@Service
public record UserInfoService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {

	public String addUser(UserInfo userInfo) {
		 	String pass = "123456";
		 	System.out.println(userInfo.toString());
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword().isEmpty() ? pass : userInfo.getPassword() ));
	        repository.save(userInfo);
	        return "Thêm user thành công!";
	    }
}
