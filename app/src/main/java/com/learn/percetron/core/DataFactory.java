package com.learn.percetron.core;

import com.learn.percetron.core.Analyst;

import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;

/**
 * 晴、陰、雨、熱、微熱、微涼、涼、亮、時間 -------搭到、距離
 */
public class DataFactory {
    public static double[] input = new double[9];
    public static double[] result = new double[9];
    private static TrainingSet<SupervisedTrainingElement> data = new TrainingSet(9, 2);

    public static TrainingSet feedback(){
        data.addElement(new SupervisedTrainingElement(input, result));
        return data;
    }
    /**
     * Training Data
     * @return data Training data
     */
    public static TrainingSet trainingData() {
        if(data.isEmpty()) {
            data.addElement(new SupervisedTrainingElement(new double[] {1, 0, 0, 0, 0, 1, 0, 1, Analyst.time(25)}, new double[] {0, Analyst.distance(-2)}));
            data.addElement(new SupervisedTrainingElement(new double[] {0, 1, 0, 0, 0, 0, 1, 1, Analyst.time(20)}, new double[] {1, Analyst.distance(2)}));
            data.addElement(new SupervisedTrainingElement(new double[] {1, 0, 0, 0, 0, 1, 0, 1, Analyst.time(20)}, new double[] {0, Analyst.distance(-2)}));
            data.addElement(new SupervisedTrainingElement(new double[] {0, 1, 0, 0, 0, 0, 1, 1, Analyst.time(20)}, new double[] {1, Analyst.distance(0)}));
            data.addElement(new SupervisedTrainingElement(new double[] {0, 1, 0, 0, 0, 1, 0, 1, Analyst.time(23)}, new double[] {0, Analyst.distance(-2)}));
            data.addElement(new SupervisedTrainingElement(new double[] {0, 1, 0, 0, 0, 0, 1, 1, Analyst.time(21)}, new double[] {0, Analyst.distance(-2)}));
            data.addElement(new SupervisedTrainingElement(new double[] {0, 0, 1, 0, 0, 1, 0, 1, Analyst.time(21)}, new double[] {1, Analyst.distance(0)}));
            data.addElement(new SupervisedTrainingElement(new double[] {1, 0, 0, 0, 0, 1, 0, 1, Analyst.time(19)}, new double[] {0, Analyst.distance(-2)}));
            data.addElement(new SupervisedTrainingElement(new double[] {1, 0, 0, 0, 1, 0, 0, 1, Analyst.time(19)}, new double[] {1, Analyst.distance(0)}));
            data.addElement(new SupervisedTrainingElement(new double[] {1, 0, 0, 0, 1, 0, 0, 1, Analyst.time(20)}, new double[] {0, Analyst.distance(-1)}));
        }else {
            System.out.println("err-from'trainingData()'");
        }
        return data;
    }
    /**
     * Test Data
     * @return data Test data
     */
    public static TrainingSet testData() {
        TrainingSet data = new TrainingSet(9, 2);
        if(data.isEmpty()) {
            data.addElement(new SupervisedTrainingElement(new double[] {0, 0, 1, 0, 0, 1, 0, 0, Analyst.time(20)}, new double[] {1, Analyst.distance(0)}));
            data.addElement(new SupervisedTrainingElement(new double[] {1, 0, 0, 0, 0, 1, 0, 1, Analyst.time(21)}, new double[] {0, Analyst.distance(-1)}));
        }else {
            System.out.println("err-from'testData()'");
        }
        return data;
    }
}
