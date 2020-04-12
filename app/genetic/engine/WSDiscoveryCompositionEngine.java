package genetic.engine;

import genetic.fitnessFunctions.QosFitnessFunction;
import genetic.models.Service;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class WSDiscoveryCompositionEngine {

    public List<Service> composeServiceWorkflow (int minQoS, boolean a_doMonitor) throws Exception {
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
        FitnessFunction myFunc = new QosFitnessFunction();
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
        Gene[] sampleGenes = new Gene[4];
        sampleGenes[0] = new IntegerGene(conf, 0, 5); // SC1
        sampleGenes[1] = new IntegerGene(conf, 0, 2); // SC2
        sampleGenes[2] = new IntegerGene(conf, 0, 8); // SC3
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
        // Here we try to read in a previous run via XMLManager.readFile(..)
        // for demonstration purpose only!
        // -----------------------------------------------------------------
        Genotype population;
        try {
            Document doc = XMLManager.readFile(new File("JGAPExample32.xml"));
            population = XMLManager.getGenotypeFromDocument(conf, doc);
        } catch (UnsupportedRepresentationException uex) {
            // JGAP codebase might have changed between two consecutive runs.
            // --------------------------------------------------------------
            population = Genotype.randomInitialGenotype(conf);
        } catch (FileNotFoundException fex) {
            population = Genotype.randomInitialGenotype(conf);
        }
        // Now we initialize the population randomly, anyway (as an example only)!
        // If you want to load previous results from file, remove the next line!
        // -----------------------------------------------------------------------
        population = Genotype.randomInitialGenotype(conf);
        // Evolve the population. Since we don't know what the best answer
        // is going to be, we just evolve the max number of times.
        // ---------------------------------------------------------------
        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 500; i++) {
//            if (!uniqueChromosomes(population.getPopulation())) {
//                throw new RuntimeException("Invalid state in generation " + i);
//            }
//            if (m_monitor != null) {
//                population.evolve(m_monitor);
//            } else {
//                population.evolve();
//            }
//        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total evolution time: " + (endTime - startTime)
                + " ms");
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
        return null;
    }

}
