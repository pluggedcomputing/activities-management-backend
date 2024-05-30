package cesar.nataniel.activitiesmanagementbackend.model;


import lombok.Data;

@Data
public class ResponsesStatistics {

    private int quantityAllAnswers;

    private int quantityCorrectsAnswers;

    private int quantityWrongsAnswers;

    private double percentageCorrectsAnswers;

    private double percentageWrongsAnswers;


    private double calculatePercentage(int quantityAnswers, int quantityAllAnswers) {
        if (quantityAllAnswers != 0) {
            return ((double) quantityAnswers / quantityAllAnswers) * 100;
        } else {
            return 0.0;
        }
    }

    public void calculatePercentageCorrectsAnswers() {
        this.percentageCorrectsAnswers = calculatePercentage(this.quantityCorrectsAnswers, this.quantityAllAnswers);

    }

    public void calculatePercentageWrongsAnswers() {
        this.percentageWrongsAnswers = calculatePercentage(this.quantityWrongsAnswers, this.quantityAllAnswers);
    }


    public void addCorrectAnswers() {
        this.quantityCorrectsAnswers++;
    }

    public void addWrongAnswers() {
        this.quantityWrongsAnswers++;
    }

    public void addAnswer() {
        this.quantityAllAnswers++;
    }
}
