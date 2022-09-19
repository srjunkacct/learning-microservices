package com.technodrome.multiplication.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.SecureRandom;
import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
public class ChallengeGeneratorServiceTest {

    private ChallengeGeneratorService challengeGeneratorService;

    private Random random;

    @BeforeEach
    public void setUp()
    {
        random = mock(SecureRandom.class, withSettings().withoutAnnotations());
        challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits()
    {
        given(random.nextInt(89)).willReturn(20,30);
        Challenge challenge = challengeGeneratorService.randomChallenge();
        then(challenge).isEqualTo(new Challenge(31,41));
    }
}
