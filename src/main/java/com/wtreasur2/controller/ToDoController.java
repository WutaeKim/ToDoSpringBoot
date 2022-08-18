package com.wtreasur2.controller;

// import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wtreasur2.dto.ResponseDTO;
import com.wtreasur2.dto.ToDoDTO;
import com.wtreasur2.entity.ToDoEntity;
import com.wtreasur2.service.ToDoService;

// import Slf4j;

@RestController
@RequestMapping("todo")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    // 데이터 삽입 요청
    // localhost/todo를 post방식으로 요청
    @PostMapping
    public ResponseEntity<?> createToDo(@RequestBody ToDoDTO dto) {
        try {
            // 임시 유저 아이디 생성
            String temporaryUserId = "temporary-user";
            // 삽입할 Entity 생성
            ToDoEntity entity = ToDoDTO.toEntity(dto);
            // 아이디 설정
            entity.setId(null);
            entity.setUserId(temporaryUserId);
            // 삽입
            List<ToDoEntity> entities = toDoService.create(entity);
            // log.error(entities.toString());
            // 응답을 만들기 위해서 ToDoEntity의 List를
            // ToDoDTO의 List로 변환
            // 이 문법(람다와 스트림)은 jdk 1.8부터 지원
            List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
            // 응답 객체를 생성
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
            // REST 응답 객체로 만들기
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getLocalizedMessage();
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveToDoList() {
        String temporaryUserId = "temporary-user";
        // 서비스를 실행해서 조건에 맞는 Entity가져오기
        List<ToDoEntity> entities = toDoService.retrieve(temporaryUserId);
        // Entity를 DTO로 변환
        List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
        // 응답 데이터 생성
        ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
        // 위와 동일한 문장
        // ResponseDTO<ToDoDTO> response = new ResponseDTO<>();
        // response.setData(dtos);

        return ResponseEntity.ok().body(response);
    }

    // 데이터 수정
    @PutMapping
    public ResponseEntity<?> updateToDo(@RequestBody ToDoDTO dto) {
        String temporaryUserId = "temporary-user";
        ToDoEntity entity = ToDoDTO.toEntity(dto);
        entity.setUserId(temporaryUserId);
        List<ToDoEntity> entities = toDoService.update(entity);
        // Entity를 DTO로 변환
        List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
        // 응답 데이터 생성
        ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
        // 위와 동일한 문장
        // ResponseDTO<ToDoDTO> response = new ResponseDTO<>();
        // response.setData(dtos);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteToDo(@RequestBody ToDoDTO dto) {
        // 넘겨받은 DTO를 가지고 Entity생성
        ToDoEntity entity = ToDoDTO.toEntity(dto);
        entity.setUserId("temporary-user");
        // 삭제 작업 수행
        List<ToDoEntity> entities = toDoService.delete(entity);
        // Entity를 DTO로 변환
        List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
        // 응답 데이터 생성
        ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
        // 위와 동일한 문장
        // ResponseDTO<ToDoDTO> response = new ResponseDTO<>();
        // response.setData(dtos);

        return ResponseEntity.ok().body(response);
    }
}
