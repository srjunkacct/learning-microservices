package com.technodrome.multiplication.challenge;

import java.util.List;

public interface ChallengeService {

    /**
     * Verifies if an attempt coming from the presentation layer is correct.
     *
     * @return the resulting ChallengeAttempt object.
     */

    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);
    List<ChallengeAttempt> getStatsForUser(String userAlias);
}
