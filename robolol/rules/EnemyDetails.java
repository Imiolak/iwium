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
        return getX() + Math.sin(Math.toRadians(scannedRobot.getHeading())) * scannedRobot.getVelocity();
    }

    public double getFutureY(long when) {
        return getY() + Math.cos(Math.toRadians(scannedRobot.getHeading())) * scannedRobot.getVelocity();
    }
}
