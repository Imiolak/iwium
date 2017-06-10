package robolol.rules;

import robocode.AdvancedRobot;

import java.awt.geom.Point2D;

/**
 * Created by Imiolak on 10-Jun-17.
 */
public abstract class ShotRuleBase {

    protected final AdvancedRobot robot;

    protected ShotRuleBase(AdvancedRobot robot) {
        this.robot = robot;
    }

    public void shoot(EnemyDetails enemyDetails, double shotStrength) {
        double bulletSpeed = 20 - shotStrength * 3;
        long time = (long)(enemyDetails.getDistance() / bulletSpeed);

        double futureX = enemyDetails.getFutureX(time);
        double futureY = enemyDetails.getFutureY(time);
        double absoluteDegrees = absoluteBearing(robot.getX(), robot.getY(), futureX, futureY);

        robot.setTurnGunRight(normalizeBearing(absoluteDegrees - robot.getGunHeading()));
        if (robot.getGunHeat() == 0
                && Math.abs(robot.getGunTurnRemaining()) < 10) {
            robot.setFire(shotStrength);
        }
    }

    private double normalizeBearing(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;

        return angle;
    }

    private double absoluteBearing(double x1, double y1, double x2, double y2) {
        double x0 = x2 - x1;
        double y0 = y2 - y1;
        double hyp = Point2D.distance(x1, y1, x2, y2);
        double arcSin = Math.toDegrees(Math.asin(x0 / hyp));
        double bearing;

        if (x0 > 0 && y0 > 0) {
            bearing = arcSin;
        } else if (x0 < 0 && y0 > 0) {
            bearing = 360 + arcSin;
        } else if (x0 > 0 && y0 < 0) {
            bearing = 180 - arcSin;
        } else {
            bearing = 180 - arcSin;
        }

        return bearing;
    }
}
