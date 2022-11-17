package com.ssafy.pjt.controller;

import java.util.List;
import java.util.Map;

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

import com.ssafy.pjt.model.dto.BoardDto;
import com.ssafy.pjt.model.service.BoardService;

@RestController
@RequestMapping("/api/board")
@CrossOrigin("*")
public class BoardRestController {
	Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	
	private BoardService boardService;

	@Autowired
	public BoardRestController(BoardService boardService) {
		this.boardService = boardService;
	}

	@PostMapping
	public ResponseEntity<?> write(@RequestBody BoardDto boardDto) {
		try {
			boardService.writeBoard(boardDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<?> listBoard(@RequestParam Map<String, String> map) {
		try {		
			logger.debug(map.toString());
			List<BoardDto> list = boardService.listBoard(map);
			if (list != null && list.size() > 0)
				return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{boardNo}")
	public ResponseEntity<?> getBoard(@PathVariable("boardNo") int boardNo) {
		try {
			BoardDto boardDto = boardService.getBoard(boardNo);
			if(boardDto != null) {
				boardService.updateHit(boardNo);
				boardDto = boardService.getBoard(boardNo);
				return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
			}
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{boardNo}")
	public ResponseEntity<?> modifyBoard(@RequestBody BoardDto boardDto,@PathVariable("boardNo") int boardNo) {
		try {
			boardDto.setBoardNo(boardNo);
			boardService.modifyBoard(boardDto);
		
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<?> deleteBoard(@PathVariable("boardNo") int boardNo) {
		try {
			boardService.deleteBoard(boardNo);
			
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> getSearchBoard(@RequestParam("keyword") String keyword) {
		try {		
			List<BoardDto> list = boardService.getSearchBoard(keyword);
			
			if (list != null && list.size() > 0)
				return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
