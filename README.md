kelp-core
=========
   
[**KeLP**][kelp-site]  is the Kernel-based Learning Platform developed in the [Semantic Analytics Group][sag-site] of the [University of Roma Tor Vergata][uniroma2-site].

This is the **KeLP** core module and it contains the abstract classes and interfaces to work with KeLP.

###**Core functionalities**

Core functionalities of KeLP comprise the interfaces and abstract classes needed to build and extend the library.
The main interfaces and abstract classes are:

* 	*Dataset*: it models the notion of a dataset as a collection of examples
* 	*Example*: it models a single example as a collection of representations
* 	*Representation*: it is the base type for a generic representation
* 	*Label*: it models the label
* 	*Kernel*: it models the notion of kernel
* 	*LearningAlgorithm*: it is the base type for a learning algorithm
* 	*PredictionFunction*: it is the base type for a function that compute a prediction

###**Learning Algorithms**

In this package different subclasses of the LearningAlgorithm interface can be found. The majority of the classes here is not an actual implementation, but they are used to build the hierarchy needed to instantiate the different kind of learning algorithms.
For example, *BinaryLearningAlgorithm* is responsible to model the notion of a learning algorithm that operates with two classes. *KernelMethod* instead is used to model the notion of learning algorithm based on Kernel functions (e.g. Support Vector Machines).


#####**Meta Algorithms**
Two implementations of meta learning algorithm are in kelp-core. These are the *OneVsAllLearningAlgorithm* and the *OneVsOneLearningAlgorithm*. Both model a multi-classification schema, respectively with a One-Vs-All and One-Vs-One strategy. They are based on a base binary learning algorithm and use it to derive a multi-classifier.

###**Prediction Functions**

The *PredictionFunction* interface model the notion of function used to make a prediction. Different classes are subtype of *PredictionFunction* depending on the role they have in classification or regression schemas. For example, *BinaryClassifier* extends a *Classifier* that is a prediction function used to derive discrete classifications.

###**Kernel functions**

*Kernel* is the base type for modeling a kernel function. Subclasses of kernel model different type of kernel functions available.

*	*DirectKernel*: it models a kernel that operate directly on a specific representation (e.g. a linear kernel or a tree kernel extends this class)
*	*KernelComposition*: it models a kernel function that operate on the result produced by another kernel function. For example, the polynomial or gaussian kernel are instances of this class.
*	*KernelCombination*: it models the basic combination of kernel function, in terms of weighted linear combination of multiple kernel functions.



[sag-site]: http://sag.art.uniroma2.it "SAG site"
[uniroma2-site]: http://www.uniroma2.it "University of Roma Tor Vergata"
[kelp-site]: http://sag.art.uniroma2.it/demo-software/kelp/ "KeLP website"
