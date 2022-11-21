package com.ssafy.pjt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.BuildingDto;
import com.ssafy.pjt.model.dto.HouseDto;
import com.ssafy.pjt.model.dto.InterestDto;
import com.ssafy.pjt.model.dto.MemberDto;
import com.ssafy.pjt.model.service.HouseService;

@RestController
@RequestMapping("/api/house")
@CrossOrigin("*")
public class HouseRestController {

	private final Logger logger = LoggerFactory.getLogger(HouseRestController.class);

	private HouseService houseService;

	@Autowired
	public HouseRestController(HouseService houseService) {
		this.houseService = houseService;
	}

	@GetMapping("/sido")
	public ResponseEntity<?> sido() {
		try {
			List<Map<String, String>> list = houseService.selectSidoNames();
			if (list != null && list.size() > 0)
				return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/gugun/{code}")
	public ResponseEntity<?> gugun(@PathVariable("code") String code) {
		try {
			String sidoCode = code.substring(0, 2);
			List<Map<String, String>> list = houseService.selectGugunNames(sidoCode);
			if (list != null && list.size() > 0)
				return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/dong/{code}")
	public ResponseEntity<?> dong(@PathVariable("code") String code) {
		try {
			String gugunCode = code.substring(0, 5);
			List<Map<String, String>> list = houseService.selectDongNames(gugunCode);
			if (list != null && list.size() > 0)
				return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchDeal/{dongCode}")
	public ResponseEntity<?> searchDeal(@PathVariable("dongCode") String dongCode) {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("dongCode", dongCode);

			List<HouseDto> list = houseService.selectDeals(map);

			if (list != null && list.size() > 0) {
				return new ResponseEntity<List<HouseDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/detail/{aptCode}")
	public ResponseEntity<?> selectBuildingDetail(@PathVariable("aptCode") String aptCode) {
		try {
			List<HouseDto> list = houseService.selectBuildingDetail(aptCode);

			if (list != null && list.size() > 0) {
				return new ResponseEntity<List<HouseDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchBuilding/{dongCode}")
	public ResponseEntity<?> selectBuilding(@PathVariable("dongCode") String dongCode) {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("dongCode", dongCode);

			List<BuildingDto> list = houseService.selectBuildingInfo(map);

			if (list != null && list.size() > 0) {
				return new ResponseEntity<List<BuildingDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchDeal/{dongCode}/{year}/{month}")
	public ResponseEntity<?> searchDeal(@PathVariable("dongCode") String dongCode,
			@PathVariable(name = "year", required = false) String year,
			@PathVariable(name = "month", required = false) String month) {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("dongCode", dongCode);
			map.put("year", year);
			map.put("month", month);

			logger.debug("파라미터 : " + dongCode + ", " + year + ", " + month);

			List<HouseDto> list = houseService.selectDeals(map);
			for (HouseDto d : list)
				logger.debug(d.toString());

			if (list != null && list.size() > 0) {
				return new ResponseEntity<List<HouseDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Map<String, String> map) {
		try {
			houseService.modifyAptDeal(map);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{no}")
	public ResponseEntity<?> delete(@PathVariable("no") String no) {
		try {
			houseService.deleteAptDeal(no);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/interest/{userId}")
	public ResponseEntity<?> getInterest(@PathVariable("userId") String userId) {
		try {
			List<InterestDto> list = houseService.selectInterest(userId);

			if (list != null && list.size() > 0)
				return new ResponseEntity<List<InterestDto>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/interest")
	public ResponseEntity<?> insertInterest(@RequestBody Map<String, String> map) {
		try {
			houseService.interestRegister(map);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/interest/{userid}/{dongcode}")
	public ResponseEntity<?> insertInterest(@PathVariable("userid") String userId,
			@PathVariable("dongcode") String dongCode) {
		try {
			Map<String, String> map = new HashMap<>();
			map.put("userId", userId);
			map.put("code", dongCode);

			houseService.interestDelete(map);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/dongDealInfo/{code}")
	public ResponseEntity<?> dongDealInfo(@PathVariable("code") String code) {
		try {
			String gugunCode = code.substring(0, 5);

			// 동 코드 획득.
			List<Map<String, String>> list = houseService.selectDongNames(gugunCode);

			// 동별 평균 매매 가격
			Map<String, Double> result = new HashMap<>();

			// 동별 평균 매매 가격 계산.
			for (Map<String, String> map : list) {
				// 동이름.
				String dongName = map.get("dongName");
				// 새로운 요소 추가
				result.put(dongName, 0.0);

				// 해당 동에서 거래된 아파트의 정보
//				List<String> aptCodeList = houseService.selectAptCodes(map.get("dongCode"));
				// System.out.println("3-" + dongName + " : " + aptCodeList.size());

//				if (aptCodeList.size() == 0)
//					continue;

				// 동별별 매매 가격
				List<String> dealList = houseService.selectAptDeals(map.get("dongCode"));
				int cnt = dealList.size();

				// 아파트 매매 가격 추가
				double sum = 0;
				for (String origin : dealList) {
					double deal = Double.parseDouble(origin.replaceAll(",", ""));
					sum += deal;
				}

				// 평균 계산.
				if (cnt != 0)
					result.put(dongName, sum / cnt);

			}

			List<Map.Entry<String, Double>> entries = new ArrayList<>(result.entrySet());

			quick(entries, 0, entries.size() - 1);

			if (list != null && list.size() > 0)
				return new ResponseEntity<List<Map.Entry<String, Double>>>(entries.subList(0, Math.min(5, list.size())), HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.debug(e.toString());
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 정렬
	private void quick(List<Entry<String, Double>> list, int start, int end) {
		int part = partition(list, start, end);
		if (start < part - 1)
			quick(list, start, part - 1);
		if (end > part)
			quick(list, part, end);
	}

	private int partition(List<Entry<String, Double>> list, int start, int end) {
		double pivot = list.get((start + end) / 2).getValue();
		while (start <= end) {
			while (list.get(start).getValue() > pivot)
				start++;
			while (list.get(end).getValue() < pivot)
				end--;
			if (start <= end) {
				swap(list, start, end);
				start++;
				end--;
			}
		}

		return start;
	}

	private void swap(List<Entry<String, Double>> list, int start, int end) {
		Map.Entry<String, Double> temp = list.get(start);
		list.set(start, list.get(end));
		list.set(end, temp);
	}
	// 정렬 끝

}
