package com.abs.ps;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.abs.ps.domain.User;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.web.dto.CheckPointDetailDto;
import com.abs.ps.web.dto.DamageInfoDto;

public class SimpleTest {

	@Test
	public void testRandomName() {
		
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < 5; i++) {
            str.append(chars.charAt((int) (Math.random() * 26)));
        }
		System.out.println(str.toString());
		
	}
	
	@Test
	public void testGetField() throws Exception {
		User user = new User();
		user.setOid(43L);
		user.setNickName("Sug");
		user.setCreateDate(new Date());
		
		Class<User> clazz = User.class;
		Field field = clazz.getDeclaredField("nickName");
		
		PropertyDescriptor pd = FilterUtil.initPropertyDescriptor(field, User.class);
		Method readMethod = pd.getReadMethod();
		Object invoke = readMethod.invoke(user);
		System.out.println(invoke);
		
	}
	
	@Test
	public void testFilterUtil() {
		
		Object value = FilterUtil.convertFieldValue(User.class, "createDate", "2017-08-01");
		
		System.out.println(value);
		System.out.println(value.getClass());
		
	}
	
	@Test
	public void testFilterUtilConvert() {
		
		Long type = FilterUtil.convertType(Long.class, "123a");
		System.out.println(type);
		
	}
	
	@Test
	public void testSetResultRowToObject() {
		FilterUtil.parseSqlRowSetToObject(null, DamageInfoDto.class);
	}
	
	
	@Test
	public void testJsonStrToObj() {
		
		String json = "[{\"itemOid\":\"87\",\"actualStockAmt\":\"123\",\"remark\":\"中文\"},{\"itemOid\":\"89\",\"actualStockAmt\":\"\",\"remark\":\"\"},{\"itemOid\":\"77\",\"actualStockAmt\":\"\",\"remark\":\"\"}]";
		
		JSONArray jsonArr = JSONArray.fromObject(json);
		JSONObject jsonObject = null;
		CheckPointDetailDto dto = null;
		List<CheckPointDetailDto> list = new LinkedList<>();
		for (int i = 0 ; i < jsonArr.size(); i++) {  
            jsonObject = jsonArr.getJSONObject(i);  
            dto = (CheckPointDetailDto) JSONObject.toBean(jsonObject, CheckPointDetailDto.class);  
            list.add(dto);  
        }  
		
		DaoSupportTest.printJsonStr(list);
		
	}
	
	
	
	@Test
	public void test() {
		String s = "name=\"bomNum\" name=\"status\" name=\"pcNum\" name=\"departName\" name=\"publishPerson\" name=\"confirmPerson\" name=\"machineName\" name=\"publishDate\" name=\"mouldName\" name=\"prodAmt\" name=\"prodId\" name=\"unitConsume\" name=\"pwActualWgt\" name=\"itemCode\" name=\"shotActualWgt\" name=\"itemColor\" name=\"period\" name=\"loss\" name=\"fetchActualNum\" name=\"plasticWeight\" name=\"dailyPc\" name=\"targetStartDate\" name=\"planTime\" name=\"targetEndDate\"";
		
		System.out.println(s.replaceAll("name=", "").replaceAll("\"", ""));
		
		// Pattern p = Pattern.compile("name=\"[a-zA-Z]+\"");
		/*Pattern p = Pattern.compile("name=\"[a-zA-Z]+\"");
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}*/
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
