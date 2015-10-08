package org.devefx.test.validator;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class AddValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateInteger("value1", "value1", "请输入加数1");
		validateInteger("value2", "value2", "请输入加数2");
	}

	@Override
	protected void handleError(Controller c) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enumeration = c.getAttrNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			map.put(name, c.getAttrForStr(name));
		}
		c.renderJson(map);
	}

}
