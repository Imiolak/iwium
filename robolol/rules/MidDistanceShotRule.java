package robolol.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import robocode.AdvancedRobot;

/**
 * Created by Imiolak on 10-Jun-17.
 */
public class MidDistanceShotRule extends ShotRuleBase implements Rule {

    public MidDistanceShotRule(AdvancedRobot robot) {
        super(robot);
    }

    @Override
    public String getName() {
        return "Mid";
    }

    @Override
    public String getDescription() {
        return "Firing at mid distances";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean evaluate(Facts facts) {
        Object enemyDetailsObj = facts.get("scannedRobot");
        if (enemyDetailsObj == null)
            return false;

        EnemyDetails enemyDetails = (EnemyDetails) enemyDetailsObj;
        return enemyDetails.getDistance() <= 550
                && enemyDetails.getDistance() > 200;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        EnemyDetails enemyDetails = (EnemyDetails) facts.get("scannedRobot");
        super.shoot(enemyDetails, 2.0);

        facts.remove("scannedRobot");
    }

    @Override
    public int compareTo(Rule o) {
        return this.getName().compareTo(o.getName());
    }
}
