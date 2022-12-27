package com.technodrome.multiplication.challenge;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Long> {

    /**
     * @return the lasdt 10 attempts for a given user, identified by their alias.
     */
    @Query("SELECT attempt FROM ChallengeAttempt attempt WHERE attempt.user.alias = ?1 ORDER BY attempt.id DESC")
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);
}
