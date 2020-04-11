package genetic;

import genetic.Parsers.QosDataParser;
import genetic.Parsers.QosDataParserDocx;

import java.io.File;

public class Console {

    public static void main(String[] args) {

        //1. Load Qos Data Parser
        QosDataParser qosDataParser = new QosDataParserDocx();
        String path = new File("").getAbsolutePath();
        qosDataParser.setFileName(path + "/data/Lab Sample Input.docx");
        qosDataParser.parseDataFromFile();
    }
}
