package robolol.rules;


import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import robocode.AdvancedRobot;

/**
 * Created by Imiolak on 09-Jun-17.
 */
public class EscapeCornerRules implements Rule {

        private final AdvancedRobot robot;

        public EscapeCornerRules(AdvancedRobot robot) {
            this.robot = robot;
        }

        @Override
        public String getName() {
            return "EscapeCorner";
        }

        @Override
        public String getDescription() {
            return "Tries to escape from corner";
        }

        @Override
        public int getPriority() {
            return 0;
        }

        @Override
        public boolean evaluate(Facts facts) {
            BattlefieldPositionDetails positionDetails =
                    (BattlefieldPositionDetails) facts.get("positionDetails");

            if (positionDetails.getOurField() != positionDetails.getEnemyField()) {
                return false;
            }
            if (positionDetails.getEnemyDistanceToClosestCorner() < positionDetails.getOurDistanceToClosestCorner()) {
                return false;
            }
            return true;
        }

        @Override
        public void execute(Facts facts) throws Exception {
            BattlefieldPositionDetails positionDetails =
                    (BattlefieldPositionDetails) facts.get("positionDetails");
            double angle;
            switch (positionDetails.getOurField()) {
            case 1:
                angle = 135 - positionDetails.getOurHeading();
                robot.turnRight(angle);
                break;
            case 2:
                angle = 225 - positionDetails.getOurHeading();
                robot.turnRight(angle);
                break;
            case 3:
                angle = 45 - positionDetails.getOurHeading();
                robot.turnRight(angle);
                break;
            default:
                angle = 315 - positionDetails.getOurHeading();
                robot.turnRight(angle);
                break;
        }
        robot.setAhead(positionDetails.getBattleFieldWidth() * 0.2);
        }

        @Override
        public int compareTo(Rule o) {
            return 0;
        }

        private void escape(boolean left, BattlefieldPositionDetails positionDetails){
            Double robotDistance = Math.sqrt(Math.pow(positionDetails.get_enemyX() - positionDetails.getOurX(), 2) +
                    Math.pow(positionDetails.get_enemyY() - positionDetails.getOurY(), 2));
            if (left){
                robot.setTurnLeft(positionDetails.getEnemyBearing() * 1.2 + 15);
            } else {
                robot.setTurnRight(positionDetails.getEnemyBearing() * 1.2 + 15);
            }
            if (robotDistance < 40) {
                if (left){
                    robot.setBack(50);
                    robot.setTurnLeft(positionDetails.getEnemyBearing() + 40);
                    robot.setAhead(30);

                } else {
                    robot.setBack(50);
                    robot.setTurnRight(positionDetails.getEnemyBearing() + 40);
                    robot.setAhead(30);
                }

            } else {
                robot.setAhead(30);
            }
        }

        private void escape2(int field, BattlefieldPositionDetails positionDetails){

        }
    }


