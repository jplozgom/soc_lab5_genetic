package genetic.fitnessFunctions;
import genetic.models.Service;
import genetic.models.ServiceCluster;
import org.jgap.*;

public class QosFitnessFunction extends FitnessFunction {

    public static final int WEIGHT_RELIABILITY = 10;
    public static final int WEIGHT_COST = 35;
    public static final int WEIGHT_RESPONSE_TIME = 20;
    public static final int WEIGHT_AVAILABILITY = 35;

    @Override
    protected double evaluate(IChromosome a_subject) {
        return 0;
    }

    /**
     *
     * @param s1
     * @param s2
     * @param s3
     * @param sc1
     * @param sc2
     * @param sc3
     * @return
     */
    public double calculateQoS(Service s1, Service s2, Service s3, ServiceCluster sc1, ServiceCluster sc2, ServiceCluster sc3) {

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

}
