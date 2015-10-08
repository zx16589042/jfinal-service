package org.devefx.service.core;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devefx.service.annotation.WebService;
import org.devefx.service.annotation.WebService.Parameter;

import com.jfinal.aop.Before;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;

public class ServiceView extends Controller {
	private static JFinal jfinal = JFinal.me();
	
	private static String mappingName;
	
	public ServiceView() {
		if (mappingName == null) {
			for (String actionName : jfinal.getAllActionKeys()) {
				String[] urlPara = {null};
				Action action = jfinal.getAction(actionName, urlPara);
				if (getClass().isAssignableFrom(action.getControllerClass())) {
					mappingName = action.getControllerKey();
					break;
				}
			}
		}
	}
	
	public void index() throws UnsupportedEncodingException {
		
		StringBuffer html = new StringBuffer("<html><head>");
		html.append("<title>Interface List</title>");
		html.append("<meta name=\"author\" content=\"yueyouqian\"/>");
		html.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		html.append("<meta http-equiv=\"Content-Type\" content=\"text/html\"></head>");		
		html.append("<body><p></p><h3>Interface List</h3><p></p><ul>");
		
		for (String actionName : jfinal.getAllActionKeys()) {
			String[] urlPara = {null};
			Action action = jfinal.getAction(actionName, urlPara);
			if (action != null && action.getMethod().isAnnotationPresent(WebService.class)) {
				html.append(String.format("<li><a target=\"_blank\" href=\"%s%s/details?service=%s\">%s</a></li>", getRequest().getContextPath(), mappingName, URLEncoder.encode(actionName, "UTF-8"), actionName));
			}
		}
		
		html.append("</ul></body></html>");
		renderHtml(html.toString());
	}
	
	public void details() {
		String service = getPara("service");
		
		String[] urlPara = {null};
		Action action = jfinal.getAction(service, urlPara);
		if (action == null) {
			renderError(404);
			return;
		}
		
		Method method = action.getMethod();
		
		setAttr("url", getRequest().getContextPath() + service);
		
		if (method.isAnnotationPresent(Before.class)) {
			Before before = method.getAnnotation(Before.class);
			String methodType = null;
			for (Class<? extends Interceptor> clazz : before.value()) {
				if (clazz == POST.class) {
					methodType = (methodType == null ? "POST": "GET/POST");
				} else if (clazz == GET.class) {
					methodType = (methodType == null ? "GET": "GET/POST");
				}
			}
			setAttr("methodType", methodType == null ? "GET/POST" : methodType);
		}
		
		if (method.isAnnotationPresent(WebService.class)) {
			WebService webService = method.getAnnotation(WebService.class);
			setAttr("description", webService.value());
			
			List<Map<String, Object>> paramList = new ArrayList<Map<String,Object>>();
			for (Parameter parameter : webService.parameter()) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("name", parameter.value());
				param.put("required", parameter.required());
				param.put("type", parameter.type());
				param.put("description", parameter.description());
				paramList.add(param);
			}
			setAttr("parameters", paramList);
		}
		
		renderJsp("/WEB-INF/classes/interface_info.jsp");
	}
	
}
