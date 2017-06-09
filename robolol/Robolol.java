package robolol;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.RulesEngineBuilder;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robolol.rules.BattlefieldPositionDetails;
import robolol.rules.PushEnemyToCornerRule;

/**
 * Created by Imiolak on 09-Jun-17.
 */
public class Robolol extends AdvancedRobot {

    private RulesEngine rulesEngine;
    private Rules movementRules;
    private Facts movementFacts;

    @Override
    public void run() {
        initRules();

        while (true) {
            turnRadarRightRadians(Double.POSITIVE_INFINITY);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        movementFacts.put("positionDetails",
                new BattlefieldPositionDetails(getX(), getY(), getHeading(), e.getBearing(),
                        e.getDistance(), getBattleFieldHeight(), getBattleFieldWidth()));

        rulesEngine.fire(movementRules, movementFacts);
    }

    private void initRules() {
        rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .build();
        movementFacts = new Facts();
        movementRules = new Rules(new PushEnemyToCornerRule(this));
    }
}
