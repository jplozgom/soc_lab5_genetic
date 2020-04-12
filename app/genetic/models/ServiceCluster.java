package genetic.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Service cluster.
 *
 * <p>Represents a service cluster. Each service cluster has a 3 attributes: list of services and a
 * totalCost and totalResponseTime. These last two attributes are used to store the sum of the costs
 * and response times of all the services in the cluster. These two values will be used for
 * normalization *
 */
public class ServiceCluster {
    private String serviceClusterCode = "";
    private List<Service> services;

    private float totalCost = 0;
    private float totalResponseTime = 0;
    private int totalItems = 0;

  /**
   * Instantiates a new Service cluster.
   *
   * @param serviceClusterCode the service cluster code
   */
  public ServiceCluster(String serviceClusterCode) {
        this.serviceClusterCode = serviceClusterCode;
        this.services = new ArrayList<Service>();
    }

  /**
   * Adds a service to a cluster
   *
   * @param service the service
   */
  public void addService(Service service) {
        this.services.add(service);
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
   * Gets service cluster code.
   *
   * @return the service cluster code
   */
  public String getServiceClusterCode() {
        return serviceClusterCode;
    }

  /**
   * Gets service list in this cluster
   *
   * @return services services
   */
  public List<Service> getServices() {
        return services;
    }

  /**
   * Gets total cost.
   *
   * @return the total cost
   */
  public float getTotalCost() {
        return totalCost;
    }

  /**
   * Increases total cost.
   *
   * @param cost the total cost
   */
  public void increaseTotalCost(float cost) {
        this.totalCost += cost;
    }

  /**
   * Gets total response time.
   *
   * @return the total response time
   */
  public float getTotalResponseTime() {
        return totalResponseTime;
    }

  /**
   * Sets total response time.
   *
   * @param responseTime the total response time
   */
  public void increaseTotalResponseTime(float responseTime) {
        this.totalResponseTime += responseTime;
    }

  /**
   * Gets total items.
   *
   * @return the total items
   */
  public int getTotalItems() {
        return totalItems;
    }

  /**
   * Increases total items
   */
  public void increaseTotalItems() {
        this.totalItems++;
    }
}
