package genetic.Parsers;

import genetic.models.Service;
import genetic.models.ServiceCluster;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * The type Qos data parser.
 */
public class QosDataParserDocx extends QosDataParser{

    /**
     * Constructor
     */
    public QosDataParserDocx() {
        super();
    }

    /**
     * Parse data.
     */
    public HashMap<String, ServiceCluster> parseDataFromFile(){

        String currentClusterCode = "";

        try {
            //file reader or input stream
            FileInputStream fis = this.openFile();
            //Document using apache poi // XWPF (XML Word Processor Format)
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            Iterator<XWPFTable> iterator = xdoc.getTablesIterator();
            if(iterator.hasNext())
            {
                XWPFTable table = iterator.next();
                this.importRows(table);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return this.getServiceClusters();
    }

    /**
     * Imports the each row in the table, it ignores row 1.
     * @param table
     */
    private void importRows(XWPFTable table){
        List<XWPFTableRow> rows = table.getRows();
        int i = 0;
        //Property to keep track of the current cluster that is being imported
        String currentServiceClusterCode = "";

        for(XWPFTableRow row : rows) {
            //ignore header
            if(i > 0){
                currentServiceClusterCode = this.importCells(row, currentServiceClusterCode);
            }
            i++;
        };
    }

    /**
     * Import all the cells in a Row. It loops over every cells and adds each property to a service. At the end it adds the new service to it's cluster
     * @param row
     * @param currentServiceClusterCode
     * @return
     */
    private String importCells(XWPFTableRow row, String currentServiceClusterCode){
        List<XWPFTableCell> cells = row.getTableCells();
        ServiceCluster currentServiceCluster = null;
        Service service = new Service();
        int i = 0;

        for(XWPFTableCell cell : cells) {
            if(i == 0){
                service = new Service();
                if(cell.getText().compareTo("") != 0){
                    currentServiceClusterCode = cell.getText().toString();
                    this.initServiceCluster(currentServiceClusterCode);
                    currentServiceCluster = this.getServiceClusters().get(currentServiceClusterCode);
                }else{
                    currentServiceCluster = this.getServiceClusters().get(currentServiceClusterCode);
                }
            }else{
                //add every property to a Service, one string and all float properties
                if(i == 1){
                    service.setCode(cell.getText().toString());
                }else{
                    float value = Float.parseFloat(cell.getText().toString());
                    if(i == 2){
                        //cost
                        service.setCost(value);
                    }
                    else if(i == 3){
                        //reliability
                        service.setReliability(value / 100);
                    }
                    else if(i == 4){
                        //response time
                        service.setResponseTime(value);
                    }
                    else if(i == 5){
                        //availability
                        service.setAvailability(value / 100);
                    }
                }
            }
            i++;

            //save current service cluster in list
            if(i >= cells.size()){
                currentServiceCluster.addService(service);
                currentServiceCluster.increaseTotalCost(service.getCost());
                currentServiceCluster.increaseTotalResponseTime(service.getResponseTime());
                this.getServiceClusters().replace(currentServiceClusterCode, currentServiceCluster);
            }
        }

        return currentServiceClusterCode;
    }



}
