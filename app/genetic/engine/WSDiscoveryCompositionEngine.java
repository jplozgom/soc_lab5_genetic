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
//        if (a_doMonitor) {
//            // Turn on monitoring/auditing of evolution progress.
//            // --------------------------------------------------
//            m_monitor = new EvolutionMonitor();
//            conf.setMonitor(m_monitor);
//        }
        // Now we need to tell the Configuration object how we want our
        // Chromosomes to be setup. We do that by actually creating a
        // sample Chromosome and then setting it on the Configuration
        // object. As mentioned earlier, we want our Chromosomes to each
        // have four genes, one for each of the coin types. We want the
        // values (alleles) of those genes to be integers, which represent
        // how many coins of that type we have. We therefore use the
        // IntegerGene class to represent each of the genes. That class
        // also lets us specify a lower and upper bound, which we set
        // to sensible values for each coin type.
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
        conf.setPopulationSize(10); // 5 * 3 * 8  ---- 5 options in SC1 x 3 options in SC2 x 8 options in SC3

        // Create random initial population of Chromosomes.
        // Here we try to read in a previous run via XMLManager.readFile(..)
        // for demonstration purpose only!
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
        System.out.println("Total evolution time: " + (endTime - startTime)
                + " ms");

        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        double score = bestSolutionSoFar.getFitnessValue();
        System.out.println("Best Score: " + (score) + "");

        int service_SC1_id = (Integer) bestSolutionSoFar.getGene(0).getAllele();
        int service_SC2_id = (Integer) bestSolutionSoFar.getGene(1).getAllele();
        int service_SC3_id = (Integer) bestSolutionSoFar.getGene(2).getAllele();

        return new WebServiceWorkflow(sc1.getServices().get(service_SC1_id), sc2.getServices().get(service_SC2_id), sc3.getServices().get(service_SC3_id), score);

        // Save progress to file. A new run of this example will then be able to
        // resume where it stopped before! --> this is completely optional.
        // ---------------------------------------------------------------------

//        // Represent Genotype as tree with elements Chromomes and Genes.
//        // -------------------------------------------------------------
//        DataTreeBuilder builder = DataTreeBuilder.getInstance();
//        IDataCreators doc2 = builder.representGenotypeAsDocument(population);
//        // create XML document from generated tree
//        XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
//        Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
//        XMLManager.writeFile(xmlDoc, new File("JGAPExample26.xml"));
//        // Display the best solution we found.
//        // -----------------------------------
//        IChromosome bestSolutionSoFar = population.getFittestChromosome();
//        double v1 = bestSolutionSoFar.getFitnessValue();
//        System.out.println("The best solution has a fitness value of " +
//                bestSolutionSoFar.getFitnessValue());
//        bestSolutionSoFar.setFitnessValueDirectly(-1);
//        System.out.println("It contains the following: ");
//        System.out.println("\t" +
//                MinimizingMakeChangeFitnessFunction.
//                        getNumberOfCoinsAtGene(
//                                bestSolutionSoFar, 0) + " quarters.");
//        System.out.println("\t" +
//                MinimizingMakeChangeFitnessFunction.
//                        getNumberOfCoinsAtGene(
//                                bestSolutionSoFar, 1) + " dimes.");
//        System.out.println("\t" +
//                MinimizingMakeChangeFitnessFunction.
//                        getNumberOfCoinsAtGene(
//                                bestSolutionSoFar, 2) + " nickels.");
//        System.out.println("\t" +
//                MinimizingMakeChangeFitnessFunction.
//                        getNumberOfCoinsAtGene(
//                                bestSolutionSoFar, 3) + " pennies.");
//        System.out.println("For a total of " +
//                MinimizingMakeChangeFitnessFunction.amountOfChange(
//                        bestSolutionSoFar) + " cents in " +
//                MinimizingMakeChangeFitnessFunction.
//                        getTotalNumberOfCoins(
//                                bestSolutionSoFar) + " coins.");
//        return null;
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
