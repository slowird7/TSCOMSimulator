package sokkiacommand;

import connection.ConnectionMode;

/**
 * 「モーター駆動の設定」
 */
public class SokkiaCommand_PA_in extends SokkiaCommand {

    private final String SEND_COMMAND = "*/PA";

    public SokkiaCommand_PA_in(int searchMethod, int trackingMode, double searchRangeHDMS, double searchRangeVDMS) {
        com = ConnectionMode.getInstance();
        receive = "";
        command = String.format("%s %d,%d,%.4f, %.4f", SEND_COMMAND, searchMethod, trackingMode, searchRangeHDMS, searchRangeVDMS);

        comSuc = command + "... succeed.";
    }

    public static void main(String[] args) {

    }
}
