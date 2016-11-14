package org.isheep.config.conditional;

import org.isheep.config.CustomSpringProfiles;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Created by anthony on 14/11/16.
 */
public class DevProfileCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        if (hasLocalProfile(environment)) {
            return ConditionOutcome.match("A local profile has been found.");
        }
        return ConditionOutcome.noMatch("No local profiles found.");
    }

    private boolean hasLocalProfile(Environment environment) {
        String[] profiles = environment.getActiveProfiles();

        return Arrays.stream(profiles).anyMatch(Predicate.isEqual(CustomSpringProfiles.DEV_PROFILE));
    }

}
