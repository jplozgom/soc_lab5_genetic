package genetic.fitnessFunctions;
import genetic.models.Service;
import genetic.models.ServiceCluster;
import org.jgap.*;

import java.util.HashMap;

/** The type Qos fitness function. */
public class QosFitnessFunction extends FitnessFunction {

  /** The constant WEIGHT_RELIABILITY. */
  public static final int WEIGHT_RELIABILITY = 10;

  /** The constant WEIGHT_COST. */
  public static final int WEIGHT_COST = 35;

  /** The constant WEIGHT_RESPONSE_TIME. */
  public static final int WEIGHT_RESPONSE_TIME = 20;

  /** The constant WEIGHT_AVAILABILITY. */
  public static final int WEIGHT_AVAILABILITY = 35;

    private HashMap<String, ServiceCluster> serviceClusters = null;

    @Override
    /**
     * Determine the fitness of the given Chromosome instance. The higher the
     * return value, the more fit the instance. This method should always
     * return the same fitness value for two equivalent Chromosome instances.
     *
     * @param a_subject the Chromosome instance to evaluate
     *
     * @return positive double reflecting the fitness rating of the given
     * Chromosome
     * @since 2.0 (until 1.1: return type int)
     * @author Neil Rotstan, Klaus Meffert, John Serri
     */
    public double evaluate(IChromosome a_subject) {
        // Take care of the fitness evaluator. It could either be weighting higher
        // fitness values higher (e.g.DefaultFitnessEvaluator). Or it could weight
        // lower fitness values higher, because the fitness value is seen as a
        // defect rate (e.g. DeltaFitnessEvaluator)
        boolean defaultComparation = a_subject.getConfiguration().getFitnessEvaluator().isFitter(2, 1);

        // The fitness value measures both how close the value is to the
        // target amount supplied by the user and the total number of coins
        // represented by the solution. We do this in two steps: first,
        // we consider only the represented amount of change vs. the target
        // amount of change and return higher fitness values for amounts
        // closer to the target, and lower fitness values for amounts further
        // away from the target. Then we go to step 2, which returns a higher
        // fitness value for solutions representing fewer total coins, and
        // lower fitness values for solutions representing more total coins.
        // ------------------------------------------------------------------
        int service_SC1_id = (Integer) a_subject.getGene(0).getAllele();
        int service_SC2_id = (Integer) a_subject.getGene(1).getAllele();
        int service_SC3_id = (Integer) a_subject.getGene(2).getAllele();

        ServiceCluster sc1 = this.serviceClusters.get("SC1");
        ServiceCluster sc2 = this.serviceClusters.get("SC2");
        ServiceCluster sc3 = this.serviceClusters.get("SC3");

        Service service_SC1 = sc1.getServices().get(service_SC1_id);
        Service service_SC2 = sc2.getServices().get(service_SC2_id);
        Service service_SC3 = sc3.getServices().get(service_SC3_id);

        double fitness;
        fitness = 0.0d;
        fitness += this.calculateQoS(service_SC1, service_SC2, service_SC3, sc1, sc2, sc3);

        System.out.println(service_SC1.getCode() + "_" + service_SC2.getCode() + "_"+service_SC3.getCode() + " = " + String.valueOf(fitness));
        // Make sure fitness value is always positive.
        // -------------------------------------------
        return Math.max(0d, fitness);
    }

  /**
   * Calculate qo s double.
   *
   * @param s1 the s 1
   * @param s2 the s 2
   * @param s3 the s 3
   * @param sc1 the sc 1
   * @param sc2 the sc 2
   * @param sc3 the sc 3
   * @return double
   */
  public float calculateQoS(Service s1, Service s2, Service s3, ServiceCluster sc1, ServiceCluster sc2, ServiceCluster sc3) {

        //1. calculate flow cost and normalize it
        float cost = s1.getCost() / sc1.getTotalCost() + s2.getCost() / sc2.getTotalCost() + s3.getCost() / sc3.getTotalCost();
        //2. calculate flow response time and normalize it
        float responseTime = s1.getResponseTime() / sc1.getTotalResponseTime() + s2.getResponseTime() / sc2.getTotalResponseTime() + s3.getResponseTime() / sc3.getTotalResponseTime();
        //3. Calculate reliability of flow
        float reliability = this.calculateReliability(s1.getReliability() , s2.getReliability() , s3.getReliability());
        //4. Calculate availability of flow
        float availability = this.calculateAvailability(s1.getAvailability() , s2.getAvailability() , s3.getAvailability());

        //5. Calculate QoS using the weight of each attribute
        return (1 - cost) * QosFitnessFunction.WEIGHT_COST / 100 + (1 - responseTime) * QosFitnessFunction.WEIGHT_RESPONSE_TIME / 100 + reliability * QosFitnessFunction.WEIGHT_RELIABILITY / 100 + availability * QosFitnessFunction.WEIGHT_AVAILABILITY / 100;
    }

    /**
     *
     * @param rel1
     * @param rel2
     * @param rel3
     * @return
     */
    private float calculateReliability(float rel1, float rel2, float rel3){
        return Math.min(rel3, Math.min(rel1, rel1 * rel2));
    }

    /**
     *
     * @param av1
     * @param av2
     * @param av3
     * @return
     */
    private float calculateAvailability(float av1, float av2, float av3){
        return Math.min(av3, Math.min(av1, av1 * av2));
    }

  /**
   * Gets service clusters.
   *
   * @return the service clusters
   */
  public HashMap<String, ServiceCluster> getServiceClusters() {
        return serviceClusters;
    }

  /**
   * Sets service clusters.
   *
   * @param serviceClusters the service clusters
   */
  public void setServiceClusters(HashMap<String, ServiceCluster> serviceClusters) {
        this.serviceClusters = serviceClusters;
    }
}
