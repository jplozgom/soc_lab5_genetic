package genetic;

import genetic.Parsers.QosDataParser;
import genetic.Parsers.QosDataParserDocx;
import genetic.fitnessFunctions.QosFitnessFunction;
import genetic.models.Service;
import genetic.models.ServiceCluster;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Console {

    public static void main(String[] args) {

        //1. Load Qos Data Parser
        QosDataParser qosDataParser = new QosDataParserDocx();
        String path = new File("").getAbsolutePath();
        qosDataParser.setFileName(path + "/data/Lab Sample Input.docx");
        HashMap<String, ServiceCluster> serviceClusters = qosDataParser.parseDataFromFile();
        System.out.println(serviceClusters.size());

        QosFitnessFunction qosFitnessFunction = new QosFitnessFunction();

        ServiceCluster sc1 = serviceClusters.get("SC1");
        ServiceCluster sc2 = serviceClusters.get("SC2");
        ServiceCluster sc3 = serviceClusters.get("SC3");
        for (int i = 0; i < 60; i++) {
            Service s1 = sc1.getServices().get(ThreadLocalRandom.current().nextInt(0, sc1.getServices().size()));
            Service s2 = sc2.getServices().get(ThreadLocalRandom.current().nextInt(0, sc2.getServices().size()));
            Service s3 = sc3.getServices().get(ThreadLocalRandom.current().nextInt(0, sc3.getServices().size()));
            double testValue = qosFitnessFunction.calculateQoS(s1, s2, s3, sc1, sc2, sc3);
            System.out.println(s1.getCode() + " __ " + s2.getCode()+ " __ " + s3.getCode());
            System.out.println(testValue);
            System.out.println("\n");
        }
    }
}
