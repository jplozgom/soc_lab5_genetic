package controllers;

import genetic.Parsers.QosDataParser;
import genetic.Parsers.QosDataParserDocx;
import genetic.engine.WSDiscoveryCompositionEngine;
import genetic.engine.WebServiceWorkflow;
import genetic.models.ServiceCluster;
import play.libs.Json;
import play.mvc.*;

import java.io.File;
import java.util.HashMap;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ApiController extends Controller {

    /**
     * [webServiceComposition description]
     * @return [description]
     */
    public Result webServiceComposition() {
        //1. Load Qos Data Parser
        QosDataParser qosDataParser = new QosDataParserDocx();
        String path = new File("").getAbsolutePath();
        qosDataParser.setFileName(path + "/data/Lab Sample Input.docx");
        HashMap<String, ServiceCluster> serviceClusters = qosDataParser.parseDataFromFile();

        WSDiscoveryCompositionEngine compositionEngine = new WSDiscoveryCompositionEngine();
        try {
            WebServiceWorkflow webServiceWorkflow = compositionEngine.composeServiceWorkflow(true, serviceClusters);
            return ok(Json.toJson(webServiceWorkflow)).withHeader("Access-Control-Allow-Origin", "*");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ok(Json.newObject()).withHeader("Access-Control-Allow-Origin", "*");
    }

}
