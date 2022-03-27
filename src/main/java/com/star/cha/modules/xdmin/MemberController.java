package com.star.cha.modules.xdmin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {

	private static final String MemberVo = null;
	@Autowired
	MemberServiceImpl service;
	
	@RequestMapping(value = "/xdmin/memberList")
	
	public String memberList(@ModelAttribute("vo") MemberVo vo, Model model) throws Exception {
//	public String memberList( Model model) throws Exception {
		
		// count 가져올 것
		int count = service.selectOneMember(vo);
		
		vo.setParamsPaging(count);
		
		// count 가 0이 아니면 List 가져오는 부분 수행 후 model 개체에 담기
		if (count != 0) {
			List<Member> list = service.selectList(vo);
			model.addAttribute("list", list);
		} else {
			// by pass
		}
		
		return "xdmin/memberList";
		
	}
	
	@RequestMapping(value = "/xdmin/memberForm")
	public String memberForm(Model model) throws Exception {
		
//		model.addAllAttributes(CodeServiceImpl.selelctListCachedCode("3"));
		
		return "xdmin/memberForm";
	}
	
	@RequestMapping(value = "/xdmin/memberInst")
	public String memberInst(Member dto, MemberVo vo, RedirectAttributes redirectAttributes) throws Exception {
		
		service.insert(dto);
		
		redirectAttributes.addAttribute("ifmmSeq", dto.getIfmmSeq());
		redirectAttributes.addAttribute("thisPage", vo.getThisPage());
		redirectAttributes.addAttribute("shmemberDelNy", vo.getShmemberDelNy());
		redirectAttributes.addAttribute("shOption", vo.getShOption());
		redirectAttributes.addAttribute("shValue", vo.getShValue());
		
		return "redirect:/xdmin/memberList";
	}
	
	public String makeQueryString(MemberVo vo) {
		String tmp = "&thisPage" + vo.getThisPage() +
					"&shmemberDelNy" + vo.getShmemberDelNy() +
					"&shOption" + vo.getShOption() +
					"&shValue" + vo.getShValue();
		return tmp;
	}
	
	@RequestMapping(value = "/xdmin/memberView")
	public String memberView(@ModelAttribute("vo") MemberVo vo, Model model) throws Exception {
		
		Member rt = service.selectOne(vo);
		
		model.addAttribute("item", rt);
		
		return "xdmin/memberView";
	}
	
	@RequestMapping(value = "/xdmin/memberForm2")
	public String memberForm2(@ModelAttribute("vo") MemberVo vo, Model model) throws Exception {
		
		// 한건의 데이터를 가져온다
		Member rt = service.selectOne(vo);
		
		model.addAttribute("item", rt);
		
		return "xdmin/memberForm2";
	}
	
	
	//실제 수정을 하는 주소
	@RequestMapping(value = "/xdmin/memberUpdt")
	public String memberUpdt(Member dto, MemberVo vo) throws Exception {
		
		//수정 프로세스 실행
		service.update(dto);
		
		return "redirect:/xdmin/memberView?ifmmSeq=" + dto.getIfmmSeq() + makeQueryString(vo);
	}
	
	@RequestMapping(value = "/xdmin/memberLogin")
	public String memberLogin() throws Exception {
		
		
		return "xdmin/memberLogin";
	}
}


