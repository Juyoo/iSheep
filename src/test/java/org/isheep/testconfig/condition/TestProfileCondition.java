package org.isheep.testconfig.condition;

import org.isheep.config.CustomSpringProfiles;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Created by raymo on 27/11/2016.
 */
public class TestProfileCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(final ConditionContext conditionContext, final AnnotatedTypeMetadata annotatedTypeMetadata) {
        final Environment environment = conditionContext.getEnvironment();
        if (hasTestProfile(environment)) {
            return ConditionOutcome.match("A test profile has been found.");
        }
        return ConditionOutcome.noMatch("No test profiles found.");
    }

    private boolean hasTestProfile(final Environment environment) {
        final String[] profiles = environment.getActiveProfiles();

        return Arrays.stream(profiles).anyMatch(Predicate.isEqual(CustomSpringProfiles.TEST_PROFILE));
    }

}
