package genetic.models;

/** The type Service. */
public class Service {

    // ***** Class attributes *****

    // Service Code - S11
    private String code = "";
    // Service Position for bit translation
    private int position = 0;
    // Service cost - 2
    private float cost = 0;
    // Service reliability - 90
    private float reliability = 0;
    // Service response time - 3
    private float responseTime = 0;
    // Service availability - 90
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
}
