package com.tf.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tf.bean.Policy;
import com.tf.bean.User;

@RestController
public class NotificationService {
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/notify",method=RequestMethod.POST)
	public List<Policy> getNotificationDetails(@RequestBody User user) {
		System.out.println("UserName input passed : "+ user.getUserName());
		RestTemplate rtmp = new RestTemplate();
		
		List<Policy> policy = rtmp.postForObject("http://localhost:8081/getAllPolicy", user, List.class);
		Date startDate = new java.util.Date();
		List<Policy> policyNotify = new ArrayList<Policy>();
		for (Policy policy1 : policy) {
			
			
			long days = getDateDiff(startDate, policy1.getExpDate(),TimeUnit.DAYS);
			if(days > 15){
				policyNotify.add(policy1);
			}
		}
		
		return policyNotify;
	}
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.DAYS);
	}
}
