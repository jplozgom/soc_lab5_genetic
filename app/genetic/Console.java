package genetic;

import genetic.Parsers.QosDataParser;
import genetic.Parsers.QosDataParserDocx;

public class Console {

    public static void main(String[] args) {

        //1. Load Qos Data Parser
        QosDataParser qosDataParser = new QosDataParserDocx();
        qosDataParser.setFileName("/../data/Lab Sample Input.docx");

        qosDataParser.parseDataFromFile();
    }
}
