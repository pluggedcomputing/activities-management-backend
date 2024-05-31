package cesar.nataniel.activitiesmanagementbackend.model;

import lombok.Data;

import java.text.DecimalFormat;
import java.util.List;

@Data
public class UserStatistics {

    private int quantityAllAnswers = 0;
    private int quantityCorrectsAnswers = 0;
    private int quantityWrongsAnswers = 0;
    private double percentageCorrectsAnswers;
    private double percentageWrongsAnswers;

    private double calculatePercentage(int quantityAnswers, int quantityAllAnswers) {
        if (quantityAllAnswers != 0) {
            return ((double) quantityAnswers / quantityAllAnswers) * 100;
        } else {
            return 0.0;
        }
    }

    public UserStatistics calculateStatistics (List<Response> responses){
        UserStatistics userStatistics = new UserStatistics();

        for (Response r: responses){
            userStatistics.quantityAllAnswers++;
            if (r.getIsCorrect()){
                userStatistics.quantityCorrectsAnswers++;
            } else{
                userStatistics.quantityWrongsAnswers++;
            }
        }

        userStatistics.percentageCorrectsAnswers = calculatePercentage(userStatistics.getQuantityCorrectsAnswers(), userStatistics.getQuantityAllAnswers());
        userStatistics.percentageWrongsAnswers = calculatePercentage(userStatistics.getQuantityWrongsAnswers(), userStatistics.getQuantityAllAnswers());

        return userStatistics;
    }
}
