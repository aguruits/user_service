package com.ecomm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ecomm.entity.User;
import com.ecomm.model.OrderVO;
import com.ecomm.model.UserVO;
import com.ecomm.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    private RestTemplate restTemplate;
	
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        log.info("Inside saveUser of UserController");
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public UserVO getUserWithDepartment(@PathVariable("id") Long userId) {
        log.info("Inside getUserWithDepartment of UserController");
        User user = userRepository.findByUserId(userId);

        OrderVO orderVO = restTemplate.getForObject("http://ORDER-SERVICE/orders/getOrdersById/" + user.getOrderId() ,OrderVO.class);
        UserVO userVO = new UserVO();
        userVO.setUser(user);
		userVO.setOrderVO(orderVO);
		
		return userVO;
    }

    @GetMapping("/userList")
    public List<UserVO> getUserList() {
        log.info("Inside getUserList of UserController");
        List<User> userList = userRepository.findAll();
        
        List<UserVO> userVOList = new ArrayList<UserVO>();
        UserVO userVO = new UserVO();
        
        for (User user : userList) {
        	
			OrderVO orderVO = restTemplate.getForObject("http://ORDER-SERVICE/orders/getOrdersById/" + user.getOrderId() ,OrderVO.class);
			
			userVO.setUser(user);
			userVO.setOrderVO(orderVO);
			
			userVOList.add(userVO);
		}
        
        return userVOList;
    }

}
