package com.goodee.finals.fault;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//JPA에서 DB와 직접 통신하는 계층
//엔티티(RideDTO)를 DB에 CRUD 기능 담당

//Service와 DB 사이 : Controller -> Service -> Repository -> DB 흐름
//Service는 비즈니스 로직만 집중하고, Repository는 DB 액세스만 담당

//JpaRepository<Entity, PK>를 상속받으면 기본 CRUD 메서드 자동 제공
//별도 구현체를 작성하지 않아도 됨 (Spring이 프록시 객체 자동 생성)

//JpaRepository에서 기본 제공하는 메서드
//save(entity) → 등록/수정
//findAll() → 전체 조회
//findById(id) → 단일 조회
//deleteById(id) → 삭제
@Repository
public interface FaultRepository extends JpaRepository<FaultDTO, Integer> {
	
	// 기본 전체 조회
	@Query("SELECT f FROM FaultDTO f WHERE f.faultDelete = false")
	Page<FaultDTO> findAllByFaultDeleteFalse(Pageable pageable);
	
	// 어트랙션 검색
	@Query("SELECT f FROM FaultDTO f WHERE f.faultDelete = false " 
			+ "AND f.rideDTO.rideName Like CONCAT('%', :keyword, '%')")
	Page<FaultDTO> findByRide(@Param("keyword") String keyword, Pageable pageable);
	
	// 신고 제목 검색
	@Query("SELECT f FROM FaultDTO f WHERE f.faultDelete = false "
			+ "AND f.faultTitle LIKE CONCAT('%', :keyword, '%')")
	Page<FaultDTO> findByTitle(@Param("titleCode") String titleCode, Pageable pageable);
	
	// 담당자 검색
	@Query("SELECT f FROM FaultDTO f WHERE f.faultDelete = false "
			+ "AND f.staffDTO.staffName LIKE CONCAT('%', :keyword ,'%')")
	Page<FaultDTO> findByStaff(@Param("keyword") String keyword, Pageable pageable);
	
	// 신고 유형 검색
	@Query("SELECT f FROM FaultDTO f WHERE f.faultDelete = false "
			+ "AND f.faultState = :stateCode")
	Page<FaultDTO> findByState(@Param("stateCode") Integer stateCode, Pageable pageable);
	
	
}
