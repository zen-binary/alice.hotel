package com.winwintek.web.backend.api.repository.client.student.agreeTerm;

import com.winwintek.web.backend.api.entity.client.student.agreeTerm.AgreeTermUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgreeTermRepository extends JpaRepository<AgreeTermUserEntity, String> {

    Optional<AgreeTermUserEntity> findByUserId(String s);

}
