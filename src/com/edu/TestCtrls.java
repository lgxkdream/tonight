package com.edu;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.edu.service.TestService;
import com.fh.controller.base.BaseController;

@Controller
@RequestMapping(value="/test")
public class TestCtrls extends BaseController {

	@RequestMapping(value="/show",method=RequestMethod.GET)
	public ModelAndView show(@RequestParam("id") Integer id,ModelAndView mv){
		System.out.println("--------------"+id);
		mv.setViewName("test2");
		mv.addObject("name", "哈哈");
		mv.addObject("id", id);
		return mv;
	}
	@RequestMapping(value="/show2",method=RequestMethod.GET)
	public String show2(@RequestParam("id") String id,Model model){
		System.out.println(id);
		model.addAttribute("name", "哈哈2");
		model.addAttribute("id", id);
		return "test2";
	}
}
