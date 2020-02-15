# GRL-Agent_modeling-Quality-Metrics
A metric suite for assessing the quality of agents modeling in GRL models

*Important notes:

    (1) This tool works on GRL models created with jUCMNav tool. 
     Therefore, jUCMNav plugin needs to be installed before installing this plugin
  
    (2) This plugin is developed and tested on Eclipse committers 6-2019

    (3) This plugin is a beta version
     
    (4) Source code is located in :
     
        org.eclipse.jucmnav.grl.QAMM/src/org/eclipse/jucmnav/grl/QAMM



*Installation Instructions:

First way:

    Go to Eclipse Help Menu -> Install New Software -> Add
                      
                Name:QAMM
                Location: http://softwareengineeringresearch.net/QAMM/

Second way:

    (1) Download GSDetector folder from this repository to your machine

    (2) Expand the downloaded RAR 
                    
    (3) Go to Eclipse Help Menu -> Install New Software -> Add -> Local -> Location of the expanded RAR
                           
                      Name:QAMM
                      


*Usage Guide
      
      (1) Open the GRL model to be analyized
      
      (2) Go to Window Menu -> Preferences -> jUCMNav -> Select GRL Agent Modeling Quality Metrics ->
            Select metrics to compute and close
     
      (3) Go to the main editor and click on the histogram button. This button can be found in:
            (1) The jUCMNav labeled as "GRL Agent Modeling Quality Metrics"
            (2) In the toolbar
            (3) Dropdown menu associated with jUCMNav files in the Navigator
