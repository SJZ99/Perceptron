package com.learn.percetron.core;

import org.neuroph.core.Layer;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.comp.BiasNeuron;
import org.neuroph.nnet.comp.InputNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.LayerFactory;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuralNetworkType;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.NguyenWidrowRandomizer;

public class NeuralNetwork extends org.neuroph.core.NeuralNetwork{
    private static final long serialVersionUID = 1L;
    private Layer last;

    public NeuralNetwork() {
        createNetwork();
    }

    private void createNetwork() {
        this.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);
        createInputLayer(9);
        for(int i = 0; i < 5; i++) {
            createSigmoidMiddleLayer(9);
        }
        createOutputLayer(2);

        NeuralNetworkFactory.setDefaultIO(this);
        BackPropagation bp = new BackPropagation();
        bp.setMaxError(0.063);
//        bp.setMaxIterations(6000);
        bp.setLearningRate(0.03);
        this.setLearningRule(bp);
        this.randomizeWeights(new NguyenWidrowRandomizer(-0.7, 0.7));
    }
    /**
     * create input layer
     * @param neuronCount neuron count
     */
    private void createInputLayer(int neuronCount) {
        NeuronProperties np = new NeuronProperties();
        np.setProperty("neuronType", InputNeuron.class);
        if(last == null) {
            last = LayerFactory.createLayer(neuronCount, np);
            last.addNeuron(new BiasNeuron());
            this.addLayer(last);
        }else {
            System.out.println("Should create input layer first!");
        }
    }
    /**
     * create sigmoid layer in middle
     * @param neuronCount neuron count
     */
    private void createSigmoidMiddleLayer(int neuronCount) {
        NeuronProperties np = new NeuronProperties();
        np.setProperty("transferFunctionType", TransferFunctionType.SIGMOID);
        Layer sigmoid = LayerFactory.createLayer(neuronCount, np);
        sigmoid.addNeuron(new BiasNeuron());
        this.addLayer(sigmoid);
        if(last != null) {
            ConnectionFactory.fullConnect(last, sigmoid);
        }
        last = sigmoid;
    }
    /**
     * create output layer
     * @param neuronCount neuron count
     */
    private void createOutputLayer(int neuronCount) {
        NeuronProperties np = new NeuronProperties();
        np.setProperty("transferFunctionType", TransferFunctionType.LINEAR);
        Layer output = LayerFactory.createLayer(neuronCount, np);
        this.addLayer(output);
        if(last != null) {
            ConnectionFactory.fullConnect(last, output);
        }
        last = output;
    }

    /**
     * when output is NaN, retrain.
     * @param data
     * @return
     */
    public boolean whenNaNLearn(TrainingSet data){
        boolean isRetrain = false;
        setInput(0, 0, 0, 0, 0, 0, 0, 0, 0);
        calculate();
        while(Double.isNaN(getOutput()[0])) {
            randomizeWeights(new NguyenWidrowRandomizer(-0.7, 0.7));
            learnInNewThread(data);
            setInput(1, 0, 0, 0, 0, 0, 0, 0, 0);
            calculate();
            isRetrain = true;
        }
        return isRetrain;
    }

    public boolean isNaN(){
        setInput(1, 0, 0, 0, 0, 0, 0, 0, 0);
        calculate();
        if(Double.isNaN(getOutput()[0])){
            return true;
        }
        return false;
    }


}
