package genetic.engine;

import genetic.models.Service;

/** The type Web service workflow. */
public class WebServiceWorkflow {

    private Service sc1Service;
    private Service sc2Service;
    private Service sc3Service;
    private float score;

  /**
   * Instantiates a new Web service workflow.
   *
   * @param sc1Service the sc 1 service
   * @param sc2Service the sc 2 service
   * @param sc3Service the sc 3 service
   * @param score the score
   */
    public WebServiceWorkflow(
      Service sc1Service, Service sc2Service, Service sc3Service, float score) {
        this.sc1Service = sc1Service;
        this.sc2Service = sc2Service;
        this.sc3Service = sc3Service;
        this.score = score;
    }

  /**
   * Gets sc 1 service.
   *
   * @return the sc 1 service
   */
  public Service getSc1Service() {
        return sc1Service;
    }

  /**
   * Gets sc 2 service.
   *
   * @return the sc 2 service
   */
  public Service getSc2Service() {
        return sc2Service;
    }

  /**
   * Gets sc 3 service.
   *
   * @return the sc 3 service
   */
  public Service getSc3Service() {
        return sc3Service;
    }

  /**
   * Gets score.
   *
   * @return the score
   */
  public float getScore() {
        return score;
    }
}
