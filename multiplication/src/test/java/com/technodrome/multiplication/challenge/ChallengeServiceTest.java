package com.technodrome.multiplication.challenge;

import com.technodrome.multiplication.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChallengeAttemptRepository attemptRepository;

    @BeforeEach
    public void setUp()
    {
       challengeService = new ChallengeServiceImpl(userRepository, attemptRepository);
       given(attemptRepository.save(any())).will(returnsFirstArg());
    }

    @Test
    public void checkCorrectAttemptTest()
    {
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "John Doe", 50 * 60);

        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User("John Doe"));
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void checkWrongAttemptTest()
    {
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "John Doe", 50 * 60 + 1);

        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        then(resultAttempt.isCorrect()).isFalse();
    }

    @Test
    public void checkExistingUserTest()
    {
        User existingUser = new User(1L, "John Doe");
        given(userRepository.findByAlias("John Doe"))
                .willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "John Doe", 5000);
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);

    }
}
