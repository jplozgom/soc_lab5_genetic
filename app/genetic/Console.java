package genetic;

import genetic.Parsers.QosDataParser;
import genetic.Parsers.QosDataParserDocx;
import genetic.engine.WSDiscoveryCompositionEngine;
import genetic.fitnessFunctions.QosFitnessFunction;
import genetic.models.Service;
import genetic.models.ServiceCluster;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Console {

    public static void main(String[] args) {

        //1. Load Qos Data Parser
        QosDataParser qosDataParser = new QosDataParserDocx();
        String path = new File("").getAbsolutePath();
        qosDataParser.setFileName(path + "/data/Lab Sample Input.docx");
        HashMap<String, ServiceCluster> serviceClusters = qosDataParser.parseDataFromFile();

        //random test for fitness function
        // Console.randomTest(serviceClusters);

        WSDiscoveryCompositionEngine compositionEngine = new WSDiscoveryCompositionEngine();
        try {
            List<Service> bestServices = compositionEngine.composeServiceWorkflow(true, serviceClusters);
            System.out.println(bestServices.get(0).getCode() + "->" + bestServices.get(1).getCode()+ "->" + bestServices.get(2).getCode());
            System.out.println("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param serviceClusters
     */
    public static void randomTest(HashMap<String, ServiceCluster> serviceClusters ){

        QosFitnessFunction qosFitnessFunction = new QosFitnessFunction();

        ServiceCluster sc1 = serviceClusters.get("SC1");
        ServiceCluster sc2 = serviceClusters.get("SC2");
        ServiceCluster sc3 = serviceClusters.get("SC3");
        Service s1f = null;
        Service s2f = null;
        Service s3f = null;
        float bestScore = 0 ;
        for (int i = 0; i < 60; i++) {
            Service s1 = sc1.getServices().get(ThreadLocalRandom.current().nextInt(0, sc1.getServices().size()));
            Service s2 = sc2.getServices().get(ThreadLocalRandom.current().nextInt(0, sc2.getServices().size()));
            Service s3 = sc3.getServices().get(ThreadLocalRandom.current().nextInt(0, sc3.getServices().size()));
            float testValue = qosFitnessFunction.calculateQoS(s1, s2, s3, sc1, sc2, sc3);
            if(testValue > bestScore ){
                bestScore = testValue;
                s1f = s1;
                s2f = s2;
                s3f = s3;
            }
        }
        System.out.println(s1f.getCode() + "->" + s2f.getCode()+ "->" + s3f.getCode());
        System.out.println(bestScore);
        System.out.println("\n");

    }
}
