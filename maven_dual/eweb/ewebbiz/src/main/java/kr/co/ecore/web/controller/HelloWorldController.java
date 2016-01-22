package kr.co.ecore.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kr.co.ecore.web.service.ReportService;

@Controller
public class HelloWorldController {
	
	@Autowired
	private ReportService reportService;	

	 @RequestMapping("/hello")
	 public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
	   String rename = reportService.getRename(name);
	   System.out.println(">>>>>>"+rename);
	   model.addAttribute("name", rename);
	   return "helloworld";
	 }

}