package robolol.rules;

import net.sf.robocode.battle.Battle;

/**
 * Created by Imiolak on 09-Jun-17.
 */
public class BattlefieldPositionDetails {

    private final double ourX;
    private final double ourY;
    private final double ourHeading;
    private final double enemyBearing;
    private final double distanceToEnemy;
    private final double battleFieldHeight;
    private final double battleFieldWidth;

    private double _enemyX;
    private double _enemyY;

    public BattlefieldPositionDetails(double ourX, double ourY, double ourHeading,
                                      double enemyBearing, double distanceToEnemy,
                                      double battleFieldHeight, double battleFieldWidth) {

        this.ourX = ourX;
        this.ourY = ourY;
        this.ourHeading = ourHeading;
        this.enemyBearing = enemyBearing;
        this.distanceToEnemy = distanceToEnemy;
        this.battleFieldHeight = battleFieldHeight;
        this.battleFieldWidth = battleFieldWidth;

        double angleToEnemy = enemyBearing;
        double angle = Math.toRadians((ourHeading + angleToEnemy) % 360);
        _enemyX = (ourX + Math.sin(angle) * distanceToEnemy);
        _enemyY = (ourY + Math.cos(angle) * distanceToEnemy);
    }

    public double getEnemyX() {
        return _enemyX;
    }

    public double getEnemyY() {
        return _enemyY;
    }

    public double getOurX() {
        return ourX;
    }

    public double getOurY() {
        return ourY;
    }

    public double getOurHeading() {
        return ourHeading;
    }

    public double getEnemyBearing() {
        return enemyBearing;
    }

    public double getDistanceToEnemy() {
        return distanceToEnemy;
    }

    public double getBattleFieldHeight() {
        return battleFieldHeight;
    }

    public double getBattleFieldWidth() {
        return battleFieldWidth;
    }

    public double get_enemyX() {
        return _enemyX;
    }

    public double get_enemyY() {
        return _enemyY;
    }

    public int getOurField() {
        if (ourX < battleFieldWidth / 2) {
            if (ourY > battleFieldHeight / 2) {
                return 1;
            }
            return 3;
        } else {
            if (ourY > battleFieldHeight / 2) {
                return 2;
            }
            return 4;
        }
    }

    public int getEnemyField() {
        if (_enemyX < battleFieldWidth / 2) {
            if (_enemyY > battleFieldHeight / 2) {
                return 1;
            }
            return 3;
        } else {
            if (_enemyY > battleFieldHeight / 2) {
                return 2;
            }
            return 4;
        }
    }

    public double getOurDistanceToClosestCorner() {
        switch (getOurField()) {
            case 1:
                return Math.sqrt(ourX * ourX + (battleFieldHeight - ourY) * (battleFieldHeight - ourY));
            case 2:
                return Math.sqrt((battleFieldWidth - ourX) * (battleFieldWidth - ourX)
                        + (battleFieldHeight - ourY) * (battleFieldHeight - ourY));
            case 3:
                return Math.sqrt(ourX * ourX + ourY * ourY);
            default:
                return Math.sqrt((battleFieldWidth - ourX) * (battleFieldWidth - ourX) + ourY * ourY);
        }
    }

    public double getEnemyDistanceToClosestCorner() {
        switch (getOurField()) {
            case 1:
                return Math.sqrt(_enemyX * _enemyX + (battleFieldHeight - _enemyY) * (battleFieldHeight - _enemyY));
            case 2:
                return Math.sqrt((battleFieldWidth - _enemyX) * (battleFieldWidth - _enemyX)
                        + (battleFieldHeight - _enemyY) * (battleFieldHeight - _enemyY));
            case 3:
                return Math.sqrt(_enemyX * _enemyX + _enemyY * _enemyY);
            default:
                return Math.sqrt((battleFieldWidth - _enemyX) * (battleFieldWidth - _enemyX) + _enemyY * _enemyY);
        }
    }
}
