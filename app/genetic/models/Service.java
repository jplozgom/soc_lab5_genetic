package genetic.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

/** The type Service. */
public class Service {

    // ***** Class attributes *****

    // Service Code - S11
    private String code = "";
    // Service Cluster Code - SC1
    private String serviceClusterCode = "";
    // Service Position for bit translation
    @JsonIgnore
    private int position = 0;
    // Service Position in Cluster for bit translation
    @JsonIgnore
    private int positionInCluster = 0;
    // Service cost - 2
    @JsonIgnore
    private float cost = 0;
    // Service reliability - 90
    @JsonIgnore
    private float reliability = 0;
    // Service response time - 3
    @JsonIgnore
    private float responseTime = 0;
    // Service availability - 90
    @JsonIgnore
    private float availability = 0;

  /** Instantiates a new Service. */
  public Service() {}

  /**
   * Gets code.
   *
   * @return the code
   */
  public String getCode() {
        return code;
    }

  /**
   * Sets code.
   *
   * @param code the code
   */
  public void setCode(String code) {
        this.code = code;
    }

  /**
   * Gets cost.
   *
   * @return the cost
   */
  public float getCost() {
        return cost;
    }

  /**
   * Sets cost.
   *
   * @param cost the cost
   */
  public void setCost(float cost) {
        this.cost = cost;
    }

  /**
   * Gets reliability.
   *
   * @return the reliability
   */
  public float getReliability() {
        return reliability;
    }

  /**
   * Sets reliability.
   *
   * @param reliability the reliability
   */
  public void setReliability(float reliability) {
        this.reliability = reliability;
    }

  /**
   * Gets response time.
   *
   * @return the response time
   */
  public float getResponseTime() {
        return responseTime;
    }

  /**
   * Sets response time.
   *
   * @param responseTime the response time
   */
  public void setResponseTime(float responseTime) {
        this.responseTime = responseTime;
    }

  /**
   * Gets availability.
   *
   * @return the availability
   */
  public float getAvailability() {
        return availability;
    }

  /**
   * Sets availability.
   *
   * @param availability the availability
   */
  public void setAvailability(float availability) {
        this.availability = availability;
    }

  /**
   * Gets position.
   *
   * @return the position
   */
  public int getPosition() {
        return position;
    }

  /**
   * Sets position.
   *
   * @param position the position
   */
  public void setPosition(int position) {
        this.position = position;
    }

  /**
   * Gets service cluster code.
   *
   * @return the service cluster code
   */
  public String getServiceClusterCode() {
        return serviceClusterCode;
    }

  /**
   * Sets service cluster code.
   *
   * @param serviceClusterCode the service cluster code
   */
  public void setServiceClusterCode(String serviceClusterCode) {
        this.serviceClusterCode = serviceClusterCode;
    }

  /**
   * Gets position in cluster.
   *
   * @return the position in cluster
   */
  public int getPositionInCluster() {
        return positionInCluster;
    }

  /**
   * Sets position in cluster.
   *
   * @param positionInCluster the position in cluster
   */
  public void setPositionInCluster(int positionInCluster) {
        this.positionInCluster = positionInCluster;
    }
}
