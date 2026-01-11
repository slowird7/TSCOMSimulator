package environment;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

public class Targets extends FaceSet {

    private List<Target> targets = new ArrayList<>();

    public Targets() {
        super();
    }

    public Target createTarget(String name, Point3D coord, Point3D bearing) {
        Target newTarget = new Target(name, coord, bearing);
        targets.add(newTarget);
        faces.add(newTarget.getFace());
        nodes.getChildren().add(newTarget.getFace().node);
        return newTarget;
    }

    /**
     * すべてのTarget情報をXML形式でファイルに保存します。
     * @param filePath 保存先のファイルパス
     * @return 保存に成功した場合はtrue、それ以外はfalse
     */
    public boolean saveToXml(String filePath) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filePath, "UTF-8")) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            writer.println("<targets>");
            
            for (Target target : targets) {
                Point3D coord = target.getCoord();
                Point3D norm = target.getBearing();
                String name = target.getFace().name;
                
                writer.println("  <target>");
                writer.println("    <name>" + escapeXml(name) + "</name>");
                writer.println("    <coord x=\"" + coord.getX() + "\" y=\"" + coord.getY() + "\" z=\"" + coord.getZ() + "\"/>");
                writer.println("    <norm x=\"" + norm.getX() + "\" y=\"" + norm.getY() + "\" z=\"" + norm.getZ() + "\"/>");
                writer.println("  </target>");
            }
            
            writer.println("</targets>");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * XMLの特殊文字をエスケープします。
     */
    private String escapeXml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }

    /**
     * XMLファイルからTarget情報を読み込み、Targetオブジェクトを作成します。
     * @param filePath 読み込むXMLファイルのパス
     * @return 読み込みに成功した場合はtrue、それ以外はfalse
     */
    public boolean loadFromXml(String filePath) {
        try {
            // 既存のターゲットをクリア
            targets.clear();
            faces.clear();
            nodes.getChildren().clear();

            // XMLファイルを読み込む
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            // すべてのtarget要素を取得
            NodeList targetNodes = document.getElementsByTagName("target");

            for (int i = 0; i < targetNodes.getLength(); i++) {
                Node node = targetNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element targetElement = (Element) node;

                    // 名前を取得
                    String name = targetElement.getElementsByTagName("name").item(0).getTextContent();

                    // 座標を取得
                    Element coordElement = (Element) targetElement.getElementsByTagName("coord").item(0);
                    double x = Double.parseDouble(coordElement.getAttribute("x"));
                    double y = Double.parseDouble(coordElement.getAttribute("y"));
                    double z = Double.parseDouble(coordElement.getAttribute("z"));
                    Point3D coord = new Point3D(x, y, z);

                    // 法線ベクトルを取得
                    Element normElement = (Element) targetElement.getElementsByTagName("norm").item(0);
                    double nx = Double.parseDouble(normElement.getAttribute("x"));
                    double ny = Double.parseDouble(normElement.getAttribute("y"));
                    double nz = Double.parseDouble(normElement.getAttribute("z"));
                    Point3D bearing = new Point3D(nx, ny, nz);

                    // Targetを作成
                    createTarget(name, coord, bearing);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Target> getTargetsList() {
        return new ArrayList<>(targets);
    }

}
