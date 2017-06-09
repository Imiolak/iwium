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
            robot.ahead(10);
            robot.back(10);
        }

        @Override
        public int compareTo(Rule o) {
            return 0;
        }
    }


