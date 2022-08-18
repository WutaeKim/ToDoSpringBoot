package com.wtreasur2.service;

import java.util.List;
import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wtreasur2.entity.ToDoEntity;
import com.wtreasur2.persistency.ToDoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
// final로 선언된 인스턴스 속성을 외부로부터 주입받도록 해주는 어노테이션
@RequiredArgsConstructor
// 로그 기록을 위한 객체 주입 - logger가 자동 주입
@Slf4j
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;

    // 유효성 검사를 위한 메서드
    private void validate(final ToDoEntity entity) {
        if (entity == null) {
            log.warn("Entity는 null일 수가 없다");
            throw new RuntimeException("Entity cannot be null");
        }
        if (entity.getUserId() == null) {
            log.warn("알 수 없는 사용자");
            throw new RuntimeException("Unknown user has come in. Keep an eye on him");
        }
    }

    // 데이터 삽입
    public List<ToDoEntity> create(final ToDoEntity entity) {
        // 유효성 검사
        validate(entity);
        // 데이터 삽입
        toDoRepository.save(entity);
        // 로그 출력
        log.info("Entity Id: {} is saved", entity.getId());

        // userId에 해당하는 전체 데이터 리턴
        return toDoRepository.findByUserId(entity.getUserId());
    }

    // 데이터 조회
    public List<ToDoEntity> retrieve(final String userId) {
        // userId에 해당하는 전체 데이터 리턴
        return toDoRepository.findByUserId(userId);
    }

    // 데이터 수정
    public List<ToDoEntity> update(final ToDoEntity entity) {
        // 유효성 검사
        validate(entity);
        // 데이터 존재 여부를 확인
        final Optional<ToDoEntity> original = toDoRepository.findById(entity.getId());
        // 데이터가 존재하는 경우에만 작업 수행
        original.ifPresent(
                todo -> {
                    todo.setTitle(entity.getTitle());
                    todo.setDone(entity.isDone());
                    toDoRepository.save(todo);
                });

        // userId에 해당하는 전체 데이터 리턴
        return retrieve(entity.getUserId());
    }

    // 데이터 삭제
    public List<ToDoEntity> delete(final ToDoEntity entity) {
        // 유효성 검사
        validate(entity);

        try {
            toDoRepository.delete(entity);
        } catch (Exception e) {
            log.error("삭제실패");
        }
        return retrieve(entity.getUserId());
    }

}
