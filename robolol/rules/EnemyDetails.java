package robolol.rules;

import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * Created by Imiolak on 10-Jun-17.
 */
public class EnemyDetails {
    private final Robot robot;
    private final ScannedRobotEvent scannedRobot;

    public EnemyDetails(Robot robot, ScannedRobotEvent scannedRobot) {

        this.robot = robot;
        this.scannedRobot = scannedRobot;
    }

    public double getDistance() {
        return scannedRobot.getDistance();
    }

    public double getAbsoluteBearing() {
        double absBearingDeg = (robot.getHeading() + scannedRobot.getBearing());
        if (absBearingDeg < 0) absBearingDeg += 360;

        return absBearingDeg;
    }

    public double getX() {
        return robot.getX() + Math.sin(Math.toRadians(getAbsoluteBearing())) * getDistance();
    }

    public double getY() {
        return robot.getY() + Math.cos(Math.toRadians(getAbsoluteBearing())) * getDistance();
    }

    public double getFutureX(long when) {
        double futureX = getX() + Math.sin(Math.toRadians(scannedRobot.getHeading())) * scannedRobot.getVelocity() * when;
        if (futureX < 0)
            return 0.0;
        if (futureX > 800.0)
            return 800.0;
        return futureX;
    }

    public double getFutureY(long when) {
        double futureY = getY() + Math.cos(Math.toRadians(scannedRobot.getHeading())) * scannedRobot.getVelocity() * when;
        if (futureY < 0)
            return 0.0;
        if (futureY > 600.0)
            return 600.0;
        return futureY;
    }
}
