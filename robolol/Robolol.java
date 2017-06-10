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
    private Rules movementRules;
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

        //rulesEngine.fire(movementRules, movementFacts);
        rulesEngine.fire(shotRules, shotFacts);
    }

    private void initRules() {
        rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .build();
        movementFacts = new Facts();
        movementRules = new Rules(new PushEnemyToCornerRule(this));
        shotFacts = new Facts();
        shotRules = new Rules();
        shotRules.register(new ShortDistanceShotRule(this));
        shotRules.register(new MidDistanceShotRule(this));
        shotRules.register(new LongDistanceShotRule(this));
    }
}
