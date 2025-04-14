package sokkiacommand;

import connection.ConnectionMode;

public class SokkiaCommand_JG_in extends SokkiaCommand {

    private final String SEND_COMMAND = "*JG";
    private final double SECPSEC = 9.26;    // for Topcon MS series
    private final int MAX_VALUE = 29400;    // for Topcon MS series

    public SokkiaCommand_JG_in(double horizontalRotationSpeedDegPerSec, double verticalRotationSpeedDegPerSec) {
        com = ConnectionMode.getInstance();
        receive = "";
        int hValue = (int) (horizontalRotationSpeedDegPerSec * 3600. / SECPSEC);
        if (hValue > MAX_VALUE) {
            hValue = MAX_VALUE;
        } else if (hValue < -MAX_VALUE) {
            hValue = -MAX_VALUE;
        }
        int vValue = (int) (verticalRotationSpeedDegPerSec * 3600. / SECPSEC);
        if (vValue > MAX_VALUE) {
            vValue = MAX_VALUE;
        } else if (vValue < -MAX_VALUE) {
            vValue = -MAX_VALUE;
        }
        command = String.format("%s %+06d,%+06d,,,,", SEND_COMMAND, hValue, vValue);
    }

}
