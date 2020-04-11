package genetic.Parsers;

/** The type Qos data parser. */
public abstract class QosDataParser {
    /**
     * File name to parse
     */
    private String fileName = "";
    /** Parse data from file. */
    public abstract void parseDataFromFile();

    /**
    * Get file name string
    * @return the string
    */
    public String getFileName() {
        return this.fileName;
    };
    /**
    * Get file name.
    * @param fileName the file name
    */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    };
}
