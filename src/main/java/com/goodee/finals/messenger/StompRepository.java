package com.goodee.finals.messenger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StompRepository extends JpaRepository<MessengerTestDTO, Long> {

}
