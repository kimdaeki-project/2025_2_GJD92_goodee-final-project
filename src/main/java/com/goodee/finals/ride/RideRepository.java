package com.goodee.finals.ride;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


// JPA에서 DB와 직접 통신하는 계층
// 엔티티(RideDTO)를 DB에 CRUD 기능 담당

// Service와 DB 사이 : Controller -> Service -> Repository -> DB 흐름
// Service는 비즈니스 로직만 집중하고, Repository는 DB 액세스만 담당

// JpaRepository<Entity, PK>를 상속받으면 기본 CRUD 메서드 자동 제공
// 별도 구현체를 작성하지 않아도 됨 (Spring이 프록시 객체 자동 생성)



// JpaRepository에서 기본 제공하는 메서드
// save(entity) → 등록/수정
// findAll() → 전체 조회
// findById(id) → 단일 조회
// deleteById(id) → 삭제
public interface RideRepository extends JpaRepository<RideDTO, String> {
	
	// 어트랙션 기종으로 조회
	List<RideDTO> findByRideType(String rideType);
	
	// 삭제 안 된 전체
    List<RideDTO> findByRideDeletedFalse();

    // 삭제 안 된 + 타입별
    List<RideDTO> findByRideTypeAndRideDeletedFalse(String rideType);

}
