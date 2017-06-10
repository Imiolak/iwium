package robolol.rules;


import net.sf.robocode.battle.Battle;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import robocode.AdvancedRobot;
import robocode.RobocodeFileOutputStream;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Imiolak on 09-Jun-17.
 */
public class PushEnemyToCornerRule implements Rule {
    private final AdvancedRobot robot;

    public PushEnemyToCornerRule(AdvancedRobot robot) {
        this.robot = robot;
    }

    @Override
    public String getName() {
        return "PushEnemyToCorner";
    }

    @Override
    public String getDescription() {
        return "Tries to pressure enemy to corner";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean evaluate(Facts facts) {
        BattlefieldPositionDetails positionDetails =
                (BattlefieldPositionDetails) facts.get("positionDetails");

        Double robotDistance = Math.sqrt(Math.pow(positionDetails.get_enemyX() - positionDetails.getOurX(), 2) +
                Math.pow(positionDetails.get_enemyY() - positionDetails.getOurY(), 2));

        if (positionDetails.getOurField() != positionDetails.getEnemyField() && robotDistance > 100) {
            return true;
        }
        if (positionDetails.getEnemyDistanceToClosestCorner() < positionDetails.getOurDistanceToClosestCorner() && robotDistance > 100) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        Random rng = new Random();
        BattlefieldPositionDetails positionDetails =
                (BattlefieldPositionDetails) facts.get("positionDetails");

        double wantedX, wantedY;

        switch (positionDetails.getEnemyField()) {
            case 1:
                wantedX = 1.5 * positionDetails.get_enemyX();
                wantedY = positionDetails.getBattleFieldHeight() - 1.5 * (positionDetails.getBattleFieldHeight() - positionDetails.get_enemyY());
                break;
            case 2:
                wantedX = positionDetails.getBattleFieldWidth() - 1.5 * (positionDetails.getBattleFieldWidth() - positionDetails.get_enemyX());
                wantedY = positionDetails.getBattleFieldHeight() - 1.5 * (positionDetails.getBattleFieldHeight() - positionDetails.get_enemyY());
                break;
            case 3:
                wantedX = 1.5 * positionDetails.get_enemyX();
                wantedY = 1.5 * positionDetails.get_enemyY();
                break;
            default:
                wantedX = positionDetails.getBattleFieldWidth() - 1.5 * (positionDetails.getBattleFieldWidth() - positionDetails.get_enemyX());
                wantedY = 1.5 * positionDetails.get_enemyY();
                break;
        }

//        wantedX += rng.nextDouble() * 800 * 0.1 - 800 * 0.05;
//        wantedY += rng.nextDouble() * 600 * 0.1 - 600 * 0.05;

        double theta = Math.atan2(wantedY - positionDetails.getOurY(), wantedX - positionDetails.getOurX());
        theta += Math.PI/2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0){
            angle += 360.0;}

        double angleDifference = angle - positionDetails.getOurHeading();
        //if (angleDifference > 5){
        //   robot.setTurnRight(angleDifference);}

        angle = positionDetails.getEnemyBearing();
        robot.setTurnRight(angle);


        Double wantedDistance = Math.sqrt(Math.pow(wantedX - positionDetails.getOurX(), 2) +
                Math.pow(wantedY - positionDetails.getOurY(), 2));
        Double robotDistance = Math.sqrt(Math.pow(positionDetails.get_enemyX() - positionDetails.getOurX(), 2) +
                Math.pow(positionDetails.get_enemyY() - positionDetails.getOurY(), 2));
        if (wantedDistance > 100 || robotDistance > 100) {
            robot.setAhead(70);
        } else {
            robot.setTurnRight(positionDetails.getEnemyBearing() + 90);
            robot.setTurnGunLeft(positionDetails.getEnemyBearing() + 90);
        }
    }

    @Override
    public int compareTo(Rule o) {
        return 0;
    }
}
