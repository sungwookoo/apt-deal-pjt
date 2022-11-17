package com.ssafy.pjt.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.MemberDto;
import com.ssafy.pjt.model.service.MemberService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class MemberRestController {
	private final Logger logger = LoggerFactory.getLogger(MemberRestController.class);
	private MemberService memberService;
	
	@Autowired
	public MemberRestController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> map, HttpSession session){
		try {
			MemberDto selected = memberService.loginMember(map);

			if (selected != null) {
				session.setAttribute("loginUser", selected);
				return new ResponseEntity<MemberDto>(selected, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		try {
			session.invalidate();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody MemberDto memberDto){
		try {
			memberService.joinMember(memberDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/find/{userId}/{userName}/{userPhone}")
	public ResponseEntity<?> find(@PathVariable("userId") String userId, @PathVariable("userName") String userName, @PathVariable("userPhone") String userPhone ){
		try {
			MemberDto memberDto = new MemberDto();
			memberDto.setUserId(userId);
			memberDto.setUserName(userName);
			memberDto.setUserPhone(userPhone);;
			
			String selected = memberService.findPwd(memberDto);

			
			if (selected != null) {
				return new ResponseEntity<String>(selected, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> find(@PathVariable("userId") String userId){
		try {
			MemberDto selected = memberService.detailMember(userId);
			
			
			if (selected != null) {
				return new ResponseEntity<MemberDto>(selected, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> userModify(@RequestBody MemberDto memberDto, @PathVariable("userId") String userId) {
		try {
			
			memberService.modifyMember(memberDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> userRemove(@PathVariable("userId") String userId) {

		try {
			memberService.deleteMember(userId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam(name="userid") String id){
		try {
			List<MemberDto> selected = memberService.userList(id.trim());
			for(MemberDto m : selected)logger.debug(m.toString());
			
			if (selected != null && selected.size() > 0) {
				return new ResponseEntity<List<MemberDto>>(selected, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
