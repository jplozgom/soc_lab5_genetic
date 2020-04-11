package genetic.Parsers;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;

/**
 * The type Qos data parser.
 */
public class QosDataParserDocx extends QosDataParser{

    private String fileName;

    /**
     * Qos data parser.
     *
     * @param fileName the file name
     */
    void QosDataParserDocx(String fileName){
        this.fileName = fileName;
    }

    /**
     * Parse data.
     */
    public void parseDataFromFile(){
        try {
            FileInputStream fis = new FileInputStream(this.fileName);
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            System.out.println(extractor.getText());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }



}
