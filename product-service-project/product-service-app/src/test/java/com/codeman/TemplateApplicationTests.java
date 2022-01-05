package com.codeman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;





/**
 * Created by hdgaadd on 2021/12/06/18:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateApplicationTests {

	//项目包名
	static String Package = "com.codeman";

	// 实体类包名
	static String entityName;

	static String day;

	static String time;

	static String template;

	@Test
	public void test() {
		String TimeNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS").format(Calendar.getInstance().getTime());
		String[] str = TimeNow.split(" ");
		day = str[0];
		time = str[1].substring(0, 8);
		template ="/**\n" +
				" * @author hdgaadd\n" +
				" * Created on " + day +"\n" +  "*/\n";

		TemplateApplicationTests auto = new TemplateApplicationTests();
		//获取所有数据表
		List<String> list = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		System.out.println("please prefix your file name ");
		String fileName = sc.nextLine();
		System.out.println("please enter which domain ");
		entityName = sc.nextLine();
		list.add(fileName);
		for (String table : list) {
			createStart(tables(table));
		}

	}



	/**
	 * 把输入字符串的首字母改成大写
	 */
	private static String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	/**
	 * 把输入字符串的首字母改成小写
	 */
	private static String initlow(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			ch[0] = (char) (ch[0] + 32);
		}
		return new String(ch);
	}

	//首字母转换和下划线转换
	private static String tables(String table) {
		String[] tables = table.split("_");
		table = "";
		for (String s : tables) {
			table += initcap(s);
		}
		return table;
	}
	/**
	 * 创建Dao
	 */
	private static String createDao(String tableName) {
		String service = "package "+Package+".dao;\n\n" +
				template +
				"public interface "+tableName+"Dao {\n" +

				"}";
		return service;
	}

	/**
	 * 创建Service
	 */
	private static String createService(String tableName) {
		String service = "package " + Package + ".service." + entityName + ";\n" +
				"\n" +
				"import org.springframework.stereotype.Service;\n" +
				"\n" +
				template +
				"@Service\n" +
				"public class " + tableName + "Service {\n" +
				"}";
		return service;
	}

	/**
	 * 创建Rpc接口
	 */
	private static String createRpc(String tableName) {
		String service = "package " + Package + ".rpc." + entityName + ";\n" +
				"\n" +
				template +
				"public interface " + tableName + "Rpc {\n" +
				"}";
		return service;
	}

	/**
	 * 创建Manager
	 */
	private static String createManager(String tableName) {
		String service = "package " + Package + ".manager." + entityName + ";\n" +
				"\n" +
				"import org.springframework.stereotype.Service;\n" +
				"\n" +
				template +
				"@Service\n" +
				"public class " + tableName + "Manager {\n" +
				"}";
		return service;
	}

	/**
	 * 创建RpcImpl
	 */
	private static String createRpcImpl(String tableName) {
		String serviceImpl = "package " + Package + ".rpc." + entityName + ";\n" +
				"\n" +
				"import org.apache.dubbo.config.annotation.DubboService;\n" +
				"\n" + template +
				"@DubboService\n" +
				"public class " + tableName + "RpcImpl implements " + tableName + "Rpc {\n" +
				"}\n";
		return serviceImpl;
	}

	/**
	 * 创建ServiceImpl
	 */
	private static String createServiceImpl(String tableName) {
		String serviceImpl = "package "+Package+".service.impl;\n" +
				"\n" +
				"import "+Package+".service." + tableName + "Service;\n" +
				"import org.springframework.stereotype.Service;\n" +
				"\n" + template +
				"@Service\n" +
				"public class " + tableName + "ServiceImpl implements " + tableName + "Service {\n" +
				"}\n";
		return serviceImpl;
	}

	/**
	 * 创建Controller
	 */
	private static String createController(String tableName) {
		String controller ="package "+Package+".controller;\n\n" +"import io.swagger.annotations.Api;\n" +
				"import org.springframework.web.bind.annotation.RequestMapping;\n" +
				"import org.springframework.web.bind.annotation.RestController;\n\n"+
				template +
				"@Api(tags = \"" + "\")\n" +
				"@RestController\n" +
				"@RequestMapping(\"/" + "\")\n" +
				"public class " + tableName + "Controller {\n" +
				"}\n";
		return controller;
	}

	/**
	 * create XML
	 */
	private static String createXml(String tableName) {
		String controller =
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
						"<mapper namespace=\"com.codeman.dao." + tableName +"Dao\">\n" +
						"</mapper>";
		return controller;
	}

	/**
	 * 开始创建
	 */
	static void createStart(String tableName) {
		//获取当前项目的路径
		String baseUrl = System.getProperty("user.dir");

		String apiUrl = baseUrl.substring(0, baseUrl.length() - 3) + "api" + "\\src\\main\\java\\com\\codeman\\";
		String url = baseUrl + "\\src\\main\\java\\com\\codeman\\";

		// 创建Service
		createFile(new File(url + "service\\" + entityName + "\\" + tableName + "Service.java"), createService(tableName));
		// 创建Manager
		createFile(new File(url + "manager\\" + entityName + "\\" + tableName + "Manager.java"), createManager(tableName));
		// 创建Rpc接口
		createFile(new File(apiUrl + "rpc\\" + entityName + "\\" + tableName + "Rpc.java"), createRpc(tableName));
		// 创建createRpcImpl
		createFile(new File(url + "rpc\\" + entityName + "\\" + tableName + "RpcImpl.java"), createRpcImpl(tableName));
		}

	/**
	 * @param file    创建的文件
	 * @param context 文件里面的内容
	 */
	static boolean createFile(File file, String context) {
		//获取文件
		File parent = file.getParentFile();
		//如果是目录
		if (parent != null) {
			//创建目录
			parent.mkdirs();
		}
		try {
			//创建文件
			file.createNewFile();
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(file);
				fileWriter.write(context);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				return false;
			}
		} catch (IOException e) {
			System.out.println("创建文件失败:" + e.getMessage());
		}
		return true;
	}
}