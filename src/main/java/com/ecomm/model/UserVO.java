package com.ecomm.model;

import com.ecomm.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	private User user;
    private OrderVO orderVO;
    
	
    
}
