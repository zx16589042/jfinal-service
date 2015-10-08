# jfinal-service
JFinal接口开发插件

提高接口开发效率

   接口文档、参数验证、在线接口测试只需要您写一个接口其他即可由框架自动帮您完成

	@WebService(value="加法运算接口", parameter={
			@Parameter(value="value1", type="int", required=true, description="加数1"), 
			@Parameter(value="value2", type="int", required=true, description="加数2")})
	@Before({POST.class, AddValidator.class})
	public void add() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", getParaToInt("value1") + getParaToInt("value2"));
		renderJson(map);
	}
