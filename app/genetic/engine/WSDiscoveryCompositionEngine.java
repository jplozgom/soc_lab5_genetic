package genetic.engine;

import genetic.fitnessFunctions.QosFitnessFunction;
import genetic.models.Service;
import genetic.models.ServiceCluster;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import java.io.*;

import org.jgap.*;
import org.jgap.audit.*;
import org.jgap.data.*;
import org.jgap.impl.*;
import org.jgap.xml.*;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WSDiscoveryCompositionEngine {

    /**
     * The total number of times we'll let the population evolve.
     */
    private static final int MAX_ALLOWED_EVOLUTIONS = 50;

    public static EvolutionMonitor m_monitor;

    public WebServiceWorkflow composeServiceWorkflow (boolean a_doMonitor, HashMap<String, ServiceCluster> serviceClusters) throws Exception {
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------
        Configuration conf = new DefaultConfiguration();
        // Care that the fittest individual of the current population is
        // always taken to the next generation.
        // Consider: With that, the pop. size may exceed its original
        // size by one sometimes!
        // -------------------------------------------------------------
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(false);
        // Set the fitness function we want to use, which is our
        // MinimizingMakeChangeFitnessFunction. We construct it with
        // the target amount of change passed in to this method.
        // ---------------------------------------------------------
        QosFitnessFunction qosFitnessFunction = new QosFitnessFunction();
        qosFitnessFunction.setServiceClusters(serviceClusters);

        FitnessFunction myFunc = qosFitnessFunction;
        conf.setFitnessFunction(myFunc);

        // Now we need to tell the Configuration object how we want our
        // Chromosomes to be setup. We do that by actually creating a
        // sample Chromosome and then setting it on the Configuration
        // object. Ee want our Chromosomes to each
        // have 3 genes, one for each Service Cluster. We want the
        // values (alleles) of those genes to be integers, which represent
        // one option in each cluster. We therefore use the
        // IntegerGene class to represent each of the genes. That class
        // also lets us specify a lower and upper bound, which we set
        // to 0 and the size of the elements in each cluster.
        // --------------------------------------------------------------
        ServiceCluster sc1 = serviceClusters.get("SC1");
        ServiceCluster sc2 = serviceClusters.get("SC2");
        ServiceCluster sc3 = serviceClusters.get("SC3");

        Gene[] sampleGenes = new Gene[3];
        sampleGenes[0] = new IntegerGene(conf, 0, sc1.getServices().size() - 1); // SC1
        sampleGenes[1] = new IntegerGene(conf, 0, sc2.getServices().size() - 1); // SC2
        sampleGenes[2] = new IntegerGene(conf, 0, sc3.getServices().size() - 1); // SC3

        IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        // Finally, we need to tell the Configuration object how many
        // Chromosomes we want in our population. The more Chromosomes,
        // the larger number of potential solutions (which is good for
        // finding the answer), but the longer it will take to evolve
        // the population (which could be seen as bad).
        // ------------------------------------------------------------
        conf.setPopulationSize(20);

        // Create random initial population of Chromosomes.
        // -----------------------------------------------------------------
        Genotype population;
        // Now we initialize the population randomly, anyway (as an example only)!
        // If you want to load previous results from file, remove the next line!
        // -----------------------------------------------------------------------
        population = Genotype.randomInitialGenotype(conf);
        // Evolve the population. Since we don't know what the best answer
        // is going to be, we just evolve the max number of times.
        // ---------------------------------------------------------------
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            if (!uniqueChromosomes(population.getPopulation())) {
                throw new RuntimeException("Invalid state in generation " + i);
            }
            if (m_monitor != null) {
                population.evolve(m_monitor);
            } else {
                population.evolve();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total evolution time: " + (endTime - startTime) + " ms");

        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        double score = bestSolutionSoFar.getFitnessValue();
        System.out.println("Best Score: " + (score) + "");

        int service_SC1_id = (Integer) bestSolutionSoFar.getGene(0).getAllele();
        int service_SC2_id = (Integer) bestSolutionSoFar.getGene(1).getAllele();
        int service_SC3_id = (Integer) bestSolutionSoFar.getGene(2).getAllele();

        return new WebServiceWorkflow(sc1.getServices().get(service_SC1_id), sc2.getServices().get(service_SC2_id), sc3.getServices().get(service_SC3_id), score);


    }

    /**
     * @param a_pop the population to verify
     * @return true if all chromosomes in the populationa are unique
     * @author Klaus Meffert
     * @since 3.3.1
     */
    public static boolean uniqueChromosomes(Population a_pop) {
        // Check that all chromosomes are unique
        for (int i = 0; i < a_pop.size() - 1; i++) {
            IChromosome c = a_pop.getChromosome(i);
            for (int j = i + 1; j < a_pop.size(); j++) {
                IChromosome c2 = a_pop.getChromosome(j);
                if (c == c2) {
                    return false;
                }
            }
        }
        return true;
    }

}
