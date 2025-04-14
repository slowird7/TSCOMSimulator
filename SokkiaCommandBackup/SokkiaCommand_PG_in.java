package sokkiacommand;

import connection.ConnectionMode;

/**
 * 「モーター駆動の設定」
 */
public class SokkiaCommand_PG_in extends SokkiaCommand {

    private final String SEND_COMMAND = "*/PG";
    private final int targettype;
    private final int prizumParm;
    private final int prizumDiameter;

    public SokkiaCommand_PG_in(int targettype, double prizumParm, double prizumDiameter) {
        com = ConnectionMode.getInstance();
        receive = "";

        this.targettype = targettype;
        this.prizumParm = (int) prizumParm;
        this.prizumDiameter = (int) prizumDiameter;

        command = SEND_COMMAND + " " + this.targettype + "," + this.prizumParm + "," + this.prizumDiameter;
        comSuc = command + "... succeed.";

    }

    public static void main(String[] args) {

    }
}
