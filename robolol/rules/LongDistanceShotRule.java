package robolol.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * Created by Imiolak on 10-Jun-17.
 */
public class LongDistanceShotRule extends ShotRuleBase implements Rule {

    public LongDistanceShotRule(AdvancedRobot robot) {
        super(robot);
    }

    @Override
    public String getName() {
        return "Long";
    }

    @Override
    public String getDescription() {
        return "Firing at long distances";
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
        return enemyDetails.getDistance() > 550;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        EnemyDetails enemyDetails = (EnemyDetails) facts.get("scannedRobot");
        super.shoot(enemyDetails, 1.0);

        facts.remove("scannedRobot");
    }

    @Override
    public int compareTo(Rule o) {
        return this.getName().compareTo(o.getName());
    }
}
