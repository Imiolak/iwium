package robolol;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.RulesEngineBuilder;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robolol.rules.*;

/**
 * Created by Imiolak on 09-Jun-17.
 */
public class Robolol extends AdvancedRobot {

    private RulesEngine rulesEngine;
    private Rules pushRules;
    private Rules escapeRules;
    private Facts movementFacts;
    private Rules shotRules;
    private Facts shotFacts;

    @Override
    public void run() {
        initRules();

        turnRadarRightRadians(Double.POSITIVE_INFINITY);
        while (true) {
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        movementFacts.put("positionDetails",
                new BattlefieldPositionDetails(getX(), getY(), getHeading(), e.getBearing(),
                        e.getDistance(), getBattleFieldHeight(), getBattleFieldWidth()));
        shotFacts.put("scannedRobot", new EnemyDetails(this, e));

        rulesEngine.fire(pushRules, movementFacts);
        rulesEngine.fire(escapeRules, movementFacts);
        rulesEngine.fire(shotRules, shotFacts);
    }

    private void initRules() {
        rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .build();

        movementFacts = new Facts();
        pushRules = new Rules(new PushEnemyToCornerRule(this));
        escapeRules = new Rules(new EscapeCornerRules(this));

        shotFacts = new Facts();
        shotRules = new Rules();
        shotRules.register(new ShortDistanceShotRule(this));
        shotRules.register(new MidDistanceShotRule(this));
        shotRules.register(new LongDistanceShotRule(this));
    }
}
