package org.devefx.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.devefx.service.annotation.WebService;
import org.devefx.service.annotation.WebService.Parameter;
import org.devefx.test.validator.AddValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;

public class HelloController extends Controller {
	
	@WebService(value="加法运算接口", parameter={
			@Parameter(value="value1", type="int", required=true, description="加数1"), 
			@Parameter(value="value2", type="int", required=true, description="加数2")})
	@Before({POST.class, AddValidator.class})
	public void add() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", getParaToInt("value1") + getParaToInt("value2"));
		renderJson(map);
	}
}
