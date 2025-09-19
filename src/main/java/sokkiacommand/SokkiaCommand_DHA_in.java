package sokkiacommand;

import javafx.concurrent.Task;
import ts.TS;
import ts.TSInterface;

import java.util.regex.Pattern;

/**
 * 座標のステークアウトデータの設定
 */
public class SokkiaCommand_DHA_in extends SokkiaCommand {
    public SokkiaCommand_DHA_in(TSInterface ts) {
        super(ts);
        commandID = "*DHA";
        p = Pattern.compile("^\\*DHA(\\d{7})VA(\\d{7})");
    }

    @Override
    public String makeResponse(String args) {
        m = p.matcher(args);
        if (!m.matches()) {
            return "\u0015";
        }
        double horizontalAngleDMS = Integer.parseInt(m.group(1)) / 10000.;
        double verticalAngleDMS = Integer.parseInt(m.group(2)) / 10000.;
        Task task = tsInterface.getTS().new Rotate(horizontalAngleDMS, verticalAngleDMS);
        task.setOnSucceeded(e -> tsInterface.respond("OK\n"));
        task.setOnFailed(e -> tsInterface.respond("E000\n"));
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        return "\u0006";
    }
}
