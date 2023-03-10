package com.ssafy.pjt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.ssafy.pjt.model.service.JwtServiceImpl;
import com.ssafy.pjt.model.service.MemberService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class MemberRestController {
	private final Logger logger = LoggerFactory.getLogger(MemberRestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MemberService memberService;

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	public MemberRestController(MemberService memberService) {
		this.memberService = memberService;
	}

//	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			logger.debug("로그인 memberDto 정보 : " + memberDto.toString());
			MemberDto loginUser = memberService.loginMember(memberDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userid", loginUser.getUserId());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserId());// key, data
				memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				logger.debug("유저 ID 정보 : {}", loginUser.getUserId());
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.debug("로그인 resultMap 정보 : " + resultMap.toString());
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/*
	 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody
	 * Map<String, String> map, HttpSession session){ try { MemberDto selected =
	 * memberService.loginMember(map);
	 * 
	 * if (selected != null) { session.setAttribute("loginUser", selected); return
	 * new ResponseEntity<MemberDto>(selected, HttpStatus.OK); } else { return new
	 * ResponseEntity<Void>(HttpStatus.UNAUTHORIZED); } } catch (Exception e) {
	 * return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userId") String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleteRefreshToken(userId);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/*
	 * @GetMapping("/logout") public ResponseEntity<?> logout(HttpSession session) {
	 * try { session.invalidate(); return new ResponseEntity<Void>(HttpStatus.OK); }
	 * catch (Exception e) { return new
	 * ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody MemberDto memberDto) {
		try {
			memberService.joinMember(memberDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/find")
	public ResponseEntity<?> find(@RequestBody MemberDto memberDto) {
		try {
			String selected = memberService.findPwd(memberDto);

			if (selected != null) {
				return new ResponseEntity<String>(selected, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("detail/{userId}")
	public ResponseEntity<?> find(@PathVariable("userId") String userId, HttpServletRequest request) {
		try {
			MemberDto selected = memberService.detailMember(userId);

			if (selected != null) {
				return new ResponseEntity<MemberDto>(selected, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> userModify(@RequestBody MemberDto memberDto, @PathVariable("userId") String userId) {
		try {
			memberService.modifyMember(memberDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> userRemove(@PathVariable("userId") String userId) {

		try {
			memberService.deleteMember(userId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam(name = "userid") String id) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			List<MemberDto> selected = memberService.userList(id.trim());
			for (MemberDto m : selected)
				logger.debug(m.toString());

			if (selected != null && selected.size() >= 0) {
				resultMap.put("message", "success");
				resultMap.put("userList", selected);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userid", memberDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// Naver 연동 로그인
	@GetMapping("/naver/{id}")
	public ResponseEntity<?> naverLogin(@PathVariable("id") String naverId) throws Exception {
		try {
			logger.debug("Naver 연동 아이디 확인 : " + naverId);
			String userId = memberService.loginNaver(naverId);

			Map<String, Object> resultMap = new HashMap<>();
			if (userId != null) {
				String accessToken = jwtService.createAccessToken("userid", userId);// key, data
				String refreshToken = jwtService.createRefreshToken("userid", userId);// key, data
				memberService.saveRefreshToken(userId, refreshToken);
				logger.debug("유저 ID 정보 : {}", userId);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);

				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);

			} else {
				resultMap.put("message", FAIL);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Naver 연동 하기
	@PutMapping("/naver")
	public ResponseEntity<?> naverConnect(@RequestBody MemberDto memberDto) throws Exception {
		try {
			boolean isNaver = memberService.loginNaver(memberDto.getNaverId()) == null ? false : true;
			Map<String, Object> resultMap = new HashMap<>();
			if (isNaver) {
				resultMap.put("message", FAIL);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			} else {
				memberService.connectNaver(memberDto);

				resultMap.put("message", SUCCESS);
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Naver 연동 해제
	@DeleteMapping("/naver/{userid}")
	public ResponseEntity<?> naverUnconnect(@PathVariable("userid") String userId) throws Exception{
		try {
			memberService.unconnectNaver(userId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
