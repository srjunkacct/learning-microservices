package com.technodrome.multiplication.challenge;

import com.technodrome.multiplication.user.User;
import com.technodrome.multiplication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO)
    {
        User user = userRepository.findByAlias(attemptDTO.getUserAlias())
                .orElseGet( () -> {
                    log.info("Creating new user with alias {}", attemptDTO.getUserAlias());
                    return userRepository.save(new User(attemptDTO.getUserAlias()));
                });

        boolean isCorrect = ( attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB());
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(null,
                user,
                attemptDTO.getFactorA(),
                attemptDTO.getFactorB(),
                attemptDTO.getGuess(),
                isCorrect);

        ChallengeAttempt storedAttempt = attemptRepository.save(checkedAttempt);
        return checkedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }
}
