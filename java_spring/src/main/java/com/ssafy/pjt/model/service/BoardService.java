package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.BoardDto;

public interface BoardService {

List<BoardDto> listBoard(Map<String, String> map) throws SQLException;

void writeBoard(BoardDto boardDto) throws SQLException;

BoardDto getBoard(int boardNo) throws SQLException;

void updateHit(int boardNo) throws SQLException;

void modifyBoard(BoardDto boardDto) throws SQLException;

void deleteBoard(int articleNo) throws SQLException;

List<BoardDto> getSearchBoard(String keyword) throws SQLException;



}
