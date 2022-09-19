package com.technodrome.multiplication.challenge;

public interface ChallengeService {

    /**
     * Verifies if an attempt coming from the presentation layer is correct.
     *
     * @return the resulting ChallengeAttempt object.
     */

    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);
}
