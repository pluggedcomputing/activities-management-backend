package cesar.nataniel.activitiesmanagementbackend.model;


import lombok.Data;

import java.util.List;

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

    public ResponsesStatistics calculateStatistics(List<Response> responses){
        ResponsesStatistics responsesStatistics = new ResponsesStatistics();

        for (Response r: responses){
            responsesStatistics.quantityAllAnswers++;
            if (r.getIsCorrect()){
                responsesStatistics.quantityCorrectsAnswers++;
            } else{
                responsesStatistics.quantityWrongsAnswers++;
            }
        }

        responsesStatistics.percentageCorrectsAnswers = calculatePercentage(responsesStatistics.getQuantityCorrectsAnswers(), responsesStatistics.getQuantityAllAnswers());
        responsesStatistics.percentageWrongsAnswers = calculatePercentage(responsesStatistics.getQuantityWrongsAnswers(), responsesStatistics.getQuantityAllAnswers());

        return responsesStatistics;
    }

}
