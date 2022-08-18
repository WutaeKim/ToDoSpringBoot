package com.wtreasur2.persistency;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtreasur2.entity.ToDoEntity;

public interface ToDoRepository
                extends JpaRepository<ToDoEntity, String> {
        // 기본 메서드가 아닌 데이터베이스 사용 함수
        // user_id를 가지고 조회하는 메서드
        List<ToDoEntity> findByUserId(String userId);
}