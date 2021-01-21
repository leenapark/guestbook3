package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value="/gbc")
public class GuestBookController {
	
	// Handler mapping
	
		// test 화면
		@RequestMapping( "/hello")
		public String hello(){
			System.out.println("/hellospring/hello");
			return "/WEB-INF/views/index.jsp";		// viewResolver 사용 시 양식 간소화
		}
	
		// 리스트
		@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
		public String addList(Model model) {
			
			System.out.println("list");
			
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestVo = guestDao.addList();
			
			System.out.println("addList: " + guestVo);
			
			model.addAttribute("addList", guestVo);
			
			return "addList";
		}
		
		// 등록
		@RequestMapping(value="/add", method={RequestMethod.GET, RequestMethod.POST})
		public String add(@RequestParam("name") String name, @RequestParam("password") String pass, @RequestParam("content") String content, Model model) {
			System.out.println("add");
			System.out.println(name + ", " + pass + ", " + content);
			
			GuestVo guestVo = new GuestVo(name, pass, content);
			GuestDao guestDao = new GuestDao();
			guestDao.guestInsert(guestVo);
			
			List<GuestVo> addList = guestDao.addList();
			model.addAttribute("addList", addList);
			
			return "redirect:/gbc/list";
		}
		
		// 삭제 - 비밀번호 확인
		@RequestMapping(value="/dForm", method= {RequestMethod.GET, RequestMethod.POST})
		public String dForm(@RequestParam("no") int no){
			System.out.println("dForm");
			
			System.out.println(no);
			
			return "deleteForm";
		}
		
		// 삭제
		@RequestMapping(value="/delete", method= {RequestMethod.GET, RequestMethod.POST})
		public String delete(@RequestParam("no") int no, @RequestParam("password") String pass ) {
			System.out.println("delete");
			//int num = Integer.parseInt(no);
			System.out.println(no + ", " + pass);
			
			GuestDao guestDao = new GuestDao();
			int check = guestDao.guestDelete(no, pass);
			if(check == 1) {
				
				System.out.println("삭제");
				return "redirect:/gbc/list";
				
			} else if(check == 0) {
				System.out.println("삭제 실패");
				
				
				return "redirect:/gbc/dForm?result=fail&no="+no;
			}
			
			return "";
		}
		
}
