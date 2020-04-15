package com.hdm.xmldemo;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class XmlDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void JsoupDemo1() throws IOException {
		//2.获取Document对象，根据xml文档获取
		//2.1获取student.xml的path
		String path = XmlDemoApplicationTests.class.getClassLoader().getResource("student.xml").getPath();
		//2.2解析xml文档，加载文档进内存，获取dom树--->Document
		Document document = Jsoup.parse(new File(path), "utf-8");
		//3.获取元素对象 Element
		Elements elements = document.getElementsByTag("name");

		System.out.println(elements.size());
		//3.1获取第一个name的Element对象
		Element element = elements.get(0);
		//3.2获取数据
		String name = element.text();
		System.out.println(name);
	}
	@Test
	void JsoupDemo2() throws IOException {
		//1.获取student.xml的path
		String path = XmlDemoApplicationTests.class.getClassLoader().getResource("student.xml").getPath();
		//2.获取Document对象
		Document document = Jsoup.parse(new File(path), "utf-8");

		//3.获取元素对象了。
		//3.1获取所有student对象
		Elements elements = document.getElementsByTag("student");
		System.out.println(elements);

		System.out.println("-----------");


		//3.2 获取属性名为id的元素对象们
		Elements elements1 = document.getElementsByAttribute("id");
		System.out.println(elements1);
		System.out.println("-----------");
		//3.2获取 number属性值为heima_0001的元素对象
		Elements elements2 = document.getElementsByAttributeValue("number", "heima_0001");
		System.out.println(elements2);

		System.out.println("-----------");
		//3.3获取id属性值的元素对象
		Element itcast = document.getElementById("itcast");
		System.out.println(itcast);
	}
	@Test
	void JsoupDemo3() throws IOException {
		//1.获取student.xml的path
		String path = XmlDemoApplicationTests.class.getClassLoader().getResource("student.xml").getPath();
		//2.获取Document对象
		Document document = Jsoup.parse(new File(path), "utf-8");
        /*
        Element：元素对象
				1. 获取子元素对象
					* getElementById​(String id)：根据id属性值获取唯一的element对象
					* getElementsByTag​(String tagName)：根据标签名称获取元素对象集合
					* getElementsByAttribute​(String key)：根据属性名称获取元素对象集合
					* getElementsByAttributeValue​(String key, String value)：根据对应的属性名和属性值获取元素对象集合

				2. 获取属性值
					* String attr(String key)：根据属性名称获取属性值
				3. 获取文本内容
					* String text():获取所有字标签的纯文本内容
					* String html():获取标签体的所有内容(包括子标签的标签和文本内容)




         */
		//通过Document对象获取name标签，获取所有的name标签，可以获取到两个
		Elements elements = document.getElementsByTag("name");
		System.out.println(elements.size());
		System.out.println("----------------");
		//通过Element对象获取子标签对象
		Element element_student = document.getElementsByTag("student").get(0);
		Elements ele_name = element_student.getElementsByTag("name");
		System.out.println(ele_name.size());

		//获取student对象的属性值
		String number = element_student.attr("NUMBER");
		System.out.println(number);
		System.out.println("------------");
		//获取文本内容
		String text = ele_name.text();
		String html = ele_name.html();
		System.out.println(text);
		System.out.println(html);
	}

	@Test
	void JsoupDemo4() throws IOException {
		//1.获取student.xml的path
		String path = XmlDemoApplicationTests.class.getClassLoader().getResource("student.xml").getPath();
		//2.获取Document对象
		Document document = Jsoup.parse(new File(path), "utf-8");

		//3.查询name标签
        /*
            div{

            }
         */
		Elements elements = document.select("name");
		System.out.println(elements);
		System.out.println("=----------------");
		//4.查询id值为itcast的元素
		Elements elements1 = document.select("#itcast");
		System.out.println(elements1);
		System.out.println("----------------");
		//5.获取student标签并且number属性值为heima_0001的age子标签
		//5.1.获取student标签并且number属性值为heima_0001
		Elements elements2 = document.select("student[number=\"heima_0001\"]");
		System.out.println(elements2);
		System.out.println("----------------");

		//5.2获取student标签并且number属性值为heima_0001的age子标签
		Elements elements3 = document.select("student[number=\"heima_0001\"] > age");
		System.out.println(elements3);
	}

	@Test
	void JsoupDemo5() throws IOException {
		//1.获取student.xml的path
		String path = XmlDemoApplicationTests.class.getClassLoader().getResource("student.xml").getPath();
		//2.获取Document对象
		Document document = Jsoup.parse(new File(path), "utf-8");

		//3.根据document对象，创建JXDocument对象
		JXDocument jxDocument = new JXDocument(document);

		//4.结合xpath语法查询
		//4.1查询所有student标签
		List<JXNode> jxNodes = jxDocument.selN("//student");
		for (JXNode jxNode : jxNodes) {
			System.out.println(jxNode);
		}

		System.out.println("--------------------");

		//4.2查询所有student标签下的name标签
		List<JXNode> jxNodes2 = jxDocument.selN("//student/name");
		for (JXNode jxNode : jxNodes2) {
			System.out.println(jxNode);
		}

		System.out.println("--------------------");

		//4.3查询student标签下带有id属性的name标签
		List<JXNode> jxNodes3 = jxDocument.selN("//student/name[@id]");
		for (JXNode jxNode : jxNodes3) {
			System.out.println(jxNode);
		}
		System.out.println("--------------------");
		//4.4查询student标签下带有id属性的name标签 并且id属性值为itcast

		List<JXNode> jxNodes4 = jxDocument.selN("//student/name[@id='itcast']");
		for (JXNode jxNode : jxNodes4) {
			System.out.println(jxNode);
		}
	}
}
