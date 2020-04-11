package genetic.Parsers;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.nio.file.NoSuchFileException;

/**
 * The type Qos data parser.
 */
public class QosDataParserDocx extends QosDataParser{

    /**
     * Parse data.
     */
    public void parseDataFromFile(){
        try {
            if(this.getFileName().compareTo("") == 0){
                throw new NoSuchFileException("Qos file not found");
                //throw new IOException("Qos file not found");
            }
            FileInputStream fis = new FileInputStream(this.getFileName());
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            System.out.println(extractor.getText());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }



}
