package robolol;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.RulesEngineBuilder;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robolol.rules.BattlefieldPositionDetails;
import robolol.rules.EscapeCornerRules;
import robolol.rules.PushEnemyToCornerRule;

/**
 * Created by Imiolak on 09-Jun-17.
 */
public class Robolol extends AdvancedRobot {

    private RulesEngine rulesEngine;
    private Rules pushRules;
    private Rules escapeRules;
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

        rulesEngine.fire(pushRules, movementFacts);
        rulesEngine.fire(escapeRules, movementFacts);
        fire(5);
    }

    private void initRules() {
        rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .build();
        movementFacts = new Facts();
        pushRules = new Rules(new PushEnemyToCornerRule(this));
        escapeRules = new Rules(new EscapeCornerRules(this));
    }
}
