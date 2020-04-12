package genetic.Parsers;

import genetic.models.ServiceCluster;

import java.io.FileInputStream;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.List;

/** The type Qos data parser. */
public abstract class QosDataParser {

    /**
     * File name to parse
     */
    private String fileName = "";

    /**
     * List of service clusters
     */
    private HashMap<String, ServiceCluster> serviceClusters;

    /** Instantiates a new Qos data parser. */
    public QosDataParser() {
        this.serviceClusters = new HashMap<String, ServiceCluster>();
    }

    /** Parse data from file. to be implemented by subclasses */
    public abstract HashMap<String, ServiceCluster> parseDataFromFile();

    /**
    * Open file using file name attribute
    *
    * @return file input stream
    */
    public FileInputStream openFile() {
        try {
            if(this.getFileName().compareTo("") == 0){
                throw new NoSuchFileException("Qos file not found");
                //throw new IOException("Qos file not found");
            }
            //file reader or input stream
            return new FileInputStream(this.getFileName());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
    * Inits a service cluster
    *
    * @param serviceClusterCode the service cluster code
    * @return
    */
    public void initServiceCluster(String serviceClusterCode) {
        if(!this.serviceClusters.containsKey(serviceClusterCode)){
            ServiceCluster serviceCluster = new ServiceCluster(serviceClusterCode);
            this.serviceClusters.put(serviceClusterCode,serviceCluster);
        }
    }

    // -------------- GETTERS AND SETTERS ---------------

    /**
    * Get file name string
    *
    * @return the string
    */
    public String getFileName() {
        return this.fileName;
    };
    /**
    * Get file name.
    *
    * @param fileName the file name
    */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    };

    /**
    * Gets service clusters.
    *
    * @return the service clusters
    */
    public HashMap<String, ServiceCluster> getServiceClusters() {
        return serviceClusters;
    }
}
